package com.ecnu.petHospital.controller;

import com.ecnu.petHospital.param.CaseCreateParam;
import com.ecnu.petHospital.service.CaseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class CaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CaseService caseService;

    @Test
    @DisplayName("成功创建病例并返回成功")
    void when_an_administrator_request_to_create_new_case_then_dispatch_to_service_and_return_success_result() throws Exception{
        CaseCreateParam caseCreateParam = CaseCreateParam.builder()
                .name("case1")
                .disease_id(1)
                .consultation("consultation")
                .diagnosis("diagnosis")
                .examination("examination")
                .treatment("treatment").build();
    }

}