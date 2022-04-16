package org.example.store.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.store.pojo.Cars;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
@Mapper
public interface GoodsMapper {
    @Select("select classification from goods group by classification")
    List<String> listClass();
    @Select("select * from cars where gid=#{arg0} and uid=#{arg1}")
    Cars findByGidAAndUid(Integer gid, String uid);
    @Update("update cars set number=#{arg0} where id=#{arg1}")
    void updateNumber(Integer number,Integer id);
    @Select("select * from cars where uid=#{arg0} group by id desc")
    List<Cars> findListByUid(String uid);
    @Update("update goods set sales=sales+#{arg0} where id=#{arg1}")
    void updateSales(Integer sales,Integer id);
}
