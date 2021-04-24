package com.ecnu.petHospital.controller;

import com.alibaba.fastjson.JSON;
import com.ecnu.petHospital.entity.Disease;
import com.ecnu.petHospital.result.CommonResult;
import com.ecnu.petHospital.result.Result;
import com.ecnu.petHospital.service.DiseaseService;
import com.ecnu.petHospital.service.impl.DiseaseServiceImpl;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class DiseaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DiseaseServiceImpl diseaseService;

    @Test
    @DisplayName("成功创建病种类别")
    void createDiseaseClass() throws Exception {
        String diseaseClassName = "diseaseClass1";

        MvcResult result = mockMvc.perform(
                post("/disease/createClass")
                .param("diseaseClassName",diseaseClassName))
                .andExpect(status().isOk())
                .andReturn();

        verify(diseaseService, times(1)).createDiseaseClass(diseaseClassName);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(),Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());
    }

    @Test
    @DisplayName("成功创建病种")
    void createDisease() throws Exception {
        Disease disease = Disease.builder()
                .classId(1)
                .name("disease1")
                .build();

        MvcResult result =mockMvc.perform(
                post("/disease/create")
                .content(JSON.toJSONString(disease))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andReturn();

        verify(diseaseService, times(1)).createDisease(disease);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());
    }

    @Test
    @DisplayName("成功获取病种分类列表")
    void getDiseaseClassList() throws Exception {

        MvcResult result = mockMvc.perform(
                post("/disease/getClasses")
        ).andExpect(status().isOk())
                .andReturn();

        verify(diseaseService, times(1)).getDiseaseClass();
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());
    }

    @Test
    @DisplayName("成功根据分类id获取病种信息")
    void getDiseaseByClassId() throws Exception {
        Integer diseaseClassId = 1;

        MvcResult result = mockMvc.perform(
                post("/disease/get")
                .param("diseaseClassId","1")
        ).andExpect(status().isOk())
                .andReturn();

        verify(diseaseService, times(1)).getDiseaseByClassId(diseaseClassId);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());

    }

    @Test
    @DisplayName("成功获取所有病种")
    void getAllDiseases() throws Exception {
        MvcResult result = mockMvc.perform(
                post("/disease/getAll")
        ).andExpect(status().isOk())
                .andReturn();

        verify(diseaseService, times(1)).getAllDiseases();
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());
    }

    @Test
    @DisplayName("成功根据id删除病种分类及相关病种")
    void deleteClassById() throws Exception {
        Integer id = 1;

        MvcResult result = mockMvc.perform(
                post("/disease/deleteClass")
                        .param("id","1")
        ).andExpect(status().isOk())
                .andReturn();

        verify(diseaseService, times(1)).deleteDiseaseClassById(id);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());
    }

    @Test
    @DisplayName("成功根据i删除病种")
    void deleteDiseaseById() throws Exception {
        Integer id = 1;

        MvcResult result = mockMvc.perform(
                post("/disease/delete")
                        .param("id","1")
        ).andExpect(status().isOk())
                .andReturn();

        verify(diseaseService, times(1)).deleteDiseaseById(id);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());
    }
}