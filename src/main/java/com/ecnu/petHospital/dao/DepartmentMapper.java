package com.ecnu.petHospital.dao;

import com.ecnu.petHospital.MyMapper;
import com.ecnu.petHospital.entity.Department;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DepartmentMapper extends MyMapper<Department> {
}
