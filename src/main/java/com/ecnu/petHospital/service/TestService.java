package com.ecnu.petHospital.service;

import com.ecnu.petHospital.entity.Test;
import com.ecnu.petHospital.entity.TestLog;
import com.ecnu.petHospital.param.AnswerSheet;
import com.ecnu.petHospital.param.PageParam;
import com.ecnu.petHospital.param.TestParam;
import com.github.pagehelper.PageInfo;


public interface TestService {

    PageInfo<Test> getTestList(PageParam pageParam);

    boolean createTest(TestParam testParam);

    boolean deleteTest(Integer id);

    Test getTest(Integer id);

    boolean doTest(AnswerSheet answerSheet);

    TestLog getTestLog(Integer testId, Integer userId);
}
