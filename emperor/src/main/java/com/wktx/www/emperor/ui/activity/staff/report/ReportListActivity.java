package com.wktx.www.emperor.ui.activity.staff.report;

import android.content.Intent;
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
import com.wktx.www.emperor.apiresult.staff.report.ReportListInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.staff.report.ReportListPresenter;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.DateUtil;
import com.wktx.www.emperor.utils.LoginUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.IView;
import com.wktx.www.emperor.utils.ToastUtil;
import com.wktx.www.emperor.widget.MyLayoutManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 管理我的员工---工作报告（任务）---报告列表
 */
public class ReportListActivity extends ABaseActivity<IView,ReportListPresenter> implements IView<List<ReportListInfoData>> {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private String hireId;//雇佣id
    private String taskId;//任务id
    private String type;//评价类型

    private BaseQuickAdapter<ReportListInfoData,BaseViewHolder> mAdapter;//RecyclerView 适配器

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
    protected void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        initData();
        initRecycleView();
        initAdapter();
        initRefreshLayout();
    }

    @Override
    protected ReportListPresenter createPresenter() {
        return new ReportListPresenter();
    }

    /**
     * 接收 StaffReportActivity 传递过来的雇佣id、报告任务id、评价类型
     */
    private void initData() {
        Intent intent = getIntent();
        hireId = intent.getStringExtra(ConstantUtil.KEY_POSITION);
        taskId = intent.getStringExtra(ConstantUtil.KEY_DATA);
        type = intent.getStringExtra(ConstantUtil.KEY_ISOK);
        if (type.equals("0")){//待评价
            tvTitle.setText(R.string.title_report1);
        }else if (type.equals("1")){//已评价
            tvTitle.setText(R.string.title_report2);
        }
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
        mAdapter = new BaseQuickAdapter<ReportListInfoData,BaseViewHolder>(R.layout.item_rv_report,null){
            @Override
            protected void convert(BaseViewHolder helper, ReportListInfoData item) {
                helper.setText(R.id.tv_time, DateUtil.getTimestamp2CustomType(item.getAdd_time(),"yyyy-MM-dd"));
                if (type.equals("0")){//待评价
                    helper.setText(R.id.tv_status,"待评价");
                    helper.setBackgroundRes(R.id.tv_status,R.drawable.shape_solid_fa716f_10);
                }else if (type.equals("1")){//已评价
                    helper.setText(R.id.tv_status,"已评价");
                    helper.setBackgroundRes(R.id.tv_status,R.drawable.shape_solid_999999_10);
                }
            }
        };
        recyclerView.setAdapter(mAdapter);
        //子控件点击事件
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (MyUtils.isFastClick1()){
                    return;
                }
                //传递报告id 评价状态 给 ReportDetailsActivity
                ReportListInfoData info = (ReportListInfoData) adapter.getData().get(position);
                Intent intent = new Intent(ReportListActivity.this, ReportDetailsActivity.class);
                intent.putExtra(ConstantUtil.KEY_POSITION,info.getId());
                intent.putExtra(ConstantUtil.KEY_ISOK,type);
                startActivity(intent);
            }
        });
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
        getPresenter().onGetReportList(hireId,taskId,type);
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
    public void onRequestSuccess(List<ReportListInfoData> tData) {
        recyclerView.setBackgroundResource(R.color.color_f0f0f0);
        mAdapter.setNewData(tData);
        swipeRefreshLayout.setRefreshing(false);
    }
    @Override
    public void onRequestFailure(String result) {
        if (result.equals("")){//没数据
            recyclerView.setBackgroundResource(R.drawable.img_nothing);
            ToastUtil.myToast("暂无任何工作报告！");
        }else {
            recyclerView.setBackgroundResource(R.drawable.img_nothing_net);
            ToastUtil.myToast(result);
        }
        mAdapter.setNewData(null);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancleMyToast();
    }
}
