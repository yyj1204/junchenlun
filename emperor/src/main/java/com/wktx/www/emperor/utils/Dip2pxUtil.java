package com.wktx.www.emperor.utils;

import android.content.Context;
import android.graphics.Paint;

/**
 * Created by 柯燕峰 on 2017/6/20 0020.
 */

public class Dip2pxUtil
{

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * DP转PX
     */
    public static int dpToPx(Context context, float dpSize) {
        return (int) (context.getResources().getDisplayMetrics().density * dpSize);
    }

    /**
     * @param backgroundTop
     * @param backgroundBottom
     * @param paint
     * @return paint绘制居中文字时，获取文本底部坐标
     */
    public static float getTextBaseLine(float backgroundTop, float backgroundBottom, Paint paint) {
        final Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return (backgroundTop + backgroundBottom - fontMetrics.bottom - fontMetrics.top) / 2;
    }
}
