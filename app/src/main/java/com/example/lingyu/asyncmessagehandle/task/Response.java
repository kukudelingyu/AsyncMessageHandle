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
    /**
     * 响应包体
     */
    private byte[] responseBody;

    public Response(int responseCode, byte[] responseBody, Exception exception, Request request) {
        this.responseCode = responseCode;
        this.responseBody = responseBody;
        this.exception = exception;
        this.request = request;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public byte[] getResult() {
        return responseBody;
    }

    public Exception getException() {
        return exception;
    }

    public Request getRequest() {
        return request;
    }
}
