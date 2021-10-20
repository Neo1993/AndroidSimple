package com.example.androidsimple.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.example.androidsimple.R;
import com.example.androidsimple.base.BaseActivity;
import com.example.androidsimple.widget.CustomDownloadBar;

public class ProgressBarActivity extends BaseActivity {
    private CustomDownloadBar progressbar;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            progressbar.setProgress(msg.what,100);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progressbar);
        initView();
    }

    private void initView() {
        progressbar = findViewById(R.id.progressbar);

        //开启线程执行任务
        new Thread(new Runnable() {
            @Override
            public void run() {
                runProgressBar();
            }
        }).start();

        //Lambda表达式实现
   /*     new Thread(() -> {
            runProgressBar();
        }).start();*/
    }

    private void runProgressBar() {
        int progress = 0;
        while (progress <= 99) {
            progress += 1;
            handler.obtainMessage(progress).sendToTarget();
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
