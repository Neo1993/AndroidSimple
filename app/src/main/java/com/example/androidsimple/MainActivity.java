package com.example.androidsimple;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.androidsimple.activity.SoulPlantActivity;
import com.example.androidsimple.base.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private TextView soulPlantTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        addListener();
    }

    private void initView(){
        soulPlantTV = findViewById(R.id.soulPlantTV);
    }

    private void addListener(){
        soulPlantTV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.soulPlantTV:
                startActivity(new Intent(MainActivity.this, SoulPlantActivity.class));
                break;
        }
    }


}