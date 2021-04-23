package com.ecnu.petHospital.service.impl;

import com.ecnu.petHospital.dao.TestLogMapper;
import com.ecnu.petHospital.dao.TestMapper;
import com.ecnu.petHospital.entity.Test;
import com.ecnu.petHospital.entity.TestLog;
import com.ecnu.petHospital.param.AnswerSheet;
import com.ecnu.petHospital.param.PageParam;
import com.ecnu.petHospital.param.TestParam;
import com.ecnu.petHospital.service.TestPaperService;
import com.ecnu.petHospital.service.TestService;
import com.ecnu.petHospital.vo.PaperVO;
import com.ecnu.petHospital.vo.TestVO;
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
    @Autowired
    private TestPaperService testPaperService;


    @Override
    public PageInfo<Test> getTestList(PageParam pageParam) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<Test> testList = testMapper.selectAll();
        return new PageInfo<>(testList);
    }

    @Override
    @Transactional
    public boolean createTest(TestParam testParam) {

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startTime = LocalDateTime.parse(testParam.getStartTime(), df);
        LocalDateTime endTime = LocalDateTime.parse(testParam.getEndTime(), df);

        Test test = new Test()
                .setName(testParam.getName())
                .setPaperId(testParam.getPaperId())
                .setStart(startTime)
                .setEnd(endTime)
                .setDuration(testParam.getDuration());
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
    public TestVO getTest(Integer testId,Integer userId) {

        Test test = testMapper.selectByPrimaryKey(testId);
        PaperVO paperVO = testPaperService.getTestPaper(test.getPaperId());
        TestLog testLog = testLogMapper.selectOne(new TestLog().setUserId(userId).setTestId(testId));
        TestVO testVO = new TestVO(test, paperVO);
        if(testLog == null) {
            testVO.setMarked(false);
        }
        else {
            testVO.setMarked(true);
            testVO.setUserScore(testLog.getScore()).setSubmitTime(testLog.getSubmitTime());
        }
        return testVO;
    }

    @Override
    @Transactional
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

    @Override
    public PageInfo<TestLog> getTestLogList(Integer testId, PageParam pageParam) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<TestLog> testLogList = testLogMapper.select(new TestLog().setTestId(testId));
        return new PageInfo<>(testLogList);
    }

    @Override
    @Transactional
    public boolean deleteTestLog(Integer id) {
        testLogMapper.deleteByPrimaryKey(id);
        return true;
    }

}
