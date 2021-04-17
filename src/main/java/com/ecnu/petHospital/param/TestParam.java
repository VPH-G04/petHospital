package com.ecnu.petHospital.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestParam {

    private String name;

    private Integer paperId;

    private String startTime;

    private String endTime;
}
