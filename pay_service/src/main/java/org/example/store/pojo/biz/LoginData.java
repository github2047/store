package org.example.store.pojo.biz;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Data
@ToString
public class LoginData {
    @NotEmpty(message = "登录账号不能为空")
    private String username;
    @NotEmpty(message = "登录密码不能为空")
    private String password;
}
