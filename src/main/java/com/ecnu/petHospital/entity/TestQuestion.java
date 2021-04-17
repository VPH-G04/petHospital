package com.ecnu.petHospital.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Table(name="test_question")
public class TestQuestion {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private Integer paperId;

    private Integer questionId;

    private Integer questionNumber;

}
