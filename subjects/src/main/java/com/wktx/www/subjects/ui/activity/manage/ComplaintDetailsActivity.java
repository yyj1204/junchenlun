package com.wktx.www.subjects.ui.activity.manage;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.manage.ComplaintDetailsInfoData;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.presenter.manage.ComplaintDetailsPresenter;
import com.wktx.www.subjects.ui.view.IView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 投诉详情
 */
public class ComplaintDetailsActivity extends ABaseActivity<IView,ComplaintDetailsPresenter> implements IView<ComplaintDetailsInfoData> {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.tv_complaintQuestion)
    TextView tvQuestion;
    @BindView(R.id.tv_complaintContent)
    TextView tvContent;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.bt_sure)
    Button btSure;

    private String hireId;//雇佣id

    @OnClick({R.id.tb_IvReturn,R.id.bt_sure})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            case R.id.bt_sure://申诉

                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_details);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_complaint_details);
        initData();
    }

    @Override
    protected ComplaintDetailsPresenter createPresenter() {
        return new ComplaintDetailsPresenter();
    }

    /**
     * 接收 ManageActivity 传递过来的雇佣id以及暂停工作id
     */
    private void initData() {
        hireId = getIntent().getStringExtra(ConstantUtil.KEY_POSITION);
        getPresenter().getInfo(hireId);
    }

    /**
     * IView
     */
    @Override
    public void onRequestSuccess(ComplaintDetailsInfoData tData) {
        tvQuestion.setText(tData.getComplaint_title());
        tvContent.setText(tData.getComplaint_content()+"\n\n"+tData.getAdd_time());
        //0:等待审核 1:已审核 2:已撤回
        String resultStr="";
        if (TextUtils.isEmpty(tData.getStatus())||tData.getStatus().equals("0")){
            resultStr="等待后台审核";
        }else {
            if (TextUtils.isEmpty(tData.getRemark())){
                resultStr="无";
            }else {
                resultStr=tData.getRemark();

            }
        }
        tvResult.setText(resultStr);
    }
    @Override
    public void onRequestFailure(String result) {
        ToastUtil.myToast(result);
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancleMyToast();
    }
}
