package org.example.store.controller.web;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ApiParam {
    /**
     * 应用ID
     */
    private Integer mch_id;
    /**
     * 接口名称
     */
    private String method;
    /**
     * 商户请求参数的签名串，详见签名
     */
    private String sign;
    /**
     * 商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
     */
    private String sign_type;
    /**
     * 发送请求的时间，格式"yyyy-MM-dd HH:mm:ss"
     */
    private String timestamp;

    /**
     * 请求内容编码
     */
    private String charset;
}
