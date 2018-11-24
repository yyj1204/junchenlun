package com.wktx.www.emperor.ui.activity.mine;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.ui.activity.staff.StaffManageActivity;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.mine.condition.ConditionBean;
import com.wktx.www.emperor.apiresult.mine.condition.ConditionInfoData;
import com.wktx.www.emperor.apiresult.staff.staff.StaffInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.staff.StaffPresenter;
import com.wktx.www.emperor.ui.activity.recruit.hire.TrusteeshipSalaryActivity;
import com.wktx.www.emperor.ui.adapter.DropDownListAdapter;
import com.wktx.www.emperor.ui.adapter.staff.StaffListAdapter;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.staff.IStaffView;
import com.wktx.www.emperor.utils.ToastUtil;
import com.wktx.www.emperor.widget.CustomDialog;
import com.wktx.www.emperor.widget.DropDownMenu;
import com.wktx.www.emperor.widget.MyLayoutManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人中心-雇佣记录
 */
public class HireRecordActivity extends ABaseActivity<IStaffView,StaffPresenter> implements IStaffView {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private DropDownListAdapter jobTypeAdapter;//工作类型筛选适配器
    private DropDownListAdapter hireStateAdapter;//雇佣状态筛选适配器
    private StaffListAdapter mAdapter;//RecyclerView 适配器

    private String[] tabTexts = {"工作类型", "雇佣状态"};
    private List<ConditionBean> jobTypeBeans = new ArrayList<>();//工作类型集合
    private List<ConditionBean> hireStateBeans = new ArrayList<>();//雇佣状态集合
    private List<String> jobTypeStrs = new ArrayList<>();//工作类型名称
    private List<String> hireStateStrs = new ArrayList<>();//雇佣状态名称
    private String jobTypeId="0";//工作类型 0:全部岗位 1:美工 2:客服 3:运营
    private String hireStateId="0";//雇佣状态 0:全部状态 1:合作中(雇佣成功) 2:请假中 3:暂停中 4:投诉中 5:被解雇 6:完结 7:退款 8:未付款到期 9:续约中
    private List<View> popupViews = new ArrayList<>();//多条件筛选的弹窗集合
    private ListView jobTypeView;
    private ListView hireStateView;

    private int page = 1;
    private static final int PAGE_SIZE = 10;//请求每页的数据量
    private boolean isRefresh;//是下拉刷新还是加载更多  false：加载更多，true：下拉刷新

    private CustomDialog customDialog;

    @OnClick({R.id.tb_IvReturn})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn:
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
        setContentView(R.layout.activity_hire_record);
        ButterKnife.bind(this);
        //设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_hire_record);
        initData();
        initView();
        initRecycleView();
        initAdapter();
        initRefreshLayout();
    }

    @Override
    protected StaffPresenter createPresenter() {
        return new StaffPresenter();
    }

    private void initData() {
        getPresenter().onGetCondition();//获取列表检索条件
    }

    private void initView() {
        //工作类型
        jobTypeView = new ListView(HireRecordActivity.this);
        jobTypeView.setDividerHeight(0);
        jobTypeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (MyUtils.isFastClick1()){
                    return;
                }
                jobTypeId = jobTypeBeans.get(position).getId();
                jobTypeAdapter.setCheckItem(position);
                dropDownMenu.setTabText(jobTypeStrs.get(position));
                dropDownMenu.closeMenu();
                refresh();
            }
        });

        //雇佣状态
        hireStateView = new ListView(HireRecordActivity.this);
        hireStateView.setDividerHeight(0);
        hireStateView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (MyUtils.isFastClick1()){
                    return;
                }
                hireStateId = hireStateBeans.get(position).getId();
                hireStateAdapter.setCheckItem(position);
                dropDownMenu.setTabText(hireStateStrs.get(position));
                dropDownMenu.closeMenu();
                refresh();
            }
        });
        //添加
        popupViews.add(jobTypeView);
        popupViews.add(hireStateView);
        //展示条件筛选结果的内容控件
        View contentView =  getLayoutInflater().inflate(R.layout.include_recyclerview_refresh, null);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        swipeRefreshLayout = (SwipeRefreshLayout) contentView.findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerView);
        dropDownMenu.setDropDownMenu(Arrays.asList(tabTexts), popupViews, contentView);
    }

    /**
     * 初始化 RecycleView
     */
    private void initRecycleView() {
        //设置刷新圈圈的颜色
        swipeRefreshLayout.setColorSchemeColors(Color.rgb(255, 179, 33));
        swipeRefreshLayout.setRefreshing(true);
        //设置分割线与垂直方向布局
        recyclerView.addItemDecoration(MyUtils.drawDivider(HireRecordActivity.this, LinearLayout.VERTICAL, R.drawable.divider_f0f0f0_14));
        MyLayoutManager manager = new MyLayoutManager(HireRecordActivity.this, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
    }

    /**
     * 为 RecyclerView 加载 Adapter
     */
    private void initAdapter() {
        mAdapter = new StaffListAdapter(HireRecordActivity.this);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        });
        recyclerView.setAdapter(mAdapter);
        //子控件点击事件
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (MyUtils.isFastClick1()){
                    return;
                }

                StaffInfoData info = mAdapter.getData().get(position);
                Intent intent = new Intent();
                if (info.getIs_pay().equals("0")&&info.getType().equals("0")){//未支付，前往托管工资界面
                    //传递 雇佣ID
                    intent.setClass(HireRecordActivity.this, TrusteeshipSalaryActivity.class);
                    intent.putExtra(ConstantUtil.KEY_DATA,info.getHire_id());
                    intent.putExtra(ConstantUtil.KEY_POSITION,ConstantUtil.ACTIVITY_GYJL);
                    startActivity(intent);
                }else{//已支付
                    //雇佣状态 0:全部状态 1:合作中(雇佣成功) 2:请假中 3:暂停中 4:投诉中 5:被解雇 6:完结 7:退款 8.已取消 9:续约中 10:待入职
                    switch (info.getType()){
                        case "1":
                        case "2":
                        case "3":
                        case "4":
                        case "5":
                        case "6":
                        case "7":
                        case "9"://传递 雇佣ID，前往我的员工界面
                            intent.setClass(HireRecordActivity.this, StaffManageActivity.class);
                            intent.putExtra(ConstantUtil.KEY_DATA,info.getHire_id());
                            startActivity(intent);
                            break;
                        case "8"://已取消（支付超时、取消、拒绝）
                            switch (info.getStatus_desc()){
                                case ConstantUtil.HIRESTATE_OVERTIME:
                                    ToastUtil.myToast("该订单超时未支付已取消！");
                                    break;
                                case ConstantUtil.HIRESTATE_CANCEL:
                                    ToastUtil.myToast("您已取消该订单！");
                                    break;
                                case ConstantUtil.HIRESTATE_REFUSED:
                                    ToastUtil.myToast("该员工拒绝您的入职邀请！");
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case "10"://如果雇佣状态待入职
                            showCancelOrdersDialog(info.getHire_id());
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }

    /**
     * 取消雇佣订单对话框
     */
    private void showCancelOrdersDialog(final String hireId) {
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setTitle("系统提示");
        builder.setMessage("该员工待入职，是否继续等待？");
        builder.setPositiveButton("继续等待", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("取消订单",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        getPresenter().onCancelOrders(hireId);
                    }
                });

        customDialog = builder.create();
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.show();
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
        page = 1;
        isRefresh=true;
        //这里的作用是防止下拉刷新的时候还可以上拉加载
        mAdapter.setEnableLoadMore(false);
        getPresenter().onGetStaffList(page);
    }
    //加载更多
    private void loadMore() {
        isRefresh=false;
        getPresenter().onGetStaffList(page);
    }


    /**
     * IStaffView
     */
    @Override
    public String getJobType() {
        return jobTypeId;
    }
    @Override
    public String getHireState() {
        return hireStateId;
    }
    @Override
    public void onGetConditionSuccessResult(ConditionInfoData result) {
        jobTypeBeans = result.getTow();
        hireStateBeans = result.getType();
        for (int i = 0; i < jobTypeBeans.size() ; i++) {
            jobTypeStrs.add(jobTypeBeans.get(i).getName());
        }
        for (int i = 0; i < hireStateBeans.size() ; i++) {
            hireStateStrs.add(hireStateBeans.get(i).getName());
        }
        jobTypeAdapter = new DropDownListAdapter(HireRecordActivity.this, jobTypeStrs);
        hireStateAdapter = new DropDownListAdapter(HireRecordActivity.this, hireStateStrs);
        jobTypeView.setAdapter(jobTypeAdapter);
        hireStateView.setAdapter(hireStateAdapter);
    }
    @Override
    public void onGetConditionFailureResult(String result) {
        ToastUtil.myToast(result);
    }
    @Override//取消订单
    public void onCancelOrdersResult(boolean isSuccess, String result) {
        ToastUtil.myToast(result);
        if (isSuccess){
            refresh();
        }
    }
    @Override
    public void onRequestSuccess(List<StaffInfoData> tData) {
        recyclerView.setBackgroundResource(R.color.color_f0f0f0);
        setData(tData);
        if (isRefresh){//停止刷新
            mAdapter.setEnableLoadMore(true);
            swipeRefreshLayout.setRefreshing(false);
        }
    }
    @Override
    public void onRequestFailure(String result) {
        String toastStr=result;
        setData(null);
        //如果是刷新，没数据代表全部没数据
        if (isRefresh){
            if (result.equals("")){
                toastStr="暂无任何雇佣记录！";
                recyclerView.setBackgroundResource(R.drawable.img_nothing_hire);
            }else {
                recyclerView.setBackgroundResource(R.drawable.img_nothing_net);
            }
            mAdapter.setEnableLoadMore(true);
            swipeRefreshLayout.setRefreshing(false);
        }else {//如果是加载，没数据代表加载完毕
            if (result.equals("")){//没数据
                toastStr="已经到底了哦！";
                mAdapter.loadMoreEnd();
            }else {//请求出错
                mAdapter.loadMoreFail();
            }
        }
        ToastUtil.myToast(toastStr);
    }

    /**
     * 设置数据
     */
    private void setData(List data) {
        page++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            mAdapter.setNewData(data);
        } else {
            if (size > 0) {
                mAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mAdapter.loadMoreEnd(isRefresh);
        } else {
            mAdapter.loadMoreComplete();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (customDialog!=null){
            customDialog.dismiss();
            customDialog=null;
        }

        ToastUtil.cancleMyToast();
    }
}
