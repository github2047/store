package org.example.store.pojo;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@ToString
public class Orders {
    @Id
    private String id;
    private Integer gid;
    private String uid;
    private String title;
    private Integer price;
    private Integer number;
    private Long total;
    private String addressee;
    private String phone;
    private String address;
    private Date create_time;
    private String create_by;
    private Date update_time;
    private String update_by;
    private Date pay_time;
    private Integer status;
}
