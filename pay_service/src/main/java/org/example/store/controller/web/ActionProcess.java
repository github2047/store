package org.example.store.controller.web;


import org.example.store.pojo.ApiResponse;
import org.example.store.util.PayApiException;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public interface ActionProcess<T extends ActionParam> {
    ApiResponse process(T actionParam);

    SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    default String getTimestamp() {
        return date.format(new Date());
    }

    default void isEmpty(Object text, int code, String message) {
        if (text == null || !StringUtils.hasLength(text.toString())) {
            throw new PayApiException(code, message);
        }
    }

    default void checkRequiredParam(Object... params) {
        if (params != null) {
            for (Object param : params) {
                isEmpty(param, 50001, "参数不正确");
            }
        }
    }
}
