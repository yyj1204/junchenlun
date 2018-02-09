package com.wktx.www.emperor.ui.activity.recruit.demand;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.login.AccountInfoData;
import com.wktx.www.emperor.apiresult.recruit.demand.DemandDetailsInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.recruit.demand.DemandDetailsPresenter;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.DateUtil;
import com.wktx.www.emperor.utils.LoginUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.view.IView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 招聘---需求列表---需求详情
 */
public class DemandDetailsActivity extends ABaseActivity<IView,DemandDetailsPresenter> implements IView<DemandDetailsInfoData> {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.tv_demandTitle)
    TextView tvDemandTitle;
    @BindView(R.id.tv_demandStatus)
    TextView tvDemandStatus;
    @BindView(R.id.tv_demandContent)
    TextView tvDemandContent;
    @BindView(R.id.tv_demandPrice)
    TextView tvDemandPrice;
    @BindView(R.id.tv_demandReleaseTime)
    TextView tvDemandReleaseTime;
    @BindView(R.id.tv_demandTimeUp)
    TextView tvDemandTimeUp;
    @BindView(R.id.tv_demandCreateTime)
    TextView tvDemandCreateTime;

    @OnClick(R.id.tb_IvReturn)
    public void MyOnclick(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demand_details);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_demand_details);
        initData();
    }

    @Override
    protected DemandDetailsPresenter createPresenter() {
        return new DemandDetailsPresenter();
    }

    /**
     * 接收 DemandActivity 传过来需求ID
     * 请求数据
     */
    private void initData() {
        String demandId = getIntent().getStringExtra(ConstantUtil.KEY_POSITION);
        getPresenter().onGetDemandInfo(demandId);
    }

    /**
     * IView
     */
    @Override
    public AccountInfoData getUserInfo() {
        AccountInfoData userInfo = LoginUtil.getinit().getUserInfo();
        return userInfo;
    }
    @Override
    public void onRequestSuccess(DemandDetailsInfoData tData) {
        String status = tData.getStatus();
        if (status.equals("0")){
            tvDemandStatus.setVisibility(View.VISIBLE);
        }else if (status.equals("1")){
            tvDemandStatus.setVisibility(View.GONE);
        }
        tvDemandTitle.setText(tData.getTitle());
        tvDemandContent.setText(tData.getDesign_pattern()+"\n"+tData.getContent());
        tvDemandPrice.setText(tData.getBudget()+"元");
        String addTime = tData.getAdd_time();
        String addTimeStr1 = DateUtil.timeNYR(addTime);//XXXX年XX月XX日
        String addTimeStr2 = DateUtil.timedate(addTime);//XXXX-XX-XX  XX:XX:XX
        tvDemandReleaseTime.setText(addTimeStr1);
        tvDemandTimeUp.setText("10天后到期");
        tvDemandCreateTime.setText(addTimeStr2);
    }
    @Override
    public void onRequestFailure(String result) {
        MyUtils.showToast(DemandDetailsActivity.this,result);
    }
}
