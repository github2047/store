package org.example.store.pay.vo;

import java.util.Date;

public class Order {
    private String out_trade_no;
    private Long total_amount;
    private String time_expire;
    private String time_start;
    private String title;
    private String detail;
    private String notify_url;

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public Order setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
        return this;
    }

    public Long getTotal_amount() {
        return total_amount;
    }

    public Order setTotal_amount(Long total_amount) {
        this.total_amount = total_amount;
        return this;
    }

    public String getTime_expire() {
        return time_expire;
    }

    public Order setTime_expire(String time_expire) {
        this.time_expire = time_expire;
        return this;
    }

    public String getTime_start() {
        return time_start;
    }

    public Order setTime_start(String time_start) {
        this.time_start = time_start;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Order setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDetail() {
        return detail;
    }

    public Order setDetail(String detail) {
        this.detail = detail;
        return this;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public Order setNotify_url(String notify_url) {
        this.notify_url = notify_url;
        return this;
    }
}
