package com.ecnu.petHospital.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="learning_video")
@Accessors(chain = true)
public class LearningVideo {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private Integer resourceId;

    private String url;

    @Column(name = "`describe`")
    private String describe;
}
