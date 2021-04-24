package com.ecnu.petHospital.service;

import com.ecnu.petHospital.entity.LearningResource;
import com.ecnu.petHospital.param.ResourceParam;
import com.ecnu.petHospital.vo.RoleVO;

public interface RoleService {
    RoleVO getAllResourceFromRole(Integer roleId);

    Integer deleteRoleResource(Integer resourceId);

    RoleVO createResource(LearningResource resource, ResourceParam resourceParam);

    Integer updateResource(LearningResource learningResource);

}
