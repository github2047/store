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
public class Goods {
    @Id
    private Integer id;
    private String title;
    private Integer unit_price;
    private Integer stock;
    private String photo;
    private String introduction;
    private String classification;
    private Integer sales;
    private Date create_time;
    private String create_by;
    private Date update_time;
    private String update_by;
    private Integer status;
}
