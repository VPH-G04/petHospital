package com.ecnu.petHospital.dao;


import com.ecnu.petHospital.entity.Case;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Mapper
public interface CaseMapper extends BaseMapper<Case> {


    List<Case> getCasesByDiseaseId(Integer disId);
}
