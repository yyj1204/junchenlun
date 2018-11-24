package com.wktx.www.emperor.ui.activity.staff;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.staff.complaint.ComplaintInfoData;
import com.wktx.www.emperor.apiresult.staff.pause.PauseInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.staff.StaffPauseWorkPresenter;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.staff.IStaffPauseWorkView;
import com.wktx.www.emperor.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 管理我的员工---暂停工作
 */
public class StaffPauseWorkActivity extends ABaseActivity<IStaffPauseWorkView,StaffPauseWorkPresenter> implements IStaffPauseWorkView {

    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.et_pauseCause)
    EditText etPauseCause;
    @BindView(R.id.linear_pauseTime)
    LinearLayout llPauseTime;
    @BindView(R.id.tv_pauseTimeCount)
    TextView tvTimeCount;
    @BindView(R.id.linear_pauseTime1)
    LinearLayout llPauseTime1;
    @BindView(R.id.tv_pauseTimeCount1)
    TextView tvTimeCount1;
    @BindView(R.id.bt_surePause)
    Button btPause;

    private boolean isPause;//true：暂停工作中，false：未暂停工作
    private String hireId;//雇佣id
    private int pauseTime=1;//暂停时间（天）
    private String pauseId;//暂停工作id

    @OnClick({R.id.tb_IvReturn,R.id.rela_pauseTimeMinus,R.id.rela_pauseTimeAdd,R.id.bt_surePause})
    public void MyOnclick(View view) {
        //将输入法隐藏
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tvTitle.getWindowToken(), 0);
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            case R.id.rela_pauseTimeMinus://减号
                if (pauseTime>1){
                    pauseTime--;
                    tvTimeCount.setText(pauseTime+"");
                }else {
                    if (MyUtils.isFastClick()) {
                        return;
                    }
                   ToastUtil.myToast("暂停时间最短为1天哦!");
                }
                break;
            case R.id.rela_pauseTimeAdd://加号
                if (pauseTime<12){
                    pauseTime++;
                    tvTimeCount.setText(pauseTime+"");
                }else {
                    if (MyUtils.isFastClick()) {
                        return;
                    }
                   ToastUtil.myToast("暂停时间最多为7天哦!");
                }
                break;
            case R.id.bt_surePause://确定暂停（确定恢复）
                if (MyUtils.isFastClick()) {
                    return;
                }
                if (isPause){//恢复工作
                    btPause.setEnabled(false);
                    getPresenter().onUnPauseWork(pauseId);
                }else {//暂停工作
                    //判断输入框格式
                    if (TextUtils.isEmpty(getPauseCause())){
                       ToastUtil.myToast("请输入暂停原因！");
                        etPauseCause.requestFocus();
                    }else {//确定暂停
                        btPause.setEnabled(false);
                        getPresenter().onPauseWork(hireId);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_pausework);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        initData();
    }

    @Override
    protected StaffPauseWorkPresenter createPresenter() {
        return new StaffPauseWorkPresenter();
    }

    /**
     * 接收 StaffManageActivity 传递过来的雇佣id以及是否暂停工作中状态
     */
    private void initData() {
        isPause = getIntent().getBooleanExtra(ConstantUtil.KEY_ISOK, false);
        hireId = getIntent().getStringExtra(ConstantUtil.KEY_POSITION);
        //如果是暂停中，则显示恢复工作的相关布局
        if (isPause){
            tvTitle.setText(R.string.title_staff_unpausework);
            btPause.setText("确定恢复");
            etPauseCause.setEnabled(false);
            llPauseTime.setVisibility(View.GONE);
            llPauseTime1.setVisibility(View.VISIBLE);
            //请求暂停信息
            getPresenter().getPauseWorkInfo(hireId);
        }else {//如果是未暂停，则显示暂停工作的相关布局
            tvTitle.setText(R.string.title_staff_pausework);
            btPause.setText("确定暂停");
            etPauseCause.setEnabled(true);
            llPauseTime.setVisibility(View.VISIBLE);
            llPauseTime1.setVisibility(View.GONE);
        }
        tvTimeCount.setText(pauseTime+"");
    }


    /**
     * IStaffPauseWorkView
     */
    @Override
    public String getPauseCause() {
        return etPauseCause.getText().toString().trim();
    }
    @Override
    public String getPauseTime() {
        return pauseTime+"";
    }
    @Override//获取暂停工作内容回调
    public void onGetPauseSuccessResult(PauseInfoData result) {
        pauseId = result.getId();
        etPauseCause.setText("暂停原因： "+result.getSuspend_describe());
        tvTimeCount1.setText(result.getSuspend_days()+"天");
    }
    @Override
    public void onGetPauseFailureResult(String result) {
       ToastUtil.myToast(result);
        finish();
    }
    @Override//获取投诉内容回调
    public void onGetComplaintSuccessResult(ComplaintInfoData result) {
    }
    @Override
    public void onGetComplaintFailureResult(String result) {
    }
    @Override//暂停工作（恢复工作）回调
    public void onRequestSuccess(String tData) {
       ToastUtil.myToast(tData);
        btPause.setEnabled(true);
        finish();
    }
    @Override
    public void onRequestFailure(String result) {
       ToastUtil.myToast(result);
        btPause.setEnabled(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancleMyToast();
    }
}
