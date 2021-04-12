package com.ecnu.petHospital.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.sql.Timestamp;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Test {
    @Id
    private Integer id;

    private Integer paperId;

    private String name;

    private Timestamp start;

    private Timestamp end;
}
