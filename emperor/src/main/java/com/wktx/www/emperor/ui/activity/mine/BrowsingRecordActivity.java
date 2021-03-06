package com.wktx.www.emperor.ui.activity.mine;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.mine.browsingrecord.BrowsingRecordInfoData;
import com.wktx.www.emperor.apiresult.mine.condition.ConditionBean;
import com.wktx.www.emperor.apiresult.mine.condition.ConditionInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.mine.BrowsingRecordPresenter;
import com.wktx.www.emperor.ui.activity.recruit.resume.ResumeActivity;
import com.wktx.www.emperor.ui.adapter.DropDownListAdapter;
import com.wktx.www.emperor.ui.adapter.mine.BrowsingListAdapter;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.mine.IMyCollectView;
import com.wktx.www.emperor.utils.ToastUtil;
import com.wktx.www.emperor.widget.DropDownMenu;
import com.wktx.www.emperor.widget.MyLayoutManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 个人中心---浏览记录
 */
public class BrowsingRecordActivity extends ABaseActivity<IMyCollectView,BrowsingRecordPresenter> implements IMyCollectView {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private DropDownListAdapter jobTypeAdapter;//条件筛选适配器
    private BrowsingListAdapter adapter;//RecyclerView 适配器

    private String[] tabTexts = {"工作类型"};
    private List<ConditionBean> jobTypeBeans = new ArrayList<>();//工作类型集合
    private List<String> jobTypeStrs = new ArrayList<>();//工作类型名称
    private String jobTypeId="0";//工作类型 0:全部岗位 1:美工 2:客服 3:运营
    private List<View> popupViews = new ArrayList<>();//多条件筛选的弹窗集合
    private ListView jobTypeView;

    private int page = 1;
    private static final int PAGE_SIZE = 10;//请求每页的数据量
    private boolean isRefresh;//是下拉刷新还是加载更多  false：加载更多，true：下拉刷新


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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browsing_record);
        ButterKnife.bind(this);
        //设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_browsing_record);
        initData();
        initView();
        initRecycleView();
        initAdapter();
        initRefreshLayout();
        refresh();
    }

    @Override
    protected BrowsingRecordPresenter createPresenter() {
        return new BrowsingRecordPresenter();
    }

    private void initData() {
        getPresenter().onGetCondition();//获取列表检索条件
    }

    private void initView() {
        //工作类型
        jobTypeView = new ListView(BrowsingRecordActivity.this);
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
        //添加
        popupViews.add(jobTypeView);
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
        recyclerView.addItemDecoration(MyUtils.drawDivider(BrowsingRecordActivity.this, LinearLayout.VERTICAL, R.drawable.divider_f0f0f0_14));
        MyLayoutManager manager = new MyLayoutManager(BrowsingRecordActivity.this, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
    }

    /**
     * 为 RecyclerView 加载 Adapter
     */
    private void initAdapter() {
        adapter = new BrowsingListAdapter(BrowsingRecordActivity.this);
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
                //将简历ID 传递给 ResumeActivity
                BrowsingRecordInfoData info = (BrowsingRecordInfoData) adapter.getData().get(position);
                Intent intent = new Intent(BrowsingRecordActivity.this, ResumeActivity.class);
                intent.putExtra(ConstantUtil.KEY_POSITION,info.getRid());
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
        page = 1;
        isRefresh=true;
        //这里的作用是防止下拉刷新的时候还可以上拉加载
        adapter.setEnableLoadMore(false);
        getPresenter().onGetBrowsingRecord(page);
    }
    //加载更多
    private void loadMore() {
        isRefresh=false;
        getPresenter().onGetBrowsingRecord(page);
    }

    /**
     * IBrowsingRecordView
     */
    @Override
    public String getJobType() {
        return jobTypeId;
    }
    @Override
    public void onGetConditionSuccessResult(ConditionInfoData result) {
        jobTypeBeans = result.getTow();
        for (int i = 0; i < jobTypeBeans.size() ; i++) {
            jobTypeStrs.add(jobTypeBeans.get(i).getName());
        }
        jobTypeAdapter = new DropDownListAdapter(BrowsingRecordActivity.this, jobTypeStrs);
        jobTypeView.setAdapter(jobTypeAdapter);
    }
    @Override
    public void onGetConditionFailureResult(String result) {
        ToastUtil.myToast(result);
    }
    @Override
    public void onCancelCollectResumeResult(boolean isSuccess, String result) {
    }
    @Override
    public void onRequestSuccess(List<BrowsingRecordInfoData> tData) {
        recyclerView.setBackgroundResource(R.color.color_f0f0f0);
        setData(tData);
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
                toastStr="暂无任何浏览记录！";
                recyclerView.setBackgroundResource(R.drawable.img_nothing_browsing);
            }else {
                recyclerView.setBackgroundResource(R.drawable.img_nothing_net);
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
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancleMyToast();
    }
}
