package com.ecnu.petHospital.controller;

import com.ecnu.petHospital.entity.User;
import com.ecnu.petHospital.param.PageParam;
import com.ecnu.petHospital.result.CommonResult;
import com.ecnu.petHospital.result.Result;
import com.ecnu.petHospital.result.UserResult;
import com.ecnu.petHospital.exception.IncorrectUsernameOrPasswordException;
import com.ecnu.petHospital.exception.UsernameAlreadyExistException;
import com.ecnu.petHospital.service.UserService;
import com.ecnu.petHospital.session.UserSessionInfo;
import com.ecnu.petHospital.vo.UserVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RestController
@CrossOrigin
@RequestMapping("/user/")
public class UserController {
    @Resource
    UserService userService;

    @ApiOperation(value = "更新个人信息", response = Result.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "要更新的用户信息", required = true),
    })
    @PutMapping("update")
    public Result<?> updateUserInfo(@Valid @RequestBody UserVO userVO){
        userService.updateInfo(userVO);
        return CommonResult.success();
    }

    @ApiOperation(value = "用户更新个人密码", response = Result.class)
    @PutMapping("updatePassword")
    public Result<?> updatePassword(@SessionAttribute UserSessionInfo userSessionInfo,
                                    @RequestParam @NotNull String oldPassword,
                                    @RequestParam @NotNull @Size(min=6,max=20) String newPassword){
        System.out.println(userSessionInfo);
        userService.updatePassword(userSessionInfo,oldPassword,newPassword);
        return CommonResult.success();
    }

    @ApiOperation(value = "管理员获取用户列表", response = Result.class)
    @GetMapping("list")
    public Result<?> getUerList(@SessionAttribute UserSessionInfo userSessionInfo,
                                PageParam pageParam){

        if(!userSessionInfo.getAdmin()){
            return CommonResult.accessDenied();
        }

        return CommonResult.success().data(userService.getUserList(pageParam));
    }

    @ApiOperation(value = "管理员删除用户", response = Result.class)
    @DeleteMapping("delete")
    public Result<?> deleteUser(@SessionAttribute UserSessionInfo userSessionInfo,
                                @RequestParam Integer userId){
        if(!userSessionInfo.getAdmin()){
            return CommonResult.accessDenied();
        }
        userService.deleteUserById(userId);
        return CommonResult.success();
    }

    @ApiOperation(value = "管理员更新用户信息", response = Result.class)
    @PutMapping("adminUpdateUser")
    public Result<?> updateUser(@SessionAttribute UserSessionInfo userSessionInfo,
                                User user){
        System.out.println(user);
        if(!userSessionInfo.getAdmin()){
            return CommonResult.accessDenied();
        }
        userService.adminUpdateInfo(user);
        return CommonResult.success();
    }


   //ok
    @PostMapping("get")
    public Result<?> getUserById(@SessionAttribute UserSessionInfo userSessionInfo,
                                 @RequestParam Integer id){
        return CommonResult.success().data(userService.getUserById(id));
    }

}
