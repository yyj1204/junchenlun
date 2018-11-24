package com.wktx.www.subjects.ui.activity.manage.report;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hedgehog.ratingbar.RatingBar;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.manage.report.ReportDetailsInfoData;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.presenter.manage.report.ReportDetailsPresenter;
import com.wktx.www.subjects.ui.activity.ImageActivity;
import com.wktx.www.subjects.ui.view.IView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.GlideUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.widget.MyLayoutManager;
import com.wktx.www.subjects.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 管理我的工作---报告详情
 */
public class ReportDetailsActivity extends ABaseActivity<IView,ReportDetailsPresenter> implements IView<ReportDetailsInfoData> {

    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    //报告详情
    @BindView(R.id.tv_storeName)
    TextView tvStoreName;
    @BindView(R.id.tv_reportTime)
    TextView tvReportTime;
    @BindView(R.id.tv_workContent)
    TextView tvWorkContent;
    @BindView(R.id.tv_storeState)
    TextView tvStoreState;
    @BindView(R.id.tv_operationPlan)
    TextView tvOperationPlan;
    @BindView(R.id.tv_needHelp)
    TextView tvNeedHelp;
    @BindView(R.id.tv_imgNum)
    TextView tvImgNum;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    //评价
    @BindView(R.id.linear_score)
    LinearLayout llScore;
    @BindView(R.id.linear_evaluate)
    LinearLayout llEvaluate;
    @BindView(R.id.tv_evaluateTime)
    TextView tvEvaluateTime;
    @BindView(R.id.rb_serviceAttitude)
    RatingBar rbServiceAttitude;
    @BindView(R.id.rb_workAbility)
    RatingBar rbWorkAbility;
    @BindView(R.id.rb_responseSpeed)
    RatingBar rbResponseSpeed;
    @BindView(R.id.tv_evaluate)
    TextView tvEvaluate;

    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;//数据表现适配器

    private List<String> imageUrlList = new ArrayList<>();//图片url集合

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
        setContentView(R.layout.activity_report_details);
        ButterKnife.bind(this);
        initData();
        initHzRecycleView();
    }

    @Override
    protected ReportDetailsPresenter createPresenter() {
        return new ReportDetailsPresenter();
    }

    /**
     * 接收 Message1TaskFragment Message1EvaluateFragment 传递过来的 reportId
     */
    private void initData() {
        String reportId = getIntent().getStringExtra(ConstantUtil.KEY_DATA);
        getPresenter().getReportInfo(reportId);
    }

    /**
     * 数据表现 横向RecycleView
     */
    private void initHzRecycleView() {
        //设置横向方向布局
        MyLayoutManager myLayoutManager = new MyLayoutManager(ReportDetailsActivity.this, LinearLayout.HORIZONTAL, false);
        recyclerView.setLayoutManager(myLayoutManager);
        mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_rv_reportdetails_data, null) {
            @Override
            protected void convert(final BaseViewHolder helper, String item) {
                ImageView ivImage = helper.getView(R.id.iv_img);
                if (!item.equals("")){
                    GlideUtil.loadImage(item,R.drawable.img_loading,ivImage);
                }

                ivImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (MyUtils.isFastClick1()){
                            return;
                        }
                        //查看大图
                        String[] imageUrls = imageUrlList.toArray(new String[imageUrlList.size()]);
                        Intent intent = new Intent(ReportDetailsActivity.this, ImageActivity.class);
                        intent.putExtra(ConstantUtil.KEY_DATA, imageUrls);
                        intent.putExtra(ConstantUtil.KEY_POSITION, helper.getLayoutPosition());
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setAdapter(mAdapter);
    }


    /**
     * IView
     */
    @Override
    public void onRequestSuccess(ReportDetailsInfoData tData) {
        imageUrlList = tData.getData_representation();
        mAdapter.setNewData(imageUrlList);
        tvImgNum.setText("数据表现（"+imageUrlList.size()+"张）");
        tvStoreName.setText(tData.getShop_name());
        tvReportTime.setText(tData.getAdd_time());
        tvWorkContent.setText(tData.getToday_work_content());
        tvStoreState.setText(tData.getStore_current_situation());
        tvOperationPlan.setText(tData.getFuture_operation_plan());
        tvNeedHelp.setText(tData.getNeed_help());

        //评分留言
        String evaluateTime = tData.getEvaluate_time();
        //如果评价时间为1970-01-01 08:00:00，则未评价。
        if (TextUtils.isEmpty(evaluateTime)||evaluateTime.equals("1970-01-01 08:00:00")){
            tvTitle.setText("报告详情(未评价)");
            llScore.setVisibility(View.GONE);
            llEvaluate.setVisibility(View.GONE);
        }else {//如果评价时间不为空，则已评价。
            tvTitle.setText(R.string.title_report_details);
            llScore.setVisibility(View.VISIBLE);
            llScore.setVisibility(View.VISIBLE);
        }
        tvEvaluateTime.setText(evaluateTime);
        rbServiceAttitude.setStar(Float.parseFloat(tData.getService_attitude()));
        rbWorkAbility.setStar(Float.parseFloat(tData.getWorking_ability()));
        rbResponseSpeed.setStar(Float.parseFloat(tData.getResponse_speed()));
        tvEvaluate.setText(tData.getEvaluation_content());
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
