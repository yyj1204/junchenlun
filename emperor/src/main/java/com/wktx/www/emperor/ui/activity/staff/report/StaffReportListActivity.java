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
import com.wktx.www.emperor.apiresult.staff.report.StaffReportListInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.staff.report.StaffReportListPresenter;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.DateUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.IView;
import com.wktx.www.emperor.utils.ToastUtil;
import com.wktx.www.emperor.widget.MyLayoutManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 管理我的员工---工作报告---工作安排列表---报告列表
 */
public class StaffReportListActivity extends ABaseActivity<IView,StaffReportListPresenter> implements IView<List<StaffReportListInfoData>> {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private String hireId;//雇佣id
    private String workId;//工作安排id
    private String type;//评价类型

    private BaseQuickAdapter<StaffReportListInfoData,BaseViewHolder> mAdapter;//RecyclerView 适配器

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
        setContentView(R.layout.activity_staff_reportlist);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        initData();
        initRecycleView();
        initAdapter();
        initRefreshLayout();
    }

    @Override
    protected StaffReportListPresenter createPresenter() {
        return new StaffReportListPresenter();
    }

    /**
     * 接收 StaffWorkListActivity 传递过来的雇佣id、工作安排id、评价类型
     */
    private void initData() {
        Intent intent = getIntent();
        hireId = intent.getStringExtra(ConstantUtil.KEY_POSITION);
        workId = intent.getStringExtra(ConstantUtil.KEY_DATA);
        type = intent.getStringExtra(ConstantUtil.KEY_ISOK);
        if (type.equals("0")){//待评价
            tvTitle.setText(R.string.title_staff_reportlist1);
        }else if (type.equals("1")){//已评价
            tvTitle.setText(R.string.title_staff_reportlist2);
        }else {//所有报告
            tvTitle.setText(R.string.title_staff_reportlist);
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
        mAdapter = new BaseQuickAdapter<StaffReportListInfoData,BaseViewHolder>(R.layout.item_rv_report,null){
            @Override
            protected void convert(BaseViewHolder helper, StaffReportListInfoData item) {
                helper.setText(R.id.tv_reportTime, DateUtil.getTimestamp2CustomType(item.getAdd_time(),"yyyy-MM-dd"));
                String evaluateTime = item.getEvaluate_time();
                if (evaluateTime.equals("0")){//评价时间为0，待评价
                    helper.setText(R.id.tv_evaluateState,"待评价");
                    helper.setBackgroundRes(R.id.tv_evaluateState,R.drawable.shape_solid_ffb321_10);
                    helper.setText(R.id.tv_evaluateTime, "");
                }else{//评价时间不为0，已评价
                    helper.setText(R.id.tv_evaluateTime, DateUtil.getTimestamp2CustomType(item.getAdd_time(),"yyyy-MM-dd"));
                    helper.setText(R.id.tv_evaluateState,"已评价");
                    helper.setBackgroundRes(R.id.tv_evaluateState,R.drawable.shape_solid_cccccc_10);
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
                StaffReportListInfoData info = (StaffReportListInfoData) adapter.getData().get(position);
                Intent intent = new Intent(StaffReportListActivity.this, ReportDetailsActivity.class);
                intent.putExtra(ConstantUtil.KEY_POSITION,info.getId());
                intent.putExtra(ConstantUtil.KEY_ISOK,info.getEvaluate_time());
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
        getPresenter().onGetReportList(hireId,workId,type);
    }


    /**
     * IView
     */
    @Override
    public void onRequestSuccess(List<StaffReportListInfoData> tData) {
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
