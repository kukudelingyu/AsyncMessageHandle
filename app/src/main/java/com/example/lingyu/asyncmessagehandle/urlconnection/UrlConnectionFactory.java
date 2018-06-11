package com.example.lingyu.asyncmessagehandle.urlconnection;

import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.internal.huc.OkHttpURLConnection;
import okhttp3.internal.huc.OkHttpsURLConnection;

/**
 * created by lingyu on 2018/6/12
 */
public class UrlConnectionFactory {

    private static UrlConnectionFactory urlConnectionFactory;

    public static UrlConnectionFactory getInstance(){
        if(urlConnectionFactory == null){
            synchronized (UrlConnectionFactory.class){
                if (urlConnectionFactory == null){
                    urlConnectionFactory = new UrlConnectionFactory();
                }

            }
        }


            return urlConnectionFactory;
    }

    /**
     * Okhttp 的客户端
     */
    private static OkHttpClient httpClient;

    /**
     * 打开一个连接
     * @param url
     * @return
     */
    public HttpURLConnection openUrl(URL url){
        return openUrl(url,null);
    }

    /**
     * 打开一个可以设置代理连接
     * @param url
     * @param proxy {@link Proxy}
     * @return
     */
    public HttpURLConnection openUrl(URL url, Proxy proxy){
        String protocol = url.getProtocol();
        OkHttpClient copy = httpClient.newBuilder()
                .proxy(proxy)
                .build();

        if (protocol.equals("http")) return new OkHttpURLConnection(url, copy);
        if (protocol.equals("https")) return new OkHttpsURLConnection(url, copy);
        throw new IllegalArgumentException("Unexpected protocol: " + protocol);

    }
}
