package com.ecnu.petHospital.controller;

import com.alibaba.fastjson.JSON;
import com.ecnu.petHospital.param.PageParam;
import com.ecnu.petHospital.param.PaperParam;
import com.ecnu.petHospital.result.CommonResult;
import com.ecnu.petHospital.result.Result;
import com.ecnu.petHospital.service.TestPaperService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class TestPaperControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TestPaperService testPaperService;

    @Test
    @DisplayName("成功获取试卷列表")
    void getTestPaperList() throws Exception {
        PageParam pageParam = PageParam.builder()
                .pageNum(1)
                .pageSize(10)
                .build();

        MvcResult result = mockMvc.perform(
                post("/paper/getPaperList")
                .content(JSON.toJSONBytes(pageParam))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andReturn();

        verify(testPaperService).getTestPaperList(pageParam);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());
    }

    @Test
    @DisplayName("成功获取所有试卷")
    void getAllPaper() throws Exception {
        MvcResult result = mockMvc.perform(
                post("/paper/getAllPaper")
        ).andReturn();

        verify(testPaperService).getAllPaper();
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());
    }

    @Test
    @DisplayName("成功创建试卷")
    void createTestPaper() throws Exception {
        PaperParam paperParam = PaperParam.builder()
                .name("p1")
                .score(100)
                .questionList(new ArrayList<>(Arrays.asList(1,2,3)))
                .build();

        MvcResult result = mockMvc.perform(
                post("/paper/createPaper")
                        .content(JSON.toJSONBytes(paperParam))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andReturn();

        verify(testPaperService).createTestPaper(paperParam);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());

    }

    @Test
    @DisplayName("成功根据id获取试卷")
    void getTestPaper() throws Exception {
        Integer paperId = 1;

        MvcResult result = mockMvc.perform(
                post("/paper/getPaper")
                        .param("paperId", "1")
        ).andReturn();

        verify(testPaperService).getTestPaper(paperId);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());

    }

    @Test
    @DisplayName("成功根据id删除试卷")
    void deleteTestPaper() throws Exception {
        Integer paperId = 1;

        MvcResult result = mockMvc.perform(
                post("/paper/deletePaper")
                        .param("paperId", "1")
        ).andReturn();

        verify(testPaperService).deleteTestPaper(paperId);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());
    }
}