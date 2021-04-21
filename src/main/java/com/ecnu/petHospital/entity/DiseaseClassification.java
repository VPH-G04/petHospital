package com.ecnu.petHospital.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "disease_classification")
public class DiseaseClassification {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String name;
}
