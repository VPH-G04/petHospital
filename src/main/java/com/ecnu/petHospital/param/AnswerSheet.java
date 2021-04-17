package com.ecnu.petHospital.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerSheet {

    private Integer userId;

    private Integer testId;

    private Integer score;

}
