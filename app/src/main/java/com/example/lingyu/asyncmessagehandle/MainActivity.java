package com.example.lingyu.asyncmessagehandle;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.lingyu.asyncmessagehandle.http.FileBinary;
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

        String path = Environment.getExternalStorageDirectory().getAbsolutePath();

        File file1 = new File(path+"/tencent/MicroMsg/WeiXin/mmexport1504517025936.jpg");

        final StringRequest request = new StringRequest(Constants.url, RequestMethod.POST);
        request.add("imageFile",new FileBinary(file1));

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
