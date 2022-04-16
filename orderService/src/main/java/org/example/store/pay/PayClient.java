package org.example.store.pay;

import com.google.gson.Gson;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.example.store.pay.util.Assert;
import org.example.store.pay.util.RSAUtils;
import org.example.store.pay.vo.Order;
import org.example.store.pay.vo.OrderResult;
import org.example.store.pay.vo.PayRecord;
import org.example.store.pay.vo.QueryOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PayClient {
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Logger logger = LoggerFactory.getLogger(PayClient.class);
    private String API_URL = "http://localhost:30083";
    private static final String charset = "UTF-8";
    private Gson gson = new Gson();

    private String secretKey;
    private String publicKey;
    private Integer merchantId;
    private static PayClient client;

    public String getSecretKey() {
        return secretKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    private PayClient(Integer merchantId, String publicKey, String secretKey, String gateway) {
        logger.info("开始初始化客户端 商户:" + merchantId);
        this.merchantId = merchantId;
        this.secretKey = secretKey;
        this.publicKey = publicKey;
        if (null != gateway) {
            API_URL = gateway;
        }
    }


    /**
     * 实例化支付客户端
     *
     * @param merchantId 商户编号
     * @param privateKey 安全密钥
     * @return
     */
    public static PayClient getClient(Integer merchantId, String publicKey, String privateKey) {
        return getClient(merchantId, publicKey, privateKey, null);
    }

    public static PayClient getClient(Integer merchantId, String publicKey, String privateKey, String gateway) {
        Assert.notNull(privateKey, org.example.store.pay.Messages.PRI_KEY_MUST_NOT_BE_NULL);
        Assert.notNull(publicKey, Messages.PUB_KEY_MUST_NOT_BE_NULL);
        if (null == client) client = new PayClient(merchantId, publicKey, privateKey, gateway);
        return client;
    }

    private List<NameValuePair> createReqParams(String method, String postParam) {
        List<NameValuePair> pairs = new ArrayList<>();
        String timestamp = df.format(new Date());
        // 计算签名
        String sign = RSAUtils.sign(postParam + timestamp, secretKey, charset);
        pairs.add(new BasicNameValuePair("method", method));
        pairs.add(new BasicNameValuePair("mch_id", this.merchantId.toString()));
        pairs.add(new BasicNameValuePair("sign_type", "RSA2"));
        pairs.add(new BasicNameValuePair("sign", sign));
        pairs.add(new BasicNameValuePair("timestamp", timestamp));
        pairs.add(new BasicNameValuePair("charset", charset));
        return pairs;
    }

    private <T> T request(String method, String param, Class clazz) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("method")
        ApiResponse<T> apiResponse = ApiRequest.post(API_URL + "/gateway.do",
                createReqParams(method, param), param, clazz);
        return (T) apiResponse.getData();
    }

    //测试配置是否正确
    public void test() {
        String message = request("pay.order.test", "{}", String.class);
        System.out.println(message);
    }

    /**
     * 到魔法支付创建支付订单
     *
     * @param order
     * @return
     */
    public OrderResult create(Order order) {
        return request("pay.order.create", gson.toJson(order), OrderResult.class);
    }

    /**
     * 查询支付订单数据
     *
     * @param no         魔法支付的订单编号
     * @param outTradeNo 外部订单编号
     * @return
     */
    public PayRecord query(String no, String outTradeNo) {
        return query(new QueryOrder(no, outTradeNo));
    }

    /**
     * 查询支付订单数据
     *
     * @param queryOrder
     * @return
     */
    public PayRecord query(QueryOrder queryOrder) {
        return request("pay.order.query", gson.toJson(queryOrder), PayRecord.class);
    }

    /**
     * 验证签名是否合法
     *
     * @param sign
     * @param no
     * @param timestamp
     * @return
     */
    public boolean verify(String sign, String no, String timestamp) {
        return RSAUtils.verify(no + timestamp, sign, publicKey, charset);
    }

    /**
     * 构建支付的url地址
     *
     * @param no
     * @param callback
     * @return
     */
    public String buildPayUrl(String no, String callback) throws URISyntaxException {
        String timestamp = df.format(new Date());
        String sign = null;
        try {
            sign = RSAUtils.sign(no + timestamp, this.getSecretKey(), charset);
        } catch (Exception e) {
            throw new PayException("接口请求数据签名失败,请检查安全证书是否配置正确");
        }
        URIBuilder builder = new URIBuilder(this.API_URL + "/pay");
        try {
            builder.setParameter("no", no);
            builder.setParameter("timestamp", timestamp);
            builder.setParameter("sign", URLEncoder.encode(sign, charset));
            builder.setParameter("callback", URLEncoder.encode(callback, charset));
            builder.setParameter("sign_type", "RSA2");
            builder.setParameter("charset", charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        builder.setParameter("merchantId", merchantId.toString());
        System.out.println("==>" + timestamp);
        return builder.build().toString();
    }

}
