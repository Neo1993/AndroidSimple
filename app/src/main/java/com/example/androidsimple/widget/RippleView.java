package com.example.androidsimple.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.example.androidsimple.R;
import com.example.androidsimple.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 涟漪效果
 * <p>
 * Created by zhuwentao on 2018-03-07.
 */
public class RippleView extends View {

    private Context mContext;

    // 画笔对象
    private Paint mPaint;

    // View宽
    private float mWidth;

    // View高
    private float mHeight;

    private int sqrtNumber;

    // 圆圈扩散的速度
    private int mSpeed;

    // 圆圈之间的密度
    private int mDensity;

    // 圆圈的颜色
    private int mColor;

    // 圆圈是否为填充模式
    private boolean mIsFill;

    // 圆圈是否为渐变模式
    private boolean mIsAlpha;

    private int innerRadius;
    private int outRadius2;
    private int outRadius3;

    private ValueAnimator animator;

    public RippleView(Context context) {
        this(context, null);
    }

    public RippleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 获取用户配置属性
        TypedArray tya = context.obtainStyledAttributes(attrs, R.styleable.mRippleView);
        mColor = tya.getColor(R.styleable.mRippleView_cColor, Color.BLUE);
        mSpeed = tya.getInt(R.styleable.mRippleView_cSpeed, 1);
        mDensity = tya.getInt(R.styleable.mRippleView_cDensity, 10);
        mIsFill = tya.getBoolean(R.styleable.mRippleView_cIsFill, false);
        mIsAlpha = tya.getBoolean(R.styleable.mRippleView_cIsAlpha, false);
        tya.recycle();

        init();
    }

    private void init() {
        mContext = getContext();

        innerRadius = DensityUtil.dip2px(mContext, 30f);
        outRadius2 = DensityUtil.dip2px(mContext, 40f);
        outRadius3 = DensityUtil.dip2px(mContext, 50f);

        // 设置画笔样式
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);

    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);

        // 内切正方形
//        drawInCircle(canvas);

        // 外切正方形
        // drawOutCircle(canvas);
    }

    /**
     * 绘制圆
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
//        canvas.save();

        mPaint.setStyle(Paint.Style.STROKE);
        if (currentValue < outRadius2) {
            int w = currentValue - innerRadius;
            mPaint.setStrokeWidth(w * 2);
            mPaint.setColor(Color.parseColor("#FFBDD9FE"));
            canvas.drawCircle(mWidth / 2, mHeight / 2, innerRadius, mPaint);
        } else {
            int w2 = currentValue - outRadius2;
            mPaint.setStrokeWidth(w2 * 2);
            mPaint.setColor(Color.parseColor("#FF5CB5F9"));
            canvas.drawCircle(mWidth / 2, mHeight / 2, outRadius2, mPaint);

            int w1 = outRadius2 - innerRadius;
            mPaint.setStrokeWidth(w1 * 2);
            mPaint.setColor(Color.parseColor("#FFBDD9FE"));
            canvas.drawCircle(mWidth / 2, mHeight / 2, innerRadius , mPaint);
        }

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(0);
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(mWidth / 2, mHeight / 2, innerRadius, mPaint);

//        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int myWidthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int myWidthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int myHeightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int myHeightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        // 获取宽度
        if (myWidthSpecMode == MeasureSpec.EXACTLY) {
            // match_parent
            mWidth = myWidthSpecSize;
        } else {
            // wrap_content
            mWidth = DensityUtil.dip2px(mContext, 120);
        }

        // 获取高度
        if (myHeightSpecMode == MeasureSpec.EXACTLY) {
            mHeight = myHeightSpecSize;
        } else {
            // wrap_content
            mHeight = DensityUtil.dip2px(mContext, 120);
        }

        // 设置该view的宽高
        setMeasuredDimension((int) mWidth, (int) mHeight);
    }

    private int currentValue;

    @SuppressLint("WrongConstant")
    public void startAnimation(int duration) {
        if (animator == null) {
            animator = ValueAnimator.ofInt(innerRadius, outRadius3);
            animator.setInterpolator(new AccelerateInterpolator());
            animator.setDuration(duration);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    currentValue = (int) animation.getAnimatedValue();
                    invalidate();
                }
            });
        }
        animator.start();
    }

}