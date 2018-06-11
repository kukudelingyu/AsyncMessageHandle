package com.example.lingyu.asyncmessagehandle.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by duanlingyu on 2018/6/11.
 * 线程执行器
 */

public enum  RequestExecutor {
    //保证全局唯一，用枚举
    INSTANCE;

    private ExecutorService service;


    RequestExecutor() {
        service = Executors.newSingleThreadExecutor();
    }

    public void execute(Request request,ResultCallback callback){

        service.execute(new RequestTask(request,callback));
    }
}
