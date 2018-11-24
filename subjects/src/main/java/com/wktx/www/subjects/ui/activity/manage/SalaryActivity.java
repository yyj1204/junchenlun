package com.wktx.www.subjects.ui.activity.manage;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.manage.salary.SalaryInfoData;
import com.wktx.www.subjects.apiresult.manage.salary.SalaryPayBean;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.presenter.manage.SalaryPresenter;
import com.wktx.www.subjects.ui.view.IView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.widget.MyLayoutManager;
import com.wktx.www.subjects.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 管理我的工作---我的工资
 */
public class SalaryActivity extends ABaseActivity<IView,SalaryPresenter> implements IView<SalaryInfoData> {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.tv_salaryTotal)
    TextView tvSalaryTotal;
    @BindView(R.id.tv_receiveMoney)
    TextView tvReceiveMoney;
    @BindView(R.id.tv_receiveLine)
    TextView tvReceiveLine;
    @BindView(R.id.tv_trusteeshipMoney)
    TextView tvTrusteeshipMoney;
    @BindView(R.id.tv_trusteeshipLine)
    TextView tvTrusteeshipLine;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private String hireId;//雇佣id

    private BaseQuickAdapter<SalaryPayBean, BaseViewHolder> mAdapter;

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
        setContentView(R.layout.activity_salary);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_manage_salary);
        initData();
        initRecycleView();
        initAdapter();
        initRefreshLayout();
        refresh();
    }

    @Override
    protected SalaryPresenter createPresenter() {
        return new SalaryPresenter();
    }

    /**
     * 接收 ManageActivity 传递过来的雇佣id
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
        mAdapter = new BaseQuickAdapter<SalaryPayBean, BaseViewHolder>(R.layout.item_rv_salary, null) {
            @Override
            protected void convert(BaseViewHolder helper, SalaryPayBean item) {
                if (TextUtils.isEmpty(item.getRemark())){
                    helper.setText(R.id.tv_remark,"无备注");
                }else {
                    helper.setText(R.id.tv_remark,item.getRemark());
                }
                helper.setText(R.id.tv_time, item.getAdd_time());
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
        recyclerView.setAdapter(mAdapter);
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
        getPresenter().getInfo(hireId);
    }

    /**
     * IView
     */
    @Override
    public void onRequestSuccess(SalaryInfoData tData) {
        tvSalaryTotal.setText(tData.getHire_price());
        tvTrusteeshipMoney.setText(tData.getTrusteeship_wages());
        tvReceiveMoney.setText(tData.getPaid_wages());
        mAdapter.setNewData(tData.getPayList());
        //如果没数据代表全部没数据
        if (tData.getPayList() == null || tData.getPayList().size() == 0) {
            ToastUtil.myToast("暂无任何已收工资！");
            recyclerView.setBackgroundResource(R.drawable.img_nothing_transaction);
        } else {
            recyclerView.setBackgroundResource(R.color.color_f0f0f0);
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
