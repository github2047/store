package org.example.store.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.example.store.pojo.User;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    User findAccountOrEmail(String account);
    @Update("update user set password=#{password},nick=#{nick},sex=#{sex},email=#{email},avatar=#{avatar} where account=#{account}")
    void update(User user);
}
