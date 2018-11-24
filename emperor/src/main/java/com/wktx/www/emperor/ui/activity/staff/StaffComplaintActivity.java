package com.wktx.www.emperor.ui.activity.staff;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.staff.complaint.ComplaintInfoData;
import com.wktx.www.emperor.apiresult.staff.pause.PauseInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.staff.StaffComplaintPresenter;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.staff.IStaffPauseWorkView;
import com.wktx.www.emperor.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 管理我的员工---发起投诉
 */
public class StaffComplaintActivity extends ABaseActivity<IStaffPauseWorkView,StaffComplaintPresenter> implements IStaffPauseWorkView {

    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.et_complaintQuestion)
    EditText etQuestion;
    @BindView(R.id.et_complaintContent)
    EditText etContent;
    @BindView(R.id.bt_sureComplaint)
    Button btComplaint;

    private boolean isComplaint;//true：投诉中，false：未投诉
    private String hireId;//雇佣id
    private String complaintId;//投诉id

    @OnClick({R.id.tb_IvReturn,R.id.bt_sureComplaint})
    public void MyOnclick(View view) {
        //将输入法隐藏
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tvTitle.getWindowToken(), 0);
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            case R.id.bt_sureComplaint://提交投诉（撤销投诉）
                if (MyUtils.isFastClick()) {
                    return;
                }
                if (isComplaint){//撤销投诉
                    btComplaint.setEnabled(false);
                    getPresenter().onUnComplaint(complaintId);
                }else {//发起投诉
                    //判断输入框格式
                    if (TextUtils.isEmpty(getPauseCause())) {
                        ToastUtil.myToast("请输入投诉问题！");
                        etQuestion.requestFocus();
                    }else if (TextUtils.isEmpty(getPauseTime())){
                        ToastUtil.myToast("请输入投诉内容！");
                        etContent.requestFocus();
                    }else {//确定投诉
                        btComplaint.setEnabled(false);
                        getPresenter().onComplaint(hireId);
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
        setContentView(R.layout.activity_staff_complaint);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        initData();
    }

    @Override
    protected StaffComplaintPresenter createPresenter() {
        return new StaffComplaintPresenter();
    }

    /**
     * 接收 StaffManageActivity 传递过来的雇佣id以及是否投诉中状态
     */
    private void initData() {
        isComplaint = getIntent().getBooleanExtra(ConstantUtil.KEY_ISOK, false);
        hireId = getIntent().getStringExtra(ConstantUtil.KEY_POSITION);
        //如果是投诉中，则显示撤销投诉的相关布局
        if (isComplaint){
            tvTitle.setText(R.string.title_staff_uncomplaint);
            btComplaint.setText("确定撤销");
            etQuestion.setEnabled(false);
            etContent.setEnabled(false);
            //请求投诉信息
            getPresenter().getComplainInfo(hireId);
        }else {//如果是未投诉，则显示发起投诉的相关布局
            tvTitle.setText(R.string.title_staff_complaint);
            btComplaint.setText("确定投诉");
            etQuestion.setEnabled(true);
            etContent.setEnabled(true);
        }
    }

    /**
     * IStaffPauseWorkView
     */
    @Override
    public String getPauseCause() {
        return etQuestion.getText().toString().trim();
    }
    @Override
    public String getPauseTime() {
        return etContent.getText().toString().trim();
    }
    @Override//获取暂停工作内容回调
    public void onGetPauseSuccessResult(PauseInfoData result) {
    }
    @Override
    public void onGetPauseFailureResult(String result) {
    }
    @Override//获取投诉内容回调
    public void onGetComplaintSuccessResult(ComplaintInfoData result) {
        complaintId = result.getId();
        etQuestion.setText(result.getComplaint_title());
        etContent.setText(result.getComplaint_content());
    }
    @Override
    public void onGetComplaintFailureResult(String result) {
        ToastUtil.myToast(result);
        finish();
    }
    @Override
    public void onRequestSuccess(String tData) {
        ToastUtil.myToast(tData);
        btComplaint.setEnabled(true);
        finish();
    }
    @Override
    public void onRequestFailure(String result) {
        ToastUtil.myToast(result);
        btComplaint.setEnabled(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancleMyToast();
    }
}
