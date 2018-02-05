package com.wktx.www.emperor.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 柯燕峰 on 2017/6/6 0006.
 */

public class ToastUtil {

    private static Toast toast;

    public static void toast(Context context, String title) {
        toast = Toast.makeText(context, title, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * toast取消
     */
    public static void cancel() {

        if (toast != null) {
            toast.cancel();
            toast = null;
        }

    }
}
