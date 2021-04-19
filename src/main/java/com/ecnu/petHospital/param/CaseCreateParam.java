package com.ecnu.petHospital.param;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaseCreateParam {

    List<MultipartFile> image;

    List<MultipartFile> video;

    List<String> imageDescription;

    List<String> imageProcedure;

    List<String> videoDescription;

    List<String> videoProcedure;

    private String name;

    private Integer disease_id;

    private String consultation;

    private String examination;

    private String diagnosis;

    private String treatment;
}
