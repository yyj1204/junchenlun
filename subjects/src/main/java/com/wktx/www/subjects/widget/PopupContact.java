package com.wktx.www.subjects.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.luck.picture.lib.tools.ScreenUtils;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.utils.ConstantUtil;

/**
 * 邀请详情联系弹窗(电话、QQ、微信)
 */
public class PopupContact extends PopupWindow implements OnClickListener {
    public Context mContext;
    public Activity mActivity;

    private LinearLayout llPhone, llQQ, llWechat;
    private TextView tvCancel;

    public PopupContact(Context context, Activity mActivity) {
        initView(context);
        this.mActivity = mActivity;
    }

    private void initView(Context mContext) {
        this.mContext = mContext;
        View v = LayoutInflater.from(mContext).inflate(R.layout.widget_popup_contact, null);
        setContentView(v);

        llPhone = (LinearLayout) v.findViewById(R.id.linear_phone);
        llQQ = (LinearLayout) v.findViewById(R.id.linear_qq);
        llWechat = (LinearLayout) v.findViewById(R.id.linear_wechat);
        tvCancel = (TextView) v.findViewById(R.id.tv_cancel);
        llPhone.setOnClickListener(this);
        llQQ.setOnClickListener(this);
        llWechat.setOnClickListener(this);
        tvCancel.setOnClickListener(this);

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
            case R.id.linear_phone:
                if (listener != null) {
                    listener.getInterviewWay(ConstantUtil.InterviewWay.PHONE);
                }
                this.dismiss();
                break;
            case R.id.linear_qq:
                if (listener != null) {
                    listener.getInterviewWay(ConstantUtil.InterviewWay.QQ);
                }
                this.dismiss();
                break;
            case R.id.linear_wechat:
                if (listener != null) {
                    listener.getInterviewWay(ConstantUtil.InterviewWay.WX);
                }
                this.dismiss();
                break;
            case R.id.tv_cancel:
                this.dismiss();
                break;
            default:
                break;
        }
    }


    public interface onGetInterviewWayClckListener {
        void getInterviewWay(ConstantUtil.InterviewWay way);
    }
    private onGetInterviewWayClckListener listener;
    public void setOnGetInterviewWayClckListener(onGetInterviewWayClckListener listener) {
        this.listener = listener;
    }
}
