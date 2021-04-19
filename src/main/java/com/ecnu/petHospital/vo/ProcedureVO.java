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
public class ProcedureVO {

    String procedureName;

    String describe;

    List<ImageVideoParam> images = new ArrayList<>();

    List<ImageVideoParam> videos = new ArrayList<>();

}
