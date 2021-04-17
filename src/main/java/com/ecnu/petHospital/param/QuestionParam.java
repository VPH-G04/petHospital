package com.ecnu.petHospital.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionParam {

    private Integer diseaseId;

    private String title;

    private String a;

    private String b;

    private String c;

    private String d;

    private String answer;
}
