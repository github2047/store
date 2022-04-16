package org.example.store.pay.vo;

import java.io.Serializable;

/**
 * 创建订单后返回的结果
 */
public class OrderResult implements Serializable {
    /**
     * 魔法支付订单编号
     */
    private String trade_no;
    /**
     * 外部系统订单编号
     */
    private String out_trade_no;
    private String sign;
    private String timestamp;

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
