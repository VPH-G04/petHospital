package com.ecnu.petHospital.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="test_log")
public class TestLog {
    @Id
    private Integer id;

    private Integer testId;

    private Integer userId;

    private Integer score;

    private Timestamp submitTime;
}
