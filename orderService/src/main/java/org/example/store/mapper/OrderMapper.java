package org.example.store.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.store.pojo.Orders;

import java.util.Date;
import java.util.List;

@Mapper
public interface OrderMapper {
    @Update("update orders set status=#{arg0} where id=#{arg1}")
    void updateStatus(Integer status,String id);
    @Select("select * from orders where uid=#{arg0} group by create_time desc")
    List<Orders> findListByUid(String uid);
    @Update("update orders set pay_time=#{arg0} where id=#{arg1}")
    void updatePayTime(Date pay_time,String id);
    @Select("select * from orders where uid=#{arg0} and status=0 group by create_time desc")
    List<Orders> findListByStatus(String uid);
}
