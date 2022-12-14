package com.demo.aspect;

import com.demo.common.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @className: DateUtil
 * @description: BodyWrite 确保controller返回都封装为Result对象
 * @version: 1.0
 * @author: minsky
 * @date: 2021/6/19
 */
@ControllerAdvice(basePackages = {"com.demo.controller"})
public class ResultHandler implements ResponseBodyAdvice {
    
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return Boolean.TRUE;
    }
    
    @Override
    public Object beforeBodyWrite(Object obj, MethodParameter methodParameter, MediaType mediaType, Class aClass,
        ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (obj instanceof Result) {
            Result<Object> result = (Result)obj;
            return result;
        }else {
            Result<Object> success = Result.success(obj);
            return success;
        }
    }
}
