package org.example.store.controller.web.pay.order.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import org.example.store.controller.web.ActionParam;

import java.util.Date;

@Data
@Accessors(chain = true)
public class CreateOrder extends ActionParam {
    private String out_trade_no;
    private Long total_amount;
    private Date time_expire;
    private Date time_start;
    private String title;
    private String detail;
    private String notify_url;
}
