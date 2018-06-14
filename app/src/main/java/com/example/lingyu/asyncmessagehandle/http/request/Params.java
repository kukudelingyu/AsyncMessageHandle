package com.example.lingyu.asyncmessagehandle.http.request;

import com.example.lingyu.asyncmessagehandle.http.Binary;

import java.io.File;

/**
 * Created by duanlingyu on 2018/6/11.
 * 请求的参数
 */

public class Params {

    private String key;

    private Object value;

    public Params(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Params(String key, Binary value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "key = "+ key + "; value = " + value;
    }
}
