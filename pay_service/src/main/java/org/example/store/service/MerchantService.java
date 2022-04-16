package org.example.store.service;


import org.example.store.pojo.Merchant;
import org.example.store.pojo.biz.RsaKey;

public interface MerchantService {
    Merchant create(String password, String appName, String account);
    Merchant login(String account, String password);

    RsaKey generateRsaKey(Integer id);
    Merchant findById(Integer id);
    int updateBalance(Integer id,Long balance);
    Merchant update(Integer id,UpdateCallback<Merchant> callback);

    boolean exists(Merchant merchant);
}
