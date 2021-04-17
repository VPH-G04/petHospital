package com.ecnu.petHospital.controller;

import com.ecnu.petHospital.param.PageParam;
import com.ecnu.petHospital.param.QuestionParam;
import com.ecnu.petHospital.result.CommonResult;
import com.ecnu.petHospital.result.Result;
import com.ecnu.petHospital.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@CrossOrigin
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/getQuestionList")
    public Result<?> getQuestionList(@RequestParam Integer diseaseId,
                                     @RequestBody PageParam pageParam) {

        return CommonResult.success().data(questionService.getQuestionList(diseaseId, pageParam));
    }

    @PostMapping("/getQuestion")
    public Result<?> getQuestion(@RequestParam Integer questionId) {

        return CommonResult.success().data(questionService.getQuestion(questionId));
    }


    @PostMapping("/createQuestion")
    public Result<?> createQuestion(@RequestBody QuestionParam questionParam) {
        questionService.createQuestion(questionParam);
        return CommonResult.success();
    }

    @PostMapping("/deleteQuestion")
    public Result<?> deleteQuestion(@RequestParam Integer questionId) {
        questionService.deleteQuestion(questionId);
        return CommonResult.success();
    }


}
