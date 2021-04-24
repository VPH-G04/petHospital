package com.ecnu.petHospital.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResourceParam {
    List<String> image;

    List<String> video;

    List<String> imageDescription;

    List<String> videoDescription;


}
