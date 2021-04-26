package com.ecnu.petHospital.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestParam {

    private String name;

    private Integer paperId;

    private String startTime;

    private String endTime;

    private Integer duration;
}
