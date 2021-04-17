package com.ecnu.petHospital.vo;

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

    List<String> images = new ArrayList<>();

    List<String> videos = new ArrayList<>();

}
