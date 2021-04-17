package com.ecnu.petHospital.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaperParam {

    private String name;

    private List<Integer> questionList;

    private Integer score = 100;
}
