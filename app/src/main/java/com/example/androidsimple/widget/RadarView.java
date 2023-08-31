package com.example.androidsimple.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.androidsimple.R;
import com.example.androidsimple.bean.RadarData;
import com.example.androidsimple.utils.MathUtil;

import java.util.List;

public class RadarView extends View {
    private Context mContext;
    private int mVertical_Line_Color = 0xffffffff;
    private int color_white = 0xffffffff;
    private int mGapStrokeColor = 0x33ffffff;                   //间隔线颜色

//    private int mOriginX;               //原点的x轴坐标
//    private int mOriginY;               //原点的y轴坐标

    private PointF mPointCenter;          //原点

    private List<RadarData> dataList;

    private int count = 6;

    private double mAngle;

    private float radius;

    private float spiltRadius;

    private int gapCount = 5;


    private Paint mLinePaint;

    private Paint mGapLinePaint1;
    private Paint mGapLinePaint2;
    private Paint mValuePaint;
    private Paint mTextPaint;

    public RadarView(Context context) {
        super(context);
        this.mContext = context;
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }


    public void setData(List<RadarData> dataList) {

        if (dataList == null || dataList.size() < 3) {
            throw new RuntimeException("数据量应不少于3");
        } else {
            this.dataList = dataList;
        }

        postInvalidate();

    }

    private void initView() {

        count = dataList.size();
        mAngle = 2 * Math.PI / count;
        radius = Math.min(mPointCenter.x, mPointCenter.y) / 2 * 0.9f;
        spiltRadius = radius / gapCount / 2;

    }

    private void initPaint() {
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(mVertical_Line_Color);

        mGapLinePaint1 = new Paint();
        mGapLinePaint1.setAntiAlias(true);
        mGapLinePaint1.setStyle(Paint.Style.STROKE);
        mGapLinePaint1.setColor(Color.TRANSPARENT);
        mGapLinePaint1.setStrokeWidth(spiltRadius);

        mGapLinePaint2 = new Paint();
        mGapLinePaint2.setAntiAlias(true);
        mGapLinePaint2.setStyle(Paint.Style.STROKE);
        mGapLinePaint2.setColor(mGapStrokeColor);
        mGapLinePaint2.setStrokeWidth(spiltRadius);

        mValuePaint = new Paint();
        mValuePaint.setAntiAlias(true);
        mValuePaint.setStyle(Paint.Style.FILL);
        mValuePaint.setColor(mVertical_Line_Color);
        mValuePaint.setAlpha(125);

        mTextPaint = new Paint();
        mTextPaint.setTextSize(sp2px(mContext, 15f));
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(color_white);

    }

    //计算出各个点的横坐标和纵坐标
    private PointF computePointXY(int position, float radius) {

        float pointX = (float) (mPointCenter.x + radius * Math.sin(mAngle * position - mAngle / 2));
        float pointY = (float) (mPointCenter.y + radius * Math.cos(mAngle * position - mAngle / 2));

        PointF pointF = new PointF(pointX, pointY);

        return pointF;

    }


    private PointF computeWebPointXY(int position, float radius) {

        float pointX = (float) (mPointCenter.x + (radius) * Math.sin(mAngle * position - mAngle / 2));
        float pointY = (float) (mPointCenter.y + (radius) * Math.cos(mAngle * position - mAngle / 2));

        PointF pointF = new PointF(pointX, pointY);

        return pointF;

    }


    //画线
    private void drawLine(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < dataList.size(); i++) {
            float x = computePointXY(i, radius).x;
            float y = computePointXY(i, radius).y;
            path.moveTo(mPointCenter.x, mPointCenter.y);
            path.lineTo(x, y);
        }
        path.close();
        canvas.drawPath(path, mLinePaint);
    }

    private void drawPolygon(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < dataList.size(); i++) {
            float x = computePointXY(i, radius).x;
            float y = computePointXY(i, radius).y;
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }
        path.close();
        canvas.drawPath(path, mLinePaint);
    }


    //画间距
    private void drawRecGap(Canvas canvas) {
        Path path = new Path();

        for (int j = 0; j < gapCount * 2; j++) {

            path.reset();
            for (int i = 0; i < dataList.size(); i++) {

                float x = computeWebPointXY(i, spiltRadius * j + spiltRadius / 2).x;
                float y = computeWebPointXY(i, spiltRadius * j + spiltRadius / 2).y;

                if (i == 0) {
                    path.moveTo(x, y);
                } else {
                    path.lineTo(x, y);
                }
            }
            path.close();
            if (j % 2 == 0) {

//                mGapLinePaint1 = new Paint();
//                mGapLinePaint1.setAntiAlias(true);
//                mGapLinePaint1.setStyle(Paint.Style.STROKE);
//                mGapLinePaint1.setColor(Color.TRANSPARENT);
//                mGapLinePaint1.setStrokeWidth(spiltRadius);
                canvas.drawPath(path, mGapLinePaint1);

            } else {
//                mGapLinePaint2.setAntiAlias(true);
//                mGapLinePaint2.setStyle(Paint.Style.STROKE);
//                mGapLinePaint2.setColor(Color.GRAY);
//                mGapLinePaint2.setStrokeWidth(spiltRadius);
                canvas.drawPath(path, mGapLinePaint2);
            }
        }
    }


    private float valuePointRadius = 2f;

    //画出数据点
    private void drawDataPoint(Canvas canvas) {
        Path path = new Path();

        for (int i = 0; i < dataList.size(); i++) {
            RadarData itemData = dataList.get(i);
            double percent = itemData.percent2;
            float x = computePointXY(i, (float) (radius * percent)).x;
            float y = computePointXY(i, (float) (radius * percent)).y;

            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }

            canvas.drawCircle(x, y, dp2px(valuePointRadius), mLinePaint);
        }

        path.close();

        canvas.drawPath(path, mValuePaint);
    }


    private void drawValueText(Canvas canvas) {
        for (int i = 0; i < dataList.size(); i++) {
            RadarData itemData = dataList.get(i);
            double percent = itemData.percent2;

            String value = MathUtil.keep2decimal(percent * 100) + "%";
            Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();

            float fontHeight = fontMetrics.descent - fontMetrics.ascent;

            float x = computePointXY(i, radius / 2).x;
            float y = computePointXY(i, radius / 2).y;

            float length = mTextPaint.measureText(value);

//            canvas.drawText(value,x-length/2,y+fontHeight/2,mTextPaint);
            mTextPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(value, x, y + fontHeight / 2, mTextPaint);

        }
    }

    private void drawValueText2(Canvas canvas) {
        for (int i = 0; i < dataList.size(); i++) {
            RadarData itemData = dataList.get(i);
            double percent = itemData.percent1;

            String title = itemData.title;
            String value = MathUtil.keep2decimal(percent * 100) + "%";
            Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();

            float fontHeight = fontMetrics.descent - fontMetrics.ascent;

            float x = computePointXY(i, radius).x;
            float y = computePointXY(i, radius).y;

            float x1;
            float y1;
            float x2;
            float y2;

            float addRadius1 = (float) (fontHeight * 3 / 2 / Math.cos(mAngle / 2));
            float addRadius2 = (float) (fontHeight / 2 / Math.cos(mAngle / 2));

            float length1 = mTextPaint.measureText(title);
            float length2 = mTextPaint.measureText(value);

//            length=length1>=length2?length1:length2;

            if (y == mPointCenter.y) {


                if (x < mPointCenter.x) {
                    x1 = x - length1;
                    x2 = x - length2;
                } else {
                    x1 = x;
                    x2 = x;
                }

                y1 = y - fontHeight / 2;
                y2 = y + fontHeight / 2;


            } else if (y < mPointCenter.y) {
                x1 = computePointXY(i, radius + addRadius1).x;
                y1 = computePointXY(i, radius + addRadius1).y;

                x2 = computePointXY(i, radius + addRadius2).x;
                y2 = computePointXY(i, radius + addRadius2).y;

                x2 = x1 - length2 / 2;
                x1 = x1 - length1 / 2;


            } else {

                x1 = computePointXY(i, radius + addRadius2 + dp2px(5f)).x;
                y1 = computePointXY(i, radius + addRadius2 + dp2px(5f)).y;

                x2 = computePointXY(i, radius + addRadius1).x;
                y2 = computePointXY(i, radius + addRadius1).y;

                x2 = x1 - length2 / 2;
                x1 = x1 - length1 / 2;

            }

            canvas.drawText(title, x1, y1, mTextPaint);
            canvas.drawText(value, x2, y2, mTextPaint);


        }
    }


/*    private void drawIcon(Canvas canvas) {

        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_down);

//        int width=bitmap.getWidth();
//        int height=bitmap.getHeight();

        float newWidth = dp2px(8f);
        float newHeight = dp2px(18f);

//        float scale=width/newWidth;
//
//        Matrix matrix=new Matrix();
//        matrix.postScale(scale,scale);
//
//        Bitmap newBitmap=Bitmap.createBitmap(bitmap,0,0,width,height,matrix,true);

//        bitmap.recycle();


//        Rect srcRect=new Rect(0,0,newBitmap.getWidth(),newBitmap.getHeight());


        for (int i = 0; i < dataList.size(); i++) {
            RadarData itemData = dataList.get(i);
            double percent = itemData.percent2;

            String value = MathUtil.keep2decimal(percent * 100) + "%";
            Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();

            float fontHeight = fontMetrics.descent - fontMetrics.ascent;

            float x = computePointXY(i, radius / 2).x;
            float y = computePointXY(i, radius / 2).y;

            float length = mTextPaint.measureText(value);

            RectF destRect = new RectF((float) (x - length / 2f - newWidth), (float) (y - newHeight / 2f), x - length / 2f, (float) (y + newHeight / 2f));
//            canvas.drawText(value,x-length/2,y,mTextPaint);
            canvas.drawBitmap(bitmap, null, destRect, mTextPaint);

        }

    }*/


    private float dp2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return dpValue * scale + 0.5f;
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mPointCenter = new PointF(w / 2, h / 2);

    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

//        Paint paint=new Paint();
//        paint.setAntiAlias(true);       //抗锯齿功能
//        paint.setStyle(Paint.Style.FILL);       //内部填充
//        paint.setColor(mVertical_Line_Color);   //设置画笔颜色

        if (dataList == null || dataList.size() == 0) {
            return;
        }
        initView();
        initPaint();
        drawLine(canvas);
//        drawPolygon(canvas);
        drawRecGap(canvas);
        drawDataPoint(canvas);
        drawValueText(canvas);
        drawValueText2(canvas);
//        drawIcon(canvas);

    }
}
