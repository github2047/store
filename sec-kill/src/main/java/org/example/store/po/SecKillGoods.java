package org.example.store.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@ToString
public class SecKillGoods {
    @Id
    private Integer id;
    private String title;
    private Integer unit_price;
    private Integer sec_kill_price;
    private Integer stock;
    private String photo;
    private String introduction;
    private String classification;
    private Integer sales;
    private Integer status;
}
