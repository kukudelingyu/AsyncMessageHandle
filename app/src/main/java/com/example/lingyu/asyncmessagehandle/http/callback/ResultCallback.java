package com.example.lingyu.asyncmessagehandle.http.callback;

import com.example.lingyu.asyncmessagehandle.http.Response;

/**
 * Created by duanlingyu on 2018/6/11.
 * 响应完成后的回调接口
 */

public interface ResultCallback<T> {

    void onSucceed(Response<T> response);

    void onFailer(Exception e);

}
