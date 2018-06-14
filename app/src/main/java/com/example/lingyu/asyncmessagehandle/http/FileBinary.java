package com.example.lingyu.asyncmessagehandle.http;

import android.webkit.MimeTypeMap;

import com.example.lingyu.asyncmessagehandle.http.callback.OnProgressCallback;
import com.example.lingyu.asyncmessagehandle.http.urlconnection.ProgressMessge;
import com.example.lingyu.asyncmessagehandle.utils.CounterOutputStream;
import com.example.lingyu.asyncmessagehandle.utils.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by duanlingyu on 2018/6/14.
 */

public class FileBinary implements Binary {
    private File file;
    /**
     * 上传进度的回调
     */
    private OnProgressCallback callback;
    /**
     * 标识是哪个文件，类似于handler中的Message.whta
     */
    private int what;

    public FileBinary(File file) {
        if(file == null && !file.exists()){
            throw new IllegalArgumentException("file is null");
        }
        this.file = file;

    }

    /**
     * 获取文件的名称
     * @return
     */
    @Override
    public String getFileNmae() {
        return file.getName();
    }

    /**
     * 获取文件的长度
     * @return
     */
    @Override
    public long getBinaryLength() {


        return file.length();

    }

    public void setOnProgressCallback(OnProgressCallback callback,int what){
        this.callback = callback;
        this.what = what;
    }

    /**
     * 获取文件的MimeType
     * @return
     */
    @Override
    public String getMimeType() {
        String mimeType = "application/octet-stream";
        if(MimeTypeMap.getSingleton().hasExtension(file.getName())){
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(file.getName());
            return mimeType;
        }

        return mimeType;
    }

    /**
     * 写文件
     * @param outputStream
     */
    @Override
    public void onWriteBinary(OutputStream outputStream){
        long allLength = getBinaryLength();
        long hasUploadLength = 0;
        int progress = 0;
        InputStream stream = null;
        try {
            stream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = stream.read(buffer)) != -1){
                outputStream.write(buffer,0,len);
                hasUploadLength+=len;
                progress = (int) (hasUploadLength/allLength);
                Logger.E("hasUploadLength:" + hasUploadLength);
                Logger.E("allLength:" + allLength);
                Logger.E("progress:" + progress);
                Poster.getInstance().post(new ProgressMessge(callback,progress,what));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
