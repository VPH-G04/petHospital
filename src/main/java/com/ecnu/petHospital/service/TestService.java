package com.ecnu.petHospital.service;

import com.ecnu.petHospital.entity.Test;
import com.ecnu.petHospital.entity.TestLog;
import com.ecnu.petHospital.param.AnswerSheet;
import com.ecnu.petHospital.param.PageParam;
import com.ecnu.petHospital.param.TestParam;
import com.ecnu.petHospital.vo.TestVO;
import com.github.pagehelper.PageInfo;


public interface TestService {

    PageInfo<Test> getTestList(PageParam pageParam);

    boolean createTest(TestParam testParam);

    boolean deleteTest(Integer id);

    TestVO getTest(Integer testId, Integer userId);

    boolean doTest(AnswerSheet answerSheet);

    TestLog getTestLog(Integer testId, Integer userId);

    PageInfo<TestLog> getTestLogList(Integer testId, PageParam pageParam);

    boolean deleteTestLog(Integer id);
}
