package com.ecnu.petHospital.vo;

import com.ecnu.petHospital.param.ImageVideoParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LearningResourceVO {
    private Integer id;

    private String title;

    private String content;

    List<ImageVideoParam> images = new ArrayList<>();

    List<ImageVideoParam> videos = new ArrayList<>();

}
