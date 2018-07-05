package com.wktx.www.emperor.utils;

/**
 *自定义Toast
 * 1.避免toast重复显示、
 * 2.切换界面toast自动消失
 */

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wktx.www.emperor.R;
import com.wktx.www.emperor.application.MyApplication;


public class ToastUtil {
    private static Toast myToast=null;

    public static void myToast(CharSequence text) {
        //首先加载一个自定义的布局
        View v = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.widget_toast, null);
        //然后找到里面的控件
        TextView textView = (TextView) v.findViewById(R.id.tv_toast);

        //避免toast长时间显示
        if (myToast != null) {
            myToast.cancel();
        }

        //接下来就是给textview设置信息
        textView.setText(text);
        //最重要的就是下面了，把布局引用到toast当中,获得toast
        myToast = new Toast(MyApplication.getContext());
        //设置toast显示的位置
        myToast.setGravity(Gravity.CENTER, 0, 0);
        //设置弹出显示的时间
        myToast.setDuration(Toast.LENGTH_SHORT);
        //设置布局
        myToast.setView(v);
        //最后一步，show出来
        myToast.show();
    }


    /**
     * 使toast不再显示
     */
    public static  void cancleMyToast() {
        if (myToast != null) {
            myToast.cancel();
        }
    }
}
