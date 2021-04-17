package com.ecnu.petHospital.dao;

import com.ecnu.petHospital.entity.Case;
import com.ecnu.petHospital.param.CaseListParam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CaseMapper extends MyMapper<Case> {

    @Select("select id, name, disease_id from `case` where disease_id = #{diseaseId} order by id desc")
    List<CaseListParam> getCasesByDisId(@Param("diseaseId") Integer diseaseId);

}
