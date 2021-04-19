package com.ecnu.petHospital.controller;

import com.ecnu.petHospital.param.AnswerSheet;
import com.ecnu.petHospital.param.PageParam;
import com.ecnu.petHospital.param.TestParam;
import com.ecnu.petHospital.result.CommonResult;
import com.ecnu.petHospital.result.Result;
import com.ecnu.petHospital.service.TestService;
import com.ecnu.petHospital.session.UserSessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @PostMapping("/getTestList")
    public Result<?> getTestList(@RequestBody PageParam pageParam){

        return CommonResult.success().data(testService.getTestList(pageParam));
    }

    @PostMapping("/createTest")
    public Result<?> createTest(@RequestBody TestParam testParam){
        testService.createTest(testParam);
        return CommonResult.success();
    }

    @PostMapping("/deleteTest")
    public Result<?> deleteTest(@RequestParam Integer testId){
        testService.deleteTest(testId);
        return CommonResult.success();
    }

    @PostMapping("/getTest")
    public Result<?> getTest(@SessionAttribute UserSessionInfo userSessionInfo, @RequestParam Integer testId){

        if(userSessionInfo == null) {
            return CommonResult.accessDenied();
        }
        return CommonResult.success().data(testService.getTest(testId, userSessionInfo.getId()));
    }

    @PostMapping("/doTest")
    public Result<?> doTest(@SessionAttribute UserSessionInfo userSessionInfo, @RequestBody AnswerSheet answerSheet){
        answerSheet.setUserId(userSessionInfo.getId());
        testService.doTest(answerSheet);
        return CommonResult.success();
    }

    @PostMapping("/getTestLog")
    public Result<?> getTestLog(@RequestParam Integer testId, @RequestParam Integer userId){

        return CommonResult.success().data(testService.getTestLog(testId, userId));
    }
}
