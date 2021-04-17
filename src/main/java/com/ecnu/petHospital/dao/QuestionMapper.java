package com.ecnu.petHospital.dao;

import com.ecnu.petHospital.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper extends MyMapper<Question> {

    @Select("select * from question where disease_id = #{diseaseId} order by id desc")
    List<Question> getQuestionList(@Param("diseaseId") Integer diseaseId);
}
