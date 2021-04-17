package com.ecnu.petHospital.service.impl;

import com.ecnu.petHospital.dao.TestLogMapper;
import com.ecnu.petHospital.dao.TestMapper;
import com.ecnu.petHospital.entity.Test;
import com.ecnu.petHospital.entity.TestLog;
import com.ecnu.petHospital.param.AnswerSheet;
import com.ecnu.petHospital.param.PageParam;
import com.ecnu.petHospital.param.TestParam;
import com.ecnu.petHospital.service.TestService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;
    @Autowired
    private TestLogMapper testLogMapper;



    @Override
    public PageInfo<Test> getTestList(PageParam pageParam) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<Test> testList = testMapper.selectAll();
        return new PageInfo<>(testList);
    }

    @Override
    @Transactional
    public boolean createTest(TestParam testParam) {

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(testParam.getStartTime(), df);
        LocalDateTime endTime = LocalDateTime.parse(testParam.getEndTime(), df);

        Test test = new Test()
                .setName(testParam.getName())
                .setPaperId(testParam.getPaperId())
                .setStart(startTime)
                .setEnd(endTime);
        testMapper.insert(test);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteTest(Integer id) {

        testMapper.deleteByPrimaryKey(id);
        return true;
    }

    @Override
    public Test getTest(Integer id) {

        return testMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean doTest(AnswerSheet answerSheet) {

        TestLog testLog = new TestLog()
                .setTestId(answerSheet.getTestId())
                .setUserId(answerSheet.getUserId())
                .setScore(answerSheet.getScore())
                .setSubmitTime(LocalDateTime.now());

        testLogMapper.insert(testLog);
        return true;
    }

    @Override
    public TestLog getTestLog(Integer testId, Integer userId) {
        TestLog testLog = new TestLog().setTestId(testId).setUserId(userId);
        return testLogMapper.selectOne(testLog);
    }

}
