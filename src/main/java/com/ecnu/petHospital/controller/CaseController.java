package com.ecnu.petHospital.controller;

import com.ecnu.petHospital.entity.Case;
import com.ecnu.petHospital.enums.FileType;
import com.ecnu.petHospital.param.CaseCreateParam;
import com.ecnu.petHospital.param.CaseParam;
import com.ecnu.petHospital.param.PageParam;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api("病例管理")
@RequestMapping("/case")
public class CaseController {

    @Autowired
    CaseService caseService;

    @PostMapping("/create")
    public Result<?> createCase(@ModelAttribute CaseCreateParam caseCreateParam, HttpServletRequest request){

        CaseParam caseParam = new CaseParam();
        if(caseCreateParam.getImage()!=null) {
            caseParam.setImage(saveFile(caseCreateParam.getImage(), request, FileType.Image));
            caseParam.setImageDescription(caseCreateParam.getImageDescription());
            caseParam.setImageProcedure(caseCreateParam.getImageProcedure());
        }

        if(caseCreateParam.getVideo()!=null) {
            caseParam.setVideo(saveFile(caseCreateParam.getVideo(), request, FileType.Video));
            caseParam.setVideoDescription(caseCreateParam.getVideoDescription());
            caseParam.setVideoProcedure(caseCreateParam.getVideoProcedure());
        }

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
    public Result<?> getCaseList(@RequestParam Integer diseaseId,
                                 @RequestBody PageParam pageParam){

        return CommonResult.success().data(caseService.getCasesByDiseaseId(diseaseId, pageParam));
    }

    @PostMapping("/get")
    public Result<?> getCaseById(@RequestParam Integer id){
        return CommonResult.success().data(caseService.getCaseById(id));
    }


}
