package com.example.androidsimple.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.androidsimple.R;
import com.example.androidsimple.bean.EvaluationRadarData;
import com.huawei.android.hms.agent.common.UIUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class EvaluationRadarView extends View {
    private Paint arcPaint = new Paint();
    private Paint dotPaint = new Paint();
    private Paint linePaint = new Paint();
    private Paint innerAreaPaint = new Paint();
    private Paint textPaint = new Paint();

    private int radius;
    private int radiusSpace;
    private int outStrokeWidth;
    private int innerStrokeWidth;
    private int radarOffset;
    private int textMargin;
    private float dotRadius;
    private int size = 5;
    private float angle;

    private Point centPoint = new Point();          //中心点
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
        radius = dip2px(80);
        radiusSpace = radius / 4;
        angle = (float) (2 * Math.PI / size);
        dotRadius = dip2px(5);
        outStrokeWidth = dip2px(3);
        innerStrokeWidth = dip2px(1);
        radarOffset = dip2px(30);
        textMargin = dip2px(16);

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

        innerAreaPaint.setAntiAlias(true);
        innerAreaPaint.setDither(true);

        textPaint.setAntiAlias(true);
        textPaint.setDither(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        centPoint.x = getWidth() / 2;
        centPoint.y = getHeight() / 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = radius * radarOffset * 2;
        int height = width;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawArc(canvas);
        drawDot(canvas);
        drawInnerArea(canvas);
        drawText(canvas);
    }

    private void drawArc(Canvas canvas) {
        for (int i = 1; i < 5; i++) {
            if (i == 4) {
                arcPaint.setPathEffect(null);
                arcPaint.setStrokeWidth(outStrokeWidth);
                arcPaint.setColor(Color.parseColor("#EEEEEF"));
            } else {
                arcPaint.setPathEffect(new DashPathEffect(new float[]{dip2px(4), dip2px(3)}, 0));
                arcPaint.setStrokeWidth(innerStrokeWidth);
                arcPaint.setColor(Color.parseColor("#E1E1E1"));
            }
            RectF rectF = new RectF(centPoint.x - i * radiusSpace, centPoint.y - i * radiusSpace, centPoint.x + i * radiusSpace, centPoint.y + i * radiusSpace);
            canvas.drawArc(rectF, -90, 360, false, arcPaint);
        }
    }

    private void drawDot(Canvas canvas) {
        for (int i = 0; i < size; i++) {
            float x = (float) (centPoint.x + radius * (Math.sin(angle * i)));
            float y = (float) (centPoint.y - radius * (Math.cos(angle * i)));
            canvas.drawLine(centPoint.x, centPoint.y, x, y, linePaint);
            canvas.drawCircle(x, y, dotRadius, dotPaint);
        }
    }

    private void drawInnerArea(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < size; i++) {
            EvaluationRadarData data = dataList.get(i);
            float percent = data.score / 100f;
            float x = (float) (centPoint.x + percent * radius * (Math.sin(angle * i)));
            float y = (float) (centPoint.y - percent * radius * (Math.cos(angle * i)));
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }
        path.close();
        innerAreaPaint.setStyle(Paint.Style.FILL);
        innerAreaPaint.setColor(Color.parseColor("#36FFC929"));
        canvas.drawPath(path, innerAreaPaint);

        innerAreaPaint.setStyle(Paint.Style.STROKE);
        innerAreaPaint.setStrokeWidth(dip2px(2));
        innerAreaPaint.setColor(Color.parseColor("#FFC929"));
        canvas.drawPath(path, innerAreaPaint);
    }

    private void drawText(Canvas canvas) {
        for (int i = 0; i < size; i++) {
            EvaluationRadarData data = dataList.get(i);
            String title = data.title;
            String score = String.valueOf(data.score);
            float x = (float) (centPoint.x + (radarOffset + radius) * (Math.sin(angle * i)));
            float y = (float) (centPoint.y - (radarOffset + radius) * (Math.cos(angle * i)));

            textPaint.setTextSize(sp2px(14));
            textPaint.setColor(Color.parseColor("#222325"));
            canvas.drawText(title, x, y, textPaint);
            textPaint.setTextSize(sp2px(11));
            textPaint.setColor(Color.parseColor("#8D949B"));
            canvas.drawText(String.format("( %s )", stripTrailingZeros(score)), x, y + textMargin, textPaint);
        }
    }

    public void setDataList(List<EvaluationRadarData> dataList) {
        if (dataList != null && !dataList.isEmpty()) {
            this.dataList.addAll(dataList);
            size = dataList.size();
            angle = (float) (2 * Math.PI / size);
        }
        invalidate();
    }

    /**
     * 屏幕宽度
     */
    public int screenWidth() {
        return getContext().getResources().getDisplayMetrics().widthPixels;
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

    /**
     * 去掉小数点后面多余的0
     * @param s
     */
    public static String stripTrailingZeros(String s) {
        if (s == null) {
            return "";
        }
        //若是String类型，也可以先转为BigDecimal
        BigDecimal value = new BigDecimal(s);
        //去除多余0
        BigDecimal noZeros = value.stripTrailingZeros();
        //BigDecimal => String
        String result = noZeros.toPlainString();
        return result;
    }

}
