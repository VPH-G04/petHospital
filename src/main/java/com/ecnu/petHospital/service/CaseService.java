package com.ecnu.petHospital.service;


import com.ecnu.petHospital.entity.Case;
import com.ecnu.petHospital.param.CaseListParam;
import com.ecnu.petHospital.param.CaseParam;
import com.ecnu.petHospital.param.PageParam;
import com.ecnu.petHospital.vo.CaseVO;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;

import java.util.List;

public interface CaseService {

     CaseVO createCase(Case newcase, CaseParam caseParam);

     Integer updateCase(Case newcase);

     Integer deleteCase(Integer id);

     CaseVO getCaseById(Integer id);

     PageInfo<CaseListParam> getCasesByDiseaseId(Integer disId, PageParam pageParam);

     List<Case> getCases();

}
