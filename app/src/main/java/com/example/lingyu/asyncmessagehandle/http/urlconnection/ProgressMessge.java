package com.example.lingyu.asyncmessagehandle.http.urlconnection;

import com.example.lingyu.asyncmessagehandle.http.callback.OnProgressCallback;

/**
 * Created by duanlingyu on 2018/6/14.
 */

public class ProgressMessge implements Runnable {
    private OnProgressCallback callback;

    private int progress;

    private int what;

    public ProgressMessge(OnProgressCallback callback,int progress,int what) {
        this.callback = callback;
        this.progress = progress;
        this.what = what;
    }

    @Override
    public void run() {
        callback.showProgress(what,progress);
    }
}
