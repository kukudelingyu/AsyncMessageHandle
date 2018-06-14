package com.example.lingyu.asyncmessagehandle.http;

import com.example.lingyu.asyncmessagehandle.http.callback.ResultCallback;
import com.example.lingyu.asyncmessagehandle.http.request.Request;
import com.example.lingyu.asyncmessagehandle.http.request.RequestMethod;
import com.example.lingyu.asyncmessagehandle.http.urlconnection.UrlConnectionFactory;
import com.example.lingyu.asyncmessagehandle.utils.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by duanlingyu on 2018/6/11.
 * 执行request的任务线程
 */

public class RequestTask<T> implements Runnable{
    /**
     * 请求体
     */
    private Request<T> request;

    private ResultCallback<T> callback;
    /**
     * 响应包体
     */
    private byte[] responseBody = null;

    public RequestTask(Request request,ResultCallback<T> callback) {
        this.request = request;
        this.callback = callback;
    }

    @Override
    public void run() {
        //异常
        Exception exception = null;
        //响应码
        int responseCode = 200;
        //响应头
        Map<String,List<String>> responseHeader;
        //请求的方法
        RequestMethod method = request.getMethod();
        //http连接
        HttpURLConnection httpURLConnection =null;
        //数据的输出流
        OutputStream outputStream = null;
        //服务器的输出流
        InputStream inputStream = null;

        // 1.建立连接
        try {
            URL url = new URL(request.getUrl());
            Logger.I("URL: "+request.getUrl());
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
            httpURLConnection.setConnectTimeout(request.getConnectionTimeOut());

            setRequestHeader(httpURLConnection,request);


        // 2.发送数据 TODO ...
            //如果是POST DELETE方法才能获取输出流
            if(method.isOutputMethod()){
                outputStream = httpURLConnection.getOutputStream();
                request.writeBody(outputStream);

            }

        // 3.读取响应 TODO ...
            responseCode = httpURLConnection.getResponseCode();
            Logger.I("responseCode:"+responseCode);
            responseHeader = httpURLConnection.getHeaderFields();
            if(isHadBody(request.getMethod(),responseCode)){
                inputStream = getInputStream(httpURLConnection,responseCode);
                ByteArrayOutputStream outputStream1 = new ByteArrayOutputStream();
                byte[] buffer = new byte[2048];
                int len;
                while ((len = inputStream.read(buffer))!= -1){
                    outputStream1.write(buffer,0,len);
                }
                outputStream1.close();
                responseBody = outputStream1.toByteArray();

            }else{
                Logger.I("没有响应包体");
            }


        } catch (Exception e) {
            exception = e;
            e.printStackTrace();
        }finally {
            if(httpURLConnection != null){
                httpURLConnection.disconnect();
            }
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }



        // 4.解析response

        T t = request.parseResponse(responseBody);

        Response<T> response = new Response<T>(responseCode,exception,request);

        response.setResult(t);

        Poster.getInstance().post(new Message(response,callback));
    }



    /**
     * 获取服务器的输出流
     */
    private InputStream getInputStream(HttpURLConnection httpURLConnection,int responseCode) throws IOException{
        if(responseCode >= 400){
            return httpURLConnection.getErrorStream();
        }else{
            return httpURLConnection.getInputStream();
        }
    }

    private void setRequestHeader(HttpURLConnection urlConnection, Request request) {
        //获取原来设置的请求头的参数
        Map<String,String> requestHeader = request.getmRequestHeader();
        //处理ContentType
        //覆盖掉用户手动输入的ContentType
        requestHeader.put("ContentType",request.getContentType());

        //处理ContentLength
        requestHeader.put("Content-Length",Long.toString(request.getContentLength()));


        //遍历用户输入的请求头的参数，添加到urlConnection中
        for (Map.Entry<String, String> stringStringEntry : requestHeader.entrySet()) {
            urlConnection.setRequestProperty(stringStringEntry.getKey(),stringStringEntry.getValue());
            Logger.I(stringStringEntry.getKey()+":  "+stringStringEntry.getValue());
        }

    }

    /**
     * 判断是否有响应包体
     * @param method
     * @param responseCode
     * @return
     */
    private boolean isHadBody(RequestMethod method,int responseCode){

        return !method.getMethod().equals(RequestMethod.HEAD)
                && !(100<= responseCode && responseCode < 200)
                && responseCode !=  204 && responseCode != 205
                && !(300<= responseCode && responseCode < 400);
    }



}
