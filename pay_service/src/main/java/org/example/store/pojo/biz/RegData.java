package org.example.store.pojo.biz;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Data
@ToString
public class RegData extends LoginData{
    @NotEmpty(message = "支付密码不能为空")
    private String payPwd;
}
