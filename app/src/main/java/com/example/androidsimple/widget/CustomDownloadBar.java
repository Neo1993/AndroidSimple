package com.example.androidsimple.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.widget.TintTypedArray;

import com.example.androidsimple.R;

public class CustomDownloadBar extends View {

    //绘制进度条和进度文字的画笔
    private Paint mProgressPaint;
    private Paint mTextPaint;
    private Paint mThumbPaint;

    private Context mContext;

    //进度条的底色和完成进度的颜色
    private int mProgressBackColor;
    private int mProgressForeColor;

    //进度条上方现实的文字
    private String mProgressText;
    //进度文字的颜色
    private int mTextColor;
    //进度文字的字体大小
    private int mTextSize;

    //进度条的起始值，当前值和结束值
    private float currentProgress;
    private float maxProgress;

    //进度条的高度
    private int mProgressBarHeight;

    //用于测量文字显示区域的宽度和高度
    private Paint.FontMetricsInt mTextFontMetrics;
    private Rect mTextBound;

    //用于绘制三角形的箭头
    private Path mPath;

    //进度条和进度文字显示框的间距
    private int mBar2TextDividerHeight;

    //三角形箭头的高度
    private int mTriangleHeight;

    //绘制进度条圆角矩形的圆角
    private int mRectCorn;

    //进度指示器外圈大小
    private int mLargeThumbRadius;
    //进度指示器内圈大小
    private int mSmallThumbRadius;

    //进度指示器外圈颜色
    private int mLargeThumbColor;
    //进度指示器内圈颜色
    private int mSmallThumbColor;

    //文字左右内边距
    private int mTextLeftRightPadding;
    //文字上下内边距
    private int mTextTopBottomPadding;

    //外部溢出宽度
    private int outWidth;

    //进度框圆角大小
    private int mTextBoxCorner;

    public CustomDownloadBar(Context context) {
        this(context, null);
    }

    public CustomDownloadBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    @SuppressLint("RestrictedApi")
    public CustomDownloadBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        if (attrs != null) {
            TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs, R.styleable.DownloadBar, defStyleAttr, defStyleAttr);
            int n = a.getIndexCount();
            for (int i = 0; i < n; i++) {
                int attr = a.getIndex(i);
                switch (attr) {
                    case R.styleable.DownloadBar_backColor:
                        mProgressBackColor = a.getColor(attr, Color.GRAY);
                        break;
                    case R.styleable.DownloadBar_foreColor:
                        mProgressForeColor = a.getColor(attr, Color.GRAY);
                        break;
                    case R.styleable.DownloadBar_boxTextColor:
                        mTextColor = a.getColor(attr, Color.GRAY);
                        break;
                    case R.styleable.DownloadBar_maxProgress:
                        maxProgress = a.getFloat(attr, 0);
                        break;
                    case R.styleable.DownloadBar_currentProgress:
                        currentProgress = a.getFloat(attr, 0);
                        mProgressText = String.valueOf((int) currentProgress) + "%";
                        break;
                    case R.styleable.DownloadBar_progressTextSize:
                        mTextSize = (int)a.getDimension(attr, 24);
                        break;
                    case R.styleable.DownloadBar_largeThumbRadius:
                        mLargeThumbRadius = (int)a.getDimension(attr, 8);
                        break;
                    case R.styleable.DownloadBar_smallThumbRadius:
                        mSmallThumbRadius = (int)a.getDimension(attr, 3);
                        break;
                    case R.styleable.DownloadBar_textLeftRightPadding:
                        mTextLeftRightPadding = (int)a.getDimension(attr, 5.5f);
                        break;
                    case R.styleable.DownloadBar_textTopBottomPadding:
                        mTextTopBottomPadding = (int)a.getDimension(attr, 3.5f);
                        break;
                    case R.styleable.DownloadBar_progressBarHeight:
                        mProgressBarHeight = (int)a.getDimension(attr, 5f);
                    case R.styleable.DownloadBar_triangleHeight:
                        mTriangleHeight = (int)a.getDimension(attr, 4.25f);
                        break;
                    case R.styleable.DownloadBar_bar2TextDividerHeight:
                        mBar2TextDividerHeight = (int)a.getDimension(attr, 8f);
                        break;
                    case R.styleable.DownloadBar_largeThumbColor:
                        mLargeThumbColor = a.getColor(attr, Color.GRAY);
                        break;
                    case R.styleable.DownloadBar_smallThumbColor:
                        mSmallThumbColor = a.getColor(attr, Color.GRAY);
                        break;
                    case R.styleable.DownloadBar_textBoxCorner:
                        mTextBoxCorner = (int)a.getDimension(attr, 3f);
                        break;
                }
            }
            a.recycle();
        }
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth() , getPaddingTop() + mTextTopBottomPadding * 2 + mTextFontMetrics.bottom - mTextFontMetrics.top + mTriangleHeight + mBar2TextDividerHeight + outWidth  + getPaddingBottom());
    }

    private void init() {
        mRectCorn = mProgressBarHeight / 2;
        mTextBound = new Rect();
        mProgressPaint = new Paint();
        mProgressPaint.setAntiAlias(true);      //抗锯齿
        mProgressPaint.setDither(true);          //防抖动
        mProgressPaint.setStyle(Paint.Style.FILL);
        mProgressPaint.setStrokeCap(Paint.Cap.ROUND);
        mProgressPaint.setStrokeWidth(mProgressBarHeight);

        mThumbPaint = new Paint();
        mThumbPaint.setAntiAlias(true);
        mThumbPaint.setDither(true);
        mThumbPaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.setColor(mTextColor);
        reCaculateTextSize();

        mPath = new Path();
    }

    private void reCaculateTextSize() {
        mTextPaint.setTextSize(mTextSize);
        mTextFontMetrics = mTextPaint.getFontMetricsInt();
        mTextPaint.getTextBounds(mProgressText, 0, mProgressText.length(), mTextBound);

        int textWidth = mTextBound.width();
        int textBoxWidth = textWidth + mTextLeftRightPadding * 2;
        outWidth = Math.max(mLargeThumbRadius * 2, textBoxWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制前清理上次绘制的痕迹
        mPath.reset();
        mProgressPaint.setColor(mProgressBackColor);

        //计算文字显示区域的宽度和高度
        int textWidth = mTextBound.width();
        int textHeight = mTextBound.height();

        //计算开始绘制进度条的y坐标
        int startLineLocationY = getPaddingTop() + mTextTopBottomPadding * 2 + textHeight + mTriangleHeight + mBar2TextDividerHeight + mLargeThumbRadius - mProgressBarHeight / 2;

        //绘制进度条底部背景
        canvas.drawRoundRect(outWidth / 2, startLineLocationY, getMeasuredWidth() - outWidth / 2, startLineLocationY + mProgressBarHeight, mRectCorn, mRectCorn, mProgressPaint);

        //绘制已经完成了的进度条
        mProgressPaint.setColor(mProgressForeColor);
        double progress = currentProgress / maxProgress ;

        int currProgress = (int) ((getMeasuredWidth() - outWidth) * progress) + outWidth / 2;
        canvas.drawRoundRect(mLargeThumbRadius, startLineLocationY, currProgress, startLineLocationY + mProgressBarHeight, mRectCorn, mRectCorn, mProgressPaint);

        //绘制thumb
        mThumbPaint.setColor(mLargeThumbColor);
        canvas.drawCircle(currProgress, startLineLocationY + mProgressBarHeight / 2, mLargeThumbRadius, mThumbPaint);
        mThumbPaint.setColor(mSmallThumbColor);
        canvas.drawCircle(currProgress, startLineLocationY + mProgressBarHeight / 2, mSmallThumbRadius, mThumbPaint);

        /*
        绘制显示文字三角形框
         */
        //计算三角形定点开始时的y坐标
        int startTriangleY = startLineLocationY - mBar2TextDividerHeight;

        mPath.moveTo(currProgress, startTriangleY);
        mPath.lineTo(currProgress + mTriangleHeight, startTriangleY - mTriangleHeight);
        mPath.lineTo(currProgress - mTriangleHeight, startTriangleY - mTriangleHeight);
        mPath.close();
        canvas.drawPath(mPath, mProgressPaint);

        canvas.drawRoundRect(currProgress - textWidth / 2 - mTextLeftRightPadding, startTriangleY - mTriangleHeight - textHeight - mTextTopBottomPadding * 2,
                currProgress + textWidth / 2 + mTextLeftRightPadding, startTriangleY - mTriangleHeight,
                mTextBoxCorner, mTextBoxCorner, mProgressPaint);

        //绘制文字
        canvas.drawText(mProgressText, (float) (currProgress - textWidth / 2), startTriangleY - mTriangleHeight - mTextTopBottomPadding , mTextPaint);

    }

    public void setProgress(float currentProgress, float maxProgress) {
        this.maxProgress = maxProgress;
        this.currentProgress = currentProgress;
        this.mProgressText = String.valueOf((int) currentProgress) + "%";
        reCaculateTextSize();
        invalidate();
    }

    public int dip2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
