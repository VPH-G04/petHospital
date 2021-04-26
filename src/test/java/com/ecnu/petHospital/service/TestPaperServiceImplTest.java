package com.ecnu.petHospital.service;

import com.ecnu.petHospital.dao.TestPaperMapper;
import com.ecnu.petHospital.dao.TestQuestionMapper;
import com.ecnu.petHospital.entity.Question;
import com.ecnu.petHospital.entity.TestPaper;
import com.ecnu.petHospital.entity.TestQuestion;
import com.ecnu.petHospital.param.PageParam;
import com.ecnu.petHospital.param.PaperParam;
import com.ecnu.petHospital.service.impl.TestPaperServiceImpl;
import com.ecnu.petHospital.vo.PaperVO;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TestPaperServiceImplTest {

    @InjectMocks
    private TestPaperServiceImpl testPaperService;

    @Mock
    private TestPaperMapper testPaperMapper;

    @Mock
    private TestQuestionMapper testQuestionMapper;

    @Test
    @DisplayName("成功获取试卷列表")
    void getTestPaperList() {
        PageParam pageParam = PageParam.builder()
                .pageNum(1)
                .pageSize(10)
                .build();
        TestPaper testPaper1 = new TestPaper()
                .setId(1)
                .setName("paper1")
                .setScore(100);
        TestPaper testPaper2 = new TestPaper()
                .setId(2)
                .setName("paper2")
                .setScore(100);

        when(testPaperMapper.selectAll()).thenReturn(new ArrayList<>(Arrays.asList(testPaper2,testPaper1)));

        PageInfo pageInfo = testPaperService.getTestPaperList(pageParam);
        verify(testPaperMapper, times(1)).selectAll();
        Assertions.assertAll(
                ()-> assertEquals(1,pageInfo.getPageNum()),
                ()-> assertEquals(2,pageInfo.getPageSize()),
                ()-> assertEquals(testPaper2.getId(),((TestPaper)pageInfo.getList().get(0)).getId()),
                ()-> assertEquals(testPaper2.getName(),((TestPaper)pageInfo.getList().get(0)).getName()),
                ()-> assertEquals(testPaper2.getScore(),((TestPaper)pageInfo.getList().get(0)).getScore()),
                ()-> assertEquals(testPaper1.getId(),((TestPaper)pageInfo.getList().get(1)).getId()),
                ()-> assertEquals(testPaper1.getName(),((TestPaper)pageInfo.getList().get(1)).getName()),
                ()-> assertEquals(testPaper1.getScore(),((TestPaper)pageInfo.getList().get(1)).getScore())
        );

    }

    @Test
    @DisplayName("成功获取所有试卷")
    void getAllPaper() {
        testPaperService.getAllPaper();
        verify(testPaperMapper, times(1)).selectAll();
    }

    @Test
    @DisplayName("成功创建试卷")
    void createTestPaper() {
        PaperParam paperParam = PaperParam.builder()
                .name("paper1")
                .score(100)
                .questionList(new ArrayList<>(Arrays.asList(2,6,4,7))).build();

        TestPaper testPaper = new TestPaper().setName(paperParam.getName()).setScore(paperParam.getScore());

        Boolean res = testPaperService.createTestPaper(paperParam);
        verify(testPaperMapper, times(1)).insert(testPaper);
        verify(testQuestionMapper,times(4)).insert(any());
        assertEquals(true,res);
    }

    @Test
    @DisplayName("成功根据id获取试卷")
    void getTestPaper() {
        Integer id = 1;
        TestPaper testPaper = new TestPaper().setId(1).setName("paper1").setScore(100);
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
        List<Question> questions = new ArrayList<>(Arrays.asList(question1, question2));
        when(testPaperMapper.selectByPrimaryKey(id)).thenReturn(testPaper);
        when(testQuestionMapper.getTestQuestionList(id))
                .thenReturn(questions);
        PaperVO paperVO = testPaperService.getTestPaper(id);
        verify(testPaperMapper, times(1)).selectByPrimaryKey(id);
        verify(testQuestionMapper, times(1)).getTestQuestionList(id);
        Assertions.assertAll(
                ()-> assertEquals(testPaper.getId(), paperVO.getId()),
                ()-> assertEquals(testPaper.getName(), paperVO.getName()),
                ()-> assertEquals(testPaper.getScore(), paperVO.getScore()),
                ()-> assertEquals(questions, paperVO.getQuestionList())
        );
    }

    @Test
    @DisplayName("成功根据id删除试卷")
    void deleteTestPaper() {
        Integer id = 1;
        Boolean res = testPaperService.deleteTestPaper(id);
        verify(testPaperMapper, times(1)).deleteByPrimaryKey(id);
        assertEquals(true, res);
    }
}