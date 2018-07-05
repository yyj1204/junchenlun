package com.wktx.www.emperor.ui.activity.mine;

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
import com.wktx.www.emperor.apiresult.login.AccountInfoData;
import com.wktx.www.emperor.apiresult.mine.browsingrecord.BrowsingRecordInfoData;
import com.wktx.www.emperor.apiresult.mine.condition.ConditionBean;
import com.wktx.www.emperor.apiresult.mine.condition.ConditionInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.mine.MyCollectPresenter;
import com.wktx.www.emperor.ui.activity.recruit.resume.ResumeActivity;
import com.wktx.www.emperor.ui.adapter.DropDownListAdapter;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.ui.adapter.mine.CollectListAdapter;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LoginUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.mine.IMyCollectView;
import com.wktx.www.emperor.utils.ToastUtil;
import com.wktx.www.emperor.widget.DropDownMenu;
import com.wktx.www.emperor.widget.MyLayoutManager;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人中心-我的收藏
 */
public class MyCollectActivity extends ABaseActivity<IMyCollectView,MyCollectPresenter> implements IMyCollectView {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SwipeMenuRecyclerView recyclerView;

    private DropDownListAdapter jobTypeAdapter;//条件筛选适配器
    private CollectListAdapter adapter;//RecyclerView 适配器

    private String tabTexts[] = {"工作类型"};
    private List<ConditionBean> jobTypeBeans = new ArrayList<>();//工作类型集合
    private List<String> jobTypeStrs = new ArrayList<>();//工作类型名称
    private String jobTypeId="0";//工作类型 0:全部岗位 1:美工 2:客服 3:运营
    private List<View> popupViews = new ArrayList<>();//多条件筛选的弹窗集合
    private ListView jobTypeView;

    private int page = 1;
    private static final int PAGE_SIZE = 10;//请求每页的数据量
    private boolean isRefresh;//是下拉刷新还是加载更多  false：加载更多，true：下拉刷新
    private int adapterPosition;//删除的position

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
        setContentView(R.layout.activity_my_collect);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_my_collect);
        initData();
        initView();
        initRecycleView();
        initAdapter();
        initRefreshLayout();
    }


    @Override
    protected MyCollectPresenter createPresenter() {
        return new MyCollectPresenter();
    }

    private void initData() {
        getPresenter().onGetCondition();//获取列表检索条件
    }

    private void initView() {
        //工作类型
        jobTypeView = new ListView(MyCollectActivity.this);
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
        recyclerView.addItemDecoration(MyUtils.drawDivider(MyCollectActivity.this, LinearLayout.VERTICAL, R.drawable.divider_f0f0f0_14));
        MyLayoutManager manager = new MyLayoutManager(MyCollectActivity.this, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        //侧滑删除
        recyclerView.setSwipeMenuCreator(swipeMenuCreator);
        recyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);
    }

    /**
     * 为 RecyclerView 加载 Adapter
     */
    private void initAdapter() {
        adapter = new CollectListAdapter(MyCollectActivity.this);
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
                Intent intent = new Intent(MyCollectActivity.this, ResumeActivity.class);
                intent.putExtra(ConstantUtil.KEY_POSITION,info.getRid());
                startActivity(intent);
            }
        });
    }

    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.px_120);

            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(MyCollectActivity.this)
                        .setBackground(R.color.color_ffb321)
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。
            }
        }
    };

    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            adapterPosition = menuBridge.getAdapterPosition();// RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {//右侧菜单
                if (MyUtils.isFastClick()){
                    return;
                }
                //取消收藏请求
                getPresenter().onCancelCollectResume(adapter.getData().get(adapterPosition).getRid());
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                //左侧菜单
            }
        }
    };

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
        getPresenter().onGetCollectList(page);
    }
    //加载更多
    private void loadMore() {
        isRefresh=false;
        getPresenter().onGetCollectList(page);
    }

    /**
     * IBrowsingRecordView
     */
    @Override
    public AccountInfoData getUserInfo() {
        AccountInfoData userInfo = LoginUtil.getinit().getUserInfo();
        return userInfo;
    }
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
        jobTypeAdapter = new DropDownListAdapter(MyCollectActivity.this, jobTypeStrs);
        jobTypeView.setAdapter(jobTypeAdapter);
    }
    @Override
    public void onGetConditionFailureResult(String result) {
        ToastUtil.myToast(result);
    }
    @Override//取消收藏
    public void onCancelCollectResumeResult(boolean isSuccess, String result) {
        ToastUtil.myToast(result);
        if (isSuccess){
            adapter.remove(adapterPosition);
        }
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
                toastStr="暂无任何收藏！";
                recyclerView.setBackgroundResource(R.drawable.img_nothing_collect);
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
