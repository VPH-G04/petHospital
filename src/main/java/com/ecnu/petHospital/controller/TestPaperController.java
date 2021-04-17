package com.ecnu.petHospital.controller;

import com.ecnu.petHospital.param.PageParam;
import com.ecnu.petHospital.param.PaperParam;
import com.ecnu.petHospital.result.CommonResult;
import com.ecnu.petHospital.result.Result;
import com.ecnu.petHospital.service.TestPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/paper")
public class TestPaperController {

    @Autowired
    private TestPaperService testPaperService;

    @PostMapping("/getPaperList")
    public Result<?> getTestPaperList(@RequestBody PageParam pageParam){

        return CommonResult.success().data(testPaperService.getTestPaperList(pageParam));
    }

    @PostMapping("/createPaper")
    public Result<?> createTestPaper(@RequestBody PaperParam paperParam){
        testPaperService.createTestPaper(paperParam);
        return CommonResult.success();
    }

    @PostMapping("/getPaper")
    public Result<?> getTestPaper(@RequestParam Integer paperId){

        return CommonResult.success().data(testPaperService.getTestPaper(paperId));
    }

    @PostMapping("/deletePaper")
    public Result<?> deleteTestPaper(@RequestParam Integer paperId){
        testPaperService.deleteTestPaper(paperId);
        return CommonResult.success();
    }
}
