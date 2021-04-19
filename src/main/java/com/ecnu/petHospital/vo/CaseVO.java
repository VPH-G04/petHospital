package com.ecnu.petHospital.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaseVO {

    Integer id;

    Integer disease_id;

    String name;

    List<ProcedureVO> procedureVOS = new ArrayList<>();
}
