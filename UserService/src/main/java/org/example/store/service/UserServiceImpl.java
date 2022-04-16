package org.example.store.service;

import org.example.store.dao.UserDao;
import org.example.store.mapper.UserMapper;
import org.example.store.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    private UserDao dao;
    private UserMapper userMapper;
    @Autowired
    public void setDao(UserDao dao) {
        this.dao = dao;
    }
    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<User> findAll() {
        return dao.findAll();
    }

    @Override
    public User findAccountOrEmail(String account) {
        return userMapper.findAccountOrEmail(account);
    }

    @Override
    public void save(User user) {
        dao.save(user);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

}
