package org.example.store.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.store.pojo.PayRecord;

@Mapper
public interface PayRecordDao extends BaseMapper<PayRecord> {
    @Select("select count(*) from pay_record where mch_id=#{merchantId} and out_trade_no=#{outTradeNo} " +
            "and (time_expire is null or time_expire > CURRENT_TIMESTAMP)")
    int getCountByOutTradeNo(@Param("outTradeNo") String outTradeNo, @Param("merchantId") Integer merchantId);

    @Update("update pay_record set status=#{pay.status},pay_time=#{pay.payTime},uid=#{pay.uid} " +
            "where no=#{pay.no}")
    int updatePayStatus(@Param("pay") PayRecord pay);
}
