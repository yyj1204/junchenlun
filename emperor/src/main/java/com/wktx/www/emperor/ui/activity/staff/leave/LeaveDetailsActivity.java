package com.wktx.www.emperor.ui.activity.staff.leave;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.staff.leave.LeaveListInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.staff.LeavePresenter;
import com.wktx.www.emperor.ui.activity.ImageActivity;
import com.wktx.www.emperor.ui.view.staff.ILeaveView;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.GlideUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 管理我的员工---请假详情界面
 */
public class LeaveDetailsActivity extends ABaseActivity<ILeaveView,LeavePresenter> implements ILeaveView {
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
    //请假申请（同意、拒绝）
    @BindView(R.id.linear_leave)
    LinearLayout llLeave;
    @BindView(R.id.tv_agree)
    TextView tvAgree;
    @BindView(R.id.tv_refuse)
    TextView tvRefuse;

    private LeaveListInfoData leaveListInfoData;//请假信息
    private List<String> imageUrlList = new ArrayList<>();//图片url集合

    @OnClick({R.id.tb_IvReturn,R.id.iv_leaveImg,R.id.tv_agree,R.id.tv_refuse})
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
            case R.id.tv_agree://同意请假
                setButtom(false);
                getPresenter().leaveAgree(leaveListInfoData.getHire_id(),leaveListInfoData.getId());
                break;
            case R.id.tv_refuse://拒绝请假
                setButtom(false);
                getPresenter().leaveRefuse(leaveListInfoData.getHire_id(),leaveListInfoData.getId());
                break;
            default:
                break;
        }
    }

    private void setButtom(boolean isEnabled) {
        tvAgree.setEnabled(isEnabled);
        tvRefuse.setEnabled(isEnabled);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_details);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_leave_details);
        initData();
    }

    @Override
    protected LeavePresenter createPresenter() {
        return new LeavePresenter();
    }

    /**
     * 接收LeaveListActivity 传递的请假信息
     */
    private void initData() {
        leaveListInfoData = (LeaveListInfoData) getIntent().getSerializableExtra(ConstantUtil.KEY_DATA);
        //审批状态
        tvStatus.setText(leaveListInfoData.getStatus());
        switch (leaveListInfoData.getStatus()){
            case "申请中":
                llLeave.setVisibility(View.VISIBLE);
                tvStatus.setTextColor(getResources().getColor(R.color.color_ffb321));
                break;
            case "已同意":
            case "已拒绝":
                llLeave.setVisibility(View.GONE);
                tvStatus.setTextColor(getResources().getColor(R.color.color_a0a0a0));
                break;
            default:
                break;
        }
        tvBeginTime.setText(leaveListInfoData.getStart_time());
        tvEndTime.setText(leaveListInfoData.getEnd_time());
        tvLeaveReason.setText(leaveListInfoData.getReason()+"\n申请时间："+ leaveListInfoData.getAdd_time());
        if (TextUtils.isEmpty(leaveListInfoData.getImage())){
            ivLeaveImg.setVisibility(View.GONE);
        }else {
            imageUrlList.add(leaveListInfoData.getImage());
            ivLeaveImg.setVisibility(View.VISIBLE);
            GlideUtil.loadImage(leaveListInfoData.getImage(),R.drawable.img_loading,ivLeaveImg);
        }
    }

    /**
     * ILeaveView
     */
    @Override//获取请假同意、拒绝回调
    public void onLeaveResult(boolean isSuccess, String msg) {
        setButtom(true);
        ToastUtil.myToast(msg);
        if (isSuccess){
            finish();
        }
    }
    @Override //获取请假记录回调
    public void onRequestSuccess(List<LeaveListInfoData> tData) {
    }
    @Override
    public void onRequestFailure(String result) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancleMyToast();
    }
}
