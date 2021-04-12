package com.ecnu.petHospital.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterParam {

    @NotBlank(message = "用户名不能为空")
    @Length(min = 2, max = 20)
    private String username;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 20)
    private String password;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式错误")
    private String email;

}
