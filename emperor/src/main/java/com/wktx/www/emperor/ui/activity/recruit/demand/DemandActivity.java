package com.wktx.www.emperor.ui.activity.recruit.demand;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.recruit.demand.DemandListInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.recruit.demand.DemandPresenter;
import com.wktx.www.emperor.ui.adapter.recruit.DemandListAdapter;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.IView;
import com.wktx.www.emperor.utils.ToastUtil;
import com.wktx.www.emperor.widget.MyLayoutManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 招聘---需求列表
 */
public class DemandActivity extends ABaseActivity<IView,DemandPresenter> implements IView<List<DemandListInfoData>> {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private DemandListAdapter mAdapter;//RecyclerView 适配器

    private int page = 1;
    private static final int PAGE_SIZE = 10;//请求每页的数据量
    private boolean isRefresh;//是下拉刷新还是加载更多  false：加载更多，true：下拉刷新

    @OnClick({R.id.tb_IvReturn})
    public void MyOnclick(View view) {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demand);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_demand_list);

        initRecycleView();
        initAdapter();
        initHeadView();
        initRefreshLayout();
    }

    @Override
    protected DemandPresenter createPresenter() {
        return new DemandPresenter();
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
        mAdapter = new DemandListAdapter();
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
                //打开需求详情界面
                Intent intent = new Intent(DemandActivity.this, DemandDetailsActivity.class);
                intent.putExtra(ConstantUtil.KEY_POSITION,mAdapter.getData().get(position).getId());
                startActivity(intent);
            }
        });
    }

    /**
     * 为 RecyclerView 添加头部控件（发布需求）
     */
    private void initHeadView() {
        View hvDemandRelease = getLayoutInflater().inflate(R.layout.item_hv_demand, (ViewGroup) recyclerView.getParent(), false);
        mAdapter.addHeaderView(hvDemandRelease);
        hvDemandRelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyUtils.isFastClick1()){
                    return;
                }
                //打开发布需求界面
                startActivity(new Intent(DemandActivity.this, DemandReleaseActivity.class));
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
        getPresenter().onGetDemandList(page);
    }
    //加载更多
    private void loadMore() {
        isRefresh=false;
        getPresenter().onGetDemandList(page);
    }

    /**
     * IView
     */
    @Override
    public void onRequestSuccess(List<DemandListInfoData> tData) {
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
                toastStr="暂无任何需求！";
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
