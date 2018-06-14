package com.example.lingyu.asyncmessagehandle.http;

import com.example.lingyu.asyncmessagehandle.http.request.Request;

/**
 * Created by duanlingyu on 2018/6/11.
 * 相应体
 */

public class Response<T> {
    /**
     * 响应码
     */
    private int responseCode;
    /**
     * 结果
     */
    private String result;
    /**
     * 发生的异常
     */
    private Exception exception;
    /**
     * 请求体
     */
    private Request request;
    /**
     * 响应包体
     */
    private T responseBody;

    public Response(int responseCode, Exception exception, Request request) {
        this.responseCode = responseCode;
        this.exception = exception;
        this.request = request;
    }

    public int getResponseCode() {
        return responseCode;
    }

    /**
     * 返回响应包体
     * @return
     */
    public T getResult() {
        return responseBody;
    }

    public void setResult(T responseBody){
        this.responseBody = responseBody;
    }

    public Exception getException() {
        return exception;
    }

    public Request getRequest() {
        return request;
    }
}
