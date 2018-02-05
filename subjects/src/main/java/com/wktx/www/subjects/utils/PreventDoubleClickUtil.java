package com.wktx.www.subjects.utils;

/**
 * Created by Administrator on 2017/8/31.
 */

import java.util.Calendar;

/**
 * 防止多次点击按钮
 * Created by Administrator on 2016/11/18.
 */
public class PreventDoubleClickUtil {

    private static long lastClickTime = 0;
    public static final int MIN_CLICK_DELAY_TIME = 4000;

    public static boolean noDoubleClick() {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            return true;
        } else {
            return false;
        }
    }
}