package com.example.androidsimple.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        PushAgent.getInstance(this).onAppStart();
    }

    // Activity页面onResume函数重载
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this); // 不能遗漏
    }
    // Activity页面onResume函数重载
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this); // 不能遗漏
    }

}
