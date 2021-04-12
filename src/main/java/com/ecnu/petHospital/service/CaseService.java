package com.ecnu.petHospital.service;


import com.ecnu.petHospital.entity.Case;

import java.util.List;

public interface CaseService {

     void createCase(Case newcase);

     void updateCase(Case newcase);

     void deleteCase(Integer id);

     Case getCaseById(Integer id);

     List<Case> getCasesByDiseaseId(Integer disId);

     List<Case> getCases();

}
