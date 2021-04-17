package com.ecnu.petHospital.service;

import com.ecnu.petHospital.entity.Question;
import com.ecnu.petHospital.param.PageParam;
import com.ecnu.petHospital.param.QuestionParam;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

@Service
public interface QuestionService {

    PageInfo<Question> getQuestionList(Integer diseaseId, PageParam pageParam);

    Question getQuestion(Integer questionId);

    boolean createQuestion(QuestionParam questionParam);

    boolean deleteQuestion(Integer id);
}
