package com.example.lingyu.asyncmessagehandle.http.request;

import com.example.lingyu.asyncmessagehandle.utils.Logger;

/**
 * Created by duanlingyu on 2018/6/14.
 *
 * 继承自request  重写解析response的方法
 */

public class StringRequest extends Request<String> {


    public StringRequest(String url) {
        this(url, RequestMethod.GET);
    }

    public StringRequest(String url, RequestMethod method) {
        super(url, method);
    }

    @Override
    public String parseResponse(byte[] responseBody) {
        if(responseBody != null && responseBody.length > 0){
            Logger.I("ResponseBody: "+new String(responseBody));
            return new String(responseBody);
        }
        return "responseBody is null";
    }
}
