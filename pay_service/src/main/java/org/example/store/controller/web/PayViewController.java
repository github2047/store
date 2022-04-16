package org.example.store.controller.web;

import lombok.SneakyThrows;

import org.example.store.pojo.Merchant;
import org.example.store.pojo.PayRecord;
import org.example.store.pojo.biz.PayParam;
import org.example.store.pojo.biz.PayStatus;
import org.example.store.service.MerchantService;
import org.example.store.service.PayService;
import org.example.store.service.UserService;
import org.example.store.util.PayException;
import org.example.store.util.RSAUtils;
import org.example.store.util.Timestamp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;

import javax.annotation.Resource;
import java.net.URLDecoder;
import java.net.URLEncoder;

@Controller
public class PayViewController {
    @Resource
    private PayService payService;
    @Resource
    private MerchantService merchantService;
    @Resource
    private UserService userService;
    @Value("${app.sign.max-deviation-time}")
    private int maxDeviationTime = 10000;
    private static final String CHARSET = "utf-8";
    @GetMapping("/pay")
    public ModelAndView pay(PayParam param) {
        System.out.println("========================================");
        System.out.println(param);
        System.out.println("========================================");
        //// 响应为表单格式，可嵌入页面，具体以返回的结果为准
        //https://openapi.alipay.com/gateway.do?
        // charset=UTF-8
        // method=alipay.trade.page.pay
        // sign=k0w1DePFqNMQWyGBwOaEsZEJuaIEQufjoPLtwYBYgiX%2FRSkBFY38VuhrNumXpoPY9KgLKtm4nwWz4DEQpGXOOLaqRZg4nDOGOyCmwHmVSV5qWKDgWMiW%2BLC2f9Buil%2BEUdE8CFnWhM8uWBZLGUiCrAJA14hTjVt4BiEyiPrtrMZu0o6%2FXsBu%2Fi6y4xPR%2BvJ3KWU8gQe82dIQbowLYVBuebUMc79Iavr7XlhQEFf%2F7WQcWgdmo2pnF4tu0CieUS7Jb0FfCwV%2F8UyrqFXzmCzCdI2P5FlMIMJ4zQp%2BTBYsoTVK6tg12stpJQGa2u3%2BzZy1r0KNzxcGLHL%2BwWRTx%2FCU%2Fg%3D%3D&
        // notify_url=http%3A%2F%2F114.55.81.185%2Fopendevtools%2Fnotify%2Fdo%2Fbf70dcb4-13c9-4458-a547-3a5a1e8ead04
        // version=1.0
        // app_id=2014100900013222
        // sign_type=RSA
        // timestamp=2021-02-02+14%3A11%3A40
        // alipay_sdk=alipay-sdk-java-dynamicVersionNo
        // format=json
        ModelAndView model = new ModelAndView();
        try {
            Merchant merchant = checkParam(param);
            PayRecord record = payService.findByNo(param.getNo());
            if (null == record) {
                throw new PayException("要支付的订单不存在");
            }
            if (record.getMchId().intValue() != merchant.getId().intValue()) {
                throw new PayException("要支付的订单不属于该商户");
            }
            if (record.getStatus() != PayStatus.UNPAID) {
                throw new PayException("订单已经支付过了,请稍后查询订单状态");
            }
            String message = "您正在使用及时到账交易";
            model.addObject("message", message);
            model.addObject("order", record);
            model.addObject("mch", merchant);
            model.addObject("param", param);
            model.addObject("money", record.getTotalFee() / 100.0);
            model.setViewName("pay");
        } catch (Exception e) {
            e.printStackTrace();
            model.addObject("message", e.getMessage());
            model.setViewName("message");
        }
        return model;
    }

    @SneakyThrows
    @PostMapping("/pay")
    @ResponseBody
    public String payProcess(PayParam param) {
        Merchant merchant = checkPayParam(param);
        // 检查订单并支付
        PayRecord pay = payService.pay(param, merchant);
        // 进入支付完成处理
        String timestamp = Timestamp.now();
        String sign = RSAUtils.sign(pay.getNo() + timestamp, merchant.getSecret(), "utf-8");
        // 构建回调路径
        UriBuilder builder = new DefaultUriBuilderFactory(
                URLDecoder.decode(param.getCallback(), CHARSET)
        ).builder();
        builder.queryParam("timestamp", timestamp);
        builder.queryParam("sign", URLEncoder.encode(sign, CHARSET));
        builder.queryParam("sign_type", "RSA2");
        builder.queryParam("no", pay.getNo());
        builder.queryParam("out_trade_no", pay.getOutTradeNo());
        builder.queryParam("once", System.currentTimeMillis());
        return builder.build().toString();
    }

    /**
     * 支付订单参数验证
     *
     * @param param
     * @return
     */
    @SneakyThrows
    private Merchant checkParam(PayParam param) {
        Merchant mch = getMerchant(param);
        notEmpty(param.getSign_type(), "缺少签名参数");
        notEmpty(param.getSign(), "缺少签名参数");
        notEmpty(param.getTimestamp(), "缺少数据请求时间戳");
        // 验证签名G
        // 测试不要验证呵呵
        if (!"-1".equalsIgnoreCase(param.getTimestamp())) {
            try {
                long diff = Timestamp.parse(param.getTimestamp()).getTime() - System.currentTimeMillis();
                if (Math.abs(diff) > maxDeviationTime) {
                    throw new PayException("客户端系统时间和服务器误差过大(" + diff + "毫秒),请检查客户端服务器系统时间");
                }
            } catch (Exception e) {
                throw new PayException("数据请求时间戳格式有误("+param.getTimestamp()+")" );
            }
        }
        if (!StringUtils.hasLength(param.getCharset())) {
            throw new PayException("缺少数据编码格式");
        }
        String source = param.getNo() + param.getTimestamp();
        String signOri = URLDecoder.decode(param.getSign(), CHARSET);
        if (!RSAUtils.verify(param.getNo() + param.getTimestamp(), signOri, mch.getPubKey(), param.getCharset())) {
            String sign = RSAUtils.sign(source, mch.getSecret(), param.getCharset());
            System.out.println(sign);
            System.out.println(signOri);
            throw new RuntimeException("数据签名未通过,请检查安全证书是否配置正确");
        }
        return mch;
    }

    private Merchant checkPayParam(PayParam param) {
        Merchant merchant = getMerchant(param);
        notEmpty(param.getUsername(), "支付账户数据有误");
        notEmpty(param.getPassword(), "支付密码数据错误");
        return merchant;
    }

    private Merchant getMerchant(PayParam param) {
        if (null == param) {
            throw new RuntimeException("支付环境参数错误");
        }
        notEmpty(param.getNo(), "支付订单编号不完整");
        if (param.getMerchantId() == null || param.getMerchantId() < 1) {
            throw new PayException("交易订单数据不完整");
        }
        Merchant mch = merchantService.findById(param.getMerchantId());
        if (null == mch) {
            throw new PayException("交易商户不存在,请联系服务提供商");
        }
        return mch;
    }

    private void notEmpty(String text, String message) {
        if (text == null || text.length() == 0) {
            throw new PayException(message);
        }
    }
}
