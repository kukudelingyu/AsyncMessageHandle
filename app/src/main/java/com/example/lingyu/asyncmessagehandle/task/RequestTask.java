package com.example.lingyu.asyncmessagehandle.task;

import com.example.lingyu.asyncmessagehandle.urlconnection.UrlConnectionFactory;
import com.example.lingyu.asyncmessagehandle.utils.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by duanlingyu on 2018/6/11.
 * 执行request的任务线程
 */

public class RequestTask implements Runnable{

    private Request request;

    private ResultCallback callback;

    public RequestTask(Request request,ResultCallback callback) {
        this.request = request;
        this.callback = callback;
    }

    @Override
    public void run() {
        //异常
        Exception exception = null;
        //响应码
        int responseCode = 200;
        //请求的方法
        RequestMethod method = request.getMethod();
        //http连接
        HttpURLConnection httpURLConnection =null;
        //数据的输出流
        OutputStream outputStream = null;

        // 1.建立连接
        try {
            URL url = new URL(request.getUrl());
            //将okhttp 转成 httpurlconnection
            httpURLConnection = UrlConnectionFactory.getInstance().openUrl(url);

            if(httpURLConnection instanceof HttpsURLConnection){
                HttpsURLConnection connection = (HttpsURLConnection) httpURLConnection;
                if(request.getFactory() != null)
                connection.setSSLSocketFactory(request.getFactory());
                if (request.getVerifier() != null)
                connection.setHostnameVerifier(request.getVerifier());

            }
            //1.1 设置请求头以及相关信息
            httpURLConnection.setRequestMethod(method.getMethod());
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(method.isOutputMethod());


        // 2.发送数据 TODO ...
            //如果是POST DELETE方法才能获取输出流
            if(method.isOutputMethod()){
                outputStream = httpURLConnection.getOutputStream();
            }else{
                throw new IllegalArgumentException(method.getMethod() +"is not get outputstrean");
            }

        // 3.读取响应 TODO ...

        } catch (IOException e) {
            exception = e;
        }finally {
            httpURLConnection.disconnect();
        }


        Logger.wtf("任务执行完毕");

        Response response = new Response(responseCode,"执行成功",exception,request);

        Poster.getInstance().post(new Message(response,callback));

    }
}
