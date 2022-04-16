package org.example.store.pojo.biz;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MerchantReg {
    @NotEmpty(message = "密码不能为空")
    private String password;
    @NotEmpty(message = "应用名称不能为空")
    private String appName;
    @NotEmpty(message = "登录账号不能为空")
    private String account;
}
