package com.ecnu.petHospital.dao;

import com.ecnu.petHospital.entity.CasePicture;
import com.ecnu.petHospital.param.CaseListParam;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CasePictureMapper extends MyMapper<CasePicture> {

    @Insert("insert into case_picture(case_id,url,`describe`,`procedure`) values (#{case_id},#{url},#{describe},#{procedure});")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insertPicture(CasePicture casePicture);

    @Select("select * from case_picture where case_id = #{caseId} order by id desc")
    List<CasePicture> getPicturesByCaseId(@Param("caseId") Integer caseId);

    @Delete("delete from case_picture where case_id=#{id}")
    Integer deletePictureByCaseId(@Param("id") Integer id);
}
