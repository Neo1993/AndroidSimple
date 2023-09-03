package com.example.androidsimple.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.androidsimple.utils.UiUtils;

/**
 * 自定义优惠券背景
 */
public class CouponLayout extends FrameLayout {
    private float radius1 = UiUtils.dp2px(12);
    private float radius2 = UiUtils.dp2px(4);
    private float strokeWidth = UiUtils.dp2px(1);
    private float offset = UiUtils.dp2px(0.5f);
    private float realRadius1;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path path = new Path();
    private int color = Color.parseColor("#FF6D3E");
    private int width;
    private int height;

    {
        //开启View级别的离屏缓冲,并关闭硬件加速，使用软件绘制
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        setWillNotDraw(false);
    }


    public CouponLayout(@NonNull Context context) {
        this(context, null);
    }

    public CouponLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CouponLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet atts, int defStyleAttr) {
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(color);
        realRadius1 = radius1 - offset;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //自定义的View能够使用wrap_content或者是match_parent的属性
//        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = (int) (getWidth() - offset);
        height = (int) (getHeight() - offset);

//        width = (int)UiUtils.dp2px(240);
//        height = (int)UiUtils.dp2px(176);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.reset();
        path.reset();

        // 填充裁切位图背景的画笔，背景设为黑色
        Paint clipDstPaint = new Paint();
        clipDstPaint.setColor(Color.WHITE);
        clipDstPaint.setStyle(Paint.Style.FILL);
        // 裁切位图中空抠图的画笔
//        Paint clipSrcPaint = new Paint();
//        clipSrcPaint.setColor(Color.WHITE);
//        clipSrcPaint.setAntiAlias(true);
//        clipSrcPaint.setStyle(Paint.Style.FILL);
//        clipSrcPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        // 绘制背景进行裁切生成中空形状裁切位图
        RectF rectf = new RectF(offset, offset, width, height);
        canvas.drawRoundRect(rectf, radius1, radius1, clipDstPaint);  // 绘制背景进行裁切生成中空形状裁切位图

        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(color);

        RectF ltRectF = new RectF(offset, offset, offset + radius1* 2, offset + radius1* 2);
        path.addArc(ltRectF, -180, 90);

        path.lineTo(height - radius2, offset);
        RectF oval1 = new RectF(height - radius2, 0 - radius2, height + radius2, radius2);
        path.addArc(oval1, 180, -180);

        path.moveTo(height + radius2, offset);
        path.lineTo(width - radius1, offset);
        RectF rtRectF = new RectF(width - radius1 * 2, offset, width, offset + radius1 * 2);
        path.addArc(rtRectF, -90, 90);

        path.lineTo(width, height - radius1);

        RectF rbRectF = new RectF(width - radius1 * 2, height - radius1 * 2, width, height);
        path.addArc(rbRectF, 0, 90);

        path.lineTo(height + radius2, height);

        RectF oval2 = new RectF(height - radius2, height - radius2, height + radius2, height + radius2);
        path.addArc(oval2, 0, -180);

        path.lineTo(radius1, height);

        RectF lbRectF = new RectF(offset,height - radius1 * 2,offset + radius1 * 2, height);
        path.addArc(lbRectF, 90, 90);

        path.moveTo(offset, height - realRadius1);
        path.lineTo(offset, realRadius1);

        canvas.drawPath(path, paint);

        Path dashPath = new Path();
        paint.setPathEffect(new DashPathEffect(new float[]{UiUtils.dp2px(4), UiUtils.dp2px(3)}, 0));
        dashPath.moveTo(height, radius2);
        dashPath.lineTo(height, height - radius2);
        canvas.drawPath(dashPath, paint);


        Path clipPath = new Path();
        RectF oval11 = new RectF(height - radius2 + offset, offset - radius2, height + radius2 - offset, radius2 - offset);
        clipPath.addArc(oval11, 180, -180);
        RectF oval22 = new RectF(height - radius2 + offset, height - radius2 + offset, height + radius2 - offset, height + radius2 - offset);

        clipPath.addArc(oval22, 0, -180);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(clipPath, paint);
    }

    public void setShapeColor(int color) {
        this.color = color;
    }



}
