package org.example.store.pay.vo;

import java.io.Serializable;

public class QueryOrder implements Serializable {
    /**
     * 商户订单号
     */
    private String out_trade_no;
    /**
     * 魔法支付交易号
     */
    private String trade_no;

    public QueryOrder() {
    }

    public QueryOrder(String no, String outTradeNo) {
        this.out_trade_no = outTradeNo;
        this.trade_no = no;
    }

    public String getOutTradeNo() {
        return out_trade_no;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.out_trade_no = out_trade_no;
    }

    public String getTradeNo() {
        return trade_no;
    }

    public void setTradeNo(String no) {
        this.trade_no = no;
    }
}
