package com.wktx.www.subjects.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wktx.www.subjects.R;
import com.wktx.www.subjects.utils.ScreenUtils;


public class SexPoPuWindow extends PopupWindow implements OnClickListener {
    public Context mContext;
    public Activity mActivity;

    private TextView tvMan, tvWomen, mCancel;

    public SexPoPuWindow(Context context, Activity mActivity) {
        initView(context);
        this.mActivity = mActivity;
    }

    private void initView(Context mContext) {
        this.mContext = mContext;
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_sexpopu, null);
        setContentView(v);

        tvMan = (TextView) v.findViewById(R.id.sex_man);
        tvWomen = (TextView) v.findViewById(R.id.sex_women);
        mCancel = (TextView) v.findViewById(R.id.sex_cancel);
        tvMan.setOnClickListener(this);
        tvWomen.setOnClickListener(this);
        mCancel.setOnClickListener(this);

        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ScreenUtils.getScreenHeight(mContext));
        // 设置SelectPicPopupWindow弹出窗体可点�?
        this.setTouchable(true);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状�?
        this.update();
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.popuwindow_from_bottom);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x50000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
    }

    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        } else {
            this.dismiss();
        }
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.sex_man:
                String str_man = tvMan.getText().toString();
                if (listener != null) {
                    listener.getText(str_man);
                }
                this.dismiss();
                break;
            case R.id.sex_women:
                String str_women = tvWomen.getText().toString();
                if (listener != null) {
                    listener.getText(str_women);
                }
                this.dismiss();
                break;
            case R.id.sex_cancel:
                this.dismiss();
                break;
            default:
                break;
        }
    }

    public interface onGetTextClckListener {
        void getText(String sex);
    }

    private onGetTextClckListener listener;

    public void setOnGetTextClckListener(onGetTextClckListener listener) {
        this.listener = listener;
    }

}
