package com.wktx.www.emperor.ui.activity.staff;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.login.AccountInfoData;
import com.wktx.www.emperor.apiresult.staff.salary.SalaryPayBean;
import com.wktx.www.emperor.apiresult.staff.salary.SalaryTrusteeshipBean;
import com.wktx.www.emperor.apiresult.staff.salary.StaffSalaryInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.staff.StaffSalaryPresenter;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.DateUtil;
import com.wktx.www.emperor.utils.LoginUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.IView;
import com.wktx.www.emperor.utils.ToastUtil;
import com.wktx.www.emperor.widget.MyLayoutManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 管理我的员工---员工工资
 */
public class StaffSalaryActivity extends ABaseActivity<IView,StaffSalaryPresenter> implements IView<StaffSalaryInfoData> {

    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.tv_salaryTotal)
    TextView tvSalaryTotal;
    @BindView(R.id.tv_payMoney)
    TextView tvPayMoney;
    @BindView(R.id.tv_payLine)
    TextView tvPayLine;
    @BindView(R.id.tv_trusteeshipMoney)
    TextView tvTrusteeshipMoney;
    @BindView(R.id.tv_trusteeshipLine)
    TextView tvTrusteeshipLine;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    private String hireId;//雇佣id

    private BaseQuickAdapter<SalaryTrusteeshipBean, BaseViewHolder> trusteeshipAdapter;
    private BaseQuickAdapter<SalaryPayBean, BaseViewHolder> payAdapter;

    private boolean isShowPay=false;//false：显示托管工资列表 ， true：显示已发工资列表


    @OnClick({R.id.tb_IvReturn,R.id.linear_trusteeship,R.id.linear_pay})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            case R.id.linear_trusteeship://托管中工资
                isShowPay=false;
                tvTrusteeshipLine.setVisibility(View.VISIBLE);
                tvPayLine.setVisibility(View.INVISIBLE);
                refresh();
                break;
            case R.id.linear_pay://已发工资
                isShowPay=true;
                tvTrusteeshipLine.setVisibility(View.INVISIBLE);
                tvPayLine.setVisibility(View.VISIBLE);
                refresh();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_salary);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_staff_salary);
        initData();
        initRecycleView();
        initAdapter();
        initRefreshLayout();
        refresh();
    }

    @Override
    protected StaffSalaryPresenter createPresenter() {
        return new StaffSalaryPresenter();
    }

    /**
     * 接收 StaffManageActivity 传递过来的雇佣id
     */
    private void initData() {
        hireId = getIntent().getStringExtra(ConstantUtil.KEY_POSITION);
    }

    /**
     * 初始化 RecycleView
     */
    private void initRecycleView() {
        //设置刷新圈圈的颜色
        swipeRefreshLayout.setColorSchemeColors(Color.rgb(255, 179, 33));
        swipeRefreshLayout.setRefreshing(true);
        //设置分割线与垂直方向布局
        recyclerView.addItemDecoration(MyUtils.drawDivider(this, LinearLayout.VERTICAL, R.drawable.divider_f0f0f0_2));
        MyLayoutManager myLayoutManager = new MyLayoutManager(this, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(myLayoutManager);
    }

    /**
     * 为 RecyclerView 加载 Adapter
     */
    private void initAdapter() {
        trusteeshipAdapter = new BaseQuickAdapter<SalaryTrusteeshipBean, BaseViewHolder>(R.layout.item_rv_staffsalary, null) {
            @Override
            protected void convert(BaseViewHolder helper, SalaryTrusteeshipBean item) {
                String beginTime = DateUtil.getTimestamp2CustomType(item.getStart_time(), "yyyy.MM.dd");
                String endTime = DateUtil.getTimestamp2CustomType(item.getEnd_time(), "yyyy.MM.dd");
                helper.setText(R.id.tv_time,beginTime+"-"+endTime);
                //判断金额格式，小数点后为"00"，直接取整
                String money = item.getTrusteeship_wages();
                String[] mSplit = money.split("[.]");
                String moneyStr="";
                if (mSplit[1].equals("00")){
                    moneyStr=mSplit[0];
                }else {
                    moneyStr=money;
                }
                helper.setText(R.id.tv_money,moneyStr);
            }
        };
        payAdapter = new BaseQuickAdapter<SalaryPayBean, BaseViewHolder>(R.layout.item_rv_staffsalary, null) {
            @Override
            protected void convert(BaseViewHolder helper, SalaryPayBean item) {
                String payTime = DateUtil.getTimestamp2CustomType(item.getPaid_time(), "yyyy.MM.dd");
                helper.setText(R.id.tv_time,payTime);
                //判断金额格式，小数点后为"00"，直接取整
                String money = item.getAmount();
                String[] mSplit = money.split("[.]");
                String moneyStr="";
                if (mSplit[1].equals("00")){
                    moneyStr=mSplit[0];
                }else {
                    moneyStr=money;
                }
                helper.setText(R.id.tv_money,moneyStr);
            }
        };
    }

    /**
     * 下拉刷新 ---  下拉加载更多
     */
    private void initRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }
    //刷新
    private void refresh() {
        getPresenter().onGetStaffSalaryInfo(hireId);
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
    public void onRequestSuccess(StaffSalaryInfoData tData) {
        tvSalaryTotal.setText(tData.getTotal_expenditure());
        tvTrusteeshipMoney.setText(tData.getTrusteeship_amount());
        tvPayMoney.setText(tData.getAlready_amount());
        if (isShowPay){//如果是已发工资---显示已发工资列表
            payAdapter.setNewData(tData.getAlready_hire());
            recyclerView.setAdapter(payAdapter);
            //如果没数据代表全部没数据
            if (tData.getAlready_hire()==null||tData.getAlready_hire().size()==0){
                ToastUtil.myToast("暂无任何已发工资！");
                recyclerView.setBackgroundResource(R.drawable.img_nothing_transaction);
            }else {
                recyclerView.setBackgroundResource(R.color.color_f0f0f0);
            }
        }else {//如果是托管工资---显示托管工资列表
            trusteeshipAdapter.setNewData(tData.getTrusteeship_list());
            recyclerView.setAdapter(trusteeshipAdapter);
            //如果没数据代表全部没数据
            if (tData.getTrusteeship_list()==null||tData.getTrusteeship_list().size()==0){
                ToastUtil.myToast("暂无任何托管中工资！");
                recyclerView.setBackgroundResource(R.drawable.img_nothing_transaction);
            }else {
                recyclerView.setBackgroundResource(R.color.color_f0f0f0);
            }
        }

        swipeRefreshLayout.setRefreshing(false);
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
