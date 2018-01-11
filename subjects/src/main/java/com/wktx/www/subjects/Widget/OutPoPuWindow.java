package com.wktx.www.subjects.Widget;

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
import com.wktx.www.subjects.Utils.Contants;
import com.wktx.www.subjects.Utils.ScreenUtils;


public class OutPoPuWindow extends PopupWindow implements OnClickListener {

    public Context mContext;
    public Activity mActivity;
    private TextView mTvSure, mTvCancel;

    public OutPoPuWindow(Context context, Activity mActivity) {
        initView(context);
        this.mActivity = mActivity;
    }

    private void initView(Context mContext) {
        this.mContext = mContext;
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_outpopu, null);
        setContentView(v);
        mTvSure = (TextView) v.findViewById(R.id.outTvSure);
        mTvCancel = (TextView) v.findViewById(R.id.outTvCancel);
        mTvSure.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);

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
            case R.id.outTvSure:
                String str_sure = mTvSure.getText().toString();
                if (listener != null) {
                    listener.getText(Contants.OUT);
                }
                this.dismiss();
                break;
            case R.id.outTvCancel:
                this.dismiss();
                break;
            default:
                break;
        }
    }


    public interface onGetTypeClckListener {
        void getText(String sure);
    }

    private onGetTypeClckListener listener;

    public void setOnGetTypeClckListener(onGetTypeClckListener listener) {
        this.listener = listener;
    }

}
