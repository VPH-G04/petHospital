package com.ecnu.petHospital.controller;

import com.alibaba.fastjson.JSON;
import com.ecnu.petHospital.entity.Case;
import com.ecnu.petHospital.entity.LearningResource;
import com.ecnu.petHospital.enums.FileType;
import com.ecnu.petHospital.enums.ProcedureType;
import com.ecnu.petHospital.param.CaseCreateParam;
import com.ecnu.petHospital.param.CaseParam;
import com.ecnu.petHospital.param.ResourceCreateParam;
import com.ecnu.petHospital.param.ResourceParam;
import com.ecnu.petHospital.result.CommonResult;
import com.ecnu.petHospital.result.Result;
import com.ecnu.petHospital.service.RoleService;
import com.sun.org.apache.regexp.internal.RE;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class LearningControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    @Spy
    private LearningController learningController;

    @Test
    @DisplayName("成功根据角色id获取学习资料")
    void when_request_to_get_learning_resource_by_role_id_then_return_list_and_success() throws Exception {
        Integer roleId = 1;

        MvcResult result = mockMvc.perform(
                get("/role/getResource")
                .param("roleId","1")
        ).andReturn();

        verify(roleService, times(1)).getAllResourceFromRole(roleId);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());

    }

    @Test
    @DisplayName("成功创建新学习资料")
    void when_request_to_create_a_learning_resource_then_create_and_success() throws Exception {
        String description1 = "description1";
        File file1 = new File("src/test/data/image/p1.PNG");
        MockMultipartFile mfile1 = new MockMultipartFile("image","p1.PNG",
                MediaType.TEXT_PLAIN_VALUE, new FileInputStream(file1));

        String description2 = "description2";
        File file2 = new File("src/test/data/image/p2.png");
        MockMultipartFile mfile2 = new MockMultipartFile("image","p2.png",
                MediaType.TEXT_PLAIN_VALUE, new FileInputStream(file2));

        String description3 = "description3";
        File file3 = new File("src/test/data/video/video.mov");
        MockMultipartFile mfile3 = new MockMultipartFile("video","video1.mov",
                MediaType.TEXT_PLAIN_VALUE, new FileInputStream(file3));

        ResourceCreateParam resourceCreateParam = ResourceCreateParam.builder()
                .roleId(1)
                .title("title")
                .content("content")
                .image(new ArrayList<>(Arrays.asList(mfile1,mfile2)))
                .imageDescription(new ArrayList<>(Arrays.asList(description1,description2)))
                .video(new ArrayList<>(Arrays.asList(mfile3)))
                .videoDescription(new ArrayList<>(Arrays.asList(description3)))
                .build();

        LearningResource resource = new LearningResource();
        BeanUtils.copyProperties(resourceCreateParam, resource);

        ResourceParam resourceParam = ResourceParam.builder()
                .image(new ArrayList<>(Arrays.asList("url1","url2")))
                .imageDescription(resourceCreateParam.getImageDescription())
                .video(new ArrayList<>(Arrays.asList("url3")))
                .videoDescription(resourceCreateParam.getVideoDescription())
                .build();

        MvcResult result = mockMvc.perform(
                multipart("/role/create")
                        .file(mfile1)
                        .file(mfile2)
                        .file(mfile3)
                        .param("imageDescription", description1)
                        .param("imageDescription", description2)
                        .param("videoDescription", description3)
                        .param("roleId","1")
                        .param("title","title")
                        .param("content", "content")
        ).andReturn();

        doReturn(new ArrayList<>(Arrays.asList("url1","url2")))
                .when(learningController).saveFile(resourceCreateParam.getVideo(), result.getRequest(), FileType.Image);
        doReturn(new ArrayList<>(Arrays.asList("url3")))
                .when(learningController).saveFile(resourceCreateParam.getVideo(), result.getRequest(), FileType.Video);
        System.out.println(learningController.saveFile(resourceCreateParam.getVideo(), result.getRequest(), FileType.Image));
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());
    }

    @Test
    @DisplayName("成功根据id删除学习资料")
    void when_request_to_delete_learning_resource_by_id_then_delete_and_success() throws Exception {
        Integer resourceId = 1;

        MvcResult result = mockMvc.perform(
                delete("/role/delete")
                .param("resourceId","1")
        ).andReturn();

        verify(roleService, times(1)).deleteRoleResource(resourceId);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());
    }

    @Test
    @DisplayName("成功更新学习资料")
    void when_request_to_update_learning_resource_by_id_then_update_and_success() throws Exception {
        LearningResource resource = LearningResource.builder()
                .id(1)
                .roleId(1)
                .title("new_title")
                .content("new_content").build();

        MvcResult result = mockMvc.perform(
                put("/role/update")
                .content(JSON.toJSONBytes(resource))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andReturn();

        verify(roleService, times(1)).updateResource(resource);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());
    }
}