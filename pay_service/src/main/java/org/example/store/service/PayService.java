package org.example.store.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.store.pojo.Merchant;
import org.example.store.pojo.PayRecord;
import org.example.store.pojo.biz.PayParam;


public interface PayService extends IService<PayRecord> {
    public IPage<PayRecord> selectList(Integer uid, Integer page, Integer size);
    public IPage<PayRecord> selectListByMerchant(Integer mchId, Integer page, Integer size);
    public PayRecord findByNoOrOutNo(String no,String outNo, Integer merchantId);
    public PayRecord findByNo(String no);
    boolean outTradeNoExists(String outNo,Integer merchantId );

    /**
     * 支付
     * @param param
     * @return
     */
    PayRecord pay(PayParam param, Merchant merchant);
}
