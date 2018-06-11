package com.example.lingyu.asyncmessagehandle.task;

/**
 * Created by duanlingyu on 2018/6/11.
 * 相应体
 */

public class Response {
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

    public Response(int responseCode, String result, Exception exception, Request request) {
        this.responseCode = responseCode;
        this.result = result;
        this.exception = exception;
        this.request = request;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResult() {
        return result;
    }

    public Exception getException() {
        return exception;
    }

    public Request getRequest() {
        return request;
    }
}
