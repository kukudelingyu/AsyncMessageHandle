package com.example.lingyu.asyncmessagehandle.task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

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

    /**
     * ssl连接验证
     */
    private SSLSocketFactory factory;
    /**
     * 主机验证
     */
    private HostnameVerifier verifier;


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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public void setMethod(RequestMethod method) {
        this.method = method;
    }

    public SSLSocketFactory getFactory() {
        return factory;
    }

    /**
     * 设置 SSLSocketFactory
     * @param factory {@link SSLSocketFactory}
     */
    public void setFactory(SSLSocketFactory factory) {
        this.factory = factory;
    }

    public HostnameVerifier getVerifier() {
        return verifier;
    }

    /**
     * 设置 HostnameVerifier
     * @param verifier {@link HostnameVerifier}
     */
    public void setVerifier(HostnameVerifier verifier) {
        this.verifier = verifier;
    }

    @Override
    public String toString() {
        return "url = " + url + "; method = " + method + "; params = " + paramList.toString();
    }
}
