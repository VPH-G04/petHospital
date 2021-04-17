package com.ecnu.petHospital.vo;

import com.ecnu.petHospital.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PaperVO {

    private Integer id;

    private String name;

    private Integer score;

    private List<Question> questionList;
}
