package com.example.lingyu.asyncmessagehandle.task;

import com.example.lingyu.asyncmessagehandle.utils.Logger;

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
        Logger.wtf("开始执行任务,请求体为："+request.toString());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Logger.wtf("任务执行完毕");

        Response response = new Response(200,"执行成功",null,request);

        Poster.getInstance().post(new Message(response,callback));

    }
}
