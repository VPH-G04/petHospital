package com.ecnu.petHospital.service;

import com.ecnu.petHospital.dao.LearningPictureMapper;
import com.ecnu.petHospital.dao.LearningResourceMapper;
import com.ecnu.petHospital.dao.LearningVideoMapper;
import com.ecnu.petHospital.dao.RoleMapper;
import com.ecnu.petHospital.entity.LearningPicture;
import com.ecnu.petHospital.entity.LearningResource;
import com.ecnu.petHospital.entity.LearningVideo;
import com.ecnu.petHospital.entity.Role;
import com.ecnu.petHospital.param.ImageVideoParam;
import com.ecnu.petHospital.param.ResourceParam;
import com.ecnu.petHospital.service.impl.RoleServiceImpl;
import com.ecnu.petHospital.vo.RoleVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RoleServiceImplTest {
    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private RoleMapper roleMapper;
    @Mock
    private LearningResourceMapper learningResourceMapper;
    @Mock
    private LearningPictureMapper learningPictureMapper;
    @Mock
    private LearningVideoMapper learningVideoMapper;

    @Test
    @DisplayName("获取职能学习资源")
    void get_role_resource(){
        LearningResource lr = new LearningResource(1,1,"title","content");

        LearningPicture lp = new LearningPicture(1,1,"url","des");

        LearningVideo lv =new LearningVideo(1,1,"url1","des1");
        List<LearningPicture> learningPictures = new ArrayList<>();
        learningPictures.add(lp);

        List<LearningVideo> learningVideos = new ArrayList<>();
        learningVideos.add(lv);

        ImageVideoParam imageVideoParam = new ImageVideoParam("url","des");

        ImageVideoParam imageVideoParam1 = new ImageVideoParam("url1","des1");

        when(roleMapper.selectOne(new Role().setId(1))).thenReturn(new Role(1,"role"));
        when(learningResourceMapper.selectOne(new LearningResource().setRoleId(1))).thenReturn(lr);

        List<ImageVideoParam> list1 = new ArrayList<>();
        list1.add(imageVideoParam);
        List<ImageVideoParam> list2 = new ArrayList<>();
        list2.add(imageVideoParam1);

        when(learningPictureMapper.getPictureByResourceId(1)).thenReturn(learningPictures);
        when(learningVideoMapper.getVideoByResourceId(1)).thenReturn(learningVideos);

        RoleVO roleVO = roleService.getAllResourceFromRole(1);

        Assertions.assertAll(
                ()-> assertEquals(1, roleVO.getId()),
                ()-> assertEquals("role", roleVO.getName()),

                ()-> assertEquals(1, roleVO.getLearningResourceVO().getId()),
                ()-> assertEquals("content", roleVO.getLearningResourceVO().getContent()),
                ()-> assertEquals("title", roleVO.getLearningResourceVO().getTitle()),

                ()-> assertEquals(list1, roleVO.getLearningResourceVO().getImages()),
                ()-> assertEquals(list2, roleVO.getLearningResourceVO().getVideos())

                );
        verify(roleMapper,times(1)).selectOne(new Role().setId(1));
        verify(learningResourceMapper,times(1)).selectOne(new LearningResource().setRoleId(1));
        verify(learningPictureMapper,times(1)).getPictureByResourceId(1);
        verify(learningVideoMapper,times(1)).getVideoByResourceId(1);

    }

    @Test
    @DisplayName("删除职能学习资源")
    void delete_role_resource(){
        roleService.deleteRoleResource(1);

        verify(learningPictureMapper,times(1)).deleteById(1);
        verify(learningVideoMapper,times(1)).deleteById(1);
        verify(learningResourceMapper,times(1)).deleteByPrimaryKey(1);

    }

    @Test
    @DisplayName("创建资源")
    void creat_resource(){
        LearningResource lr = new LearningResource(1,1,"title","content");

        LearningPicture lp = new LearningPicture(1,1,"url","des");

        LearningVideo lv =new LearningVideo(1,1,"url1","des1");
        List<LearningPicture> learningPictures = new ArrayList<>();
        learningPictures.add(lp);

        List<LearningVideo> learningVideos = new ArrayList<>();
        learningVideos.add(lv);

        ImageVideoParam imageVideoParam = new ImageVideoParam("url","des");

        ImageVideoParam imageVideoParam1 = new ImageVideoParam("url1","des1");

        when(roleMapper.selectOne(new Role().setId(1))).thenReturn(new Role(1,"role"));
        when(learningResourceMapper.selectOne(new LearningResource().setRoleId(1))).thenReturn(lr);

        List<ImageVideoParam> list1 = new ArrayList<>();
        list1.add(imageVideoParam);
        List<ImageVideoParam> list2 = new ArrayList<>();
        list2.add(imageVideoParam1);

        when(learningPictureMapper.getPictureByResourceId(1)).thenReturn(learningPictures);
        when(learningVideoMapper.getVideoByResourceId(1)).thenReturn(learningVideos);

        RoleVO roleVO = roleService.getAllResourceFromRole(1);

        Assertions.assertAll(
                ()-> assertEquals(1, roleVO.getId()),
                ()-> assertEquals("role", roleVO.getName()),

                ()-> assertEquals(1, roleVO.getLearningResourceVO().getId()),
                ()-> assertEquals("content", roleVO.getLearningResourceVO().getContent()),
                ()-> assertEquals("title", roleVO.getLearningResourceVO().getTitle()),

                ()-> assertEquals(list1, roleVO.getLearningResourceVO().getImages()),
                ()-> assertEquals(list2, roleVO.getLearningResourceVO().getVideos())

        );
        verify(roleMapper,times(1)).selectOne(new Role().setId(1));
        verify(learningResourceMapper,times(1)).selectOne(new LearningResource().setRoleId(1));
        verify(learningPictureMapper,times(1)).getPictureByResourceId(1);
        verify(learningVideoMapper,times(1)).getVideoByResourceId(1);


        roleService.createResource(new LearningResource().setRoleId(1),new ResourceParam());

        verify(learningResourceMapper,times(1)).insert(new LearningResource().setRoleId(1));

    }

    @Test
    @DisplayName("更新资源")
    void update_resource(){
        when(learningResourceMapper.updateByPrimaryKey(new LearningResource())).thenReturn(0);

        roleService.updateResource(new LearningResource());

        verify(learningResourceMapper,times(1)).updateByPrimaryKey(new LearningResource());
    }
}
