package com.example.androidsimple.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.androidsimple.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import anet.channel.util.Utils;

public class EvaluationLevelView extends View {
    private float progressWidth;
    private float barWidth;
    private float firstBarHeight;
    private float lastBarHeight;
    private float barOffset;
    private float space;
    private int currentLevel;
    private int maxLevel = 9;
    private int size;
    private float fontHeight;
    private float horizonOffset;
    private float offsetY;
    private float firstX;
    private float lastX;
    private float firstY;
    private float lastY;
    private List<String> tagList = new ArrayList<>();
    private Paint tagPaint = new Paint();
    private Paint barPaint = new Paint();
    private Paint progressPaint = new Paint();
    private Paint thumbPaint = new Paint();
    private Paint bubblePaint = new Paint();
    private List<Point> points = new ArrayList<>();

    public EvaluationLevelView(Context context) {
        super(context);
        initView();
    }

    public EvaluationLevelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public void initView() {
        size = maxLevel - 1;
        progressWidth = dip2px(7);
        barWidth = dip2px(10);
        firstBarHeight = dip2px(20);
        lastBarHeight = dip2px(100);
        fontHeight = tagPaint.getFontMetrics().bottom - tagPaint.getFontMetrics().top;
        offsetY = dip2px(42);
        horizonOffset = dip2px(10);

        tagPaint.setAntiAlias(true);
        tagPaint.setDither(true);
        tagPaint.setTextAlign(Paint.Align.CENTER);
        tagPaint.setColor(Color.parseColor("#8D949B"));
        tagPaint.setTextSize(sp2px(12));

        barPaint.setAntiAlias(true);
        barPaint.setDither(true);
        barPaint.setColor(Color.parseColor("#F4F6F8"));

        progressPaint.setAntiAlias(true);
        progressPaint.setDither(true);
        progressPaint.setColor(Color.parseColor("#F4F6F8"));

        thumbPaint.setAntiAlias(true);
        thumbPaint.setDither(true);
        thumbPaint.setStyle(Paint.Style.FILL);

        bubblePaint.setAntiAlias(true);
        bubblePaint.setDither(true);
        bubblePaint.setStyle(Paint.Style.FILL);
        bubblePaint.setTextAlign(Paint.Align.CENTER);
        bubblePaint.setTextSize(sp2px(12));

        for (int i = 0; i < maxLevel; i++) {
            tagList.add(String.format("T%d", i + 1));
        }
        barOffset = (lastBarHeight - firstBarHeight) / size;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = 0;
        int height = 0;
        //获得宽度MODE
        int modeW = MeasureSpec.getMode(widthMeasureSpec);
        //获得宽度的值
        if (modeW == MeasureSpec.AT_MOST) {
            width = MeasureSpec.getSize(widthMeasureSpec);
        }
        if (modeW == MeasureSpec.EXACTLY) {
            width = widthMeasureSpec;
        }
        if (modeW == MeasureSpec.UNSPECIFIED) {
            width = screenWidth();
        }
        //获得高度MODE
        int modeH = MeasureSpec.getMode(height);
        //获得高度的值
        if (modeH == MeasureSpec.AT_MOST) {
            height = MeasureSpec.getSize(heightMeasureSpec);
        }
        if (modeH == MeasureSpec.EXACTLY) {
            height = heightMeasureSpec;
        }
        if (modeH == MeasureSpec.UNSPECIFIED) {
            //ScrollView和HorizontalScrollView
            height = (int) (offsetY + dip2px(20 + 100 + 16) + fontHeight);
        }
        //设置宽度和高度
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        points.clear();
        float totalBarWidth = 0f;
        for (int i = 0; i < maxLevel; i++) {
            String tag = tagList.get(i);
            float width = tagPaint.measureText(tag);
            totalBarWidth += width;
        }

        space = (getWidth() - totalBarWidth - horizonOffset * 2) / size;

        float currentTotalWidth = 0f;
        for (int i = 0; i < maxLevel; i++) {
            String tag = tagList.get(i);
            float width = tagPaint.measureText(tag);
            float x = horizonOffset + i * space + currentTotalWidth + width / 2;

            drawTagText(canvas, tag, x);
            currentTotalWidth += width;

            drawBar(canvas, i, x);
        }

        drawProgress(canvas);
        drawThumb(canvas);
        drawLevelBubble(canvas);
    }

    private void drawTagText(Canvas canvas, String tag, float x) {
        float y = getHeight() - fontHeight / 2 ;
        canvas.drawText(tag, x, y, tagPaint);
    }

    private void drawBar(Canvas canvas, int index, float x) {
        int color = Color.parseColor(currentLevel - 1 == index ? "#FFC929" : "#F4F6F8");
        barPaint.setColor(color);

        float bottom = offsetY + dip2px(20 + 100);
        float top = bottom - (firstBarHeight + index * barOffset);
        RectF rectF = new RectF(x - barWidth / 2, top, x + barWidth / 2, bottom);
        canvas.drawRoundRect(rectF, dip2px(10), dip2px(10), barPaint);

        points.add(new Point((int)x, (int)top - dip2px(19)));

        if (index == 0) {
            firstX = x;
            firstY = top - dip2px(19);
        } else if (index == size) {
            lastX = x;
            lastY = top - dip2px(19);
        }
    }

    private void drawProgress(Canvas canvas) {
//        canvas.drawLine(firstX, firstY, lastX, lastY, progressPaint);
//        double angle = Math.atan(Math.abs((lastY - firstY) / lastX - firstX));
//        RectF rectF = new RectF(firstX - progressWidth / 4, firstY - progressWidth / 4, firstX + progressWidth / 4, firstY + progressWidth / 4);
//        canvas.drawArc(rectF, (float) (0 - angle - 90), - 180, false, progressPaint);

        canvas.save();
        RectF rectF  = new RectF(firstX, firstY - progressWidth / 2, lastX + barWidth / 2, firstY + progressWidth / 2);
        canvas.skew((firstY - lastY) / (lastX - firstX), 0 - (firstY - lastY) / (lastX - firstX));
        canvas.translate(0 - space - barWidth / 2, 0);
        canvas.drawRoundRect(rectF, progressWidth, progressWidth, progressPaint);
        canvas.restore();

    }

    private void drawThumb(Canvas canvas) {
        Point point = points.get(currentLevel - 1);
        thumbPaint.setColor(Color.parseColor("#FFC929"));
        canvas.drawCircle(point.x, point.y + dip2px(3) - 1, dip2px(6), thumbPaint);
        thumbPaint.setColor(Color.parseColor("#FFFFFF"));
        canvas.drawCircle(point.x, point.y + dip2px(3) - 1, dip2px(3) - 2, thumbPaint);
    }

    private void drawLevelBubble(Canvas canvas) {
        Point point = points.get(currentLevel - 1);
        int centX = point.x;
        int centY = point.y;
        String text = tagList.get(currentLevel - 1);
        float fontWidth = bubblePaint.measureText(text);
        float fongtHeight = bubblePaint.getFontMetrics().bottom - bubblePaint.getFontMetrics().top;
        float horizonPadding = dip2px(10);
        float vertiPadding = dip2px(7);
        float triangle = dip2px(5);
        float marginBottom = dip2px(15);
        RectF rectF = new RectF(centX - fontWidth / 2 -  horizonPadding, centY - vertiPadding * 2 + triangle - fongtHeight - marginBottom, centX + fontWidth /2 + horizonPadding, centY - triangle - marginBottom);
        bubblePaint.setColor(Color.parseColor("#FFC929"));
        canvas.drawRoundRect(rectF, dip2px(20), dip2px(20), bubblePaint);
        Path path = new Path();
        path.moveTo(centX - triangle, centY - marginBottom - triangle);
        path.lineTo(centX,centY - marginBottom);
        path.lineTo(centX + triangle, centY - marginBottom - triangle);
        path.close();
        canvas.drawPath(path, bubblePaint);
        bubblePaint.setColor(Color.parseColor("#333333"));
        canvas.drawText(text, centX, centY - triangle - marginBottom - vertiPadding + 2, bubblePaint);
    }

    private void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        if(currentLevel < 1) currentLevel = 1;
        if(currentLevel > maxLevel) {
           currentLevel = maxLevel;
        }
        this.currentLevel = currentLevel;
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

}


