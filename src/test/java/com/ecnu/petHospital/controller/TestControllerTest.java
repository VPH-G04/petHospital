package com.ecnu.petHospital.controller;

import com.alibaba.fastjson.JSON;
import com.ecnu.petHospital.param.AnswerSheet;
import com.ecnu.petHospital.param.PageParam;
import com.ecnu.petHospital.param.TestParam;
import com.ecnu.petHospital.result.CommonResult;
import com.ecnu.petHospital.result.Result;
import com.ecnu.petHospital.service.TestService;
import com.ecnu.petHospital.session.UserSessionInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TestService testService;

    @Test
    @DisplayName("成功获取测试列表")
    void getTestList() throws Exception {
        PageParam pageParam = PageParam.builder()
                .pageNum(1)
                .pageSize(10)
                .build();

        MvcResult result = mockMvc.perform(
                post("/test/getTestList")
                        .content(JSON.toJSONBytes(pageParam))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andReturn();

        verify(testService,times(1)).getTestList(pageParam);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());
    }

    @Test
    @DisplayName("成功创建测试")
    void createTest() throws Exception {
        TestParam testParam = TestParam.builder()
                .name("test1")
                .duration(120)
                .startTime("start_time")
                .endTime("end_time")
                .build();

        MvcResult result = mockMvc.perform(
                post("/test/createTest")
                        .content(JSON.toJSONBytes(testParam))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andReturn();

        verify(testService,times(1)).createTest(testParam);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());

    }

    @Test
    @DisplayName("成功根据id删除测试")
    void deleteTest() throws Exception {
        Integer testId = 1;

        MvcResult result = mockMvc.perform(
                post("/test/deleteTest")
                        .param("testId","1")
        ).andReturn();

        verify(testService,times(1)).deleteTest(testId);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());
    }

    @Test
    @DisplayName("成功根据id获取测试")
    void getTest() throws Exception {
        MockHttpSession session = new MockHttpSession();
        UserSessionInfo userSessionInfo = UserSessionInfo.builder()
                .id(1)
                .username("lionel")
                .email("email")
                .admin(true).build();
        session.setAttribute("userSessionInfo", userSessionInfo);
        Integer testId = 1;

        MvcResult result = mockMvc.perform(
                post("/test/getTest")
                .param("testId", "1")
                .session(session)
        ).andReturn();

        verify(testService, times(1)).getTest(testId,userSessionInfo.getId());
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());

    }

    @Test
    @DisplayName("成功创建测试记录")
    void doTest() throws Exception {
        MockHttpSession session = new MockHttpSession();
        UserSessionInfo userSessionInfo = UserSessionInfo.builder()
                .id(1)
                .username("lionel")
                .email("email")
                .admin(true).build();
        session.setAttribute("userSessionInfo", userSessionInfo);
        AnswerSheet answerSheet = AnswerSheet.builder()
                .testId(1)
                .userId(1)
                .score(99)
                .build();

        MvcResult result = mockMvc.perform(
                post("/test/doTest")
                        .content(JSON.toJSONBytes(answerSheet))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .session(session)
        ).andReturn();

        verify(testService, times(1)).doTest(answerSheet);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());
    }

    @Test
    @DisplayName("成功根据id获取测试记录信息")
    void getTestLog() throws Exception {
        Integer userId = 1;
        Integer testId = 1;

        MvcResult result = mockMvc.perform(
                post("/test/getTestLog")
                .param("userId","1")
                .param("testId","1")
        ).andReturn();

        verify(testService, times(1)).getTestLog(testId,userId);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());

    }

    @Test
    @DisplayName("成功获取测试记录列表")
    void getTestLogList() throws Exception {
        PageParam pageParam = PageParam.builder()
                .pageNum(1)
                .pageSize(10).build();
        Integer testId = 1;

        MvcResult result = mockMvc.perform(
                post("/test/getTestLogList?testId=1")
                        .content(JSON.toJSONString(pageParam))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andReturn();

        verify(testService, times(1)).getTestLogList(testId,pageParam);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());
    }

    @Test
    @DisplayName("成功根据id删除病例")
    void deleteTestLog() throws Exception {
        Integer id = 1;


        MvcResult result = mockMvc.perform(
                post("/test/deleteTestLog")
                        .param("id", "1")
        ).andReturn();

        verify(testService, times(1)).deleteTestLog(id);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());

    }
}