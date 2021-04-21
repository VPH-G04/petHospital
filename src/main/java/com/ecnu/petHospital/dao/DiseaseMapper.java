package com.ecnu.petHospital.dao;

import com.ecnu.petHospital.entity.Disease;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DiseaseMapper extends MyMapper<Disease>{

    @Select("select * from disease where class_id=#{classId};")
    List<Disease> getDiseaseByClassId(@Param("classId") Integer classId);

    @Delete("delete from disease where class_id=#{classId};")
    Integer deleteDiseasesByClassId(@Param("classId")Integer classId);

}
