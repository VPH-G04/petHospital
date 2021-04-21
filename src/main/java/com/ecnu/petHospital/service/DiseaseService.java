package com.ecnu.petHospital.service;

import com.ecnu.petHospital.entity.Disease;
import com.ecnu.petHospital.entity.DiseaseClassification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DiseaseService {

    List<DiseaseClassification> getDiseaseClass();

    List<Disease> getDiseaseByClassId(Integer diseaseClassId);

    Integer createDiseaseClass(String diseaseClassName);

    Integer createDisease(Disease disease);

    Integer deleteDiseaseClassById(Integer id);

    Integer deleteDiseaseById(Integer id);
}
