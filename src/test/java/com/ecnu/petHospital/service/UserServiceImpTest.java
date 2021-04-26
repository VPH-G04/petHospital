package com.ecnu.petHospital.service;

import com.ecnu.petHospital.dao.UserMapper;
import com.ecnu.petHospital.entity.User;
import com.ecnu.petHospital.enums.CustomExceptionType;
import com.ecnu.petHospital.exception.CustomException;
import com.ecnu.petHospital.exception.IncorrectUsernameOrPasswordException;
import com.ecnu.petHospital.exception.UsernameAlreadyExistException;
import com.ecnu.petHospital.exception.UsernameNotExistsException;
import com.ecnu.petHospital.param.LoginParam;
import com.ecnu.petHospital.param.PageParam;
import com.ecnu.petHospital.param.RegisterParam;
import com.ecnu.petHospital.service.impl.UserServiceImp;
import com.ecnu.petHospital.session.UserSessionInfo;
import com.ecnu.petHospital.vo.UserVO;
import com.github.pagehelper.PageInfo;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImpTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImp userService;

    @Test
    @DisplayName("成功登录")
    void when_service_do_login_with_matched_name_and_password_then_dispatch_mapper_to_return_user(){
        User user = new User(1,"lionel","123","email",true);
        LoginParam loginParam = new LoginParam("email","123");

        when(userMapper.selectOne(new User().setEmail(loginParam.getEmail()).setPassword(loginParam.getPassword()))).thenReturn(user);
        userService.login(loginParam);
        verify(userMapper,times(1)).selectOne(new User().setEmail(loginParam.getEmail()).setPassword(loginParam.getPassword()));
    }

    @Test
    @DisplayName("用户更新信息")
    void user_update_info(){
        User user = new User(1,"lionel","123","email",true);

        when(userMapper.selectByPrimaryKey(1)).thenReturn(user);
        when(userMapper.selectOne(new User().setEmail(user.getEmail()))).thenReturn(null);

        userService.updateInfo(new UserVO(user.getId(),user.getUsername(),user.getEmail()));

        verify(userMapper,times(1)).selectByPrimaryKey(1);
        verify(userMapper,times(1)).selectOne(new User().setEmail(user.getEmail()));
        verify(userMapper,times(1)).updateByPrimaryKeySelective(user);
    }

    @Test
    @DisplayName("用户更新邮箱失败")
    void user_update_info_fail(){
        User user = new User(1,"lionel","123","email",true);

        when(userMapper.selectByPrimaryKey(1)).thenReturn(user);
        when(userMapper.selectOne(new User().setEmail(user.getEmail()))).thenReturn(new User().setEmail(user.getEmail()).setId(2));


        Throwable exception = assertThrows(CustomException.class,()->{
            userService.updateInfo(new UserVO(user.getId(),user.getUsername(),user.getEmail()));
        });

        assertEquals(CustomExceptionType.EMAIL_ALREADY_EXISTS.getDesc(),exception.getMessage());
    }

    @Test
    @DisplayName("通过id查找用户")
    void get_user_by_id(){
        User user = new User(1,"lionel","123","email",true);

        when(userMapper.getUserById(1)).thenReturn(new UserVO(1,"lionel","email"));
        UserVO vo = userService.getUserById(1);
        verify(userMapper,times(1)).getUserById(1);
        Assertions.assertAll(
                ()->assertEquals(1,vo.getId()),
                ()->assertEquals("lionel",vo.getUsername()),
                ()->assertEquals("email",vo.getEmail())

                );

    }

    @Test
    @DisplayName("注册")
    void register(){
        User user = User.builder()
                .email("email")
                .password("password")
                .username("user")
                .admin(false).build();
        when(userMapper.selectOne(new User().setEmail("email"))).thenReturn(null);

        userService.register(new RegisterParam("user","password","email"));
        verify(userMapper,times(1)).selectOne(new User().setEmail("email"));
        verify(userMapper,times(1)).insert(user);
    }

    @Test
    @DisplayName("用户更新密码")
    void user_update_password(){
        User user = new User(1,"lionel","123","email",true);

        when(userMapper.selectByPrimaryKey(1)).thenReturn(user);
        when(userMapper.updatePasswordById(1,"new_password")).thenReturn(0);

        userService.updatePassword(new UserSessionInfo(1,"lionel","email",false),"123","new_password");
        verify(userMapper,times(1)).selectByPrimaryKey(1);
        verify(userMapper,times(1)).updatePasswordById(1,"new_password");
    }

    @Test
    @DisplayName("管理员获取用户列表")
    void admin_get_user_list(){
        User user = new User(1,"lionel","123","email",true);

        User user1 = new User(2,"user2","123456","email2",false);

        List<User> list = new ArrayList<>();
        list.add(user);list.add(user1);

        when(userMapper.selectAll()).thenReturn(list);

        PageInfo pageInfo = userService.getUserList(new PageParam(1,10));

        Assertions.assertAll(
                ()->assertEquals(1,pageInfo.getPageNum()),
                ()->assertEquals(2,pageInfo.getPageSize()),

                ()->assertEquals(1,((User)pageInfo.getList().get(0)).getId()),
                ()->assertEquals("email",((User)pageInfo.getList().get(0)).getEmail()),
                ()->assertEquals("lionel",((User)pageInfo.getList().get(0)).getUsername()),
                ()->assertEquals("123",((User)pageInfo.getList().get(0)).getPassword()),
                ()->assertEquals(true,((User)pageInfo.getList().get(0)).getAdmin()),

                 ()->assertEquals(2,((User)pageInfo.getList().get(1)).getId()),
                ()->assertEquals("email2",((User)pageInfo.getList().get(1)).getEmail()),
                ()->assertEquals("user2",((User)pageInfo.getList().get(1)).getUsername()),
                ()->assertEquals("123456",((User)pageInfo.getList().get(1)).getPassword()),
                ()->assertEquals(false,((User)pageInfo.getList().get(1)).getAdmin())

                );
    }

    @Test
    @DisplayName("管理员删除用户")
    void admin_delete_user(){
        User user = new User(1,"lionel","123","email",false);

        when(userMapper.selectByPrimaryKey(1)).thenReturn(user);
        when(userMapper.deleteByPrimaryKey(1)).thenReturn(0);

        userService.deleteUserById(1);

        verify(userMapper,times(1)).selectByPrimaryKey(1);
        verify(userMapper,times(1)).deleteByPrimaryKey(1);
    }

    @Test
    @DisplayName("管理员更新用户信息")
    void admin_update_user_info(){
        User user = new User(1,"lionel","123","email",false);

        when(userMapper.selectByPrimaryKey(1)).thenReturn(user);
        when(userMapper.selectOne(new User().setEmail("email"))).thenReturn(new User().setEmail("email").setId(1));
        when(userMapper.updateByPrimaryKey(user)).thenReturn(0);
        userService.adminUpdateInfo(user);

        verify(userMapper,times(1)).selectByPrimaryKey(1);
        verify(userMapper,times(1)).selectOne(new User().setEmail("email"));
        verify(userMapper,times(1)).updateByPrimaryKeySelective(user);
    }

}