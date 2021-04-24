package com.ecnu.petHospital.controller;

import com.ecnu.petHospital.entity.LearningResource;
import com.ecnu.petHospital.enums.FileType;
import com.ecnu.petHospital.param.ResourceCreateParam;
import com.ecnu.petHospital.param.ResourceParam;
import com.ecnu.petHospital.result.CommonResult;
import com.ecnu.petHospital.result.Result;
import com.ecnu.petHospital.service.RoleService;
import com.ecnu.petHospital.util.FileUtil;
import com.ecnu.petHospital.vo.RoleVO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api("职能学习管理")
@RequestMapping("/role/")
public class LearningController {
    @Resource
    private RoleService roleService;

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

    @GetMapping("getResource")
    public Result<?> getRole(@RequestParam Integer roleId){
        return CommonResult.success().data(roleService.getAllResourceFromRole(roleId));
    }

    @PostMapping("create")
    public Result<?> create(ResourceCreateParam resourceCreateParam,HttpServletRequest request){

        System.out.println(resourceCreateParam);
        ResourceParam resourceParam = new ResourceParam();
        if(resourceCreateParam.getImage()!=null){
            resourceParam.setImage(saveFile(resourceCreateParam.getImage(),request, FileType.Image));
            resourceParam.setImageDescription(resourceCreateParam.getImageDescription());
        }

        if(resourceCreateParam.getVideo()!=null){
            resourceParam.setVideo(saveFile(resourceCreateParam.getVideo(),request, FileType.Video));
            resourceParam.setVideoDescription(resourceCreateParam.getVideoDescription());
        }

        LearningResource resource = new LearningResource();
        resource.setRoleId(resourceCreateParam.getRoleId());
        resource.setTitle(resourceCreateParam.getTitle());
        resource.setContent(resourceCreateParam.getContent());

        RoleVO vo =roleService.createResource(resource,resourceParam);
        return CommonResult.success().data(vo);
    }

    @DeleteMapping("delete")
    public Result<?> delete(@RequestParam Integer resourceId){
        System.out.println(resourceId);
        roleService.deleteRoleResource(resourceId);
        return CommonResult.success();
    }

    @PutMapping("update")
    public Result<?> update(LearningResource learningResource){
        roleService.updateResource(learningResource);
        return CommonResult.success();
    }


}
