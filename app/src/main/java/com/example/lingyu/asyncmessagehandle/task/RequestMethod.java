package com.example.lingyu.asyncmessagehandle.task;

/**
 * Created by duanlingyu on 2018/6/11.
 * 请求方法的类
 */

public enum  RequestMethod {

    GET("GET"),
    POST("POST"),
    HEAD("HEAD"),
    DELETE("DELETE");

    private String method;
    RequestMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return method;
    }
}
