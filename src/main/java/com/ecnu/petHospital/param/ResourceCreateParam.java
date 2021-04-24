package com.ecnu.petHospital.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceCreateParam {
    List<MultipartFile> image;

    List<MultipartFile> video;

    List<String> imageDescription;

    List<String> videoDescription;

    private Integer roleId;

    private String title;

    private String content;

}
