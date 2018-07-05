package com.wktx.www.emperor.ui.activity.staff.report;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hedgehog.ratingbar.RatingBar;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.login.AccountInfoData;
import com.wktx.www.emperor.apiresult.staff.report.ReportDetailsInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.staff.report.ReportDetailsPresenter;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.DateUtil;
import com.wktx.www.emperor.utils.GlideUtil;
import com.wktx.www.emperor.utils.LoginUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.staff.report.IReportDetailsView;
import com.wktx.www.emperor.utils.ToastUtil;
import com.wktx.www.emperor.widget.MyLayoutManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 管理我的员工---工作报告---评价报告
 */
public class ReportDetailsActivity extends ABaseActivity<IReportDetailsView,ReportDetailsPresenter> implements IReportDetailsView {
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
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    //评价
    @BindView(R.id.rb_serviceAttitude)
    RatingBar rbServiceAttitude;
    @BindView(R.id.rb_workAbility)
    RatingBar rbWorkAbility;
    @BindView(R.id.rb_responseSpeed)
    RatingBar rbResponseSpeed;
    @BindView(R.id.et_evaluate)
    EditText etEvaluate;
    @BindView(R.id.bt_sureEvaluate)
    Button btSureEvaluate;
    
    
    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;//数据表现适配器

    private String evaluateState;//评价状态
    private String reportId;//报告id

    private String attitudeStar="0";//服务态度星星数
    private String abilityStar="0";//工作能力星星数
    private String speedStar="0";//响应速度星星数


    @OnClick({R.id.tb_IvReturn,R.id.bt_sureEvaluate})
    public void MyOnclick(View view) {
        //将输入法隐藏
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tvTitle.getWindowToken(), 0);
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            case R.id.bt_sureEvaluate://确认评价
                if (MyUtils.isFastClick()){
                    return;
                }
                //判断输入框格式
                if (TextUtils.isEmpty(getEvaluateContent())){
                    ToastUtil.myToast("请输入评价留言！");
                    etEvaluate.requestFocus();
                }else {//确认评价
                    btSureEvaluate.setEnabled(false);
                    getPresenter().onEvaluateReport(reportId);
                }
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
        // 设置右滑动返回
//        Slidr.attach(this);
        initData();
        initUI();
        initRbListener();
        initHzRecycleView();
    }

    @Override
    protected ReportDetailsPresenter createPresenter() {
        return new ReportDetailsPresenter();
    }

    /**
     * 接收 ReportListActivity 传递过来的报告id、评价状态
     */
    private void initData() {
        reportId = getIntent().getStringExtra(ConstantUtil.KEY_POSITION);
        evaluateState = getIntent().getStringExtra(ConstantUtil.KEY_ISOK);
        getPresenter().onGetReportInfo(reportId);
    }

    /**
     * 初始化控件（待评价可编辑，已评价不可编辑）
     */
    private void initUI() {
        if (evaluateState.equals("0")){//待评价
            tvTitle.setText(R.string.title_report_evaluate);
            rbServiceAttitude.setmClickable(true);
            rbWorkAbility.setmClickable(true);
            rbResponseSpeed.setmClickable(true);
            etEvaluate.setEnabled(true);
            btSureEvaluate.setVisibility(View.VISIBLE);
        }else if (evaluateState.equals("1")) {//已评价
            tvTitle.setText(R.string.title_report_details);
            rbServiceAttitude.setmClickable(false);
            rbWorkAbility.setmClickable(false);
            rbResponseSpeed.setmClickable(false);
            etEvaluate.setEnabled(false);
            btSureEvaluate.setVisibility(View.GONE);
        }
    }

    /**
     * 评分控件点击事件
     */
    private void initRbListener() {
        rbServiceAttitude.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float RatingCount) {
                attitudeStar=RatingCount+"";
            }
        });
        rbWorkAbility.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float RatingCount) {
                abilityStar=RatingCount+"";
            }
        });
        rbResponseSpeed.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float RatingCount) {
                speedStar=RatingCount+"";
            }
        });
    }

    /**
     * 数据表现
     */
    private void initHzRecycleView() {
        //设置横向方向布局
        MyLayoutManager myLayoutManager = new MyLayoutManager(ReportDetailsActivity.this, LinearLayout.HORIZONTAL, false);
        recyclerView.setLayoutManager(myLayoutManager);
        mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_rv_reportdetails, null) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                if (!item.equals("")){
                    ImageView ivImage = helper.getView(R.id.iv_img);
                    GlideUtil.loadImage(item,R.drawable.img_loading,ivImage);
                }
            }
        };
        recyclerView.setAdapter(mAdapter);
    }


    /**
     * IReportDetailsView
     */
    @Override
    public AccountInfoData getUserInfo() {
        AccountInfoData userInfo = LoginUtil.getinit().getUserInfo();
        return userInfo;
    }
    @Override
    public String getAttitude() {
        return attitudeStar;
    }
    @Override
    public String getAbility() {
        return abilityStar;
    }
    @Override
    public String getSpeed() {
        return speedStar;
    }
    @Override
    public String getEvaluateContent() {
        return etEvaluate.getText().toString().trim();
    }
    @Override//报告评价发布是否成功的回调
    public void onEvaluateReportResult(boolean isSuccess, String msg) {
        ToastUtil.myToast(msg);
        btSureEvaluate.setEnabled(true);
        if (isSuccess){
            finish();
        }
    }
    @Override
    public void onRequestSuccess(ReportDetailsInfoData tData) {
        tvStoreName.setText(tData.getShop_name());
        tvReportTime.setText(DateUtil.getTimestamp2CustomType(tData.getAdd_time(),"yyyy-MM-dd"));
        tvWorkContent.setText(tData.getToday_work_content());
        tvStoreState.setText(tData.getStore_current_situation());
        tvOperationPlan.setText(tData.getFuture_operation_plan());
        tvNeedHelp.setText(tData.getNeed_help());
        rbServiceAttitude.setStar(Float.parseFloat(tData.getService_attitude()));
        rbWorkAbility.setStar(Float.parseFloat(tData.getWorking_ability()));
        rbResponseSpeed.setStar(Float.parseFloat(tData.getResponse_speed()));
        etEvaluate.setText(tData.getEvaluation_content());
        mAdapter.setNewData(tData.getData_representation());
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
