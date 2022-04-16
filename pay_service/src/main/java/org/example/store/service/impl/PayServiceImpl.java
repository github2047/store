package org.example.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.example.store.dao.PayRecordDao;
import org.example.store.pojo.Merchant;
import org.example.store.pojo.PayRecord;
import org.example.store.pojo.Userinfo;
import org.example.store.pojo.biz.PayParam;
import org.example.store.pojo.biz.PayStatus;
import org.example.store.service.MerchantService;
import org.example.store.service.PayService;
import org.example.store.service.UserService;
import org.example.store.util.PayException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class PayServiceImpl extends ServiceImpl<PayRecordDao, PayRecord> implements PayService {

    @Resource
    private PayRecordDao payRecordDao;
    @Resource
    private UserService userService;
    @Resource
    private MerchantService merchantService;

    @Override
    public IPage<PayRecord> selectList(Integer uid, Integer page, Integer size) {
        QueryWrapper<PayRecord> q = new QueryWrapper<>();
        q.orderByDesc("pay_time");
        q.eq("uid", uid);
        Page<PayRecord> p = new Page<>(page, size);
        return getBaseMapper().selectPage(p, q);
    }

    @Override
    public IPage<PayRecord> selectListByMerchant(Integer mchId, Integer page, Integer size) {
        QueryWrapper<PayRecord> q = new QueryWrapper<>();
        q.orderByDesc("create_time");
        q.eq("mch_id", mchId);
        q.ne("status", 0);
        Page<PayRecord> p = new Page<>(page, size);
        return getBaseMapper().selectPage(p, q);
    }

    public PayRecord findByNo(String no) {
        if (!StringUtils.hasLength(no)) return null;
        QueryWrapper<PayRecord> q = new QueryWrapper<>();
        q.eq("no", no);
        return getBaseMapper().selectOne(q);
    }

    @Override
    public PayRecord findByNoOrOutNo(String no, String outNo, Integer merchantId) {
        QueryWrapper<PayRecord> q = new QueryWrapper<>();
        if (StringUtils.hasLength(no)) {
            q.eq("no", no);
        }else if(merchantId == null || merchantId == 0){
            throw new IllegalArgumentException("商户编号不可以为空");
        }
        if (StringUtils.hasLength(outNo)) {
            q.eq("out_trade_no", outNo);
        }
        if(merchantId != null){
            q.eq("mch_id", merchantId);
        }
        return getBaseMapper().selectOne(q);
    }

    @Override
    public boolean outTradeNoExists(String outNo, Integer merchantId) {
        return getBaseMapper().getCountByOutTradeNo(outNo, merchantId) > 0;
    }

    @Transactional
    @Override
    public PayRecord pay(PayParam param, Merchant merchant) {
        PayRecord payRecord = findByNo(param.getNo());
        if (payRecord.getStatus() != PayStatus.UNPAID) {
            throw new RuntimeException("当前订单不可以进行支付");
        }
        // 减少余额
        Userinfo userinfo = userService.consume(param.getUsername(), param.getPassword(), payRecord.getTotalFee());
        int updateCount = merchantService.updateBalance(merchant.getId(),payRecord.getTotalFee());
        if(updateCount < 1){
            throw new PayException("商户数据状态更新失败了");
        }
        payRecord.setPayTime(new Date());
        // 设置付款方
        payRecord.setUid(userinfo.getId());
        // 设置支付状态
        payRecord.setStatus(PayStatus.CONFIRM_PAID);
//        this.getBaseMapper().updateById(payRecord);
        int count = payRecordDao.updatePayStatus(payRecord);
        if(count < 1){
            throw new PayException("修改订单支付状态失败");
        }
        return payRecord;
    }

    //    @Override
//    public Page<PayRecord> selectList(Integer uid,Integer page, Integer size) {
//        Pageable pageRequest = PageRequest.of(page,size, Sort.Direction.DESC,"pay_time");
//        PayRecord r = new PayRecord();
//        r.setUid(uid);
//        Example<PayRecord> q = Example.of(r);
//        Page<PayRecord> records = payRecordDao.findAll(q,pageRequest);
//        return records;
//    }
}
