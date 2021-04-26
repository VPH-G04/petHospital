package com.ecnu.petHospital.controller;

import com.alibaba.fastjson.JSON;
import com.ecnu.petHospital.entity.Department;
import com.ecnu.petHospital.param.PageParam;
import com.ecnu.petHospital.result.CommonResult;
import com.ecnu.petHospital.result.Result;
import com.ecnu.petHospital.service.DepartmentService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    @Test
    @DisplayName("成功获取科室信息")
    void when_request_to_get_department_by_id_then_return_success() throws Exception {

        Integer departmentId = 1;

        MvcResult result = mockMvc.perform(
                get("/department/getDepartment")
                .param("departmentId","1")
        ).andReturn();

        verify(departmentService, times(1)).getOne(departmentId);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());

    }

    @Test
    @DisplayName("成功新建科室")
    void when_request_to_create_a_department_then_create_and_return_success() throws Exception {
        Department department = Department.builder()
                .name("department1")
                .describe("description")
                .principal("role1").build();

        MvcResult result = mockMvc.perform(
                post("/department/add")
                .content(JSON.toJSONBytes(department))
                .contentType(MediaType.APPLICATION_JSON_UTF8)

        ).andReturn();

        verify(departmentService, times(1)).addDepartment(department);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());

    }

    @Test
    @DisplayName("成功根据id删除科室")
    void when_request_to_delete_a_department_by_id_then_delete_and_return_success() throws Exception {
        Integer departmentId = 1;

        MvcResult result = mockMvc.perform(
                delete("/department/delete")
                        .param("departmentId","1")
        ).andReturn();

        verify(departmentService, times(1)).delete(departmentId);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());
    }

    @Test
    @DisplayName("成功修改科室信息")
    void when_request_to_update_a_department_then_update_and_return_success() throws Exception {
        Department department = Department.builder()
                .name("new_department_name")
                .describe("new_description")
                .principal("new_role").build();

        MvcResult result = mockMvc.perform(
                put("/department/mod")
                        .content(JSON.toJSONBytes(department))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)

        ).andReturn();

        verify(departmentService, times(1)).modDepartment(department);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());
    }

    @Test
    @DisplayName("成功获取科室列表")
    void when_request_to_get_department_list_then_return_list_and_return_success() throws Exception {

        PageParam pageParam = PageParam.builder()
                .pageNum(1)
                .pageSize(10).build();

        MvcResult result = mockMvc.perform(
                get("/department/list")
                        .content(JSON.toJSONBytes(pageParam))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)

        ).andReturn();

        verify(departmentService, times(1)).getDepartmentList(pageParam);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());
    }
}