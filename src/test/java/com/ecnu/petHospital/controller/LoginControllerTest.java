package com.ecnu.petHospital.controller;

import com.alibaba.fastjson.JSON;
import com.ecnu.petHospital.entity.User;
import com.ecnu.petHospital.enums.CustomExceptionType;
import com.ecnu.petHospital.exception.CustomException;
import com.ecnu.petHospital.exception.IncorrectUsernameOrPasswordException;
import com.ecnu.petHospital.exception.UsernameAlreadyExistException;
import com.ecnu.petHospital.exception.UsernameNotExistsException;
import com.ecnu.petHospital.param.LoginParam;
import com.ecnu.petHospital.param.RegisterParam;
import com.ecnu.petHospital.result.CommonResult;
import com.ecnu.petHospital.result.Result;
import com.ecnu.petHospital.result.UserResult;
import com.ecnu.petHospital.service.UserService;
import com.ecnu.petHospital.session.UserSessionInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;


    @Test
    @DisplayName("成功登录")
    void when_matched_user_request_to_login_then_dispatch_to_service_and_return_success() throws Exception{
        LoginParam loginParam = new LoginParam("1103683657@qq.com","123123");
        when(userService.login(loginParam)).thenReturn(new User(1,"lionel","123123","1103683657@qq.com",false));
        MvcResult result = mockMvc.perform(
                post("/login")
                        .content(JSON.toJSONString(loginParam))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andReturn();

        verify(userService,times(1)).login(loginParam);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());
    }

    @Test
    @DisplayName("未注册用户登录或用户名密码错误返回无效用户名或密码")
    void when_invalid_username_request_to_login_then_dispatch_to_service_and_return_exception()throws Exception{

        LoginParam loginParam = new LoginParam("1103683657@qq.com","123123");
        when(userService.login(loginParam)).thenThrow(new CustomException(CustomExceptionType.INVALID_EMAIL_OR_PASSWORD));
        MvcResult result = mockMvc.perform(
                post("/login")
                        .content(JSON.toJSONString(loginParam))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andReturn();

        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(UserResult.INVALID_USERNAME_OR_PASSWORD,res.getCode());
    }


    @Test
    @DisplayName("成功注册")
    void when_unregistered_user_request_to_regist_then_dispatch_to_service_and_return_success() throws Exception {

        RegisterParam registerParam = new RegisterParam("newuser","123123","123123@qq.com");

        MvcResult result = mockMvc.perform(
                post("/register")
                .content(JSON.toJSONString(registerParam))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andReturn();

        verify(userService,times(1)).register(registerParam);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());
    }

    @Test
    @DisplayName("重复注册返回邮箱已存在")
    void when_registered_user_request_to_register_then_return_user_already_exist() throws Exception {

        RegisterParam registerParam = new RegisterParam("newuser","123123","123123@qq.com");
        when(userService.register(registerParam)).thenThrow(new CustomException(CustomExceptionType.EMAIL_ALREADY_EXISTS));
        MvcResult result = mockMvc.perform(
                post("/register")
                        .content(JSON.toJSONString(registerParam))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andReturn();

        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(UserResult.USERNAME_ALREADY_EXISTS, res.getCode());
    }

    @Test
    @DisplayName("成功登出")
    void when_request_to_logout_then_return_success() throws Exception {
        MockHttpSession session = new MockHttpSession();
        UserSessionInfo userSessionInfo = new UserSessionInfo(1,"lionel","email",false);
        session.setAttribute("userSessionInfo",userSessionInfo);

        MvcResult result = mockMvc.perform(
                post("/logout")
                    .session(session)
        ).andReturn();
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());
    }

}