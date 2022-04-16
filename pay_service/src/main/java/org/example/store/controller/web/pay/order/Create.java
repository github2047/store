package org.example.store.controller.web.pay.order;


import org.example.store.controller.web.ActionProcess;
import org.example.store.controller.web.pay.order.vo.CreateOrder;
import org.example.store.controller.web.pay.order.vo.TradeOrder;
import org.example.store.pojo.ApiResponse;
import org.example.store.pojo.PayRecord;
import org.example.store.pojo.biz.PayStatus;
import org.example.store.service.PayService;
import org.example.store.util.PayApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@ActionRouter(
        name = "pay.order.create",
        paramType = CreateOrder.class
)
@Component
public class Create implements ActionProcess<CreateOrder> {

    private PayService payService;

    @Autowired
    public void setPayService(PayService payService) {
        this.payService = payService;
    }

    private synchronized String createNo() {
        //时间（精确到毫秒）
        DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        return LocalDateTime.now().format(ofPattern);
    }

    @Override
    public ApiResponse process(CreateOrder data) {
        if (null == data) {
            throw new PayApiException(50001, "参数无效");
        }
        checkRequiredParam(
                data.getOut_trade_no(),
                data.getNotify_url(),
                data.getTotal_amount(),
                data.getTime_start()
        );



        // 检查订单是否重复
        if (payService.outTradeNoExists(data.getOut_trade_no(), data.getMerchant().getId())) {
            throw new PayApiException(50002, "订单已经存在了");
        }
        // 支付数据
        PayRecord record = new PayRecord();
        record.setNo(createNo());
//        record.setAttach(data.getApp_id());
        record.setOutTradeNo(data.getOut_trade_no());
        record.setDetail(data.getDetail());
        record.setMchId(data.getMerchant().getId());
        record.setNotifyUrl(data.getNotify_url());
        record.setTitle(data.getTitle())
                .setTotalFee(data.getTotal_amount())
                .setStatus(PayStatus.UNPAID);
        record.setTimeStart(data.getTime_start());

        if (data.getTime_expire() != null) {
            record.setTimeExpire(data.getTime_expire());
        }
        boolean save = payService.save(record);
        if (save) {
            TradeOrder order = new TradeOrder();
            order.setTradeNo(record.getNo())
                    .setOutTradeNo(record.getOutTradeNo())
                    .setTimestamp(getTimestamp());

            return ApiResponse.Success(order);
        }
        return ApiResponse.Error(50002, "创建支付订单失败");
    }
}
