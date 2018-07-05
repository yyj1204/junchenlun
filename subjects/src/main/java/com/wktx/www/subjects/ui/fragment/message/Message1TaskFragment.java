package com.wktx.www.subjects.ui.fragment.message;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.login.AccountInfoData;
import com.wktx.www.subjects.apiresult.message.TaskListInfoData;
import com.wktx.www.subjects.basemvp.ALazyLoadBaseFragment;
import com.wktx.www.subjects.presenter.message.TaskPresenter;
import com.wktx.www.subjects.ui.activity.manage.ReportDetailsActivity;
import com.wktx.www.subjects.ui.activity.manage.ReportReleaseActivity;
import com.wktx.www.subjects.ui.adapter.message.TaskAdapter;
import com.wktx.www.subjects.ui.view.IView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.DateUtil;
import com.wktx.www.subjects.utils.LoginUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.widget.MyLayoutManager;
import com.wktx.www.subjects.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 消息片段---工作---任务消息
 */
public class Message1TaskFragment  extends ALazyLoadBaseFragment<IView,TaskPresenter> implements IView<List<TaskListInfoData>> {
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private TaskAdapter mAdapter;//RecyclerView 适配器

    private int page = 1;
    private static final int PAGE_SIZE = 10;//请求每页的数据量
    private boolean isRefresh;//是下拉刷新还是加载更多  ：下拉刷新

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
            if (!isRefresh){
                refresh();
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
        refresh();
    }


    public Message1TaskFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_common, container, false);
        ButterKnife.bind(this, view);
        initRecycleView();
        initAdapter();
        initRefreshLayout();
        return view;
    }

    @Override
    protected TaskPresenter createPresenter() {
        return new TaskPresenter();
    }

    /**
     * 初始化 RecycleView
     */
    private void initRecycleView() {
        //设置刷新圈圈的颜色
        swipeRefreshLayout.setColorSchemeColors(Color.rgb(255, 179, 33));
        swipeRefreshLayout.setRefreshing(true);
        //设置分割线与垂直方向布局
        recyclerView.addItemDecoration(MyUtils.drawDivider(getActivity(), LinearLayout.VERTICAL, R.drawable.divider_f0f0f0_14));
        MyLayoutManager myLayoutManager = new MyLayoutManager(getActivity(), LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(myLayoutManager);
    }

    /**
     * 为 RecyclerView 加载 Adapter
     */
    private void initAdapter() {
        mAdapter = new TaskAdapter(getContext());
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        });
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (MyUtils.isFastClick1()){
                    return;
                }

                //如果解雇id为0,正常执行
                if (mAdapter.getData().get(position).getDismissal_id().equals("0")){
                    //截止日期跟当前日期比较
                    String endTime = mAdapter.getData().get(position).getEnd_time();
                    String currentTime = DateUtil.getCurrentDateNYRSFM2();
                    boolean isBigger = DateUtil.compareDate2Bigger3(endTime, currentTime, "yyyy-MM-dd HH:mm:ss");
                    if (isBigger){//如果isBigger=true，说明截止日期未到
                        String reportId = mAdapter.getData().get(position).getReport_id();
                        Intent intent = new Intent();
                        if (reportId==null||reportId.equals("0")){//打开报告提交界面
                            intent.putExtra(ConstantUtil.KEY_DATA,mAdapter.getData().get(position));
                            intent.setClass(getActivity(), ReportReleaseActivity.class);
                        }else {//打开报告详情界面
                            intent.putExtra(ConstantUtil.KEY_DATA,reportId);
                            intent.setClass(getActivity(), ReportDetailsActivity.class);
                        }
                        startActivity(intent);
                    }else {
                        MyUtils.showToast(getActivity(),"当前任务截止时间已到！");
                    }
                }else {//如果解雇id不为0，说明被解雇或者解雇中
                    if (mAdapter.getData().get(position).getStatus().equals("5")){
                        MyUtils.showToast(getActivity(),"您已被解雇,请到管理查看详情！");
                    }else {
                        MyUtils.showToast(getActivity(),"您被解雇中,请到管理查看详情！");
                    }
                    //定位到 ManageFragment
//                    MainActivity activity = (MainActivity) getActivity();
//                    activity.setCurrenPager(1);
                }
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
        page = 1;
        isRefresh=true;
        //这里的作用是防止下拉刷新的时候还可以上拉加载
        mAdapter.setEnableLoadMore(false);
        getPresenter().getInfo(page);
    }

    //加载更多
    private void loadMore() {
        isRefresh=false;
        getPresenter().getInfo(page);
    }


    /**
     * IView
     */
    @Override
    public AccountInfoData getUserInfo() {
        AccountInfoData userInfo = LoginUtil.getinit().getUserInfo();
        return userInfo;
    }
    @Override//获取任务消息回调
    public void onRequestSuccess(List<TaskListInfoData> tData) {
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
//                toastStr="暂无任何任务消息！";
                recyclerView.setBackgroundResource(R.drawable.img_nothing);
            }else {
                recyclerView.setBackgroundResource(R.drawable.img_nothing_net);
                 ToastUtil.myToast(toastStr);
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
}
