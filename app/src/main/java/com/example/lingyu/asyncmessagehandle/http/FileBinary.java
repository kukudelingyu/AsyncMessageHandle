package com.example.lingyu.asyncmessagehandle.http;

import android.webkit.MimeTypeMap;

import com.example.lingyu.asyncmessagehandle.utils.CounterOutputStream;

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


    public FileBinary(File file) {
        if(file == null && !file.exists()){
            throw new IllegalArgumentException("file is null");
        }
        this.file = file;
    }


    @Override
    public String getFileNmae() {
        return file.getName();
    }

    @Override
    public long getBinaryLength() {


        return file.length();

    }

    @Override
    public String getMimeType() {
        String mimeType = "application/octet-stream";
        if(MimeTypeMap.getSingleton().hasExtension(file.getName())){
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(file.getName());
            return mimeType;
        }

        return mimeType;
    }

    @Override
    public void onWriteBinary(OutputStream outputStream){
        InputStream stream = null;
        try {
            stream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = stream.read(buffer)) != -1){
                outputStream.write(buffer,0,len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
