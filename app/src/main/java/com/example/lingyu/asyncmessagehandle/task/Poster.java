package com.example.lingyu.asyncmessagehandle.task;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by duanlingyu on 2018/6/11.
 */

public class Poster extends Handler {

    private Poster poster;

    private Poster() {
        super(Looper.getMainLooper());
    }

    /**
     * 获取单例
     * @return
     */
    public Poster getInstance(){
        if (poster == null){
            synchronized (Poster.class){
                if (poster == null){
                    poster = new Poster();
                }
            }
        }

        return poster;
    }
}
