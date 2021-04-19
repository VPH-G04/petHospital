package com.ecnu.petHospital.service;

import com.ecnu.petHospital.entity.User;
import com.ecnu.petHospital.param.LoginParam;
import com.ecnu.petHospital.param.PageParam;
import com.ecnu.petHospital.param.RegisterParam;
import com.ecnu.petHospital.session.UserSessionInfo;
import com.ecnu.petHospital.vo.UserVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

//@Service
public interface UserService {

    //登陆
    User login(LoginParam loginParam);

    //更新用户信息
    Boolean updateInfo(UserVO userVO);

    //获取所有用户
    PageInfo<User> getUserList(PageParam pageParam);


    //根据用户名获取用户
    UserVO getUserByUsername(String username);

    //根据用户Id获取用户
    UserVO getUserById(Integer id);

    //注册新用户
    int register(RegisterParam registerParam);

    //更新用户密码
    int updatePassword(UserSessionInfo userSessionInfo, String oldPassword, String newPassword);

    //删除用户
    int deleteUserById(Integer id);

    //更新用户信息
    Boolean adminUpdateInfo(User user);

}
