package com.wktx.www.subjects.ui.activity.mine;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.mine.tradingrecord.TradingRecordInfoData;
import com.wktx.www.subjects.apiresult.mine.tradingrecord.TradingBalanceInfoData;
import com.wktx.www.subjects.apiresult.login.AccountInfoData;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.presenter.mine.TradingRecordPresenter;
import com.wktx.www.subjects.ui.view.mine.ITradingRecordView;
import com.wktx.www.subjects.utils.LoginUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.widget.MyLayoutManager;
import com.wktx.www.subjects.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人中心---交易记录
 */
public class TradingRecordActivity extends ABaseActivity<ITradingRecordView,TradingRecordPresenter> implements ITradingRecordView {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.tv_balanceMoney)
    TextView tvBalanceMoney;
    @BindView(R.id.tv_balanceLine)
    TextView tvBalanceLine;
    @BindView(R.id.tv_trusteeshipMoney)
    TextView tvTrusteeshipMoney;
    @BindView(R.id.tv_trusteeshipLine)
    TextView tvTrusteeshipLine;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    private BaseQuickAdapter<TradingRecordInfoData, BaseViewHolder> mAdapter;

    private int page = 1;
    private static final int PAGE_SIZE = 10;//请求每页的数据量
    private boolean isRefresh;//是下拉刷新还是加载更多  false：加载更多，true：下拉刷新

    @OnClick({R.id.tb_IvReturn})
    public void MyOnclick(View view) {
        switch (view.getId()){
            case R.id.tb_IvReturn:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trading_record);
        // 设置右滑动返回
        Slidr.attach(this);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.title_trading_record);
        initRecycleView();
        initAdapter();
        initRefreshLayout();
        refresh();
    }

    @Override
    protected TradingRecordPresenter createPresenter() {
        return new TradingRecordPresenter();
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
        mAdapter = new BaseQuickAdapter<TradingRecordInfoData, BaseViewHolder>(R.layout.item_rv_tradingrecord, null) {
            @Override
            protected void convert(BaseViewHolder helper, TradingRecordInfoData item) {
                if (item.getRemark()==null||item.getRemark().equals("")){
                    helper.setText(R.id.tv_remark,"无备注");
                }else {
                    helper.setText(R.id.tv_remark,item.getRemark());
                }
                helper.setText(R.id.tv_time, item.getTime());
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
        getPresenter().getTradingRecord(page);
        getPresenter().getInfo();
    }
    //加载更多
    private void loadMore() {
        isRefresh=false;
        getPresenter().getTradingRecord(page);
    }


    /**
     * ITradingRecordView
     */
    @Override
    public AccountInfoData getUserInfo() {
        AccountInfoData userInfo = LoginUtil.getinit().getUserInfo();
        return userInfo;
    }
    @Override
    public void onGetBalanceSuccessResult(TradingBalanceInfoData result) {
        tvBalanceMoney.setText(result.getBalance());
        tvTrusteeshipMoney.setText(result.getTrusteeship());
    }
    @Override
    public void onGetBalanceFailureResult(String result) {
        tvBalanceMoney.setText("0");
        tvTrusteeshipMoney.setText("0");
         ToastUtil.myToast(result);
    }
    @Override
    public void onRequestSuccess(List<TradingRecordInfoData> tData) {
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
                toastStr="暂无任何交易记录！";
                recyclerView.setBackgroundResource(R.drawable.img_nothing_transaction);
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
        ToastUtil.cancleMyToast();
    }
}
