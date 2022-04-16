package org.example.store.controller.web.pay.order;


import org.example.store.controller.web.ActionProcess;
import org.example.store.controller.web.pay.order.vo.QueryOrder;
import org.example.store.pojo.ApiResponse;
import org.example.store.pojo.PayRecord;
import org.example.store.service.PayService;
import org.example.store.util.PayApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@ActionRouter(
        name = "pay.order.query",
        paramType = QueryOrder.class
)
@Component
public class Query implements ActionProcess<QueryOrder> {

    private PayService payService;

    @Autowired
    public void setPayService(PayService payService) {
        this.payService = payService;
    }

    @Override
    public ApiResponse process(QueryOrder param) {

        if (!StringUtils.hasLength(param.getTrade_no()) && !StringUtils.hasLength(param.getOut_trade_no())) {
            throw new PayApiException(50001, "商户订单号与支付交易号必须设置一个");
        }
        PayRecord payRecord = payService.findByNoOrOutNo(
                param.getTrade_no(),
                param.getOut_trade_no(),
                param.getMerchant().getId()
        );
        if (null == payRecord) {
            return ApiResponse.Error(50002, "要查询的支付订单不存在");
        }
        return ApiResponse.Success(payRecord);
    }
}
