package com.ecnu.petHospital.dao;

import com.ecnu.petHospital.entity.DiseaseClassification;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DiseaseClassMapper extends MyMapper<DiseaseClassification> {
}
