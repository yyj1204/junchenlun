package com.wktx.www.emperor.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by yyj on 2017/8/23.
 * 滑动监听 --- 实现标题栏透明度随之渐变
 */

public class MyScrollView extends ScrollView {
    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //TODO 重写onScrollChanged()方法
    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        //5.确定监听的时机，并且确定要传递的参数
        if (listener != null)
        {
            listener.onScrollChange(y);
        }
        super.onScrollChanged(x, y, oldx, oldy);
    }

    //1.定义监听接口
    public interface ScrollViewListener
    {
        //4.定义抽象方法
        void onScrollChange(int height);
    }
    //2.定义接口变量
    private ScrollViewListener listener;
    //3.定义设置监听接口方法
    public void setOnScrollViewListener(ScrollViewListener l)
    {
        this.listener = l;
    }
}
