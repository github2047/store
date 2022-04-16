package org.example.store.pojo.biz;

import lombok.Data;

@Data
public class PayParam {
    private String no;
    private String username;
    private Integer merchantId;
    private String password;
    private String notifyUrl;
    private String callback;
    private String sign;
    private String sign_type;
    private String timestamp;
    private String charset;
}
