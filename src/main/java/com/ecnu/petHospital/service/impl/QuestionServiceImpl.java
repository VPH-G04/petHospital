package com.ecnu.petHospital.service.impl;

import com.ecnu.petHospital.dao.QuestionMapper;
import com.ecnu.petHospital.entity.Question;
import com.ecnu.petHospital.param.PageParam;
import com.ecnu.petHospital.param.QuestionParam;
import com.ecnu.petHospital.service.QuestionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    @Override
    public PageInfo<Question> getQuestionList(Integer diseaseId, PageParam pageParam) {

        PageHelper.startPage(pageParam.getPageNum(),pageParam.getPageSize());
        Question question = new Question().setDiseaseId(diseaseId);
        List<Question> questionList = questionMapper.select(question);
        return new PageInfo<>(questionList);
    }

    @Override
    public Question getQuestion(Integer questionId) {

        return questionMapper.selectByPrimaryKey(questionId);
    }

    @Override
    @Transactional
    public boolean createQuestion(QuestionParam questionParam) {
        Question question = new Question()
                .setDiseaseId(questionParam.getDiseaseId())
                .setTitle(questionParam.getTitle())
                .setA(questionParam.getA())
                .setB(questionParam.getB())
                .setC(questionParam.getC())
                .setD(questionParam.getD())
                .setAnswer(questionParam.getAnswer());
        questionMapper.insert(question);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteQuestion(Integer id) {

        questionMapper.deleteByPrimaryKey(id);
        return true;
    }


}
