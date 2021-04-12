package com.ecnu.petHospital.service.impl;

import com.ecnu.petHospital.entity.Department;
import com.ecnu.petHospital.dao.DepartmentMapper;
import com.ecnu.petHospital.entity.common.PageParam;
import com.ecnu.petHospital.enums.CustomExceptionType;
import com.ecnu.petHospital.exception.CustomException;
import com.ecnu.petHospital.service.DepartmentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Resource
    private DepartmentMapper departmentMapper;

    @Override
    public Department getOne(Integer departmentId) {
        return departmentMapper.selectByPrimaryKey(departmentId);
    }

    @Override
    @Transactional
    public Department addDepartment(Department department) {
        Example example = new Example(Department.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name",department.getName());

        Department department1 = departmentMapper.selectOneByExample(example);
        if(department1!=null) throw new CustomException(CustomExceptionType.DEPARTMENT_ADD_ERROR);
        departmentMapper.insert(department);
        return departmentMapper.selectOneByExample(example);
    }

    @Override
    @Transactional
    public Boolean modDepartment(Department department) {
        //修改科室名字时需注意不能跟已有科室重名
        Example example = new Example(Department.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name",department.getName());

        Department department1 = departmentMapper.selectOneByExample(example);
        if(department1!=null && department1!=department ) throw new CustomException(CustomExceptionType.DEPARTMENT_MODIFY_ERROR);


        return  departmentMapper.updateByPrimaryKey(department)>0;

    }

    @Override
    @Transactional
    public Boolean delete(Integer departmentId) {
        return departmentMapper.deleteByPrimaryKey(departmentId) > 0;
    }

    @Override
    public PageInfo<Department> getDepartmentList(PageParam pageParam) {
        PageHelper.startPage(pageParam.getPageNum(),pageParam.getPageSize(),pageParam.getOrderBy());
        List<Department> departmentList=departmentMapper.selectAll();
        return new PageInfo<>(departmentList);
    }
}
