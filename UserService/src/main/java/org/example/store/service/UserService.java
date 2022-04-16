package org.example.store.service;

import org.example.store.pojo.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findAccountOrEmail(String account);
    void save(User user);
    void update(User user);
}
