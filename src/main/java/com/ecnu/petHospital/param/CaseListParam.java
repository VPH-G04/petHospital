package com.ecnu.petHospital.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaseListParam {

    Integer id;

    Integer disease_id;

    String name;

}
