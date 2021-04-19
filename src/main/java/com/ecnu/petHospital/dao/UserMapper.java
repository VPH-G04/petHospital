package com.ecnu.petHospital.dao;

import com.ecnu.petHospital.entity.User;
import com.ecnu.petHospital.vo.UserVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper extends MyMapper<User> {

    //根据用户名获取用户
    @Select("SELECT * FROM user WHERE username=#{username};")
    User getUserByUsername(@Param("username") String username);

    //根据用户id获取用户
    @Select("select * from user where id=#{id};")
    UserVO getUserById(Integer id);

    @Select("select count(*) from user")
    int getCountOfUser();


    //更新用户密码
    @Update("update user set password=#{password} where id=#{id}")
    int updatePasswordById(@Param("id") Integer id,
                           @Param("password") String password);

    //更改权限
    @Update("update user set admin=#{admin} where id=#{id}")
    int updatePermission(@Param("id") Integer id,
                         @Param("admin") Integer admin);


}
