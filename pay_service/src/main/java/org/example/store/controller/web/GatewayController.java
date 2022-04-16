package org.example.store.controller.web;

import com.alibaba.fastjson.JSON;

import org.example.store.controller.web.pay.order.ActionRouter;
import org.example.store.controller.web.pay.order.Create;
import org.example.store.controller.web.pay.order.Query;
import org.example.store.controller.web.pay.order.Test;
import org.example.store.controller.web.pay.order.vo.ActionProcessData;
import org.example.store.pojo.ApiResponse;
import org.example.store.pojo.Merchant;
import org.example.store.service.MerchantService;
import org.example.store.util.PayApiException;
import org.example.store.util.RSAUtils;
import org.example.store.util.Timestamp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class GatewayController {

    /**
     * 最大误差时间
     */
    @Value("${app.sign.max-deviation-time}")
    private int maxDeviationTime = 10000;

    private Map<String, ActionProcessData> processMap;
    @Resource
    private Create create;
    @Resource
    private Query query;
    @Resource
    private Test test;

    @Resource
    private MerchantService merchantService;


    @PostConstruct
    void init() {
        processMap = new HashMap<>();
        initAction(create);
        initAction(query);
        initAction(test);
    }

    void initAction(ActionProcess process) {
        ActionRouter annotation = process.getClass().getAnnotation(ActionRouter.class);
        if (null == annotation) {
            throw new RuntimeException(process.getClass().getName() + "没有设置ActionRouter注解");
        }
        processMap.put(annotation.name(), new ActionProcessData(annotation.paramType(), process));
    }

    @RequestMapping("/gateway.do")
    public ApiResponse process(@RequestBody String json, ApiParam params) {
        try {
            String method = params.getMethod();
            if (null == method || !processMap.containsKey(method.toString())) {
                throw new PayApiException(5000, "请求方法名无效");
            }
            // 验证数据
            Merchant merchant = checkRequestParam(params, json);
//            Map<String, Object>
//            String json = JSON.toJSONString(data);
            // 将数据转换成对象
            ActionProcessData action = processMap.get(method);
            ActionParam actionParam = (ActionParam) JSON.parseObject(json, action.getType());

            actionParam.merchant = merchant;
            return action.getProcess().process(actionParam);
        } catch (PayApiException e) {
            return ApiResponse.Error(e.getCode(), e.getMessage());
        } catch (Exception ex) {
            System.out.println(json);
            throw new RuntimeException(ex);
        }
    }

    /**
     * 验证请求参数是否合法
     *
     * @param param
     * @param paramJson
     * @return
     */
    private Merchant checkRequestParam(ApiParam param, String paramJson) {
        if (param == null) {
            throw new PayApiException(4000, "缺少参数");
        }
        if (param.getMch_id() == null) {
            throw new PayApiException(4001, "缺少mch_id参数");
        }

        if (!StringUtils.hasLength(param.getSign_type())) {
            throw new PayApiException(4002, "缺少签名参数");
        }
        if (!StringUtils.hasLength(param.getSign())) {
            throw new PayApiException(4003, "缺少签名参数");
        }
        if (!StringUtils.hasLength(param.getTimestamp())) {
            throw new PayApiException(4004, "缺少数据请求时间戳");
        }
        // 测试不要验证呵呵
        if (!"-1".equalsIgnoreCase(param.getTimestamp())) {
            try {
                long diff = Timestamp.parse(param.getTimestamp()).getTime() - System.currentTimeMillis();
                if (Math.abs(diff) > maxDeviationTime) {
                    throw new PayApiException(4004, "客户端系统时间和服务器误差过大(" + diff + "ms)");
                }
            } catch (Exception e) {
                throw new PayApiException(4004, "数据请求时间戳格式有误("+param.getTimestamp()+")");
            }
        }
        if (!StringUtils.hasLength(param.getCharset())) {
            throw new PayApiException(4005, "缺少数据编码格式");
        }

        // 检查商户数据
        Merchant m = merchantService.findById(param.getMch_id());
        if (m == null) {
            throw new PayApiException(4006, "商户编号不正确");
        }
        if (!StringUtils.hasLength(m.getPubKey()) || !StringUtils.hasLength(m.getSecret())) {
            throw new PayApiException(4007, "请先登录后台生成安全证书");
        }
        checkSign(paramJson, param, m);
        return m;
    }

    private void checkSign(String paramSource, ApiParam params, Merchant m) {
        if (!RSAUtils.verify(paramSource + params.getTimestamp(),
                params.getSign(), m.getPubKey(), params.getCharset())) {
            throw new RuntimeException("数据签名未通过,请检查安全证书是否配置正确");
        }
    }
}
