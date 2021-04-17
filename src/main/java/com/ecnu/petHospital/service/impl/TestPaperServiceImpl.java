package com.ecnu.petHospital.service.impl;

import com.ecnu.petHospital.dao.TestPaperMapper;
import com.ecnu.petHospital.dao.TestQuestionMapper;
import com.ecnu.petHospital.entity.Question;
import com.ecnu.petHospital.entity.TestPaper;
import com.ecnu.petHospital.entity.TestQuestion;
import com.ecnu.petHospital.param.PageParam;
import com.ecnu.petHospital.param.PaperParam;
import com.ecnu.petHospital.service.TestPaperService;
import com.ecnu.petHospital.vo.PaperVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class TestPaperServiceImpl implements TestPaperService {

    @Autowired
    private TestPaperMapper testPaperMapper;
    @Autowired
    private TestQuestionMapper testQuestionMapper;



    @Override
    public PageInfo<TestPaper> getTestPaperList(PageParam pageParam) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize(), "id desc");
        List<TestPaper> testPaperList = testPaperMapper.selectAll();
        return new PageInfo<>(testPaperList);
    }

    @Override
    @Transactional
    public boolean createTestPaper(PaperParam paperParam) {

        TestPaper testPaper = new TestPaper().setName(paperParam.getName()).setScore(paperParam.getScore());
        int paperId = testPaperMapper.insert(testPaper);
        int i = 1;
        for (Integer questionId : paperParam.getQuestionList()) {
            TestQuestion testQuestion = new TestQuestion()
                    .setQuestionId(questionId)
                    .setQuestionNumber(i++)
                    .setPaperId(paperId);
            testQuestionMapper.insert(testQuestion);
        }
        return true;
    }

    @Override
    public PaperVO getTestPaper(Integer id) {

        TestPaper testPaper = testPaperMapper.selectByPrimaryKey(id);
        List<Question> questionList = testQuestionMapper.getTestQuestionList(id);

        return new PaperVO()
                .setId(testPaper.getId())
                .setName(testPaper.getName())
                .setScore(testPaper.getScore())
                .setQuestionList(questionList);
    }

    @Override
    @Transactional
    public boolean deleteTestPaper(Integer id) {

        testPaperMapper.deleteByPrimaryKey(id);
        return true;
    }

}
