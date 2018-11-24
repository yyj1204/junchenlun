package com.wktx.www.subjects.ui.activity.mine;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.apiresult.main.condition.ConditionBean;
import com.wktx.www.subjects.apiresult.main.condition.ConditionInfoData;
import com.wktx.www.subjects.apiresult.main.demand.DemandListInfoData;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.presenter.mine.InterviewRecordPresenter;
import com.wktx.www.subjects.ui.activity.main.PositionDetailsActivity;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.ui.adapter.DropDownListAdapter;
import com.wktx.www.subjects.ui.adapter.mine.InterviewRecordAdapter;
import com.wktx.www.subjects.ui.view.mine.IMyCollectView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.widget.DropDownMenu;
import com.wktx.www.subjects.widget.MyLayoutManager;
import com.wktx.www.subjects.utils.ToastUtil;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人中心-面试记录
 */
public class InterviewRecordActivity extends ABaseActivity<IMyCollectView,InterviewRecordPresenter> implements IMyCollectView {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SwipeMenuRecyclerView recyclerView;

    private DropDownListAdapter jobTypeAdapter;//条件筛选适配器
    private InterviewRecordAdapter mAdapter;//RecyclerView 适配器

    private String tabTexts[] = {"工作类型"};
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
        setContentView(R.layout.activity_interview_record);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_interview_record);
        initView();
        initRecycleView();
        initAdapter();
        initRefreshLayout();
        initData();
    }

    @Override
    protected InterviewRecordPresenter createPresenter() {
        return new InterviewRecordPresenter();
    }

    private void initData() {
        getPresenter().getConditionInfo();//获取列表检索条件
        refresh();
    }

    private void initView() {
        //工作类型
        jobTypeView = new ListView(InterviewRecordActivity.this);
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
        View contentView =  getLayoutInflater().inflate(R.layout.include_swiperecyclerview_refresh, null);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        swipeRefreshLayout = (SwipeRefreshLayout) contentView.findViewById(R.id.swipeRefreshLayout);
        recyclerView = (SwipeMenuRecyclerView) contentView.findViewById(R.id.recyclerView);
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
        recyclerView.addItemDecoration(MyUtils.drawDivider(InterviewRecordActivity.this, LinearLayout.VERTICAL, R.drawable.divider_f0f0f0_14));
        MyLayoutManager manager = new MyLayoutManager(InterviewRecordActivity.this, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
    }

    /**
     * 为 RecyclerView 加载 Adapter
     */
    private void initAdapter() {
        mAdapter = new InterviewRecordAdapter(InterviewRecordActivity.this);
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
                //打开招聘详情界面（传递招聘需求id）
                Intent intent = new Intent(InterviewRecordActivity.this, PositionDetailsActivity.class);
                intent.putExtra(ConstantUtil.KEY_DATA,mAdapter.getData().get(position).getDemand_id());
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
               initData();
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
     * IMyCollectView
     */
    @Override
    public String getJobType() {
        return jobTypeId;
    }
    @Override
    public void onGetConditionSuccessResult(ConditionInfoData result) {
        ArrayList<ConditionBean> positionList = result.getTowList();
        positionList.add(0,new ConditionBean("0","全部"));
        jobTypeBeans = positionList;
        jobTypeStrs.clear();
        for (int i = 0; i < jobTypeBeans.size() ; i++) {
            jobTypeStrs.add(jobTypeBeans.get(i).getName());
        }
        jobTypeAdapter = new DropDownListAdapter(InterviewRecordActivity.this, jobTypeStrs);
        jobTypeView.setAdapter(jobTypeAdapter);
    }
    @Override
    public void onGetConditionFailureResult(String result) {
        ToastUtil.myToast(result);
    }
    @Override//收藏界面用的
    public void onCancelCollectResult(boolean isSuccess, String result) {
    }
    @Override
    public void onRequestSuccess(List<DemandListInfoData> tData) {
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
                toastStr="暂无任何面试记录！";
                recyclerView.setBackgroundResource(R.drawable.img_nothing);
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
