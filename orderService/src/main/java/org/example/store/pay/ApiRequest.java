package org.example.store.pay;

import com.google.gson.Gson;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;

public class ApiRequest {
    private static Logger logger = LoggerFactory.getLogger(ApiRequest.class);

    public static <T> ApiResponse<T> post(String url, List<NameValuePair> params, String data, Class resultClass) {
        CloseableHttpClient client = HttpClients.createDefault();
        URI uri = null;
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            if (null != params) {
                uriBuilder.setParameters(params);
            }
            uri = uriBuilder.build();
        } catch (URISyntaxException e) {
            throw new PayException("接口地址或者参数有误", e);
        }
        HttpPost req = new HttpPost(uri);
        req.addHeader("Content-type", "application/json; charset=utf-8");
        req.setHeader("Accept", "application/json");
        req.addHeader("User-Agent", "pay-client-v1.0");
        // 设置数据
        req.setEntity(new StringEntity(data, Charset.forName("UTF-8")));
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String resp = null;
        try {
            logger.info("post data:===>" + data);
            resp = client.execute(req, responseHandler);
        } catch (IOException e) {
            e.printStackTrace();
            throw new PayException("读取响应数据异常", e);
        }
        if (resp == null || resp.length() == 0) {
            throw new PayException("获取支付服务响应异常");
        }
        logger.info("return:==>" + resp);
        Gson gson = new Gson();
        Type type = type(ApiResponse.class, resultClass);
        ApiResponse response = gson.fromJson(resp, type);
        if (response.getCode() != 0) {
            throw new PayException(response.getMessage());
        }
        return response;
    }

    static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }
}
