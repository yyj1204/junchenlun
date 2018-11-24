package com.wktx.www.subjects.ui.activity.manage.leave;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.manage.leave.LeaveListInfoData;
import com.wktx.www.subjects.ui.activity.ImageActivity;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.GlideUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 管理我的工作---请假详情界面
 */
public class LeaveDetailsActivity extends AutoLayoutActivity {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_beginTime)
    TextView tvBeginTime;
    @BindView(R.id.tv_endTime)
    TextView tvEndTime;
    @BindView(R.id.tv_leaveReason)
    TextView tvLeaveReason;
    @BindView(R.id.iv_leaveImg)
    ImageView ivLeaveImg;

    private List<String> imageUrlList = new ArrayList<>();//图片url集合

    @OnClick({R.id.tb_IvReturn,R.id.iv_leaveImg})
    public void MyOnclick(View view) {
        if (MyUtils.isFastClick()){
            return;
        }
        switch (view.getId()){
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.iv_leaveImg://查看大图
                String[] imageUrls = imageUrlList.toArray(new String[imageUrlList.size()]);
                Intent intent = new Intent(LeaveDetailsActivity.this, ImageActivity.class);
                intent.putExtra(ConstantUtil.KEY_DATA, imageUrls);
                intent.putExtra(ConstantUtil.KEY_POSITION, 0);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_details);
        //沉浸式状态栏
        StatusBarUtil.setColor(LeaveDetailsActivity.this, getResources().getColor(R.color.color_ffb321),0);
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_leave_details);
        initData();
    }

    /**
     * 接收LeaveListActivity 传递的请假信息
     */
    private void initData() {
        LeaveListInfoData leaveListInfoData = (LeaveListInfoData) getIntent().getSerializableExtra(ConstantUtil.KEY_DATA);
        //审批状态
        tvStatus.setText(leaveListInfoData.getStatus());
        switch (leaveListInfoData.getStatus()){
            case "申请中":
                tvStatus.setTextColor(getResources().getColor(R.color.color_ffb321));
                break;
            case "已同意":
            case "已拒绝":
                tvStatus.setTextColor(getResources().getColor(R.color.color_a0a0a0));
                break;
            default:
                break;
        }
        tvBeginTime.setText(leaveListInfoData.getStart_time());
        tvEndTime.setText(leaveListInfoData.getEnd_time());
        tvLeaveReason.setText(leaveListInfoData.getReason()+"\n申请时间："+leaveListInfoData.getAdd_time());
        if (TextUtils.isEmpty(leaveListInfoData.getImage())){
            ivLeaveImg.setVisibility(View.GONE);
        }else {
            imageUrlList.add(leaveListInfoData.getImage());
            ivLeaveImg.setVisibility(View.VISIBLE);
            GlideUtil.loadImage(leaveListInfoData.getImage(),R.drawable.img_loading,ivLeaveImg);
        }
    }
}
