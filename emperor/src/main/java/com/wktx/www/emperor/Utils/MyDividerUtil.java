package com.wktx.www.emperor.Utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.DividerItemDecoration;

/**
 * Created by Administrator on 2017/10/29.
 */

public class MyDividerUtil {
    public static  DividerItemDecoration drawDivider(Context context,int oriention,int id) {
        //水平分割线
        Drawable drawable = context.getResources().getDrawable(id);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, oriention);
        dividerItemDecoration.setDrawable(drawable);
        return  dividerItemDecoration;
    }
}
