package com.wktx.www.subjects.widget;

import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wktx.www.subjects.R;


public class PopupLoading {

	//TODO 加载数据弹窗
	public static PopupWindow popupLoading(LayoutInflater inflater, View view) {
		View item_popup = inflater.inflate(R.layout.widget_popup_loading, null);
		PopupWindow popupWindow = new PopupWindow(item_popup, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
		popupWindow.setAnimationStyle(R.style.popupStyle);//设置动画
		popupWindow.setOutsideTouchable(false);//设置外部可点击
		popupWindow.setBackgroundDrawable(new BitmapDrawable());//设置背景
		popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        TextView tv_loading = (TextView) item_popup.findViewById(R.id.tv_loading);
        tv_loading.setText("加载中...");
		return popupWindow;
	}
}
