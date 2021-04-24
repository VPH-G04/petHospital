package com.ecnu.petHospital.service;

import com.ecnu.petHospital.dao.UserMapper;
import com.ecnu.petHospital.entity.User;
import com.ecnu.petHospital.exception.IncorrectUsernameOrPasswordException;
import com.ecnu.petHospital.exception.UsernameAlreadyExistException;
import com.ecnu.petHospital.exception.UsernameNotExistsException;
import com.ecnu.petHospital.param.LoginParam;
import com.ecnu.petHospital.param.PageParam;
import com.ecnu.petHospital.param.RegisterParam;
import com.ecnu.petHospital.service.impl.UserServiceImp;
import com.ecnu.petHospital.session.UserSessionInfo;
import com.ecnu.petHospital.vo.UserVO;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImpTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImp userService;

    @Test
    @DisplayName("成功登录")
    void when_service_do_login_with_matched_name_and_password_then_dispatch_mapper_to_return_user(){
        User user = new User(1,"lionel","123","email",true);
        LoginParam loginParam = new LoginParam("email","123");

        when(userMapper.selectOne(new User().setEmail(loginParam.getEmail()).setPassword(loginParam.getPassword()))).thenReturn(user);
        userService.login(loginParam);
        verify(userMapper,times(1)).selectOne(new User().setEmail(loginParam.getEmail()).setPassword(loginParam.getPassword()));
    }

}