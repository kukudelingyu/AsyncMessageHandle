package com.example.lingyu.asyncmessagehandle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.lingyu.asyncmessagehandle.task.Request;
import com.example.lingyu.asyncmessagehandle.task.RequestExecutor;
import com.example.lingyu.asyncmessagehandle.task.Response;
import com.example.lingyu.asyncmessagehandle.task.ResultCallback;
import com.example.lingyu.asyncmessagehandle.utils.Logger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Request request = new Request("http://www.baidu.com");
        request.add("uername","zhangsan");
        request.add("password","123456");

        findViewById(R.id.btn_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestExecutor.INSTANCE.execute(request, new ResultCallback() {
                    @Override
                    public void onSucceed(Response response) {
                        Logger.wtf("执行结果为："+response.getResult());
                    }

                    @Override
                    public void onFailer(Exception e) {
                        Logger.wtf("执行结果为：执行失败");
                    }
                });
            }
        });
    }
}
