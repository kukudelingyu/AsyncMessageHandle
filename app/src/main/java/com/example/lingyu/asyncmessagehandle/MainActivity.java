package com.example.lingyu.asyncmessagehandle;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.lingyu.asyncmessagehandle.http.FileBinary;
import com.example.lingyu.asyncmessagehandle.http.callback.OnProgressCallback;
import com.example.lingyu.asyncmessagehandle.http.callback.ResultCallback;
import com.example.lingyu.asyncmessagehandle.http.RequestExecutor;
import com.example.lingyu.asyncmessagehandle.http.request.RequestMethod;
import com.example.lingyu.asyncmessagehandle.http.Response;
import com.example.lingyu.asyncmessagehandle.http.request.StringRequest;
import com.example.lingyu.asyncmessagehandle.utils.Constants;
import com.example.lingyu.asyncmessagehandle.utils.Logger;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OnProgressCallback mCallback = new OnProgressCallback() {
            @Override
            public void showProgress(int what, int progress) {

                //Logger.E("what:"+what +"==="+"progress:" + progress);
            }
        };
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();

        File file1 = new File(path+"/Pictures/1522676262470.jpg");

        final StringRequest request = new StringRequest(Constants.url, RequestMethod.POST);
        //设置文件参数
        FileBinary binary = new FileBinary(file1);
        binary.setOnProgressCallback(mCallback,1);
        request.add("imageFile",binary);

        findViewById(R.id.btn_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestExecutor.INSTANCE.execute(request, new ResultCallback<String>() {
                    @Override
                    public void onSucceed(Response<String> response) {
                        Logger.I("执行成功!");
                    }

                    @Override
                    public void onFailer(Exception e) {

                        Logger.I("执行失败!" );
                    }
                });
            }
        });



    }
}
