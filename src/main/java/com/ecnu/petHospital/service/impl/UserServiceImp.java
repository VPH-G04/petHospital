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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public Boolean updateInfo(UserVO userVO) {
        User user = userMapper.selectByPrimaryKey(userVO.getId());
        Optional.ofNullable(user).orElseThrow(()->new CustomException(CustomExceptionType.USER_NOT_EXISTS));
        System.out.println(user);
        System.out.println(userVO);

        User emailUser=userMapper.selectOne(new User().setEmail(userVO.getEmail()));
        System.out.println(emailUser);
        if(emailUser!=user && emailUser!=null)
            throw new CustomException(CustomExceptionType.EMAIL_ALREADY_EXISTS);

        user.setEmail(userVO.getEmail()).setUsername(userVO.getUsername());
        userMapper.updateByPrimaryKeySelective(user);
        return true;

    }

    @Override
    public UserVO getUserById(Integer id) {
        return userMapper.getUserById(id);
    }

    @Override
    @Transactional
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
    @Transactional
    public int updatePassword(UserSessionInfo userSessionInfo, String oldPassword, String newPassword) {
        User user = userMapper.selectByPrimaryKey(userSessionInfo.getId());
        //System.out.println(oldPassword+"   "+user.getPassword());
        if(!oldPassword.equals(user.getPassword()))
            throw new CustomException(CustomExceptionType.INCORRECT_EMAIL_OR_PASSWORD,"密码错误");
        return userMapper.updatePasswordById(userSessionInfo.getId(),newPassword);
    }

    @Override
    public PageInfo<User> getUserList(PageParam pageParam) {
        PageHelper.startPage(pageParam.getPageNum(),pageParam.getPageSize());

        List<User> userList = userMapper.selectAll();
        return new PageInfo<>(userList);
    }

    @Override
    @Transactional
    public int deleteUserById(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        if(user.getAdmin())
            throw new CustomException(CustomExceptionType.ACCESSED_DENIED,"无法删除其他管理员账号");
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional
    public Boolean adminUpdateInfo(User user) {
        User user1 = userMapper.selectByPrimaryKey(user.getId());
        System.out.println(user1);
        Optional.ofNullable(user1).orElseThrow(()->new CustomException(CustomExceptionType.USER_NOT_EXISTS));
        System.out.println("user:"+user);
        System.out.println("user1:"+user1);


        User emailUser=userMapper.selectOne(new User().setEmail(user.getEmail()));
        System.out.println("emailUser:"+emailUser);

        if(emailUser!= null && !emailUser.getId().equals(user1.getId()))
            throw new CustomException(CustomExceptionType.EMAIL_ALREADY_EXISTS);
        user1.setEmail(user.getEmail()).setUsername(user.getUsername()).setPassword(user.getPassword());
        userMapper.updateByPrimaryKeySelective(user);
        return true;

    }
}
