package org.example.store.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(name = "messages")
public class Message {
    @Id
    @Column(name = "id", nullable = false)
    @GenericGenerator(name = "id", strategy = "auto")
    private Integer id;

    private String title;
    private String content;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;
    private Byte status;
}
