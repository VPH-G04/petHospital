package com.ecnu.petHospital.dao;

import com.ecnu.petHospital.entity.LearningPicture;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LearningPictureMapper extends MyMapper<LearningPicture>{
    @Select("select * from `learning_picture` where resource_id = #{resourceId} order by id asc")
    List<LearningPicture> getPictureByResourceId(@Param("resourceId") Integer resourceId);

    @Delete("delete from  `learning_picture` where resource_id = #{resourceId}")
    Integer deleteById(@Param("resourceId") Integer resourceId);
}
