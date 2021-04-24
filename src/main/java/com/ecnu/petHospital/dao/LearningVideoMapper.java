package com.ecnu.petHospital.dao;

import com.ecnu.petHospital.entity.LearningVideo;
import com.ecnu.petHospital.param.CaseListParam;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LearningVideoMapper extends MyMapper<LearningVideo>{
    @Select("select * from `learning_video` where resource_id = #{resourceId} order by id asc")
    List<LearningVideo> getVideoByResourceId(@Param("resourceId") Integer resourceId);

    @Delete("delete from  `learning_video` where resource_id = #{resourceId}")
    Integer deleteById(@Param("resourceId") Integer resourceId);
}
