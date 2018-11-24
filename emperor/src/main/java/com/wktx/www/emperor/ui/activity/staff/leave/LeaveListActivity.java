package com.wktx.www.emperor.ui.activity.staff.leave;

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
import com.wktx.www.emperor.apiresult.staff.leave.LeaveListInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.staff.LeavePresenter;
import com.wktx.www.emperor.ui.view.staff.ILeaveView;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.utils.ToastUtil;
import com.wktx.www.emperor.widget.MyLayoutManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 管理我的员工---请假记录界面
 */
public class LeaveListActivity extends ABaseActivity<ILeaveView,LeavePresenter> implements ILeaveView {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private String hireId;//雇佣id
    private BaseQuickAdapter<LeaveListInfoData, BaseViewHolder> mAdapter;

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
        setContentView(R.layout.activity_leave_list);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_staff_leavelist);
        initData();
        initRecycleView();
        initAdapter();
        initRefreshLayout();
    }

    @Override
    protected LeavePresenter createPresenter() {
        return new LeavePresenter();
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
        mAdapter = new BaseQuickAdapter<LeaveListInfoData, BaseViewHolder>(R.layout.item_rv_leavelist, null) {
            @Override
            protected void convert(BaseViewHolder helper, LeaveListInfoData item) {
                helper.setText(R.id.tv_beginTime,"开始时间："+item.getStart_time());
                helper.setText(R.id.tv_endTime,"结束时间："+item.getEnd_time());
                //审批状态
                TextView tvStatus = helper.getView(R.id.tv_leaveStatus);
                tvStatus.setText(item.getStatus());
                switch (item.getStatus()){
                    case "申请中":
                        tvStatus.setTextColor(getResources().getColor(R.color.color_ffb321));
                        break;
                    case "已同意":
                    case "已拒绝":
                        tvStatus.setTextColor(getResources().getColor(R.color.color_a0a0a0));
                        break;
                    default:
                        break;
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
                //将请假信息 传递给请假详情
                Intent intent = new Intent(LeaveListActivity.this, LeaveDetailsActivity.class);
                intent.putExtra(ConstantUtil.KEY_DATA,mAdapter.getData().get(position));
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
        getPresenter().getInfo(hireId);
    }

    /**
     * ILeaveView
     */
    @Override//获取请假同意、拒绝回调
    public void onLeaveResult(boolean isSuccess, String msg) {
    }
    @Override //获取请假记录回调
    public void onRequestSuccess(List<LeaveListInfoData> tData) {
        recyclerView.setBackgroundResource(R.color.color_f0f0f0);
        mAdapter.setNewData(tData);
        swipeRefreshLayout.setRefreshing(false);
    }
    @Override
    public void onRequestFailure(String result) {
        if (result.equals("")){//没数据
            recyclerView.setBackgroundResource(R.drawable.img_nothing);
            ToastUtil.myToast("暂无任何请假记录！");
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
