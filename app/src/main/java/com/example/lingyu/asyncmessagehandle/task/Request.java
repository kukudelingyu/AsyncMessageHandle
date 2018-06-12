package com.example.lingyu.asyncmessagehandle.task;

import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    /**
     * 请求头
     */
    private Map<String,String> mRequestHeader;
    /**
     * 请求的数据类型
     */
    private String contentType;
    /**
     * 是否强制开启表单提交
     */
    private boolean isFormData = false;


    public Request(String url) {
        this(url,RequestMethod.GET);
    }

    public Request(String url, RequestMethod method) {
        this.url = url;
        this.method = method;
        paramList = new ArrayList<>();
        mRequestHeader = new HashMap<>();
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

    /**
     * 获取请求头
     * @return
     */
    public Map<String, String> getmRequestHeader() {

        return mRequestHeader;
    }

    /**
     * 设置请求头
     * @param key
     * @param value
     */
    public void setmRequestHeader(String key,String value) {

        mRequestHeader.put(key,value);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取请求方法
     * @return
     */
    public RequestMethod getMethod() {
        return method;
    }

    /**
     * 设置请求方法
     * @param method
     */
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

    /**
     * 获取请求的数据类型
     * @return
     */
    public String getContentType() {
        if(TextUtils.isEmpty(contentType)){
            return contentType;
        }else if(isFormData){         //是否是表单提交或者参数是文件（只能通过表单或者body来提交）
            //表单提交的特殊ContentType
            // ContentType:multipart/form-data boundary=--xxxxxxxx-------
            // 表单中的String item==========
            // Content-Disposition: form-data; name="keyName"       //相当于key=value中的key
            // Content-Type: text/plain charset: "utf-8"
            //
            // String的数据...
            // 表单中的File item========
            // Content-Disposition: form-data; name="keyName"; fileName="abc.jpg"       //相当于key=value中的key
            // Content-Type: image/jpeg;
            //
            // String的数据...
        }
            return "";

    }

    /**
     * 设置请求的数据类型
     * @param contentType
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void formData(boolean isFormData){
        if (method.isOutputMethod()){
            this.isFormData = isFormData;
        }else{
            throw new IllegalArgumentException(method.getMethod() + " is not support outputstream");

        }
    }

    @Override
    public String toString() {
        return "url = " + url + "; method = " + method + "; params = " + paramList.toString();
    }
}
