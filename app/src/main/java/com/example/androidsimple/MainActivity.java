package com.example.androidsimple;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.androidsimple.activity.CalendarActivity;
import com.example.androidsimple.activity.CommonWebActivity;
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
    }

    private void addListener(){
        soulPlantTV.setOnClickListener(this);
        webTV.setOnClickListener(this);
        progressbarTV.setOnClickListener(this);
        calendarTV.setOnClickListener(this);
        sidebarTV.setOnClickListener(this);
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
        }
    }


}