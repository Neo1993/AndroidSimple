package com.example.androidsimple.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.androidsimple.R;
import com.example.androidsimple.base.BaseActivity;
import com.example.androidsimple.bean.RadarData;
import com.example.androidsimple.widget.EvaluationLevelView;
import com.example.androidsimple.widget.RadarView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CustomViewActivity extends BaseActivity {
    private int currentLevel = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        EvaluationLevelView evaluationLevelView = findViewById(R.id.evaluationLevelView);
        RadarView radarView = findViewById(R.id.evaluationRadarView);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                currentLevel++;
                runOnUiThread(()-> {
                    evaluationLevelView.setCurrentLevel(currentLevel);
                });
            }
        }, 0, 1000);


        List<RadarData> dataList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            RadarData radarData = new RadarData();
            radarData.initData(i);
            dataList.add(radarData);
        }

        radarView.setData(dataList);
    }


}
