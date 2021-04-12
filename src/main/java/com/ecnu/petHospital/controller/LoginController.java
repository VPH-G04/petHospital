package com.ecnu.petHospital.controller;

import com.ecnu.petHospital.entity.User;
import com.ecnu.petHospital.result.CommonResult;
import com.ecnu.petHospital.result.Result;
import com.ecnu.petHospital.result.UserResult;
import com.ecnu.petHospital.exception.IncorrectUsernameOrPasswordException;
import com.ecnu.petHospital.exception.UsernameAlreadyExistException;
import com.ecnu.petHospital.exception.UsernameNotExistsException;
import com.ecnu.petHospital.param.LoginParam;
import com.ecnu.petHospital.param.RegisterParam;
import com.ecnu.petHospital.service.UserService;
import com.ecnu.petHospital.session.UserSessionInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.Email;

@RestController
@CrossOrigin
@Api("登录注册")
@Validated
public class LoginController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "登录", response = Result.class)
    @ApiImplicitParams({
//            @ApiImplicitParam(name = "email", value = "注册邮箱", required = true, paramType = "query", dataType = "String"),
//            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query", dataType = "String")
    })
    @PostMapping("/login")
    public Result<?> login(@RequestBody @Valid LoginParam loginParam,HttpSession httpSession){
        //参数校验
//        LoginParam loginParam = new LoginParam(email,password);
//        if(loginParam.getEmail().length() < 2 || loginParam.getEmail().length() > 20
//                ||loginParam.getPassword().length() < 2 ||loginParam.getPassword().length() > 20){
//            return UserResult.invalidUsernameOrPassword();
//        }
        UserSessionInfo userSessionInfo = new UserSessionInfo();

        //请求转发，会话管理
//        try{
//            User user = userService.login(loginParam);
//            BeanUtils.copyProperties(user,userSessionInfo);
//            httpSession.setAttribute("userSessionInfo",userSessionInfo);
//        }catch (UsernameNotExistsException e){
//            return UserResult.usernameNotExists();
//        }
//        catch (IncorrectUsernameOrPasswordException e){
//            return UserResult.incorrectUsernameOrPassword();
//        }
        User user = userService.login(loginParam);
        BeanUtils.copyProperties(user,userSessionInfo);
        httpSession.setAttribute("userSessionInfo",userSessionInfo);
        return CommonResult.success().data(userSessionInfo);
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody @Valid RegisterParam registerParam){

        userService.register(registerParam);
         return CommonResult.success();
    }

//OK

    @PostMapping("/logout")
    public Result<?> logout(HttpSession session){
        session.invalidate();
        return CommonResult.success();
    }

}
