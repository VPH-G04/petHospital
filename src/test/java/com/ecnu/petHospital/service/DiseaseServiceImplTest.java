package com.ecnu.petHospital.service;

import com.ecnu.petHospital.dao.DiseaseClassMapper;
import com.ecnu.petHospital.dao.DiseaseMapper;
import com.ecnu.petHospital.entity.Disease;
import com.ecnu.petHospital.entity.DiseaseClassification;
import com.ecnu.petHospital.service.impl.DiseaseServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class DiseaseServiceImplTest {

    @InjectMocks
    private DiseaseServiceImpl diseaseService;

    @Mock
    private DiseaseMapper diseaseMapper;

    @Mock
    private DiseaseClassMapper diseaseClassMapper;

    @Test
    @DisplayName("成功获取病种分类列表")
    void getDiseaseClass() {
        DiseaseClassification classification = DiseaseClassification.builder()
                .id(1)
                .name("class1")
                .build();
        DiseaseClassification classification1 = DiseaseClassification.builder()
                .id(2)
                .name("class2")
                .build();
        List<DiseaseClassification> list = new ArrayList<>(Arrays.asList(classification,classification1));
        when(diseaseClassMapper.selectAll()).thenReturn(list);

        List<DiseaseClassification> res = diseaseService.getDiseaseClass();
        verify(diseaseClassMapper, times(1)).selectAll();
        Assertions.assertAll(
                ()->assertEquals(1,res.get(0).getId()),
                ()->assertEquals("class1",res.get(0).getName()),
                ()->assertEquals(2,res.get(1).getId()),
                ()->assertEquals("class2",res.get(1).getName())
        );
    }

    @Test
    @DisplayName("成功根据分类id获取病种信息")
    void getDiseaseByClassId() {
        Disease disease1 = Disease.builder()
                .id(1)
                .classId(1)
                .name("disease1")
                .build();
        Disease disease2 = Disease.builder()
                .id(2)
                .classId(1)
                .name("disease2")
                .build();
        List<Disease> list = new ArrayList<>(Arrays.asList(disease1,disease2));
        when(diseaseMapper.getDiseaseByClassId(1)).thenReturn(list);

        List<Disease> res = diseaseService.getDiseaseByClassId(1);
        verify(diseaseMapper, times(1)).getDiseaseByClassId(1);
        Assertions.assertAll(
                ()->assertEquals(1,res.get(0).getId()),
                ()->assertEquals(1,res.get(0).getClassId()),
                ()->assertEquals("disease1",res.get(0).getName()),
                ()->assertEquals(2,res.get(1).getId()),
                ()->assertEquals(1,res.get(1).getClassId()),
                ()->assertEquals("disease2",res.get(1).getName())
        );
    }

    @Test
    @DisplayName("成功获取所有病种信息")
    void getAllDiseases() {
        Disease disease1 = Disease.builder()
                .id(1)
                .classId(1)
                .name("disease1")
                .build();
        Disease disease2 = Disease.builder()
                .id(2)
                .classId(3)
                .name("disease2")
                .build();
        List<Disease> list = new ArrayList<>(Arrays.asList(disease1,disease2));
        when(diseaseMapper.selectAll()).thenReturn(list);

        List<Disease> res = diseaseService.getAllDiseases();
        verify(diseaseMapper, times(1)).selectAll();
        Assertions.assertAll(
                ()->assertEquals(1,res.get(0).getId()),
                ()->assertEquals(1,res.get(0).getClassId()),
                ()->assertEquals("disease1",res.get(0).getName()),
                ()->assertEquals(2,res.get(1).getId()),
                ()->assertEquals(3,res.get(1).getClassId()),
                ()->assertEquals("disease2",res.get(1).getName())
        );
    }

    @Test
    @DisplayName("成功创建病种分类")
    void createDiseaseClass() {

        String className = "class1";
        DiseaseClassification classification = DiseaseClassification.builder()
                .name("class1")
                .build();

        when(diseaseClassMapper.insert(classification)).thenReturn(1);

        Integer res = diseaseService.createDiseaseClass(className);
        verify(diseaseClassMapper, times(1)).insert(classification);
        assertEquals(1,res);

    }

    @Test
    @DisplayName("成功创建病种信息")
    void createDisease() {
        Disease disease = Disease.builder()
                .classId(1)
                .name("disease1")
                .build();

        when(diseaseMapper.insert(disease)).thenReturn(1);

        Integer res = diseaseService.createDisease(disease);
        verify(diseaseMapper, times(1)).insert(disease);
        assertEquals(1,res);
    }

    @Test
    @DisplayName("成功根据分类id删除病种分类及相关病种")
    void deleteDiseaseClassById() {

        diseaseService.deleteDiseaseClassById(1);
        verify(diseaseMapper, times(1)).deleteDiseasesByClassId(1);
        verify(diseaseClassMapper, times(1)).deleteByPrimaryKey(1);
    }

    @Test
    @DisplayName("成功根据id删除病种信息")
    void deleteDiseaseById() {
        diseaseService.deleteDiseaseById(1);
        verify(diseaseMapper, times(1)).deleteByPrimaryKey(1);
    }
}