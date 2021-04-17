package com.ecnu.petHospital.dao;

import com.ecnu.petHospital.entity.CasePicture;
import com.ecnu.petHospital.entity.CaseVideo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CaseVideoMapper extends MyMapper<CaseVideo>{

    @Insert("insert into case_video(case_id,url,`describe`,`procedure`) values (#{case_id},#{url},#{describe},#{procedure});")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insertVideo(CaseVideo caseVideo);

    @Select("select * from case_video where case_id = #{caseId} order by id desc")
    List<CaseVideo> getVideosByCaseId(@Param("caseId") Integer caseId);

    @Delete("delete from case_picture where case_id=#{id}")
    Integer deleteVideoByCaseId(@Param("id") Integer id);
}
