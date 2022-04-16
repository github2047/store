package org.example.store.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
public class User {
    @Id
    private String account;
    private String password;
    private String nick;
    private String sex;
    private String email;
    private String avatar;
    private Integer level;
    private Date create_time;
    private Integer status;
}