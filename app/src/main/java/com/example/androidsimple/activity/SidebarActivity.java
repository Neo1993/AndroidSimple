package com.example.androidsimple.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.androidsimple.R;
import com.example.androidsimple.base.BaseActivity;
import com.example.androidsimple.utils.PinYinStringHelper;

public class SidebarActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sidebar);
        initView();
    }

    private void initView(){
       String alpha =  PinYinStringHelper.getAlpha("张三");
        Log.d("TAG", "alpha====" + alpha);

    }

}
