package com.lib.common.image;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageLoader {
    //默认加载
    public static void displayImageView(Context mContext, String path, ImageView mImageView){
        Glide.with(mContext).load(path).into(mImageView);
    }
}
