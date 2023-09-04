package com.example.androidsimple.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.androidsimple.R;
import com.example.androidsimple.base.BaseActivity;
import com.example.androidsimple.bean.EvaluationRadarData;
import com.example.androidsimple.bean.RadarData;
import com.example.androidsimple.widget.EvaluationLevelView;
import com.example.androidsimple.widget.EvaluationRadarView;
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
        EvaluationRadarView radarView = findViewById(R.id.radarView);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                currentLevel++;
                runOnUiThread(()-> {
                    evaluationLevelView.setMaxLevel(9);
                    evaluationLevelView.setCurrentLevel(currentLevel);
                });
            }
        }, 0, 1000);


        List<EvaluationRadarData> dataList = new ArrayList<>();
        EvaluationRadarData data1 = new EvaluationRadarData("听力", 70);
        EvaluationRadarData data2 = new EvaluationRadarData("口语", 60);
        EvaluationRadarData data3 = new EvaluationRadarData("语法", 70);
        EvaluationRadarData data4 = new EvaluationRadarData("词汇", 60);
        EvaluationRadarData data5 = new EvaluationRadarData("阅读", 80);

        dataList.add(data1);
        dataList.add(data2);
        dataList.add(data3);
        dataList.add(data4);
//        dataList.add(data5);

        radarView.setDataList(dataList);
    }


}
