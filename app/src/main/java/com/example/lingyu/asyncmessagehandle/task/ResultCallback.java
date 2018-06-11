package com.example.lingyu.asyncmessagehandle.task;

/**
 * Created by duanlingyu on 2018/6/11.
 * 响应完成后的回调接口
 */

public interface ResultCallback {

    void onSucceed(Response response);

    void onFailer(Exception e);

}
