package com.lib.common.image;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class ImageLoader {
    /**
     * 默认加载
     */
    public static void loadImageView(Context context, String path, ImageView imageView){
        Glide.with(context).load(path).into(imageView);
    }


    /**
     * 设置加载中以及加载失败图片
     */
    public static void loadImageViewLoding(Context context, String path, ImageView imageView, int lodingImage, int errorImageView) {
        Glide.with(context).load(path).placeholder(lodingImage).error(errorImageView).into(imageView);
    }

    /**
     * 加载圆角图片
     */
    public static void loadRoundImageView(Context context, String path, ImageView imageView, int roundingRadius){
        //设置图片圆角角度
        RoundedCorners roundedCorners= new RoundedCorners(roundingRadius);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
//        RequestOptions options= RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
        Glide.with(context).load(path).apply(options).into(imageView);
    }

    /**
     * 裁剪成圆形图片
     */
    public static void loadCircleImageView(Context context, String path, ImageView imageView){
        //设置图片圆角角度
        CircleCrop circleCrop= new CircleCrop();
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
//        RequestOptions options= RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
        RequestOptions options = RequestOptions.bitmapTransform(circleCrop);
        Glide.with(context).load(path).apply(options).into(imageView);
    }


}
