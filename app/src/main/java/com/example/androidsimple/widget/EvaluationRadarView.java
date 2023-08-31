package com.example.androidsimple.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.androidsimple.bean.EvaluationRadarData;

import java.util.ArrayList;
import java.util.List;

public class EvaluationRadarView extends View {
    private List<EvaluationRadarData> dataList = new ArrayList<>();

    public EvaluationRadarView(Context context) {
        super(context);
        initView();
    }

    public EvaluationRadarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {

    }

    public void setDataList(List<EvaluationRadarData> dataList) {
        if (dataList != null && !dataList.isEmpty()) {
            this.dataList.addAll(dataList);
        }
        invalidate();
    }


}
