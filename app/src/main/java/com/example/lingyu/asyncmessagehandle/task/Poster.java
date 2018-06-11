package com.example.lingyu.asyncmessagehandle.task;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by duanlingyu on 2018/6/11.
 * 一个可获取在主线程的单例handler的类
 */

public class Poster extends Handler {

    private static Poster poster;

    private Poster() {
        super(Looper.getMainLooper());
    }

    /**
     * 获取单例
     * @return
     */
    public static Poster getInstance(){
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
