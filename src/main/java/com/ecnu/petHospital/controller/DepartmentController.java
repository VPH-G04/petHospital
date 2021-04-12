package com.ecnu.petHospital.controller;

import com.ecnu.petHospital.entity.Department;
import com.ecnu.petHospital.entity.common.PageParam;
import com.ecnu.petHospital.result.CommonResult;
import com.ecnu.petHospital.result.Result;
import com.ecnu.petHospital.service.DepartmentService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Resource
    private DepartmentService departmentService;

    @GetMapping("/getDepartment")
    @ApiOperation(value = "获取科室信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="departmentId",value = "科室id",required = true)
    })
    public Result<?> get(int departmentId){
        return CommonResult.success().data(departmentService.getOne(departmentId));
    }

    @PostMapping("/add")
    @ApiOperation(value = "新建科室",notes = "不需要科室id")
    @ApiImplicitParams({
    })
    public Result<?> add(Department department){

        return CommonResult.success().data(departmentService.addDepartment(department));
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "删除科室")
    @ApiImplicitParams({
            @ApiImplicitParam(name="departmentId",value = "科室id",required = true)
    })
    public Result<?> del(int departmentId){
        return CommonResult.success().data(departmentService.delete(departmentId));
    }

    @PutMapping("/mod")
    @ApiOperation(value = "修改科室")
    @ApiImplicitParams({
            @ApiImplicitParam(name="department",value = "科室信息",required = true)
    })
    public Result<?> mod(Department department){
        return CommonResult.success().data(departmentService.modDepartment(department));
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取所有科室",notes = "不传参默认为请求第一页，每页为10个")
    @ApiImplicitParams({

    })
    public Result<?> list(PageParam pageParam){
        return CommonResult.success().data(departmentService.getDepartmentList(pageParam));
    }
}
