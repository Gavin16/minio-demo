package com.demo.common;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @className: Result
 * @description: 结果封装体
 * @version: 1.0
 * @author: minsky
 * @date: 2022/9/5
 */

public class Result<T> implements Serializable {
    private static final long serialVersionUID = 8048838914337668812L;
    private String requestId;
    private Boolean success;
    private Integer business;
    private Integer code;
    private String message;
    private String date;
    private String version;
    private T model;
    private static final String SUCCESS_MESSAGE = "SUCCESS";

    public Result() {
    }

    public Result(boolean success) {
        this.success = success;
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T model) {
        Result<T> result = new Result(true);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        result.setDate(sdf.format(new Date()));
        result.setCode(200);
        result.setMessage("SUCCESS");
        result.setSuccess(true);
        result.setModel(model);
        return result;
    }

    public static <T> Result<T> error(String message) {
        return error(message, 500);
    }

    public static <T> Result<T> error(String message, Integer code) {
        Result<T> result = new Result(false);
        result.setMessage(message);
        result.setCode(code);
        result.setSuccess(false);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        result.setDate(sdf.format(new Date()));
        return result;
    }

    public Result(String requestId, boolean success) {
        this.requestId = requestId;
        this.success = success;
        Integer code = success ? 200 : 500;
        this.setCode(code);
    }

    public boolean isSuccess() {
        return null == this.success ? false : this.success;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getBusiness() {
        return this.business;
    }

    public void setBusiness(Integer business) {
        this.business = business;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public T getModel() {
        return this.model;
    }

    public void setModel(T model) {
        this.model = model;
    }
}

