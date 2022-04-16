package org.example.store.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Data
@Accessors(chain = true)
@Entity
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String password;
    private String account;
    @TableField("app_name")
    private String appName;
    @TableField("app_icon")
    private String appIcon;
    private String secret;
    @TableField("pub_key")
    private String pubKey;
    private Long balance;
}
