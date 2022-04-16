package org.example.store.po;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;

/**
 * ES中商品的文档对象模型
 */
@Data
@Document(indexName = "goods_info")
@Accessors(chain = true)
@ToString
public class EsGoods {
    @Id
    private Integer id;
    @Field(type = FieldType.Text,analyzer = "ik_smart",searchAnalyzer = "ik_smart")
    private String title;
    private String picture;
    private Double price;
    @Field(type = FieldType.Text,analyzer = "ik_smart",searchAnalyzer = "ik_smart")
    private String classification;
    private Integer sales;//销量
}