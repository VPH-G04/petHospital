package com.ecnu.petHospital.dao;


import com.ecnu.petHospital.entity.Case;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

@Mapper
public interface CaseMapper extends BaseMapper<Case> {

}
