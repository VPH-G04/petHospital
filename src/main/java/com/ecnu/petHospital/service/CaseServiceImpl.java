package com.ecnu.petHospital.service;

import com.ecnu.petHospital.dao.CaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CaseServiceImpl implements CaseService {

    @Autowired
    CaseMapper caseMapper;



}
