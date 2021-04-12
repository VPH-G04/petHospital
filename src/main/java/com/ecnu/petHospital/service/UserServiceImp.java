package com.ecnu.petHospital.service;

import com.ecnu.petHospital.entity.User;
import com.ecnu.petHospital.paging.PageParam;
import com.ecnu.petHospital.dao.UserMapper;
import com.ecnu.petHospital.exception.IncorrectUsernameOrPasswordException;
import com.ecnu.petHospital.exception.UsernameAlreadyExistException;
import com.ecnu.petHospital.exception.UsernameNotExistsException;
import com.ecnu.petHospital.param.LoginParam;
import com.ecnu.petHospital.param.RegisterParam;
import com.ecnu.petHospital.session.UserSessionInfo;
import com.ecnu.petHospital.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class    UserServiceImp implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User login(LoginParam loginParam) {
        String username = loginParam.getUsername();
        String password = loginParam.getPassword();
        User user = userMapper.getUserByUsername(username);
        if(user == null)
            throw new UsernameNotExistsException();
        if(!password.equals(user.getPassword()))
            throw new IncorrectUsernameOrPasswordException();
        return user;
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

        if(userMapper.getUserByUsername(username) != null)
            throw new UsernameAlreadyExistException();

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setAdmin(false);


        System.out.println(user);
        return userMapper.insertUser(user);
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
    public List<User> getUserList(PageParam pageParam) {
        return userMapper.getUserList(pageParam);
    }

    @Override
    public int deleteUserById(Integer id) {
        return userMapper.deleteUserById(id);
    }

    @Override
    public int getCountOfUser(){return userMapper.getCountOfUser();}

}
