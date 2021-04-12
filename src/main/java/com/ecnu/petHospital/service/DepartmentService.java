package com.ecnu.petHospital.service;

import com.ecnu.petHospital.entity.Department;
import com.ecnu.petHospital.entity.common.PageParam;
import com.github.pagehelper.PageInfo;

public interface DepartmentService {

    Department getOne(Integer departmentId);

    Department addDepartment(Department department);

    Boolean modDepartment(Department department);

    Boolean delete(Integer departmentId);

    PageInfo<Department> getDepartmentList(PageParam pageParam);
}
