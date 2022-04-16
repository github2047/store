package org.example.store.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.store.pojo.Userinfo;

@Mapper
public interface UserDao extends BaseMapper<Userinfo> {
    @Select("select * from userinfo where email=#{email} and password=#{password}")
    Userinfo findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    @Select("select * from userinfo where phone=#{phone} and password=#{password} limit 1")
    Userinfo findByPhoneAndPassword(@Param("phone") String phone, @Param("password") String password);

    @Select("select * from userinfo where username=#{username} and password=#{password} limit 1")
    Userinfo findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Select("select * from userinfo where email=#{email} limit 1")
    Userinfo findByEmail(@Param("email") String email);

    @Select("select * from userinfo where phone=#{phone} limit 1")
    Userinfo findByPhone(@Param("phone") String phone);

    /**
     * 账户余额减少
     *
     * @param id
     * @param fee
     * @return
     */
    @Update("update userinfo set balance=balance-#{fee} where id=#{id} and balance >= #{fee}")
    int minusBalance(@Param("id") Integer id, @Param("fee") Long fee);

    @Update("update userinfo set balance=balance+#{fee} where id=#{id}")
    int addBalance(@Param("id") Integer id, @Param("fee") Long fee);
}
