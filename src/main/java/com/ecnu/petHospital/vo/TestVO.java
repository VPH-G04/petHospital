package com.ecnu.petHospital.vo;

import com.ecnu.petHospital.entity.Question;
import com.ecnu.petHospital.entity.Test;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TestVO {

    private Integer id;

    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime start;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime end;

    private Integer duration;

    private Integer paperId;

    private String paperName;

    private Integer questionNum;

    private Integer score;

    private List<Question> questionList;

    private boolean marked;

    private Integer userScore;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime submitTime;

    public TestVO(Test test, PaperVO paper){
        this.id = test.getId();
        this.name = test.getName();
        this.start = test.getStart();
        this.end = test.getEnd();
        this.duration = test.getDuration();
        this.paperId = paper.getId();
        this.paperName = paper.getName();
        this.questionNum = paper.getQuestionList().size();
        this.questionList = paper.getQuestionList();
        this.score = paper.getScore();
    }
}
