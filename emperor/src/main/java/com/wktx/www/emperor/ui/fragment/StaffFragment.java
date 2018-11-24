package com.wktx.www.emperor.ui.fragment;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.ui.activity.MainActivity;
import com.wktx.www.emperor.ui.activity.staff.StaffManageActivity;
import com.wktx.www.emperor.ui.activity.main.message.MessageActivity;
import com.wktx.www.emperor.apiresult.mine.condition.ConditionBean;
import com.wktx.www.emperor.apiresult.mine.condition.ConditionInfoData;
import com.wktx.www.emperor.apiresult.staff.staff.StaffInfoData;
import com.wktx.www.emperor.basemvp.ALazyLoadFragment;
import com.wktx.www.emperor.presenter.staff.StaffPresenter;
import com.wktx.www.emperor.ui.activity.login.LoginActivity;
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
 * 员工片段
 */
public class StaffFragment extends ALazyLoadFragment<IStaffView,StaffPresenter> implements IStaffView {
    @BindView(R.id.linear_nothing)
    LinearLayout llNothing;
    @BindView(R.id.tv_onlineExperience)
    TextView tvExperience;
    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private DropDownListAdapter jobTypeAdapter;//工作类型筛选适配器
    private DropDownListAdapter hireStateAdapter;//雇佣状态筛选适配器
    private StaffListAdapter adapter;//RecyclerView 适配器

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

    @OnClick({R.id.iv_message,R.id.tv_onlineExperience,R.id.tv_selectStaff})
    public void MyOnclick(View view) {
        if (MyUtils.isFastClick1()){
            return;
        }
        switch (view.getId()) {
            case R.id.iv_message://消息通知
                isLoginStartActivity(MessageActivity.class);
                break;
            case R.id.tv_onlineExperience://体验在线办公
                if (getUserInfo()==null){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }else {
                    //定位到 MainFragment
                    MainActivity activity = (MainActivity) getActivity();
                    activity.setCurrenPager(0);
                }
//                ToastUtil.myToast("还没做");
                break;
            case R.id.tv_selectStaff://挑合适的员工
                //定位到 RecruitFragment
                MainActivity activity = (MainActivity) getActivity();
                activity.setCurrenPager(1);
                break;
            default:
                break;
        }
    }

    /**
     * 登录了才能打开对应的界面
     */
    private void isLoginStartActivity(Class<?> clazz) {
        if (getUserInfo()!=null){
            startActivity(new Intent(getActivity(), clazz));
        }else {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }


    /**
     * 片段是否可见
     * @param isVisible falese 可见 -> 不可见
     *  此时 isRefresh=true，在片段不可见时候将 isRefresh = false
     * @param isVisible true  不可见 -> 可见
     *  如果 isRefresh=false 说明已经创建过，防止多次请求接口，加判断
     */
    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible){
            if (getUserInfo()==null){
                tvExperience.setText("未登录，去登录");
                llNothing.setVisibility(View.VISIBLE);
                dropDownMenu.setVisibility(View.GONE);
            }else {
                if (!isRefresh){
                    llNothing.setVisibility(View.GONE);
                    dropDownMenu.setVisibility(View.VISIBLE);
                    initData();
                    refresh();
                }
            }
        }else {
            if (isRefresh){
                isRefresh=false;
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    /**
     * 片段第一次被创建（可见）时才会执行到这个方法
     * 加载数据
     * isRefresh=true
     */
    @Override
    protected void onFragmentFirstVisible() {
        if (getUserInfo()==null){
            tvExperience.setText("未登录，去登录");
            llNothing.setVisibility(View.VISIBLE);
            dropDownMenu.setVisibility(View.GONE);
        }else {
            llNothing.setVisibility(View.GONE);
            dropDownMenu.setVisibility(View.VISIBLE);
            initData();
            refresh();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserInfo()==null){
            tvExperience.setText("未登录，去登录");
            llNothing.setVisibility(View.VISIBLE);
            dropDownMenu.setVisibility(View.GONE);
        }else {
            llNothing.setVisibility(View.GONE);
            dropDownMenu.setVisibility(View.VISIBLE);
            initData();
            refresh();
        }
    }

    public StaffFragment() {
    }
    public static StaffFragment newInstance(String info) {
        Bundle args = new Bundle();
        StaffFragment fragment = new StaffFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff, container, false);
        ButterKnife.bind(this, view);
        initView();
        initRecycleView();
        initAdapter();
        initRefreshLayout();
        return view;
    }

    @Override
    protected StaffPresenter createPresenter() {
        return new StaffPresenter();
    }


    private void initData() {
        getPresenter().onGetCondition1();//获取列表检索条件
    }

    private void initView() {
        //工作类型
        jobTypeView = new ListView(getContext());
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
        hireStateView = new ListView(getContext());
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
        @SuppressLint("RestrictedApi") View contentView =  getLayoutInflater(new Bundle()).inflate(R.layout.include_recyclerview_refresh, null);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        swipeRefreshLayout = (SwipeRefreshLayout) contentView.findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerView);
        dropDownMenu.setDropDownMenu(Arrays.asList(tabTexts), popupViews, contentView);
    }

    /**
     * 初始化筛选结果 RecycleView
     */
    private void initRecycleView() {
        //设置刷新圈圈的颜色
        swipeRefreshLayout.setColorSchemeColors(Color.rgb(255, 179, 33));
        swipeRefreshLayout.setRefreshing(true);
        //设置分割线与垂直方向布局
        recyclerView.addItemDecoration(MyUtils.drawDivider(getContext(), LinearLayout.VERTICAL, R.drawable.divider_f0f0f0_14));
        MyLayoutManager manager = new MyLayoutManager(getContext(), LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
    }

    /**
     * 为 RecyclerView 加载 Adapter
     */
    private void initAdapter() {
        adapter = new StaffListAdapter(getContext());
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        });
        recyclerView.setAdapter(adapter);
        //子控件点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (MyUtils.isFastClick1()){
                    return;
                }

                StaffInfoData info = (StaffInfoData) adapter.getData().get(position);
                Intent intent = new Intent();
                if (info.getIs_pay().equals("0")&&info.getType().equals("0")){//未支付，前往托管工资界面
                    //传递 雇佣ID
                    intent.setClass(getActivity(), TrusteeshipSalaryActivity.class);
                    intent.putExtra(ConstantUtil.KEY_DATA,info.getHire_id());
                    intent.putExtra(ConstantUtil.KEY_POSITION,ConstantUtil.ACTIVITY_GYJL);
                    startActivity(intent);
                }else {//已支付
                    //雇佣状态 0:全部状态 1:合作中(雇佣成功) 2:请假中 3:暂停中 4:投诉中 5:被解雇 6:完结 7:退款 8.已取消 9:续约中. 10:待入职
                    switch (info.getType()){
                        case "1":
                        case "2":
                        case "3":
                        case "4":
                        case "5":
                        case "6":
                        case "7":
                        case "9"://传递 雇佣ID，前往我的员工界面
                            intent.setClass(getActivity(), StaffManageActivity.class);
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
        CustomDialog.Builder builder = new CustomDialog.Builder(getContext());
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
        adapter.setEnableLoadMore(false);
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
        jobTypeStrs.clear();
        hireStateStrs.clear();
        for (int i = 0; i < jobTypeBeans.size() ; i++) {
            jobTypeStrs.add(jobTypeBeans.get(i).getName());
        }
        for (int i = 0; i < hireStateBeans.size() ; i++) {
            hireStateStrs.add(hireStateBeans.get(i).getName());
        }
        jobTypeAdapter = new DropDownListAdapter(getContext(), jobTypeStrs);
        hireStateAdapter = new DropDownListAdapter(getContext(), hireStateStrs);
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
        setData(tData);
        llNothing.setVisibility(View.GONE);
        dropDownMenu.setVisibility(View.VISIBLE);
        recyclerView.setBackgroundResource(R.color.color_f0f0f0);
        if (isRefresh){//停止刷新
            adapter.setEnableLoadMore(true);
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
                toastStr="暂无任何员工！";
                if (getJobType().equals("0")&getHireState().equals("0")){
                    tvExperience.setText("暂无员工，在线体验办公");
                    llNothing.setVisibility(View.VISIBLE);
                    dropDownMenu.setVisibility(View.GONE);
                }else {
                    llNothing.setVisibility(View.GONE);
                    dropDownMenu.setVisibility(View.VISIBLE);
                    recyclerView.setBackgroundResource(R.drawable.img_nothing_hire);
                    ToastUtil.myToast(toastStr);
                }
            }else {
                recyclerView.setBackgroundResource(R.drawable.img_nothing_net);
                ToastUtil.myToast(toastStr);
            }
            adapter.setEnableLoadMore(true);
            swipeRefreshLayout.setRefreshing(false);
        }else {//如果是加载，没数据代表加载完毕
            if (result.equals("")){//没数据
                toastStr="已经到底了哦！";
                adapter.loadMoreEnd();
            }else {//请求出错
                adapter.loadMoreFail();
            }
            ToastUtil.myToast(toastStr);
        }
    }

    /**
     * 设置数据
     */
    private void setData(List data) {
        page++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            adapter.setNewData(data);
        } else {
            if (size > 0) {
                adapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            adapter.loadMoreEnd(isRefresh);
        } else {
            adapter.loadMoreComplete();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        jobTypeStrs.clear();
        hireStateStrs.clear();
        popupViews.clear();

        jobTypeId="0";//工作类型Id
        hireStateId="0";//雇佣状态Id

        if (customDialog!=null){
            customDialog.dismiss();
            customDialog=null;
        }
    }
}
