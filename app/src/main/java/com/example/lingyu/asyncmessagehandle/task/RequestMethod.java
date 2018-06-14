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

    public String getMethod(){
        return this.method;
    }

    @Override
    public String toString() {
        return method;
    }

    public boolean isOutputMethod() {
        switch (this){
            case POST:
            case DELETE:
                return true;
            default:
                return false;

        }

    }
}
