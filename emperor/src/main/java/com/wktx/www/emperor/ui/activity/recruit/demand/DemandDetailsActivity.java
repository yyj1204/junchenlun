package com.wktx.www.emperor.ui.activity.recruit.demand;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.recruit.demand.DemandDetailsInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.recruit.demand.DemandDetailsPresenter;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.DateUtil;
import com.wktx.www.emperor.utils.GlideUtil;
import com.wktx.www.emperor.ui.view.IView;
import com.wktx.www.emperor.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

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

    @BindView(R.id.tv_position)
    TextView tvPosition;
    @BindView(R.id.tv_experience)
    TextView tvExperience;
    @BindView(R.id.tv_platform)
    TextView tvPlatform;
    @BindView(R.id.tv_category)
    TextView tvCategory;
    @BindView(R.id.tv_patternTitle)
    TextView tvPatternTitle;
    @BindView(R.id.tv_pattern)
    TextView tvPattern;

    @BindView(R.id.tv_demandPrice)
    TextView tvDemandPrice;
    @BindView(R.id.tv_hireType)
    TextView tvHireType;
    @BindView(R.id.tv_demandEndTime)
    TextView tvDemandEndTime;
    @BindView(R.id.tv_demandTimeUp)
    TextView tvDemandTimeUp;

    @BindView(R.id.civ_logo)
    CircleImageView ivLogo;
    @BindView(R.id.tv_storeName)
    TextView tvStoreName;
    @BindView(R.id.tv_demandReleaseTime)
    TextView tvDemandReleaseTime;

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
    public void onRequestSuccess(DemandDetailsInfoData tData) {
        String status = tData.getStatus();
        if (status.equals("0")){
            tvDemandStatus.setVisibility(View.VISIBLE);
        }else if (status.equals("1")){
            tvDemandStatus.setVisibility(View.GONE);
        }
        tvDemandTitle.setText(tData.getTitle());
        tvDemandContent.setText(tData.getContent());

        tvPosition.setText(tData.getTow().getName());
        //工作经验
        String workingYears = tData.getWorking_years();
        if (workingYears.equals("未设置")){
            workingYears="经验不限";
        }
        tvExperience.setText(workingYears);
        tvPlatform.setText(tData.getBgap().getName());
        tvCategory.setText(tData.getBgat().getName());
        //设计模式
        if (tData.getDesign_pattern()==null){
            tvPatternTitle.setVisibility(View.GONE);
            tvPattern.setText("");
        }else {
            tvPatternTitle.setVisibility(View.VISIBLE);
            tvPattern.setText(tData.getDesign_pattern().getName());
        }
        tvDemandPrice.setText(tData.getBudget()+"元");
        tvHireType.setText(tData.getHire_type());

        tvDemandEndTime.setText(DateUtil.getTimestamp2CustomType(tData.getEnd_time(),"yyyy年MM月dd日"));
        tvDemandReleaseTime.setText(DateUtil.getTimestamp2CustomType(tData.getAdd_time(),"yyyy-MM-dd HH:mm:ss"));
        //计算到期时间
        long curTimeLong = System.currentTimeMillis()/1000L;
        String curTimeStr = DateUtil.getTimestamp2CustomType(curTimeLong + "", "yyyy-MM-dd");
        String endTimeStr = DateUtil.getTimestamp2CustomType(tData.getEnd_time(), "yyyy-MM-dd");
        int gapDays = DateUtil.getGapDays(curTimeStr, endTimeStr)-1;
        if (gapDays>0){
            tvDemandTimeUp.setText(gapDays+"天后到期");
        }else if (gapDays==0){
            tvDemandTimeUp.setText("今日到期");
        }else if (gapDays<0){
            tvDemandTimeUp.setText("该需求已到期");
        }


        if (TextUtils.isEmpty(tData.getStore().getShop_logo())){
            ivLogo.setBackgroundResource(R.drawable.img_mine_head);
        }else {
            GlideUtil.loadImage(tData.getStore().getShop_logo(),R.drawable.img_mine_head,ivLogo);
        }
        tvStoreName.setText(tData.getStore().getShop_name());
    }
    @Override
    public void onRequestFailure(String result) {
        finish();
        ToastUtil.myToast(result);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancleMyToast();
    }
}
