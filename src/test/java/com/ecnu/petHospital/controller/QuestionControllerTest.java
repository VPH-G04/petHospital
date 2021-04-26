package com.ecnu.petHospital.controller;

import com.alibaba.fastjson.JSON;
import com.ecnu.petHospital.param.PageParam;
import com.ecnu.petHospital.param.QuestionParam;
import com.ecnu.petHospital.result.CommonResult;
import com.ecnu.petHospital.result.Result;
import com.ecnu.petHospital.service.QuestionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService questionService;

    @Test
    @DisplayName("成功获取问题列表")
    void getQuestionList() throws Exception {
        Integer diseaseId = 1;
        PageParam pageParam = PageParam.builder()
                .pageNum(1)
                .pageSize(10)
                .build();

        MvcResult result = mockMvc.perform(
                post("/question/getQuestionList/?diseaseId=1")
                .content(JSON.toJSONBytes(pageParam))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andReturn();

        verify(questionService, times(1)).getQuestionList(diseaseId,pageParam);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());


    }

    @Test
    @DisplayName("成功根据id获取问题信息")
    void getQuestion() throws Exception {
        Integer questionId = 1;

        MvcResult result = mockMvc.perform(
                post("/question/getQuestion")
                .param("questionId", "1")
        ).andReturn();

        verify(questionService, times(1)).getQuestion(questionId);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());

    }

    @Test
    @DisplayName("成功创建问题")
    void createQuestion() throws Exception {
        QuestionParam questionParam = QuestionParam.builder()
                .title("q1")
                .a("a")
                .b("b")
                .c("c")
                .d("d")
                .answer("answer")
                .build();
        MvcResult result = mockMvc.perform(
                post("/question/createQuestion")
                        .content(JSON.toJSONBytes(questionParam))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andReturn();

        verify(questionService, times(1)).createQuestion(questionParam);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());

    }

    @Test
    @DisplayName("成功根据id删除问题")
    void deleteQuestion() throws Exception {
        Integer questionId = 1;

        MvcResult result = mockMvc.perform(
                post("/question/deleteQuestion/")
                        .param("questionId","1")
        ).andReturn();

        verify(questionService, times(1)).deleteQuestion(questionId);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());
    }
}