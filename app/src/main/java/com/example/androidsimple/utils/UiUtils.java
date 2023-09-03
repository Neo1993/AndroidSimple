package com.example.androidsimple.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public final class UiUtils {
    /**
     * 屏幕宽度
     */
    public static int screenWidth() {
        return UtilsConfig.context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 屏幕高度
     */
    public static int screenHeight() {
        DisplayMetrics outMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) UtilsConfig.context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getRealMetrics(outMetrics);
        return outMetrics.heightPixels;
//		return UtilsConfig.context.getResources().getDisplayMetrics().heightPixels;		// 这种方法获取的在有虚拟键的手机上会变小
    }

    /**
     * dp转px
     */
    public static float dp2px(float dp) {
        return dp * Resources.getSystem().getDisplayMetrics().density;
    }

    /**
     * px转dp
     */
    public static float px2dp(float px) {
        return px / Resources.getSystem().getDisplayMetrics().density;
    }

    /**
     * sp转px
     */
    public static float sp2px(float sp) {
        return sp * Resources.getSystem().getDisplayMetrics().scaledDensity;
    }

    /**
     * px转sp
     */
    public static float px2sp(float px) {
        return px / Resources.getSystem().getDisplayMetrics().scaledDensity;
    }
}
