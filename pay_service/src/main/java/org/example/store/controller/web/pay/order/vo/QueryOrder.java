package org.example.store.controller.web.pay.order.vo;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.example.store.controller.web.ActionParam;

@Data
@ToString
@Accessors(chain = true)
public class QueryOrder extends ActionParam {
    /**
     * 商户订单号
     */
    private String out_trade_no;
    /**
     * 魔法交易号
     */
    private String trade_no;
}
