package com.ecnu.petHospital.dao;

import com.ecnu.petHospital.entity.Question;
import com.ecnu.petHospital.entity.TestQuestion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TestQuestionMapper extends MyMapper<TestQuestion> {

    @Select("select q.* from test_question tq join question q where tq.question_id = q.id and tq.paper_id = #{paperId} order by tq.question_number asc;")
    List<Question> getTestQuestionList(@Param("paperId") Integer paperId);

}
