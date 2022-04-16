package org.example.store.controller;

import lombok.SneakyThrows;
import org.example.store.pay.PayClient;
import org.example.store.pay.vo.Order;
import org.example.store.pay.vo.OrderResult;
import org.example.store.pay.vo.PayRecord;
import org.example.store.pay.vo.QueryOrder;
import org.example.store.po.PayOrder;
import org.example.store.pojo.Orders;
import org.example.store.service.OrderService;
import org.example.store.vo.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/pay")
@CrossOrigin // 允许跨域
public class PayController {
    @Value("${pay.id}")
    private Integer merchantId;
    @Value("${pay.public-key}")
    private String publicKey;
    @Value("${pay.private-key}")
    private String privateKey;
    @Resource
    private OrderService orderService;
    @Resource
    private PayClient payClient;
    @Value("${server.port}")
    private Integer port;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private HashMap<String, Boolean> orderStatus = new HashMap<>();

    @SneakyThrows
    @RequestMapping("pay-success")
    public String paySuccess(String out_trade_no, String no, String sign, String timestamp) {
        // 由于签名被转码 所以需要解码
        sign = URLDecoder.decode(sign, "utf-8");
        // TODO 订单的支付状态更改
        // 验证请求是否合法
        if (payClient.verify(sign, no, timestamp)) {
            // 查询订单
            QueryOrder queryOrder = new QueryOrder();
            queryOrder.setTradeNo(no);
            PayRecord query = payClient.query(queryOrder);

            // 判断支付系统的支付状态
            if (query.getStatus() == PayRecord.PayStatus.PAID ||
                    query.getStatus() == PayRecord.PayStatus.CONFIRM_PAID) {
                orderStatus.put(out_trade_no, true); // 这是为已支付
            }
            return "success";
        }
        return "来源不合法";
    }

    /**
     * 检测订单支付状态
     *
     * @param id
     * @return
     */
    @GetMapping("status")
    public String checkOrder(String id) {
        if (orderStatus.containsKey(id)) {
            // 状态为true表示已支付
            return orderStatus.get(id) ? "yes" : "no";
        }
        return "no";
    }

    @RequestMapping("start-pay")
    public ApiResponse startPay(String id) {
//        System.out.println("consumer===>" + id);
        ApiResponse response = new ApiResponse();
        Orders order = orderService.findById(id);// 到提供者查询订单信息
        if (order == null) {
            response.setCode(1);
            response.setMessage("没有找到响应的商品订单");
            return response;
        }
        if (order.getStatus() == 1) {
            response.setCode(2);
            response.setMessage("订单已支付了");
            return response;
        }
        if (order.getStatus() == 2) {
            response.setCode(2);
            response.setMessage("订单已取消");
            return response;
        }
        orderStatus.put(id, false);
        String successUrl = "http://localhost:" + port + "/pay/pay-success";
        // 创建支付订单
        PayOrder payOrder = new PayOrder();
        payOrder.setOut_trade_no(order.getId());// 设置商城支付编号
        payOrder.setTitle(order.getTitle()); // 设置支付标题
        payOrder.setTotal_amount(order.getTotal()*100); // 设置订单金额
        payOrder.setNotify_url(successUrl); // 设置通知url
        payOrder.setTime_start(dateFormat.format(new Date())); // 开始时间
        try {
            // 到支付系统下单
            OrderResult orderResult = payClient.create(payOrder);
            // 下单成功后进行构造支付链接
            String payUrl = payClient.buildPayUrl(orderResult.getTrade_no(), successUrl);
            response.setCode(0);
            response.setData(payUrl);
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(3);
            response.setMessage(e.getMessage());
        }
        return response;
    }
}
