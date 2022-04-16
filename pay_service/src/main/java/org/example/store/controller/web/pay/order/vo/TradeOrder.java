package org.example.store.controller.web.pay.order.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TradeOrder {
    @JsonProperty("trade_no")
    private String tradeNo;
    @JsonProperty("out_trade_no")
    private String outTradeNo;
    private String sign;
    private String timestamp;
}
