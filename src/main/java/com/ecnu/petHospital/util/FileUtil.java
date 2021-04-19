package com.ecnu.petHospital.util;

import com.ecnu.petHospital.enums.FileType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Configuration
public class FileUtil {

    private static String fileSavePath;

    @Value("${file.file-save-path}")
    private void setFileSavePath(String fileSavePath){
        FileUtil.fileSavePath = fileSavePath;
    }

    public static String saveFile(MultipartFile file, HttpServletRequest request, String type){
        String returnUrl = "";
        String fileName = file.getOriginalFilename();
        System.out.println("Upload file: " + fileName);

        if (fileName != null && !fileName.equals("")) {
            try {
                System.out.println("FileSavePath: " + fileSavePath);
                String url;
                if(type.equals(FileType.Image)) {
                    url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/upload/images/";
                }
                else {
                    url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/upload/videos/";
                }
                System.out.println("url: "+url);

                String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
                System.out.println("fileSuffix: "+fileSuffix);

                fileName = new Date().getTime() + "_" + new Random().nextInt(1000) + fileSuffix;
                System.out.println("fileName: "+fileName);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String fileAdd = sdf.format(new Date());
                File dir = new File(fileSavePath + "/" + type + fileAdd);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                file.transferTo(new File(dir, fileName));
                returnUrl = url + fileAdd + "/" + fileName;
                System.out.println("returnUrl: "+returnUrl);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return returnUrl;
    }
}
