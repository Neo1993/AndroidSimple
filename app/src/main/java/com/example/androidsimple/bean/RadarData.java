package com.example.androidsimple.bean;

public class RadarData {
    public String title;

    public double percent1 = 1;
    public double percent2;

    public void initData(int position) {
        title = String.format("第%d组数据", position);
        percent2 = Math.random();
    }

}
