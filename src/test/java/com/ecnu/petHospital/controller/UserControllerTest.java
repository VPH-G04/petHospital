package com.ecnu.petHospital.controller;

import com.alibaba.fastjson.JSON;

import com.ecnu.petHospital.entity.User;
import com.ecnu.petHospital.param.PageParam;
import com.ecnu.petHospital.result.CommonResult;
import com.ecnu.petHospital.result.Result;
import com.ecnu.petHospital.service.UserService;
import com.ecnu.petHospital.session.UserSessionInfo;
import com.ecnu.petHospital.vo.UserVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import springfox.documentation.spring.web.json.Json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("成功修改用户信息")
    void when_user_update_userInfo_should_dispatcher_to_service_and_return_success_result() throws Exception {

        UserVO userVO = UserVO.builder()
                .id(1)
                .email("10175101121@stu.ecnu.edu.cn")
                .username("newUsername")
                .build();


        MvcResult result = mockMvc.perform(
                put("/user/update")
                        .content(JSON.toJSONString(userVO))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andReturn();

        verify(userService,times(1)).updateInfo(userVO);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());
    }

    @Test
    @DisplayName("成功修改密码")
    void when_user_update_password_should_dispatcher_to_service_and_return_success_result() throws Exception {
        MockHttpSession session = new MockHttpSession();
        UserSessionInfo userSessionInfo = new UserSessionInfo(1,"lionel","email",false);
        session.setAttribute("userSessionInfo", userSessionInfo);

        MvcResult result = mockMvc.perform(
                put("/user/updatePassword")
                        .param("oldPassword", "123")
                        .param("newPassword","321")
                        .session(session)
        ).andReturn();

        verify(userService,times(1)).updatePassword(userSessionInfo,"123","321");
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());

    }


    @Test
    @DisplayName("管理员成功删除用户")
    void when_an_administrator_request_to_delete_an_user_should_dispatcher_to_service_and_return_success_result() throws Exception {
        MockHttpSession session = new MockHttpSession();
        UserSessionInfo userSessionInfo = new UserSessionInfo(1,"lionel","email",true);
        session.setAttribute("userSessionInfo", userSessionInfo);

        MvcResult result = mockMvc.perform(
                delete("/user/delete")
                        .param("userId","2")
                        .session(session)
        ).andReturn();

        verify(userService,times(1)).deleteUserById(2);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());
    }

    @Test
    @DisplayName("管理员成功获取用户列表")
    void when_an_administrator_request_to_list_user_should_dispatcher_to_service_and_return_success_result() throws Exception {

        MockHttpSession session = new MockHttpSession();
        UserSessionInfo userSessionInfo = UserSessionInfo.builder()
                .id(1)
                .username("lionel")
                .email("email")
                .admin(true).build();
        session.setAttribute("userSessionInfo", userSessionInfo);
        PageParam pageParam = PageParam.builder()
                .pageNum(1)
                .pageSize(10).build();

        MvcResult result = mockMvc.perform(
                get("/user/list")
                        .content(JSON.toJSONString(pageParam))
                        .session(session)
        ).andReturn();

        verify(userService, times(1)).getUserList(pageParam);

        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
            assertEquals(CommonResult.SUCCESS, res.getCode());
    }

    @Test
    @DisplayName("管理员成功更新用户信息")
    void when_an_administrator_request_to_update_userInfo_should_return_denied_result() throws Exception {

        MockHttpSession session = new MockHttpSession();
        UserSessionInfo userSessionInfo = UserSessionInfo.builder()
                .id(1)
                .username("lionel")
                .email("email")
                .admin(true).build();
        session.setAttribute("userSessionInfo", userSessionInfo);

        User user = User.builder()
                .id(1)
                .email("email")
                .password("123")
                .admin(true).build();

        MvcResult result = mockMvc.perform(
                put("/user/adminUpdateUser")
                        .content(JSON.toJSONString(user))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .session(session)
        ).andReturn();

        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());
    }

    @Test
    @DisplayName("成功根据id获取用户信息")
    void when_request_to_get_user_by_id_should_dispatcher_service_to_return_success_result() throws Exception{

        when(userService.getUserById(1)).thenReturn(new UserVO(1,"lionel","emial"));
        MvcResult result = mockMvc.perform(
                post("/user/get")
                        .param("id", "1")
        ).andReturn();

        verify(userService).getUserById(1);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());
    }

}