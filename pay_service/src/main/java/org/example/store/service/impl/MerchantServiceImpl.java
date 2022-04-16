package org.example.store.service.impl;


import org.example.store.dao.MerchantDao;
import org.example.store.pojo.Merchant;
import org.example.store.pojo.biz.RsaKey;
import org.example.store.service.MerchantService;
import org.example.store.service.UpdateCallback;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Resource
    private MerchantDao merchantDao;


    @Override
    public Merchant create(String password, String appName, String account) {
        if (merchantDao.findByAppName(appName).isPresent()) {
            throw new RuntimeException("已经存在该应用了");
        }
        Merchant m = new Merchant().setBalance(0l)
                .setPassword(password)
                .setAccount(account)
                .setAppIcon("https://pic5.58cdn.com.cn/nowater/webim/big/n_v23ea68bc4db7e4d179737abd798639022.png")
                .setAppName(appName);

        return merchantDao.save(m);
    }

    @Override
    public Merchant login(String account, String password) {

        Optional<Merchant> merchant = null;
        if (account.matches("^\\d+$")) {
            merchant = merchantDao.findByIdAndPassword(Integer.valueOf(account), password);
        } else {
            merchant = merchantDao.findByAccountAndPassword(account, password);
        }
        if (merchant == null || !merchant.isPresent()) {
            throw new RuntimeException("商户编号或者密码不正确");
        }
        return merchant.get().setPubKey(null).setPassword(null).setSecret(null);
    }

    @Override
    public RsaKey generateRsaKey(Integer id) {
        Optional<Merchant> optionalMerchant = merchantDao.findById(id);
        if (!optionalMerchant.isPresent()) {
            throw new RuntimeException("商户编号不正确");
        }
        RsaKey rsaKey = RsaKey.generate();
        if (rsaKey == null) {
            throw new RuntimeException("生成证书失败");
        }
        // 完成rsa的更新
        Merchant m = optionalMerchant.get();
        m.setPubKey(rsaKey.getPub());
        m.setSecret(rsaKey.getPri());
        merchantDao.save(m);
        return rsaKey;
    }

    @Override
    public Merchant findById(Integer id) {
        Optional<Merchant> merchant = merchantDao.findById(id);
        return merchant.isPresent() ? merchant.get() : null;
    }

    @Override
    public int updateBalance(Integer id,Long balance) {
        if(balance > 0) return merchantDao.increaseBalance(id,balance);
        return merchantDao.decreaseBalance(id,Math.abs(balance));
    }

    @Override
    public boolean exists(Merchant m) {
        return merchantDao.exists(Example.of(m));
    }

    @Override
    public Merchant update(Integer id, UpdateCallback<Merchant> callback) {
        Optional<Merchant> merchant = merchantDao.findById(id);
        if(!merchant.isPresent()){
            throw new RuntimeException("要修改的数据不存在");
        }
        merchantDao.save(callback.onUpdate(merchant.get()));
        return merchant.get();
    }
}
