package com.example.androidsimple.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.androidsimple.R;
import com.example.androidsimple.bean.EvaluationRadarData;
import com.huawei.android.hms.agent.common.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class EvaluationRadarView extends View {
    private Paint arcPaint = new Paint();
    private Paint dotPaint = new Paint();
    private Paint linePaint = new Paint();

    private int radius;
    private int radiusSpace;
    private int outStrokeWidth;
    private int innerStrokeWidth;
    private int radarOffset;
    private float dotRadius;
    private int size = 5;
    private float angle;
    private int realRadius;
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
        angle = (float) (2 * Math.PI / size);
        dotRadius = dip2px(10);

        arcPaint.setAntiAlias(true);
        arcPaint.setDither(true);
        arcPaint.setStyle(Paint.Style.STROKE);

        dotPaint.setAntiAlias(true);
        dotPaint.setDither(true);
        dotPaint.setStyle(Paint.Style.FILL);
        dotPaint.setColor(Color.parseColor("#EEEEEF"));

        linePaint.setAntiAlias(true);
        linePaint.setDither(true);
        linePaint.setColor(Color.parseColor("#E1E1E1"));
        linePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        linePaint.setStrokeWidth(dip2px(1));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        radius = getWidth() / 2;
        radiusSpace = radius / 4;

        outStrokeWidth = dip2px(5);
        innerStrokeWidth = dip2px(1);
        radarOffset = outStrokeWidth / 2 + 3;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawArc(canvas);
        drawDot(canvas);
    }

    private void drawArc(Canvas canvas) {
        for (int i = 1; i < size; i++) {
            if (i == size - 1) {
                arcPaint.setPathEffect(null);
                arcPaint.setStrokeWidth(outStrokeWidth);
                arcPaint.setColor(Color.parseColor("#EEEEEF"));
            } else {
                arcPaint.setPathEffect(new DashPathEffect(new float[]{dip2px(4), dip2px(3)}, 0));
                arcPaint.setStrokeWidth(innerStrokeWidth);
                arcPaint.setColor(Color.parseColor("#E1E1E1"));
            }
            RectF rectF = new RectF(radarOffset + radius - i * radiusSpace, radius - i * radiusSpace + radarOffset,radius + i * radiusSpace - radarOffset, radius + i * radiusSpace - radarOffset);
            canvas.drawArc(rectF, -90, 360, false, arcPaint);
        }
    }

    private void drawDot(Canvas canvas) {
        realRadius = radius - radarOffset;
        for (int i = 0; i < size; i++) {
            float x = (float) (radius + realRadius * (Math.sin(angle * i)));
            float y = (float) (radius - realRadius * (Math.cos(angle * i)));
            canvas.drawLine(radius, radius, x, y, linePaint);
            canvas.drawCircle(x, y, dotRadius, dotPaint);
        }
    }

    private void drawInnerDot(Canvas canvas) {

    }

    public void setDataList(List<EvaluationRadarData> dataList) {
        if (dataList != null && !dataList.isEmpty()) {
            this.dataList.addAll(dataList);
            size = dataList.size();
        }
        invalidate();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     * @param dpValue   dp值
     * @return  px值
     */
    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     * @param pxValue 字体大小像素
     * @return 字体大小sp
     */
    public int px2sp(Context context, float pxValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     * @param spValue 字体大小sp
     * @return 字体大小像素
     */
    public int sp2px(float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
