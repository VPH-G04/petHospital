package com.ecnu.petHospital.service;

import com.ecnu.petHospital.dao.QuestionMapper;
import com.ecnu.petHospital.entity.Question;
import com.ecnu.petHospital.param.PageParam;
import com.ecnu.petHospital.param.QuestionParam;
import com.ecnu.petHospital.service.impl.QuestionServiceImpl;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class QuestionServiceImplTest {

    @InjectMocks
    private QuestionServiceImpl questionService;

    @Mock
    private QuestionMapper questionMapper;

    @Test
    @DisplayName("成功根据病种id获取问题列表")
    void getQuestionList() {
        Integer diseaseId = 1;
        PageParam pageParam = PageParam.builder()
                .pageNum(1)
                .pageSize(10)
                .build();
        Question question = Question.builder()
                .diseaseId(diseaseId).build();
        Question question1 = Question.builder()
                .id(1)
                .diseaseId(1)
                .title("title1")
                .a("a1")
                .b("b1")
                .c("c1")
                .d("d1")
                .answer("a1").build();
        Question question2 = Question.builder()
                .id(2)
                .diseaseId(1)
                .title("title2")
                .a("a2")
                .b("b2")
                .c("c2")
                .d("d2")
                .answer("b2").build();

        when(questionMapper.select(question)).thenReturn(new ArrayList<>(Arrays.asList(question1,question2)));

        PageInfo pageInfo = questionService.getQuestionList(diseaseId,pageParam);
        verify(questionMapper, times(1)).select(question);
        Assertions.assertAll(
                ()-> assertEquals(1,pageInfo.getPageNum()),
                ()-> assertEquals(2,pageInfo.getPageSize()),
                ()-> assertEquals(question1.getId(),((Question)pageInfo.getList().get(0)).getId()),
                ()-> assertEquals(question1.getDiseaseId(),((Question)pageInfo.getList().get(0)).getDiseaseId()),
                ()-> assertEquals(question1.getTitle(),((Question)pageInfo.getList().get(0)).getTitle()),
                ()-> assertEquals(question1.getAnswer(),((Question)pageInfo.getList().get(0)).getAnswer()),
                ()-> assertEquals(question2.getId(),((Question)pageInfo.getList().get(1)).getId()),
                ()-> assertEquals(question2.getDiseaseId(),((Question)pageInfo.getList().get(1)).getDiseaseId()),
                ()-> assertEquals(question2.getTitle(),((Question)pageInfo.getList().get(1)).getTitle()),
                ()-> assertEquals(question2.getAnswer(),((Question)pageInfo.getList().get(1)).getAnswer())
        );

    }

    @Test
    @DisplayName("成功根据id获取问题信息")
    void getQuestion() {
        Integer questionId = 1;
        Question question = Question.builder()
                .id(1)
                .diseaseId(10)
                .title("title")
                .a("a")
                .b("b")
                .c("c")
                .d("d")
                .answer("c").build();

        when(questionMapper.selectByPrimaryKey(questionId)).thenReturn(question);
        Question returnQuestion = questionService.getQuestion(questionId);
        verify(questionMapper, times(1)).selectByPrimaryKey(questionId);
        Assertions.assertAll(
                ()-> assertEquals(question.getId(),returnQuestion.getId()),
                ()-> assertEquals(question.getDiseaseId(),returnQuestion.getDiseaseId()),
                ()-> assertEquals(question.getTitle(),returnQuestion.getTitle()),
                ()-> assertEquals(question.getAnswer(),returnQuestion.getAnswer()),
                ()-> assertEquals(question.getA(),returnQuestion.getA()),
                ()-> assertEquals(question.getB(),returnQuestion.getB()),
                ()-> assertEquals(question.getC(),returnQuestion.getC()),
                ()-> assertEquals(question.getD(),returnQuestion.getD())
        );

    }

    @Test
    @DisplayName("成功创建新问题")
    void createQuestion() {
        QuestionParam questionParam = QuestionParam.builder()
                .diseaseId(10)
                .title("title")
                .a("a")
                .b("b")
                .c("c")
                .d("d")
                .answer("c").build();

        Question question = Question.builder()
                .diseaseId(10)
                .title("title")
                .a("a")
                .b("b")
                .c("c")
                .d("d")
                .answer("c").build();

        when(questionMapper.insert(question)).thenReturn(1);
        Boolean res = questionService.createQuestion(questionParam);
        verify(questionMapper, times(1)).insert(question);
        assertEquals(true, res);

    }

    @Test
    @DisplayName("成功根据id删除问题")
    void deleteQuestion() {
        Integer id = 1;

        when(questionMapper.deleteByPrimaryKey(id)).thenReturn(1);
        Boolean res = questionService.deleteQuestion(id);
        verify(questionMapper, times(1)).deleteByPrimaryKey(id);
        assertEquals(true, res);
    }
}