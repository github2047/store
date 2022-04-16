package org.example.store.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SecMapper {
    @Update("update goods set sales=sales+#{arg0} where id=#{arg1}")
    void updateSales(Integer sales,Integer id);
}
