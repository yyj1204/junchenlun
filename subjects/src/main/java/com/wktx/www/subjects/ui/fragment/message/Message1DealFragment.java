package com.wktx.www.subjects.ui.fragment.message;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.login.AccountInfoData;
import com.wktx.www.subjects.apiresult.message.DealListInfoData;
import com.wktx.www.subjects.basemvp.ALazyLoadBaseFragment;
import com.wktx.www.subjects.presenter.message.DealPresenter;
import com.wktx.www.subjects.ui.view.IView;
import com.wktx.www.subjects.utils.LoginUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.widget.MyLayoutManager;
import com.wktx.www.subjects.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 消息片段---工作---交易消息
 */
public class Message1DealFragment extends ALazyLoadBaseFragment<IView,DealPresenter> implements IView<List<DealListInfoData>> {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private BaseQuickAdapter<DealListInfoData, BaseViewHolder> mAdapter;//RecyclerView 适配器

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


    public Message1DealFragment() {
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
    protected DealPresenter createPresenter() {
        return new DealPresenter();
    }


    /**
     * 初始化 RecycleView
     */
    private void initRecycleView() {
        //设置刷新圈圈的颜色
        swipeRefreshLayout.setColorSchemeColors(Color.rgb(255, 179, 33));
        swipeRefreshLayout.setRefreshing(true);
        //设置分割线与垂直方向布局
        recyclerView.addItemDecoration(MyUtils.drawDivider(getActivity(), LinearLayout.VERTICAL, R.drawable.divider_f0f0f0_2));
        MyLayoutManager myLayoutManager = new MyLayoutManager(getActivity(), LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(myLayoutManager);
    }

    /**
     * 为 RecyclerView 加载 Adapter
     */
    private void initAdapter() {
        mAdapter = new BaseQuickAdapter<DealListInfoData, BaseViewHolder>(R.layout.item_rv_tradingrecord, null) {
            @Override
            protected void convert(BaseViewHolder helper, DealListInfoData item) {
                if (item.getRemark()==null||item.getRemark().equals("")){
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
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        });
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
    @Override
    public void onRequestSuccess(List<DealListInfoData> tData) {
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
//                toastStr="暂无任何交易消息！";
                recyclerView.setBackgroundResource(R.drawable.img_nothing_transaction);
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
