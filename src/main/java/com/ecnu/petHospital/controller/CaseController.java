package com.ecnu.petHospital.controller;

import com.ecnu.petHospital.entity.Case;
import com.ecnu.petHospital.enums.FileType;
import com.ecnu.petHospital.param.CaseCreateParam;
import com.ecnu.petHospital.param.CaseParam;
import com.ecnu.petHospital.result.CommonResult;
import com.ecnu.petHospital.result.Result;
import com.ecnu.petHospital.service.CaseService;
import com.ecnu.petHospital.session.UserSessionInfo;
import com.ecnu.petHospital.util.FileUtil;
import com.ecnu.petHospital.vo.CaseVO;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Api("病例管理")
@RequestMapping("/case")
public class CaseController {

    @Autowired
    CaseService caseService;

    @PostMapping("/create")
    public Result<?> createCase(@ModelAttribute CaseCreateParam caseCreateParam, HttpServletRequest request){

        CaseParam caseParam = new CaseParam();

        if(caseCreateParam.getCImage()!=null)
        caseParam.setCImage(saveFile(caseCreateParam.getCImage(),request, FileType.Image));
        if(caseCreateParam.getDImage()!=null)
        caseParam.setDImage(saveFile(caseCreateParam.getDImage(),request, FileType.Image));
        if(caseCreateParam.getEImage()!=null)
        caseParam.setEImage(saveFile(caseCreateParam.getEImage(),request, FileType.Image));
        if(caseCreateParam.getTImage()!=null)
        caseParam.setTImage(saveFile(caseCreateParam.getTImage(),request, FileType.Image));

        if(caseCreateParam.getCVideo()!=null)
            caseParam.setCVideo(saveFile(caseCreateParam.getCVideo(),request, FileType.Video));
        if(caseCreateParam.getDVideo()!=null)
            caseParam.setDVideo(saveFile(caseCreateParam.getDVideo(),request, FileType.Video));
        if(caseCreateParam.getEVideo()!=null)
            caseParam.setEVideo(saveFile(caseCreateParam.getEVideo(),request, FileType.Video));
        if(caseCreateParam.getTVideo()!=null)
            caseParam.setTVideo(saveFile(caseCreateParam.getTVideo(),request, FileType.Video));

        Case newCase = new Case();
        BeanUtils.copyProperties(caseCreateParam,newCase);

        CaseVO caseVO = caseService.createCase(newCase, caseParam);
        return  CommonResult.success().data(caseVO);
    }

    private List<String> saveFile(List<MultipartFile> upfiles, HttpServletRequest request, String type){
        List<String> urls = new ArrayList<>();
        for(int i=0; i<upfiles.size();i++){
            System.out.println(i);
            try{
                String url = FileUtil.saveFile(upfiles.get(i), request, type);
                urls.add(url);
            }catch (Exception e){
                System.out.println(e);
            }
        }
        return urls;
    }

    @PostMapping("/delete")
    public Result<?> deleteCase(@SessionAttribute UserSessionInfo userSessionInfo,
                                @RequestParam Integer id){
        if(!userSessionInfo.getAdmin()){
            return CommonResult.accessDenied();
        }
        caseService.deleteCase(id);
        return CommonResult.success();
    }

    @PostMapping("/getcases")
    public Result<?> getCaseList(@RequestParam Integer diseaseId){
        return CommonResult.success().data(caseService.getCasesByDiseaseId(diseaseId));
    }

    @PostMapping("/get")
    public Result<?> getCaseById(@RequestParam Integer id){
        return CommonResult.success().data(caseService.getCaseById(id));
    }

//
//    @PostMapping("/update")
//    public Result<?> upateCase(@SessionAttribute UserSessionInfo userSessionInfo,
//                               @RequestBody Case caseParam){
//
//
//    }


}
