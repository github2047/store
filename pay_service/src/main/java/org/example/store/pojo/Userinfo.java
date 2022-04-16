package org.example.store.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Data
@Accessors(chain = true)
public class Userinfo {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String email;
    private String phone;
    private Long balance;
    private String password;
    @TableField("pay_pwd")
    private String payPwd;
    private Date createTime;
    private Date updateTime;
    private Integer status;


}
