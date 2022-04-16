package org.example.store.pay.vo;


import java.util.Date;

public class PayRecord {

    public class PayStatus {
        /**
         * 未支付
         */
        public static final int UNPAID = 1;
        /**
         * 已支付(未确认)
         */
        public static final int PAID = 2;
        /**
         * 已支付
         */
        public static final int CONFIRM_PAID = 3;
        /**
         * 已退款
         */
        public static final int REFUNDED = 4;
    }
//    public enum PayStatus {
//        UN_PAY(1, "未支付"),
//        PAID(2, "已支付(未确认)"),
//        CONFIRM_PAID(3, "已支付"),
//        REFUNDED(4, "已退款");
//
//        private int value;
//        private String name;
//
//        private PayStatus(int v, String n) {
//            this.value = v;
//            this.name = n;
//        }
//
//        public int getValue() {
//            return value;
//        }
//
//        public void setValue(int value) {
//            this.value = value;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//    }

    private String no;
    private Integer mchId;
    private Integer uid;
    private String title;
    private String detail;
    private String outTradeNo;
    private String attach;
    private Long totalFee;
    private Date timeStart;
    private Date timeExpire;
    private String notifyUrl;
    private Date payTime;
    private Date createTime;
    private Date updateTime;
    private Integer status;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Integer getMchId() {
        return mchId;
    }

    public void setMchId(Integer mchId) {
        this.mchId = mchId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public Long getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Long totalFee) {
        this.totalFee = totalFee;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public Date getTimeExpire() {
        return timeExpire;
    }

    public void setTimeExpire(Date timeExpire) {
        this.timeExpire = timeExpire;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PayRecord{" +
                "no='" + no + '\'' +
                ", mchId=" + mchId +
                ", uid=" + uid +
                ", title='" + title + '\'' +
                ", detail='" + detail + '\'' +
                ", outTradeNo='" + outTradeNo + '\'' +
                ", attach='" + attach + '\'' +
                ", totalFee=" + totalFee +
                ", timeStart=" + timeStart +
                ", timeExpire=" + timeExpire +
                ", notifyUrl='" + notifyUrl + '\'' +
                ", payTime=" + payTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", status=" + status +
                '}';
    }
}
