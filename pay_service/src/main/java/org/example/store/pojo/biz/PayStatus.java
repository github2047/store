package org.example.store.pojo.biz;

/**
 * 支付状态，1为正常支付(已到账),2为已支付(未到账),3为已退款
 */
public class PayStatus {
    public static final int UNPAID = 1;
    public static final int PAID = 2;
    public static final int CONFIRM_PAID = 3;
    public static final int REFUNDED = 4;
}
