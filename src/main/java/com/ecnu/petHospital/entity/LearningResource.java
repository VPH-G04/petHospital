package com.ecnu.petHospital.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="learning_resource")
public class LearningResource {
    @Id
    private Integer id;

    private Integer roleId;

    private String title;

    private String content;

}
