package com.ecnu.petHospital.controller;

import com.ecnu.petHospital.entity.Case;
import com.ecnu.petHospital.enums.ProcedureType;
import com.ecnu.petHospital.param.CaseCreateParam;
import com.ecnu.petHospital.param.CaseParam;
import com.ecnu.petHospital.param.PageParam;
import com.ecnu.petHospital.service.CaseService;
import com.ecnu.petHospital.session.UserSessionInfo;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.alibaba.fastjson.JSON;

import springfox.documentation.spring.web.json.Json;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class CaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CaseService caseService;

    @InjectMocks
    CaseController caseController;

    @Test
    @DisplayName("成功创建病例并返回成功")
    void when_request_to_create_new_case_then_dispatch_to_service_and_return_success_result() throws Exception{

        String description1 = "description1";
        String procedure1 = ProcedureType.consultation;
        File file1 = new File("src/test/data/image/p1.PNG");
        MockMultipartFile mfile1 = new MockMultipartFile("image","p1.PNG",
                MediaType.TEXT_PLAIN_VALUE, new FileInputStream(file1));

        String description2 = "description2";
        String procedure2 = ProcedureType.consultation;
        File file2 = new File("src/test/data/image/p2.png");
        MockMultipartFile mfile2 = new MockMultipartFile("image","p2.png",
                MediaType.TEXT_PLAIN_VALUE, new FileInputStream(file2));

        String description3 = "description3";
        String procedure3 = ProcedureType.diagnosis;
        File file3 = new File("src/test/data/video/video.mov");
        MockMultipartFile mfile3 = new MockMultipartFile("video","video1.mov",
                MediaType.TEXT_PLAIN_VALUE, new FileInputStream(file3));


        CaseCreateParam caseCreateParam = CaseCreateParam.builder()
                .name("case1")
                .disease_id(1)
                .consultation("consultation")
                .diagnosis("diagnosis")
                .examination("examination")
                .treatment("treatment")
                .image(new ArrayList<>(Arrays.asList(mfile1,mfile2)))
                .imageDescription(new ArrayList<>(Arrays.asList(description1,description2)))
                .imageProcedure(new ArrayList<>(Arrays.asList(procedure1,procedure2)))
                .video(new ArrayList<>(Arrays.asList(mfile3)))
                .videoDescription(new ArrayList<>(Arrays.asList(description3)))
                .videoProcedure(new ArrayList<>(Arrays.asList(procedure3)))
                .build();

        Case newcase = new Case();
        BeanUtils.copyProperties(caseCreateParam,newcase);

        CaseParam caseParam = CaseParam.builder()
                .image(new ArrayList<>(Arrays.asList("url1","url2")))
                .imageDescription(caseCreateParam.getImageDescription())
                .imageProcedure(caseCreateParam.getImageProcedure())
                .video(new ArrayList<>(Arrays.asList("url3")))
                .videoDescription(caseCreateParam.getVideoDescription())
                .videoProcedure(caseCreateParam.getVideoProcedure())
                .build();

        MvcResult mvcResult = mockMvc.perform(multipart("/case/create")
//                .requestAttr("caseCreateParam",caseCreateParam)
                        .file(mfile1)
                        .file(mfile2)
                        .file(mfile3)
                        .param("imageDescription", description1)
                        .param("imageProcedure", procedure1)
                        .param("imageDescription", description2)
                        .param("imageProcedure", procedure2)
                        .param("videoDescription", description3)
                        .param("videoProcedure", procedure3)
                        .param("name","case1")
                        .param("disease_id", "1")
                        .param("consultation","consultation")
                        .param("diagnosis","diagnosis")
                        .param("examination", "examination")
                        .param("treatment", "treatment")
        )
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println(content);
        int code = JsonPath.parse(content).read("$.code");
        assertThat(code, equalTo(200));

    }


    @Test
    @DisplayName("成功删除病例并返回成功")
    void when_request_to_delete_a_case_then_dispatch_to_service_and_return_success_result() throws Exception{

        Integer id = 40;
        MockHttpSession session = new MockHttpSession();
        UserSessionInfo userSessionInfo = new UserSessionInfo(1, "lionel", "10175101121@stu.ecnu.edu.cn",true);
        session.setAttribute("userSessionInfo", userSessionInfo);

        MvcResult mvcResult = mockMvc.perform(post("/case/delete")
                .param("id", "40")
                .session(session)
        )
                .andExpect(status().isOk())
                .andReturn();

        verify(caseService, times(1)).deleteCase(id);
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println(content);
        int code = JsonPath.parse(content).read("$.code");
        assertThat(code, equalTo(200));

    }

    @Test
    @DisplayName("成功获取病例列表并返回成功")
    void when_request_to_get_cases_then_dispatch_to_service_and_return_success_result() throws Exception{

        Integer diseaseId = 1;
        PageParam pageParam = new PageParam(1,10);

        MvcResult mvcResult = mockMvc.perform(post("/case/getcases?diseaseId=1")
//                .param("diseaseId", "1")
                        .content(JSON.toJSONString(pageParam))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andReturn();

        verify(caseService, times(1)).getCasesByDiseaseId(diseaseId, pageParam);
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println(content);
        int code = JsonPath.parse(content).read("$.code");
        assertThat(code, equalTo(200));

    }

    @Test
    @DisplayName("成功根据id获取病例并返回成功")
    void when_request_to_get_case_by_id_then_dispatch_to_service_and_return_success_result() throws Exception{

        Integer id = 40;

        MvcResult mvcResult = mockMvc.perform(post("/case/get")
                .param("id", "40")
        )
                .andExpect(status().isOk())
                .andReturn();

        verify(caseService, times(1)).getCaseById(id);
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println(content);
        int code = JsonPath.parse(content).read("$.code");
        assertThat(code, equalTo(200));

    }
}