package com.example.lingyu.asyncmessagehandle.http;

import java.io.File;
import java.io.OutputStream;

/**
 * Created by duanlingyu on 2018/6/14.
 */

public interface Binary {

    /**
     * 获取文件名称
     */
    String getFileNmae();

    /**
     * 获取ContentLength
     */
    long getBinaryLength();

    /**
     * 获取MimeType
     */
    String getMimeType();



    void onWriteBinary(OutputStream outputStream);


}
