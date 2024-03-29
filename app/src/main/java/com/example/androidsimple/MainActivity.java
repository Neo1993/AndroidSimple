package com.example.androidsimple;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.androidsimple.activity.CalendarActivity;
import com.example.androidsimple.activity.CommonWebActivity;
import com.example.androidsimple.activity.CustomViewActivity;
import com.example.androidsimple.activity.DownloadVideoActivity;
import com.example.androidsimple.activity.ProgressBarActivity;
import com.example.androidsimple.activity.SidebarActivity;
import com.example.androidsimple.activity.SoulPlantActivity;
import com.example.androidsimple.base.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private TextView soulPlantTV;
    private TextView webTV;
    private TextView progressbarTV;
    private TextView calendarTV;
    private TextView sidebarTV;
    private TextView downloadVideoTV;
    private TextView customViewTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        addListener();
    }

    private void initView(){
        soulPlantTV = findViewById(R.id.soulPlantTV);
        webTV = findViewById(R.id.webTV);
        progressbarTV = findViewById(R.id.progressbarTV);
        calendarTV = findViewById(R.id.calendarTV);
        sidebarTV = findViewById(R.id.sidebarTV);
        downloadVideoTV = findViewById(R.id.downloadVideoTV);
        customViewTV = findViewById(R.id.customViewTV);

        String imgUrl = "https://s3site.hanyubar.com/20230809/d21cfc77ed684c90bcc29211e04cc334.jpg";
//        String imgUrl = "https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF";
        Glide.with(this).load(imgUrl).into((ImageView) findViewById(R.id.testIV));
    }

    private void addListener(){
        soulPlantTV.setOnClickListener(this);
        webTV.setOnClickListener(this);
        progressbarTV.setOnClickListener(this);
        calendarTV.setOnClickListener(this);
        sidebarTV.setOnClickListener(this);
        downloadVideoTV.setOnClickListener(this);
        customViewTV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.soulPlantTV:
                startActivity(new Intent(MainActivity.this, SoulPlantActivity.class));
                break;
            case R.id.webTV:
                startActivity(new Intent(MainActivity.this, CommonWebActivity.class));
                break;
            case R.id.progressbarTV:
                startActivity(new Intent(MainActivity.this, ProgressBarActivity.class));
                break;
            case R.id.calendarTV:
                startActivity(new Intent(MainActivity.this, CalendarActivity.class));
                break;
            case R.id.sidebarTV:
                startActivity(new Intent(MainActivity.this, SidebarActivity.class));
                break;
            case R.id.downloadVideoTV:
                startActivity(new Intent(MainActivity.this, DownloadVideoActivity.class));
                break;
            case R.id.customViewTV:
                startActivity(new Intent(MainActivity.this, CustomViewActivity.class));
                break;
        }
    }


}