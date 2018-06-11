package com.example.lingyu.asyncmessagehandle.task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by duanlingyu on 2018/6/11.
 * 请求体
 */

public class Request {
    /**
     * 请求的地址
     */
    private String url;
    /**
     * 请求的方法
     */
    private RequestMethod method;
    /**
     * 请求的参数
     */
    private List<Params> paramList;


    public Request(String url) {
        this(url,RequestMethod.GET);
    }

    public Request(String url, RequestMethod method) {
        this.url = url;
        this.method = method;
        paramList = new ArrayList<>();
    }

    /**
     * 增加参数
     * @param key
     * @param value
     */
    public void add(String key,String value){

        paramList.add(new Params(key,value));

    }

    public void add(String key,File value){

        paramList.add(new Params(key,value));

    }

    @Override
    public String toString() {
        return "url = " + url + "; method = " + method + "; params = " + paramList.toString();
    }
}
