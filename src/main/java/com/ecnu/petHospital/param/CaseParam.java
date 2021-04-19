package com.ecnu.petHospital.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CaseParam {

    List<String> image;

    List<String> video;

    List<String> imageDescription;

    List<String> imageProcedure;

    List<String> videoDescription;

    List<String> videoProcedure;
}
