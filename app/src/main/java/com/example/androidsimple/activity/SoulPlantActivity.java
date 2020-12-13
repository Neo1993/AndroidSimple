package com.example.androidsimple.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.androidsimple.R;
import com.example.androidsimple.adapter.MyPlantAdapter;
import com.example.androidsimple.adapter.TestAdapter;
import com.example.androidsimple.base.BaseActivity;
import com.example.androidsimple.bean.SearchBean;

import java.util.ArrayList;
import java.util.List;

import me.haowen.soulplanet.view.SoulPlanetsView;

public class SoulPlantActivity extends BaseActivity {
    private SoulPlanetsView soulPlanetView;
    private MyPlantAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soul_plant);
        initView();
        addListener();
    }

    private void initView() {
        soulPlanetView = findViewById(R.id.soulPlanetView);
        adapter = new MyPlantAdapter();
        soulPlanetView.setAdapter(adapter);
        testData();
    }

    private void addListener() {
        soulPlanetView.setOnTagClickListener(new SoulPlanetsView.OnTagClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, int position) {
                Toast.makeText(SoulPlantActivity.this,adapter.getItem(position).hs_name,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void testData(){
        String[] strings = new String[]{"信条","心动的信号","令人心动的offer","危情三日","蓝色防线","心灵传输者","火星救援",
        "意外杀手","这个杀手不太冷","机器人总动员","摔跤的爸爸","金刚川","八佰","三体","姜子牙","天堂电影院","流浪地球",
        "星际穿越","战狼2","少年张三丰"};

        List<SearchBean> searchBeanList = new ArrayList<>();
        for (int i = 0; i<strings.length; i++){
            SearchBean searchBean= new SearchBean();
            searchBean.hs_name = strings[i];
            searchBeanList.add(searchBean);
        }

        adapter.setData(searchBeanList);
    }

}
