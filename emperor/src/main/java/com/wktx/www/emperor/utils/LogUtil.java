package com.wktx.www.emperor.utils;

import android.util.Log;


/**
 * Log统一管理类
 * @author yyj
 */

public class LogUtil {
    private LogUtil()
    {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isDebug = false;// 是否需要打印bug，可以在application的onCreate函数里面初始化
    private static final String TAG = "way";

    // 下面四个是默认tag的函数
    public static void info(String msg)
    {
        if (isDebug) {
           Log.i(TAG, msg);
        }
    }

    public static void debug(String msg)
    {
        if (isDebug) {
            Log.d(TAG, msg);
        }
    }

    public static void error(String msg)
    {
        if (isDebug) {
            Log.e(TAG, msg);
        }
    }

    public static void verbose(String msg)
    {
        if (isDebug) {
            Log.v(TAG, msg);
        }
    }

    // 下面是传入自定义tag的函数
    public static void info(String tag, String msg)
    {
        if (isDebug) {
           Log.i(tag, msg);
        }
    }

    public static void debug(String tag, String msg)
    {
        if (isDebug) {
           Log.d(tag, msg);
        }
    }

    public static void error(String tag, String msg)
    {
        if (isDebug) {
            Log.e(tag, msg);
        }
    }

    public static void verbose(String tag, String msg)
    {
        if (isDebug) {
            Log.d(tag, msg);
        }
    }
}
