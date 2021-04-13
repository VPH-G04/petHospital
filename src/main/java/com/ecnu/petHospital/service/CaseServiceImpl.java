package com.ecnu.petHospital.service;

import com.ecnu.petHospital.dao.CaseMapper;
import com.ecnu.petHospital.entity.Case;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaseServiceImpl implements CaseService {

    @Autowired
    CaseMapper caseMapper;


    @Override
    public void createCase(Case newcase) {
        caseMapper.insert(newcase);
    }

    @Override
    public void updateCase(Case newcase) {
        caseMapper.updateByPrimaryKey(newcase);
    }

    @Override
    public void deleteCase(Integer id) {
        caseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Case getCaseById(Integer id) {
        return caseMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Case> getCasesByDiseaseId(Integer disId) {
        return null;
    }

    @Override
    public List<Case> getCases() {
        return caseMapper.selectAll();
    }
}
