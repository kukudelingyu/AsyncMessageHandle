package com.example.lingyu.asyncmessagehandle.utils;

import android.util.Log;

/**
 * Created by duanlingyu on 2018/6/11.
 */

public class Logger {
    /**
     * 日志的tag名称
     */
    private static String TAG = "AsyncMwssageHandle";
    /**
     * 是否打印日志 true打印  false不打印
     */
    private static boolean debug = true;


    private String getMessage(Object object){

        return object.toString()==null? "null":object.toString();
    }

    /**
     * 打印debug级别的日志
     * @param object
     */
    public void D (Object object){

        Log.d(TAG,getMessage(object));
    }
    /**
     * 打印info级别的日志
     * @param object
     */
    public void I (Object object){

        Log.i(TAG,getMessage(object));
    }
    /**
     * 打印warn级别的日志
     * @param object
     */
    public void W (Object object){

        Log.w(TAG,getMessage(object));
    }

    /**
     * 打印error级别的日志
     * @param object
     */
    public void E (Object object){

        Log.e(TAG,getMessage(object));

    }
    /**
     * 打印wtf级别的日志
     * @param object
     */
    public void wtf (Object object){

        Log.wtf(TAG,getMessage(object));

    }

}
