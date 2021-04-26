package com.ecnu.petHospital.service;

import com.ecnu.petHospital.dao.DepartmentMapper;
import com.ecnu.petHospital.entity.Department;
import com.ecnu.petHospital.param.CaseListParam;
import com.ecnu.petHospital.param.PageParam;
import com.ecnu.petHospital.service.impl.DepartmentServiceImpl;
import com.github.pagehelper.PageInfo;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DepartmentServiceImplTest {
    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @Mock
    private DepartmentMapper departmentMapper;

    @Test
    @DisplayName("根据部门id获取部门信息")
    void return_department_by_id(){
        Department department = Department.builder()
                .id(10)
                .name("department10")
                .describe("this is describe")
                .principal("jason is principal").build();

        departmentService.addDepartment(department);

        verify(departmentMapper,times(1)).insert(department);

        when(departmentMapper.selectByPrimaryKey(10)).thenReturn(department);

        Department res = departmentService.getOne(10);
        Assertions.assertAll(
                ()->assertEquals(10,res.getId()),
                ()->assertEquals("department10",res.getName()),
                ()->assertEquals("this is describe",res.getDescribe()),
                ()->assertEquals("jason is principal",res.getPrincipal())

                );
    }

    @Test
    @DisplayName("分页返回部门信息")
    void return_department_by_pages(){
        Department department = Department.builder()
                .id(10)
                .name("department10")
                .describe("this is describe")
                .principal("jason is principal").build();

        Department department1 = Department.builder()
                .id(11)
                .name("department11")
                .describe("this is describe2")
                .principal("jason is principal2").build();

        List<Department> list = new ArrayList<>();
        list.add(department);
        list.add(department1);

        when(departmentMapper.selectAll()).thenReturn(list);

        PageParam pageParam = new PageParam(1,5);

        PageInfo pageInfo = departmentService.getDepartmentList(pageParam);

        verify(departmentMapper,times(1)).selectAll();
        System.out.println(pageInfo);

        Assertions.assertAll(
                ()-> assertEquals(1,pageInfo.getPageNum()),
                ()-> assertEquals(2,pageInfo.getPageSize()),
                ()-> assertEquals(10,((Department)pageInfo.getList().get(0)).getId()),
                ()-> assertEquals("department10",((Department)pageInfo.getList().get(0)).getName()),
                ()-> assertEquals("this is describe",((Department)pageInfo.getList().get(0)).getDescribe()),

                ()-> assertEquals(11,((Department)pageInfo.getList().get(1)).getId()),
                ()-> assertEquals("department11",((Department)pageInfo.getList().get(1)).getName()),
                ()-> assertEquals("this is describe2",((Department)pageInfo.getList().get(1)).getDescribe())

        );
    }

    @Test
    @DisplayName("根据部门id删除部门")
    void delete_department_by_id(){
        departmentService.delete(10);

        verify(departmentMapper,times(1)).deleteByPrimaryKey(10);
    }


    @Test
    @DisplayName("修改部门信息")
    void mod_department_info(){
        Department department = Department.builder()
                .id(10)
                .name("department_10")
                .describe("this is describe")
                .principal("jason is principal").build();

        departmentService.addDepartment(department);

        verify(departmentMapper,times(1)).insert(department);

        department.setName("department_11");

        departmentService.modDepartment(department);

        verify(departmentMapper,times(1)).updateByPrimaryKey(department);
    }

}
