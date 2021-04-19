package com.ecnu.petHospital.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CaseParam {


    List<String> cImage;

    List<String> eImage;

    List<String> dImage;

    List<String> tImage;

    List<String> cVideo;

    List<String> dVideo;

    List<String> eVideo;

    List<String> tVideo;
}
