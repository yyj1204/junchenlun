package com.wktx.www.subjects.ui.activity.manage;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.manage.PauseDetailsInfoData;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.presenter.manage.PauseDetailsPresenter;
import com.wktx.www.subjects.ui.view.IView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 暂停工作详情
 */
public class PauseDetailsActivity extends ABaseActivity<IView,PauseDetailsPresenter> implements IView<PauseDetailsInfoData> {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.tv_pauseBeginTime)
    TextView tvBeginTime;
    @BindView(R.id.tv_pauseEndTime)
    TextView tvEndTime;
    @BindView(R.id.tv_pauseTimeCount)
    TextView tvTimeCount;
    @BindView(R.id.tv_pauseCause)
    TextView tvPauseCause;

    private String hireId;//雇佣id
    private String pauseId;//暂停工作id

    @OnClick({R.id.tb_IvReturn})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pause_details);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_pause_details);
        initData();
    }

    @Override
    protected PauseDetailsPresenter createPresenter() {
        return new PauseDetailsPresenter();
    }

    /**
     * 接收 ManageActivity 传递过来的雇佣id以及暂停工作id
     */
    private void initData() {
        hireId = getIntent().getStringExtra(ConstantUtil.KEY_POSITION);
        pauseId = getIntent().getStringExtra(ConstantUtil.KEY_DATA);
        getPresenter().getInfo(hireId,pauseId);
    }

    /**
     * IView
     */
    @Override
    public void onRequestSuccess(PauseDetailsInfoData tData) {
        tvBeginTime.setText(tData.getSuspend_start());
        tvEndTime.setText(tData.getSuspend_end());
        tvTimeCount.setText(tData.getSuspend_days()+"天");
        tvPauseCause.setText(tData.getSuspend_describe()+"\n\n"+tData.getAdd_time());
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
