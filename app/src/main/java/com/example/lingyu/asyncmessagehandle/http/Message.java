package com.example.lingyu.asyncmessagehandle.http;

import com.example.lingyu.asyncmessagehandle.http.callback.ResultCallback;

/**
 * Created by duanlingyu on 2018/6/11.
 * 需要传递到主线程的消息
 */

public class Message implements Runnable{
    private Response response;

    private ResultCallback callback;

    public Message(Response response,ResultCallback callback) {
        this.response = response;
        this.callback = callback;
    }

    @Override
    public void run() {
        if(response.getException() != null){
            callback.onFailer(response.getException());
        }else{
            callback.onSucceed(response);
        }
    }
}
