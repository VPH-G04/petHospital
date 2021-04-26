package com.ecnu.petHospital.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class Question {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private Integer diseaseId;

    private String title;

    private String a;

    private String b;

    private String c;

    private String d;

    private String answer;
}
