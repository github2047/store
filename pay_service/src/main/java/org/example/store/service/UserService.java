package org.example.store.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.store.pojo.Userinfo;


public interface UserService extends IService<Userinfo> {
    Userinfo login(String username, String password);
    Userinfo getByName(String name);
    Userinfo charge(Integer id,Long money);
    Userinfo consume(String username,String password,Long fee);
}
