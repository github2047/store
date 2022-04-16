package org.example.store.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.example.store.dao.UserDao;
import org.example.store.pojo.Userinfo;
import org.example.store.service.UserService;
import org.example.store.util.PayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, Userinfo> implements UserService {

    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    private boolean isEmail(String email) {
        if (!StringUtils.hasLength(email)) return false;
        String regex = "^([a-z0-9A-Z]+[-|\\\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\\\.)+[a-zA-Z]{2,}$";
        return Pattern.compile(regex).matcher(email).matches();
    }

    private boolean isPhone(String phone) {
        if (!StringUtils.hasLength(phone)) return false;
        //1[3456789][0-9]{9}
        String regex = "^\\d+$";
        return Pattern.compile(regex).matcher(phone).matches();
    }

    @Override
    public Userinfo login(String username, String password) {
        Userinfo userinfo = isPhone(username)
                ? userDao.findByPhoneAndPassword(username, password)
                : userDao.findByEmailAndPassword(username, password);
        if (null == userinfo) {
            throw new RuntimeException("用户名或密码不正确");
        }
        return userinfo;
    }

    @Override
    public Userinfo getByName(String name) {
        if (isPhone(name)) {
            return userDao.findByPhone(name);
        }
        return userDao.findByEmail(name);
    }

    @Override
    public Userinfo charge(Integer id, Long money) {
        Userinfo userinfo = userDao.selectById(id);
        if (userinfo == null) {
            throw new RuntimeException("充值的用户不存在");
        }
        int i = userDao.addBalance(id, money);
        if(i < 1) {
            throw new RuntimeException("充值失败");
        }
        userinfo.setBalance(
                userinfo.getBalance() + money
        );
        return userinfo;
    }

    @Override
    public Userinfo consume(String username, String password, Long fee) {
        Userinfo user = getByName(username);
        if (user == null) {
            throw new PayException("支付账号信息不正确");
        }
        if (!password.equals(user.getPayPwd())) {
            throw new PayException("支付密码不正确");
        }
        if (user.getBalance() < fee) {
            throw new PayException("账户余额不足");
        }
        int count = userDao.minusBalance(user.getId(), fee);
        if (count < 1) {
            throw new PayException("支付失败");
        }
        return user;
    }
}
