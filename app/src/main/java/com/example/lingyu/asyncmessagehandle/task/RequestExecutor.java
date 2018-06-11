package com.example.lingyu.asyncmessagehandle.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by duanlingyu on 2018/6/11.
 */

public enum  RequestExecutor {

    INSTANCE;

    private ExecutorService service;

    RequestExecutor() {
        service = Executors.newSingleThreadExecutor();
    }

    public void execute(){

    }
}
