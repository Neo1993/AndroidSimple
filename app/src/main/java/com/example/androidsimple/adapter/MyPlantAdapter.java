package com.example.androidsimple.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidsimple.bean.SearchBean;

import java.util.ArrayList;
import java.util.List;

import me.haowen.soulplanet.adapter.PlanetAdapter;
import me.haowen.soulplanet.utils.SizeUtils;
import me.haowen.soulplanet.view.PlanetView;

public class MyPlantAdapter extends PlanetAdapter {
    private List<SearchBean> dataList = new ArrayList<>();

    public void setData(List<SearchBean> datas){
        dataList.clear();
        dataList.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public View getView(Context context, int position, ViewGroup parent) {
        PlanetView planetView = new PlanetView(context);
        planetView.setSign(getItem(position).hs_name);

        int starColor = position % 2 == 0 ? PlanetView.COLOR_FEMALE : PlanetView.COLOR_MALE;
        boolean hasShadow = false;

        String str = "";
        if (position % 12 == 0) {
            str = "最活跃";
            starColor = PlanetView.COLOR_MOST_ACTIVE;
        } else if (position % 20 == 0) {
            str = "最匹配";
            starColor = PlanetView.COLOR_BEST_MATCH;
        } else if (position % 33 == 0) {
            str = "最新人";
            starColor = PlanetView.COLOR_MOST_NEW;
        } else if (position % 18 == 0) {
            hasShadow = true;
            str = "最闪耀";
        } else {
            str = "描述";
        }
        planetView.setStarColor(starColor);
        planetView.setHasShadow(hasShadow);
        planetView.setMatch(position * 2 + "%", str);
        if (hasShadow) {
            planetView.setMatchColor(starColor);
        } else {
            planetView.setMatchColor(PlanetView.COLOR_MOST_ACTIVE);
        }
        int starWidth = SizeUtils.dp2px(context, 50.0f);
        int starHeight = SizeUtils.dp2px(context, 85.0f);
        int starPaddingTop = SizeUtils.dp2px(context, 20.0f);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(starWidth, starHeight);
        planetView.setPadding(0, starPaddingTop, 0, 0);
        planetView.setLayoutParams(layoutParams);
        return planetView;
    }

    @Override
    public SearchBean getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public int getPopularity(int position) {
        return 0;
    }

    @Override
    public void onThemeColorChanged(View view, int themeColor) {

    }
}
