package com.ecnu.petHospital.service;


import com.ecnu.petHospital.entity.Case;
import com.ecnu.petHospital.param.CaseListParam;
import com.ecnu.petHospital.param.CaseParam;
import com.ecnu.petHospital.vo.CaseVO;
import io.swagger.models.auth.In;

import java.util.List;

public interface CaseService {

     CaseVO createCase(Case newcase, CaseParam caseParam);

     Integer updateCase(Case newcase);

     Integer deleteCase(Integer id);

     CaseVO getCaseById(Integer id);

     List<CaseListParam> getCasesByDiseaseId(Integer disId);

     List<Case> getCases();

}
