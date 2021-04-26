package com.ecnu.petHospital.service;

import com.ecnu.petHospital.dao.TestLogMapper;
import com.ecnu.petHospital.dao.TestMapper;
import com.ecnu.petHospital.entity.Question;
import com.ecnu.petHospital.entity.TestLog;
import com.ecnu.petHospital.entity.TestPaper;
import com.ecnu.petHospital.param.AnswerSheet;
import com.ecnu.petHospital.param.PageParam;
import com.ecnu.petHospital.param.TestParam;
import com.ecnu.petHospital.service.impl.TestServiceImpl;
import com.ecnu.petHospital.vo.PaperVO;
import com.ecnu.petHospital.vo.TestVO;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TestServiceImplTest {

    @InjectMocks
    private TestServiceImpl testService;

    @Mock
    private TestMapper testMapper;
    @Mock
    private TestLogMapper testLogMapper;
    @Mock
    private TestPaperService testPaperService;

    @Test
    @DisplayName("成功获取测试列表")
    void getTestList() {
        PageParam pageParam = PageParam.builder()
                .pageNum(1)
                .pageSize(10)
                .build();
        com.ecnu.petHospital.entity.Test test = com.ecnu.petHospital.entity.Test.builder()
                .id(1)
                .paperId(2)
                .name("test1")
                .start(LocalDateTime.of(2021,4,20,12,00))
                .end(LocalDateTime.of(2021,4,20,14,00))
                .duration(120)
                .build();
        List<com.ecnu.petHospital.entity.Test> testList = new ArrayList<>(Arrays.asList(test));

        when(testMapper.selectAll()).thenReturn(testList);
        PageInfo pageInfo = testService.getTestList(pageParam);
        verify(testMapper,times(1)).selectAll();
        Assertions.assertAll(
                ()-> assertEquals(1,pageInfo.getPageNum()),
                ()-> assertEquals(1,pageInfo.getPageSize()),
                ()-> assertEquals(test.getId(),((com.ecnu.petHospital.entity.Test)pageInfo.getList().get(0)).getId()),
                ()-> assertEquals(test.getName(),((com.ecnu.petHospital.entity.Test)pageInfo.getList().get(0)).getName()),
                ()-> assertEquals(test.getPaperId(),((com.ecnu.petHospital.entity.Test)pageInfo.getList().get(0)).getPaperId()),
                ()-> assertEquals(test.getDuration(),((com.ecnu.petHospital.entity.Test)pageInfo.getList().get(0)).getDuration()),
                ()-> assertEquals(test.getStart(),((com.ecnu.petHospital.entity.Test)pageInfo.getList().get(0)).getStart()),
                ()-> assertEquals(test.getEnd(),((com.ecnu.petHospital.entity.Test)pageInfo.getList().get(0)).getEnd())
        );
    }

    @Test
    @DisplayName("成功创建测试")
    void createTest() {
        TestParam testParam = TestParam.builder()
                .paperId(1)
                .name("test1")
                .startTime("2021-04-20 12:00")
                .endTime("2021-04-20 14:00")
                .duration(120).build();

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startTime = LocalDateTime.parse(testParam.getStartTime(), df);
        LocalDateTime endTime = LocalDateTime.parse(testParam.getEndTime(), df);

        com.ecnu.petHospital.entity.Test test = new com.ecnu.petHospital.entity.Test()
                .setName(testParam.getName())
                .setPaperId(testParam.getPaperId())
                .setStart(startTime)
                .setEnd(endTime)
                .setDuration(testParam.getDuration());
        boolean res = testService.createTest(testParam);
        verify(testMapper, times(1)).insert(test);
        assertEquals(true, res);
    }

    @Test
    @DisplayName("成功根据id删除测试信息")
    void deleteTest() {
        Integer id = 1;
        Boolean res = testService.deleteTest(id);
        verify(testMapper, times(1)).deleteByPrimaryKey(id);
        assertEquals(true, res);
    }

    @Test
    @DisplayName("成功根据id获取测试信息")
    void get_Test_when_did_not_complete_test() {
        Integer testId = 1;
        Integer userId1 = 2;
        Integer userId2 = 3;

        com.ecnu.petHospital.entity.Test test = com.ecnu.petHospital.entity.Test.builder()
                .id(1)
                .paperId(2)
                .name("test1")
                .start(LocalDateTime.of(2021,4,20,12,00))
                .end(LocalDateTime.of(2021,4,20,14,00))
                .duration(120)
                .build();

        Question question1 = Question.builder()
                .id(1)
                .diseaseId(1)
                .title("title1")
                .a("a1")
                .b("b1")
                .c("c1")
                .d("d1")
                .answer("a1").build();
        List<Question> questions = new ArrayList<>(Arrays.asList(question1));
        PaperVO paperVO = new PaperVO().setId(2).setName("paper1").setScore(100).setQuestionList(questions);
        TestLog testLog1 = null;
        TestLog testLog2 = TestLog.builder()
                .id(1)
                .testId(1)
                .userId(3)
                .score(90)
                .submitTime(LocalDateTime.of(2021,4,20,13,00))
                .build();


        when(testMapper.selectByPrimaryKey(testId)).thenReturn(test);
        when(testPaperService.getTestPaper(test.getPaperId())).thenReturn(paperVO);
        when(testLogMapper.selectOne(new TestLog().setUserId(userId1).setTestId(testId))).thenReturn(testLog1);
        when(testLogMapper.selectOne(new TestLog().setUserId(userId2).setTestId(testId))).thenReturn(testLog2);

        //未完成试卷时
        TestVO testVO = testService.getTest(testId,userId1);
        verify(testMapper, times(1)).selectByPrimaryKey(testId);
        verify(testPaperService, times(1)).getTestPaper(test.getPaperId());
        verify(testLogMapper, times(1)).selectOne(new TestLog().setUserId(userId1).setTestId(testId));
        Assertions.assertAll(
                ()-> assertEquals(test.getId(),testVO.getId()),
                ()-> assertEquals(test.getPaperId(), testVO.getPaperId()),
                ()-> assertEquals(test.getName(),testVO.getName()),
                ()-> assertEquals(test.getStart(), testVO.getStart()),
                ()-> assertEquals(test.getEnd(), testVO.getEnd()),
                ()-> assertEquals(test.getDuration(), testVO.getDuration()),
                ()-> assertEquals(paperVO.getName(), testVO.getPaperName()),
                ()-> assertEquals(paperVO.getScore(), testVO.getScore()),
                ()-> assertEquals(paperVO.getQuestionList(), testVO.getQuestionList()),
                ()-> assertEquals(paperVO.getQuestionList().size(),testVO.getQuestionNum()),
                ()-> assertEquals(false, testVO.isMarked())
        );

        //完成试卷时
        TestVO testVO1 = testService.getTest(testId,userId2);
        verify(testMapper, times(2)).selectByPrimaryKey(testId);
        verify(testPaperService, times(2)).getTestPaper(test.getPaperId());
        verify(testLogMapper, times(1)).selectOne(new TestLog().setUserId(userId2).setTestId(testId));
        Assertions.assertAll(
                ()-> assertEquals(test.getId(),testVO1.getId()),
                ()-> assertEquals(test.getPaperId(), testVO1.getPaperId()),
                ()-> assertEquals(test.getName(),testVO1.getName()),
                ()-> assertEquals(test.getStart(), testVO1.getStart()),
                ()-> assertEquals(test.getEnd(), testVO1.getEnd()),
                ()-> assertEquals(test.getDuration(), testVO1.getDuration()),
                ()-> assertEquals(paperVO.getName(), testVO1.getPaperName()),
                ()-> assertEquals(paperVO.getScore(), testVO1.getScore()),
                ()-> assertEquals(paperVO.getQuestionList(), testVO1.getQuestionList()),
                ()-> assertEquals(paperVO.getQuestionList().size(),testVO1.getQuestionNum()),
                ()-> assertEquals(true, testVO1.isMarked())
        );

    }

    @Test
    @DisplayName("成功记录测试内容")
    void doTest() {
        AnswerSheet answerSheet = AnswerSheet.builder()
                .testId(1)
                .userId(2)
                .score(90)
                .build();

        TestLog testLog = new TestLog()
                .setTestId(answerSheet.getTestId())
                .setUserId(answerSheet.getUserId())
                .setScore(answerSheet.getScore())
                .setSubmitTime(LocalDateTime.now());

        boolean res = testService.doTest(answerSheet);
        verify(testLogMapper, times(1)).insert(testLog);
        assertEquals(true, res);
    }

    @Test
    @DisplayName("成功根据id获取测试记录信息")
    void getTestLog() {
        Integer testId = 1;
        Integer userId = 2;

        TestLog testLog = new TestLog().setTestId(testId).setUserId(userId);
        when(testLogMapper.selectOne(testLog))
                .thenReturn(new TestLog().setId(2).setTestId(1).setUserId(2).setScore(90).setSubmitTime(LocalDateTime.of(2021,4,20,13,00)));
        TestLog res = testService.getTestLog(testId,userId);
        verify(testLogMapper, times(1)).selectOne(testLog);
        Assertions.assertAll(
                ()-> assertEquals(2,res.getId()),
                ()-> assertEquals(testId,res.getTestId()),
                ()-> assertEquals(userId, res.getUserId()),
                ()-> assertEquals(90, res.getScore()),
                ()-> assertEquals(LocalDateTime.of(2021,4,20,13,00), res.getSubmitTime())
        );

    }

    @Test
    @DisplayName("成功获取测试记录列表")
    void getTestLogList() {
        Integer testId = 1;
        PageParam pageParam = PageParam.builder()
                .pageNum(1)
                .pageSize(10).build();

        TestLog testLog = TestLog.builder()
                .id(1)
                .testId(1)
                .userId(3)
                .score(90)
                .submitTime(LocalDateTime.of(2021,4,20,13,00))
                .build();

        List<TestLog> testLogs = new ArrayList<>(Arrays.asList(testLog));

        when(testLogMapper.select(new TestLog().setTestId(testId))).thenReturn(testLogs);
        PageInfo pageInfo = testService.getTestLogList(testId,pageParam);
        verify(testLogMapper,times(1)).select(new TestLog().setTestId(testId));
        Assertions.assertAll(
                ()-> assertEquals(1,pageInfo.getPageNum()),
                ()-> assertEquals(1,pageInfo.getPageSize()),
                ()-> assertEquals(testLog.getId(),((TestLog)pageInfo.getList().get(0)).getId()),
                ()-> assertEquals(testLog.getTestId(),((TestLog)pageInfo.getList().get(0)).getTestId()),
                ()-> assertEquals(testLog.getUserId(),((TestLog)pageInfo.getList().get(0)).getUserId()),
                ()-> assertEquals(testLog.getScore(),((TestLog)pageInfo.getList().get(0)).getScore()),
                ()-> assertEquals(testLog.getSubmitTime(),((TestLog)pageInfo.getList().get(0)).getSubmitTime())
        );

    }

    @Test
    @DisplayName("成功删除测试记录")
    void deleteTestLog() {
        Integer id = 1;
        Boolean res = testService.deleteTestLog(id);
        verify(testLogMapper, times(1)).deleteByPrimaryKey(id);
        assertEquals(true, res);
    }
}