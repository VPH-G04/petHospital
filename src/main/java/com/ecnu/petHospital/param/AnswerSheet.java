package com.ecnu.petHospital.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerSheet {

    private Integer userId;

    private Integer testId;

    private Integer score;

}
