package com.ecnu.petHospital.service.impl;

import com.ecnu.petHospital.dao.DiseaseClassMapper;
import com.ecnu.petHospital.dao.DiseaseMapper;
import com.ecnu.petHospital.entity.Disease;
import com.ecnu.petHospital.entity.DiseaseClassification;
import com.ecnu.petHospital.service.DiseaseService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiseaseServiceImpl implements DiseaseService {

    @Autowired
    DiseaseMapper diseaseMapper;

    @Autowired
    DiseaseClassMapper diseaseClassMapper;

    @Override
    public List<DiseaseClassification> getDiseaseClass() {
        return diseaseClassMapper.selectAll();
    }

    @Override
    public List<Disease> getDiseaseByClassId(Integer diseaseClassId) {
        return diseaseMapper.getDiseaseByClassId(diseaseClassId);
    }

    @Override
    public List<Disease> getAllDiseases() {
        return diseaseMapper.selectAll();
    }

    @Override
    public Integer createDiseaseClass(String diseaseClassName) {
        DiseaseClassification diseaseClassification = new DiseaseClassification();
        diseaseClassification.setName(diseaseClassName);
        return diseaseClassMapper.insert(diseaseClassification);
    }

    @Override
    public Integer createDisease(Disease disease) {
        return diseaseMapper.insert(disease);
    }

    @Override
    public Integer deleteDiseaseClassById(Integer id) {
        diseaseMapper.deleteDiseasesByClassId(id);
        return diseaseClassMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer deleteDiseaseById(Integer id) {
        return diseaseMapper.deleteByPrimaryKey(id);
    }


}
