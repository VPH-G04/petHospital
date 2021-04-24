package com.ecnu.petHospital.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVO {
    private Integer id;

    private String name;

    LearningResourceVO learningResourceVO;

}
