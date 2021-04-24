package com.ecnu.petHospital.service.impl;

import com.ecnu.petHospital.dao.LearningPictureMapper;
import com.ecnu.petHospital.dao.LearningResourceMapper;
import com.ecnu.petHospital.dao.LearningVideoMapper;
import com.ecnu.petHospital.dao.RoleMapper;
import com.ecnu.petHospital.entity.*;
import com.ecnu.petHospital.enums.CustomExceptionType;
import com.ecnu.petHospital.enums.FileType;
import com.ecnu.petHospital.exception.CustomException;
import com.ecnu.petHospital.param.ImageVideoParam;
import com.ecnu.petHospital.param.ResourceParam;
import com.ecnu.petHospital.service.RoleService;
import com.ecnu.petHospital.vo.LearningResourceVO;
import com.ecnu.petHospital.vo.RoleVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private LearningResourceMapper learningResourceMapper;
    @Resource
    private LearningPictureMapper learningPictureMapper;
    @Resource
    private LearningVideoMapper learningVideoMapper;

    @Override
    public RoleVO getAllResourceFromRole(Integer roleId) {
        Role role = roleMapper.selectOne(new Role().setId(roleId));
        if(role==null)
            throw new CustomException(CustomExceptionType.ROLE_NOT_EXIST);

        LearningResourceVO learningResourceVO = new LearningResourceVO();

        LearningResource learningResource = learningResourceMapper.selectOne(new LearningResource().setRoleId(roleId));
        learningResourceVO.setId(learningResource.getId());
        learningResourceVO.setTitle(learningResource.getTitle());
        learningResourceVO.setContent(learningResource.getContent());

        List<LearningVideo> videoList = learningVideoMapper.getVideoByResourceId(learningResource.getId());
        System.out.println(videoList);

        List<LearningPicture> pictureList = learningPictureMapper.getPictureByResourceId(learningResource.getId());
        System.out.println(pictureList);

        for (LearningVideo learningVideo : videoList) {
            learningResourceVO.getVideos().add(new ImageVideoParam(learningVideo.getUrl(), learningVideo.getDescribe()));
        }

        for(LearningPicture learningPicture : pictureList){
            learningResourceVO.getImages().add(new ImageVideoParam(learningPicture.getUrl(),learningPicture.getDescribe()));

        }

        return new RoleVO(role.getId(),role.getName(),learningResourceVO);

    }

    @Override
    @Transactional
    public Integer deleteRoleResource(Integer resourceId) {
        System.out.println(resourceId);
        learningPictureMapper.deleteById(resourceId);
        learningVideoMapper.deleteById(resourceId);

        return learningResourceMapper.deleteByPrimaryKey(resourceId);
    }


    private void insertPicOrVid(List<String> tmp, Integer id,  List<String> description, String type) {
        for (int i = 0; i < tmp.size(); i++) {
            if (type.equals(FileType.Image)) {
                LearningPicture lp = new LearningPicture();
                lp.setResourceId(id);
                lp.setUrl(tmp.get(i));
                lp.setDescribe(description.get(i));
                learningPictureMapper.insert(lp);

            } else {
                LearningVideo lv = new LearningVideo();
                lv.setResourceId(id);
                lv.setUrl(tmp.get(i));
                lv.setDescribe(description.get(i));
                learningVideoMapper.insert(lv);

            }

        }
    }

    @Override
    @Transactional
    public RoleVO createResource(LearningResource resource, ResourceParam resourceParam) {
        learningResourceMapper.insert(resource);
        Integer id = resource.getId();
        System.out.println("id"+id);

        List<String> image = resourceParam.getImage();
        if (image != null)
            insertPicOrVid(image, id, resourceParam.getImageDescription(), FileType.Image);

        List<String> video = resourceParam.getVideo();
        if (video != null)
            insertPicOrVid(video, id, resourceParam.getVideoDescription(), FileType.Video);

        return getAllResourceFromRole(resource.getRoleId());

    }

    @Override
    public Integer updateResource(LearningResource learningResource) {
        return learningResourceMapper.updateByPrimaryKey(learningResource);

    }
}
