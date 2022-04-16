package org.example.store.config;

import org.example.store.pojo.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class AppExceptionHandler {
    private static final String BAD_REQUEST_MSG = "请求参数错误";

    // <1> 处理 form data方式调用接口校验失败抛出的异常
    @ExceptionHandler(BindException.class)
    public ApiResponse bindExceptionHandler(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<String> collect = fieldErrors.stream()
                .map(o -> o.getDefaultMessage())
                .collect(Collectors.toList());
        return ApiResponse.Error(5004, BAD_REQUEST_MSG, collect);
    }

    // <2> 处理 json 请求体调用接口校验失败抛出的异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<String> collect = fieldErrors.stream()
                .map(o -> o.getDefaultMessage())
                .collect(Collectors.toList());
        return ApiResponse.Error(5005, BAD_REQUEST_MSG, collect);
    }

    // <3> 处理单个参数校验失败抛出的异常
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse constraintViolationExceptionHandler(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        List<String> collect = constraintViolations.stream()
                .map(o -> o.getMessage())
                .collect(Collectors.toList());
        return ApiResponse.Error(5006, BAD_REQUEST_MSG, collect);
    }


    @ExceptionHandler(SQLException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse sqlExceptionHandler(HttpServletRequest req, SQLException e) {
        return ApiResponse.Error(5002, "数据库执行异常:" + e.getMessage() + e.getSQLState());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse runtimeExceptionHandler(HttpServletRequest req, RuntimeException e) {
        e.printStackTrace();
        if (e instanceof HttpMessageConversionException) {
            return ApiResponse.Error(5003, "服务器参数异常:" + e.getMessage());
        }
        if (e instanceof IllegalArgumentException) {
            return ApiResponse.Error(5004, "参数异常:" + e.getMessage());
        }
        return ApiResponse.Error(5001, e.getMessage());
    }

    //
    @ExceptionHandler(value = Exception.class) // 要捕获的异常类型
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse exceptionHandler(HttpServletRequest req, Exception e) {

        return ApiResponse.Error(5000, "App Internal Error:" + e.getMessage());
    }
}
