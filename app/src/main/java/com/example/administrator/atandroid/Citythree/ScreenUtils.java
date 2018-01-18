package com.example.administrator.atandroid.Citythree;

import android.content.Context;
import android.util.DisplayMetrics;

public class ScreenUtils
{
    
    private static int width = 0;
    
    private static int height = 0;
    
    private static float density = 0;
    
    /**
     * @return 初始化
     */
    public static void reInit(Context context)
    {
        if (height == 0 || width == 0 || density == 0)
        {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            height = dm.heightPixels;
            width = dm.widthPixels;
            density = dm.density;
        }
    }
    
    /**
     * @return 屏幕宽度
     */
    public static int getWidth(Context context)
    {
        reInit(context);
        return width;
    }
    
    /**
     * @return 密度
     */
    public static float getDensity(Context context)
    {
        reInit(context);
        return density;
    }
    
    /**
     * @return 屏幕高度
     */
    public static int getHeight(Context context)
    {
        reInit(context);
        return height;
    }
    
    /**
     * @return dp转px
     */
    public static int dp2px(Context context, float dpValue)
    {
        reInit(context);
        return (int) (dpValue * density + 0.5f);
    }
    
    /**
     * @return px转dp
     */
    public static int px2dp(Context context, float pxValue)
    {
        reInit(context);
        return (int) (pxValue / density);
    }
}
