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
@Table(name="learning_resource")
@Accessors(chain = true)
public class LearningResource {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private Integer roleId;

    private String title;

    private String content;

}
