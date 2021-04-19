package com.ecnu.petHospital.param;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaseCreateParam {

    List<MultipartFile> cImage;

    List<MultipartFile> eImage;

    List<MultipartFile> dImage;

    List<MultipartFile> tImage;

    List<MultipartFile> cVideo;

    List<MultipartFile> dVideo;

    List<MultipartFile> eVideo;

    List<MultipartFile> tVideo;

    private String name;

    private Integer disease_id;

    private String consultation;

    private String examination;

    private String diagnosis;

    private String treatment;
}
