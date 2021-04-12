package com.ecnu.petHospital.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class User {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String username;

    private String password;

    @NotNull(message=" 邮箱不能为空 ")
    @Email(message = "邮箱格式错误")
    private String email;

    private boolean admin;


}
