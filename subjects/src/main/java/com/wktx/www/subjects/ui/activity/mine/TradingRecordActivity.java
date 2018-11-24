package com.wktx.www.subjects.ui.activity.mine;

import android.content.Intent;
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
import com.wktx.www.subjects.apiresult.mine.tradingrecord.TradingBean;
import com.wktx.www.subjects.apiresult.mine.tradingrecord.TradingRecordInfoData;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.presenter.mine.TradingRecordPresenter;
import com.wktx.www.subjects.ui.activity.manage.ManageActivity;
import com.wktx.www.subjects.ui.activity.message.InviteDetailsActivity;
import com.wktx.www.subjects.ui.view.IView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.DateUtil;
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
public class TradingRecordActivity extends ABaseActivity<IView,TradingRecordPresenter> implements IView<TradingRecordInfoData> {
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

    private BaseQuickAdapter<TradingBean, BaseViewHolder> mAdapter;

    private String type="transa";//transa:已收工资 hire:托管

    private int page = 1;
    private static final int PAGE_SIZE = 10;//请求每页的数据量
    private boolean isRefresh;//是下拉刷新还是加载更多  false：加载更多，true：下拉刷新

    @OnClick({R.id.tb_IvReturn,R.id.linear_balance,R.id.linear_trusteeship})
    public void MyOnclick(View view) {
        switch (view.getId()){
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.linear_balance://已收工资
                type="transa";
                tvBalanceLine.setVisibility(View.VISIBLE);
                tvTrusteeshipLine.setVisibility(View.INVISIBLE);
                refresh();
                break;
            case R.id.linear_trusteeship://托管中
                type="hire";
                tvBalanceLine.setVisibility(View.INVISIBLE);
                tvTrusteeshipLine.setVisibility(View.VISIBLE);
                refresh();
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
        mAdapter = new BaseQuickAdapter<TradingBean, BaseViewHolder>(R.layout.item_rv_tradingrecord, null) {
            @Override
            protected void convert(BaseViewHolder helper, TradingBean item) {
                helper.setText(R.id.tv_time, DateUtil.getTimestamp2CustomType(item.getAdd_time(),"yyyy.MM.dd hh:mm:ss"));
                helper.setText(R.id.tv_companyName, "雇主:"+item.getName()+",工作类型:"+item.getTow());
                //交易内容
                if (TextUtils.isEmpty(item.getRemark())){
                    helper.setText(R.id.tv_remark,"无");
                }else {
                    helper.setText(R.id.tv_remark,item.getRemark());
                }
                //判断金额格式，小数点后为"00"，直接取整
                String money = item.getAmount();
                String[] mSplit = money.split("[.]");
                String moneyStr="";
                if (mSplit[1].equals("00")){
                    moneyStr=mSplit[0];
                }else {
                    moneyStr=money;
                }
                helper.setText(R.id.tv_money,moneyStr+"元");
            }
        };
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
                TradingBean tradingBean = mAdapter.getData().get(position);
                //雇佣状态 0:全部状态 1:合作中(雇佣成功) 2:请假中 3:暂停中 4:投诉中 5:被解雇 6:完结 7:退款 8:已取消 9:续约中 10:待入职
                switch (tradingBean.getStatus()) {
                    case "1":
                    case "2":
                    case "3":
                    case "4":
                    case "5":
                    case "6":
                    case "7":
                    case "9"://传递 雇佣ID，前往工作管理界面
                        Intent intent = new Intent(TradingRecordActivity.this, ManageActivity.class);
                        intent.putExtra(ConstantUtil.KEY_DATA, tradingBean.getHire_id());
                        startActivity(intent);
                        break;
                    case "8":
                        ToastUtil.myToast("该订单已取消！");
                        break;
                    case "10"://如果雇佣状态待入职 打开邀请详情界面
                        Intent intent1 = new Intent(TradingRecordActivity.this, InviteDetailsActivity.class);
                        intent1.putExtra(ConstantUtil.KEY_DATA, tradingBean.getHire_id());
                        startActivity(intent1);
                        break;
                    default:
                        break;
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
        getPresenter().getTradingRecord(page,type);
    }
    //加载更多
    private void loadMore() {
        isRefresh=false;
        getPresenter().getTradingRecord(page,type);
    }


    /**
     * IView
     */
    @Override
    public void onRequestSuccess(TradingRecordInfoData tData) {
        if (TextUtils.isEmpty(tData.getTransa_amount())){
            tvBalanceMoney.setText("0");
        }else {
            tvBalanceMoney.setText(tData.getTransa_amount());
        }
        if (TextUtils.isEmpty(tData.getTrusteeship_amount())){
            tvTrusteeshipMoney.setText("0");
        }else {
            tvTrusteeshipMoney.setText(tData.getTrusteeship_amount());
        }
        setData(tData.getList());
        if (isRefresh){//刷新
            //如果是刷新，没数据代表全部没数据
            if (tData.getList()==null||tData.getList().size()==0){
                ToastUtil.myToast("暂无任何交易记录！");
                recyclerView.setBackgroundResource(R.drawable.img_nothing_transaction);
            }else {
                recyclerView.setBackgroundResource(R.color.color_f0f0f0);
            }
            mAdapter.setEnableLoadMore(true);
            swipeRefreshLayout.setRefreshing(false);
        }else {//如果是加载，没数据代表加载完毕
            if (tData.getList()==null||tData.getList().size()==0){
                mAdapter.loadMoreEnd();
                ToastUtil.myToast("已经到底了哦！");
            }
        }
    }

    @Override
    public void onRequestFailure(String result) {
        ToastUtil.myToast(result);
        if (isRefresh){//如果是刷新，请求出错代表全部没数据
            setData(null);
            recyclerView.setBackgroundResource(R.drawable.img_nothing_net);
            mAdapter.setEnableLoadMore(true);
            swipeRefreshLayout.setRefreshing(false);
        }else {
            mAdapter.loadMoreFail();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancleMyToast();
    }
}
