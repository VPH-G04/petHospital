package com.ecnu.petHospital.controller;

import com.ecnu.petHospital.entity.Disease;
import com.ecnu.petHospital.result.CommonResult;
import com.ecnu.petHospital.result.Result;
import com.ecnu.petHospital.service.DiseaseService;
import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/disease")
@Api("病种管理")
public class DiseaseController {

    @Autowired
    DiseaseService diseaseService;

    @PostMapping("/createClass")
    public Result<?> createDiseaseClass(@RequestParam String diseaseClassName){
        diseaseService.createDiseaseClass(diseaseClassName);
        return CommonResult.success();
    }

    @PostMapping("/create")
    public Result<?> createDisease(@RequestBody Disease disease){
        diseaseService.createDisease(disease);
        return CommonResult.success().data(disease);
    }

    @PostMapping("/getClasses")
    public Result<?> getDiseaseClassList(){
        return CommonResult.success().data(diseaseService.getDiseaseClass());
    }

    @PostMapping("/get")
    public Result<?> getDiseaseByClassId(@RequestParam Integer diseaseClassId){
        return CommonResult.success().data(diseaseService.getDiseaseByClassId(diseaseClassId));
    }

    @PostMapping("/getAll")
    public Result<?> getAllDiseases(){
        return CommonResult.success().data(diseaseService.getAllDiseases());
    }

    @PostMapping("/deleteClass")
    public Result<?> deleteClassById(@RequestParam Integer id){
        diseaseService.deleteDiseaseClassById(id);
        return CommonResult.success();
    }

    @PostMapping("/delete")
    public Result<?> deleteDiseaseById(@RequestParam Integer id){
        diseaseService.deleteDiseaseById(id);
        return CommonResult.success();
    }
}


