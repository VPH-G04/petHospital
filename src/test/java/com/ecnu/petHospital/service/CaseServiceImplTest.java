package com.ecnu.petHospital.service;

import com.ecnu.petHospital.dao.CaseMapper;
import com.ecnu.petHospital.dao.CasePictureMapper;
import com.ecnu.petHospital.dao.CaseVideoMapper;
import com.ecnu.petHospital.entity.Case;
import com.ecnu.petHospital.entity.CasePicture;
import com.ecnu.petHospital.entity.CaseVideo;
import com.ecnu.petHospital.enums.ProcedureType;
import com.ecnu.petHospital.param.CaseListParam;
import com.ecnu.petHospital.param.CaseParam;
import com.ecnu.petHospital.param.PageParam;
import com.ecnu.petHospital.service.impl.CaseServiceImpl;
import com.ecnu.petHospital.vo.CaseVO;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class CaseServiceImplTest {

    @InjectMocks
    private CaseServiceImpl caseService;

    @Mock
    private CaseMapper caseMapper;

    @Mock
    private CasePictureMapper casePictureMapper;

    @Mock
    private CaseVideoMapper caseVideoMapper;


    @Test
    @DisplayName("成功创建病例并存储相关图片视频")
    void when_service_do_create_case_with_newcase_and_caseParam_then_dispatch_to_mapper_create_case() {

        Case newcase = Case.builder()
                .id(1)
                .name("case1")
                .disease_id(1)
                .consultation("consultation")
                .diagnosis("diagnosis")
                .examination("examination")
                .treatment("treatment")
                .build();

        CaseParam caseParam = CaseParam.builder()
                .image(new ArrayList<>(Arrays.asList("image_url_1","image_url_2")))
                .imageDescription(new ArrayList<>(Arrays.asList("image_description_1",":image_description_2")))
                .imageProcedure(new ArrayList<>(Arrays.asList(ProcedureType.consultation, ProcedureType.consultation)))
                .video(new ArrayList<>(Arrays.asList("video1")))
                .videoDescription(new ArrayList<>(Arrays.asList("video_description_1")))
                .videoProcedure(new ArrayList<>(Arrays.asList(ProcedureType.diagnosis)))
                .build();

        caseService.createCase(newcase, caseParam);
        verify(caseMapper, times(1)).insert(newcase);
        for(int i=0; i<caseParam.getImage().size(); i++){
            CasePicture cp = CasePicture.builder()
                    .case_id(1)
                    .describe(caseParam.getImageDescription().get(i))
                    .procedure(caseParam.getImageProcedure().get(i))
                    .url(caseParam.getImage().get(i))
                    .build();
            verify(casePictureMapper, times(1)).insertPicture(cp);
        }
        for(int i=0; i<caseParam.getVideo().size(); i++){
            CaseVideo cv = CaseVideo.builder()
                    .case_id(1)
                    .describe(caseParam.getVideoDescription().get(i))
                    .procedure(caseParam.getVideoProcedure().get(i))
                    .url(caseParam.getVideo().get(i))
                    .build();
            verify(caseVideoMapper, times(1)).insertVideo(cv);
        }

    }

    @Test
    @DisplayName("成功按id删除病例及相关图片视频")
    void when_service_do_delete_case_by_id_then_dispatch_to_mapper_delete_case() {

        caseService.deleteCase(1);
        verify(casePictureMapper, times(1)).deletePictureByCaseId(1);
        verify(caseVideoMapper, times(1)).deleteVideoByCaseId(1);
        verify(caseMapper, times(1)).deleteByPrimaryKey(1);
    }

    @Test
    @DisplayName("成功按id查询病例")
    void when_service_do_get_case_by_id_then_dispatch_to_mapper_get_case() {
        Case acase = Case.builder()
                .id(1)
                .disease_id(2)
                .name("case1")
                .consultation("consultation")
                .diagnosis("diagnosis")
                .examination("examination")
                .treatment("treatment")
                .build();
        CasePicture casePicture = CasePicture.builder()
                .id(10)
                .case_id(1)
                .describe("image_des")
                .procedure(ProcedureType.consultation)
                .url("image_url")
                .build();
        CaseVideo caseVideo = CaseVideo.builder()
                .id(11)
                .case_id(1)
                .describe("video_des")
                .procedure(ProcedureType.treatment)
                .url("video_url")
                .build();
        List<CasePicture> images = new ArrayList<>(Arrays.asList(casePicture));
        List<CaseVideo> videos = new ArrayList<>(Arrays.asList(caseVideo));

        when(caseMapper.selectByPrimaryKey(1)).thenReturn(acase);
        when(casePictureMapper.getPicturesByCaseId(1)).thenReturn(images);
        when(caseVideoMapper.getVideosByCaseId(1)).thenReturn(videos);

        CaseVO caseVO = caseService.getCaseById(1);
        verify(caseMapper, times(1)).selectByPrimaryKey(1);
        verify(casePictureMapper, times(1)).getPicturesByCaseId(1);
        verify(caseVideoMapper, times(1)).getVideosByCaseId(1);
        Assertions.assertAll(
                ()-> assertEquals(1, caseVO.getId()),
                ()-> assertEquals("case1", caseVO.getName()),
                ()-> assertEquals(2, caseVO.getDisease_id()),
                ()-> assertEquals(ProcedureType.consultation, caseVO.getProcedureVOS().get(0).getProcedureName()),
                ()-> assertEquals("consultation", caseVO.getProcedureVOS().get(0).getDescribe()),
                ()-> assertEquals("image_url", caseVO.getProcedureVOS().get(0).getImages().get(0).getUrl()),
                ()-> assertEquals("image_des", caseVO.getProcedureVOS().get(0).getImages().get(0).getDescription()),
                ()-> assertEquals(ProcedureType.treatment, caseVO.getProcedureVOS().get(3).getProcedureName()),
                ()-> assertEquals("treatment", caseVO.getProcedureVOS().get(3).getDescribe()),
                ()-> assertEquals("video_url", caseVO.getProcedureVOS().get(3).getVideos().get(0).getUrl()),
                ()-> assertEquals("video_des", caseVO.getProcedureVOS().get(3).getVideos().get(0).getDescription())
        );

    }

    @Test
    @DisplayName("成功根据DiseaseId获取病例列表")
    void getCasesByDiseaseId() {
        PageParam pageParam = new PageParam(1,10);

        CaseListParam caseListParam = CaseListParam.builder()
                .id(1)
                .diseaseId(1)
                .name("case1")
                .build();
        CaseListParam caseListParam1 = CaseListParam.builder()
                .id(2)
                .diseaseId(1)
                .name("case2")
                .build();
        List<CaseListParam> caseList = new ArrayList<>(Arrays.asList(caseListParam,caseListParam1));

        when(caseMapper.getCasesByDisId(1)).thenReturn(caseList);

        PageInfo pageInfo = caseService.getCasesByDiseaseId(1,pageParam);

        verify(caseMapper, times(1)).getCasesByDisId(1);
        Assertions.assertAll(
                ()-> assertEquals(1,pageInfo.getPageNum()),
                ()-> assertEquals(2,pageInfo.getPageSize()),
                ()-> assertEquals(1,((CaseListParam)pageInfo.getList().get(0)).getId()),
                ()-> assertEquals(1,((CaseListParam)pageInfo.getList().get(0)).getDiseaseId()),
                ()-> assertEquals("case1",((CaseListParam)pageInfo.getList().get(0)).getName()),
                ()-> assertEquals(2,((CaseListParam)pageInfo.getList().get(1)).getId()),
                ()-> assertEquals(1,((CaseListParam)pageInfo.getList().get(1)).getDiseaseId()),
                ()-> assertEquals("case2",((CaseListParam)pageInfo.getList().get(1)).getName())

        );

    }
}