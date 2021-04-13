package com.ecnu.petHospital.service.impl;

import com.ecnu.petHospital.entity.User;
import com.ecnu.petHospital.enums.CustomExceptionType;
import com.ecnu.petHospital.exception.CustomException;
import com.ecnu.petHospital.dao.UserMapper;
import com.ecnu.petHospital.exception.IncorrectUsernameOrPasswordException;
import com.ecnu.petHospital.exception.UsernameAlreadyExistException;
import com.ecnu.petHospital.exception.UsernameNotExistsException;
import com.ecnu.petHospital.param.LoginParam;
import com.ecnu.petHospital.param.PageParam;
import com.ecnu.petHospital.param.RegisterParam;
import com.ecnu.petHospital.service.UserService;
import com.ecnu.petHospital.session.UserSessionInfo;
import com.ecnu.petHospital.vo.UserVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class    UserServiceImp implements UserService {

    @Resource
    UserMapper userMapper;

    @Override
    public User login(LoginParam loginParam) {
        User user1 = userMapper.selectOne(new User().setEmail(loginParam.getEmail()).setPassword(loginParam.getPassword()));
        Optional.ofNullable(user1).orElseThrow(()->new CustomException(CustomExceptionType.INCORRECT_EMAIL_OR_PASSWORD));

//        if(user == null)
//            throw new UsernameNotExistsException();
//        if(!password.equals(user.getPassword()))
//            throw new IncorrectUsernameOrPasswordException();
        return user1;
    }

    @Override
    public UserVO getUserByUsername(String username) {
        User user = userMapper.getUserByUsername(username);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user,userVO);
        return userVO;
    }

    @Override
    public UserVO getUserById(Integer id) {
        return userMapper.getUserById(id);
    }

    @Override
    public int register(RegisterParam registerParam) {

        String username = registerParam.getUsername();
        String password = registerParam.getPassword();
        String email = registerParam.getEmail();
        System.out.println(email);

        User user =new User().setEmail(email).setPassword(password).setUsername(username).setAdmin(false);

        User user1 = userMapper.selectOne(new User().setEmail(email));
        System.out.println(user1);
        Optional.ofNullable(user1).ifPresent(u->{throw new CustomException(CustomExceptionType.EMAIL_ALREADY_EXISTS);});

        return userMapper.insert(user);
//        return 1;
    }


    @Override
    public int updatePassword(UserSessionInfo userSessionInfo, String oldPassword, String newPassword) {
        User user = userMapper.getUserByUsername(userSessionInfo.getUsername());
        //System.out.println(oldPassword+"   "+user.getPassword());
        if(!oldPassword.equals(user.getPassword()))
            throw new IncorrectUsernameOrPasswordException();
        return userMapper.updatePasswordById(userSessionInfo.getId(),newPassword);
    }

    @Override
    public int updateUsername(Integer id, String username) {
        if(userMapper.getUserByUsername(username)!=null)
            throw  new UsernameAlreadyExistException();
        return userMapper.updateUsernameById(id,username);
    }

    @Override
    public int updateTelephone(Integer id, String telephone) {
        return userMapper.updateTelephoneById(id,telephone);
    }

    @Override
    public int updateEmail(Integer id, String email) {
        return userMapper.updateEmailById(id,email);
    }

    @Override
    public int updatePermission(Integer id, Integer admin) {
        return userMapper.updatePermission(id,admin);
    }

    @Override
    public PageInfo<User> getUserList(PageParam pageParam) {
        PageHelper.startPage(pageParam.getPageNum(),pageParam.getPageSize());

        List<User> userList = userMapper.getUserList();
        return new PageInfo<>(userList);
    }

    @Override
    public int deleteUserById(Integer id) {
        return userMapper.deleteUserById(id);
    }

    @Override
    public int getCountOfUser(){return userMapper.getCountOfUser();}

}
