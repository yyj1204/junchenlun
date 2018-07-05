package com.wktx.www.emperor.ui.activity.main.artistcase;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.apiresult.login.AccountInfoData;
import com.wktx.www.emperor.apiresult.main.artistcase.WorksConditionBean;
import com.wktx.www.emperor.apiresult.main.artistcase.WorksConditionInfoData;
import com.wktx.www.emperor.apiresult.recruit.resume.WorksListInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.main.ArtistCasePresenter;
import com.wktx.www.emperor.ui.activity.recruit.resume.WorksDetailsActivity;
import com.wktx.www.emperor.ui.adapter.DropDownListAdapter;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.ui.adapter.main.ArtistCaseAdapter;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LoginUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.main.IArtistCaseView;
import com.wktx.www.emperor.utils.ToastUtil;
import com.wktx.www.emperor.widget.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yyj on 2018/3/30.
 * 首页---美工案例
 */
public class ArtistCaseActivity extends ABaseActivity<IArtistCaseView,ArtistCasePresenter> implements IArtistCaseView {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    //条件筛选适配器
    private DropDownListAdapter designAdapter;//设计类型
    private DropDownListAdapter categoryAdapter;//类目
    //RecyclerView 适配器
    private ArtistCaseAdapter adapter;

    private String tabTexts[] = {"设计类型", "擅长类目"};
    private List<WorksConditionBean> designBeans = new ArrayList<>();//设计类型集合
    private List<WorksConditionBean> categoryBeans = new ArrayList<>();//类目集合
    private List<String> designStrs = new ArrayList<>();//设计类型名称
    private List<String> categoryStrs = new ArrayList<>();//类目名称

    private String designId="0";//设计类型Id
    private String categoryId="0";//类目Id
    private String browseId="0";//最多浏览 0:否 1:是
    private String likedId="0";//最多喜欢 0:否 1:是
    private List<View> popupViews = new ArrayList<>();//多条件筛选的弹窗集合
    private ListView designView;
    private ListView categoryView;

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
        setContentView(R.layout.activity_artist_case);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_artist_case);
        initData();
        initView();
        initRecycleView();
        initAdapter();
        initRefreshLayout();
        refresh();
    }

    @Override
    protected ArtistCasePresenter createPresenter() {
        return new ArtistCasePresenter();
    }

    private void initData() {
        getPresenter().onGetCondition();//获取列表检索条件
    }

    private void initView() {
        //测试tabView扩展功能
        View screenView = LayoutInflater.from(this).inflate(R.layout.item_dropdown_screening, null);
        screenView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));

        //设计类型
        designView = new ListView(ArtistCaseActivity.this);
        designView.setDividerHeight(0);
        designView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (MyUtils.isFastClick1()){
                    return;
                }
                designId = designBeans.get(position).getId();
                designAdapter.setCheckItem(position);
                dropDownMenu.setTabText(designStrs.get(position));
                dropDownMenu.closeMenu();
                refresh();
            }
        });

        //擅长类目
        categoryView = new ListView(ArtistCaseActivity.this);
        categoryView.setDividerHeight(0);
        categoryView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (MyUtils.isFastClick1()){
                    return;
                }
                categoryId = categoryBeans.get(position).getId();
                categoryAdapter.setCheckItem(position);
                dropDownMenu.setTabText(categoryStrs.get(position));
                dropDownMenu.closeMenu();
                refresh();
            }
        });
        //添加
        popupViews.add(designView);
        popupViews.add(categoryView);
        //展示条件筛选结果的内容控件
        View contentView =  getLayoutInflater().inflate(R.layout.include_recyclerview_refresh, null);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        swipeRefreshLayout = (SwipeRefreshLayout) contentView.findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerView);
        dropDownMenu.setDropDownMenu(Arrays.asList(tabTexts), popupViews, contentView);
        //添加自定义的tabView(筛选)
        dropDownMenu.addTab(screenView, 2);
        screenView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyUtils.isFastClick1()){
                    return;
                }
                if (dropDownMenu.isActive()) {
                    dropDownMenu.closeMenu();
                }else {
                    //打开筛选界面（最多浏览、最多喜欢）
                    Intent intent = new Intent(ArtistCaseActivity.this, ScreeningArtistActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(ConstantUtil.KEY_DATA,browseId);//浏览ID
                    bundle.putString(ConstantUtil.KEY_INFO,likedId);//喜欢ID
                    intent.putExtras(bundle);
                    startActivityForResult(intent,ConstantUtil.REQUESTCODE_SCREENING);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                }
            }
        });
    }

    /**
     * 初始化 RecycleView
     */
    private void initRecycleView() {
        //设置刷新圈圈的颜色
        swipeRefreshLayout.setColorSchemeColors(Color.rgb(255, 179, 33));
        swipeRefreshLayout.setRefreshing(true);
        recyclerView.setPadding(20,10,20,10);
        //设置网格布局
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ArtistCaseActivity.this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    /**
     * 为 RecyclerView 加载 Adapter
     */
    private void initAdapter() {
        adapter = new ArtistCaseAdapter(ArtistCaseActivity.this);
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
                //将作品ID 传递给 WorksDetailsActivity
                WorksListInfoData info = (WorksListInfoData) adapter.getData().get(position);
                Intent intent = new Intent(ArtistCaseActivity.this, WorksDetailsActivity.class);
                intent.putExtra(ConstantUtil.KEY_POSITION,info.getId());
                intent.putExtra(ConstantUtil.KEY_ISOK,ConstantUtil.ACTIVITY_AL);
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
        getPresenter().onGetWorksList(page);
    }
    //加载更多
    private void loadMore() {
        isRefresh=false;
        getPresenter().onGetWorksList(page);
    }

    /**
     * IArtistCaseView
     */
    @Override
    public AccountInfoData getUserInfo() {
        AccountInfoData userInfo = LoginUtil.getinit().getUserInfo();
        return userInfo;
    }
    @Override
    public String getDesignTypeId() {
        return designId;
    }

    @Override
    public String getCategoryId() {
        return categoryId;
    }

    @Override
    public String getBrowseId() {
        return browseId;
    }

    @Override
    public String getLikedId() {
        return likedId;
    }

    @Override
    public void onGetConditionSuccessResult(WorksConditionInfoData result) {
        designBeans = result.getDesign_pattern();
        categoryBeans = result.getBgat();
        for (int i = 0; i < designBeans.size() ; i++) {
            designStrs.add(designBeans.get(i).getName());
        }
        for (int i = 0; i < categoryBeans.size() ; i++) {
            categoryStrs.add(categoryBeans.get(i).getName());
        }
        designAdapter = new DropDownListAdapter(ArtistCaseActivity.this, designStrs);
        categoryAdapter = new DropDownListAdapter(ArtistCaseActivity.this, categoryStrs);
        designView.setAdapter(designAdapter);
        categoryView.setAdapter(categoryAdapter);
    }

    @Override
    public void onGetConditionFailureResult(String result) {
        ToastUtil.myToast(result);
    }
    @Override
    public void onRequestSuccess(List<WorksListInfoData> tData) {
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
                toastStr="暂无任何作品！";
                recyclerView.setBackgroundResource(R.drawable.img_nothing);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case ConstantUtil.REQUESTCODE_SCREENING:
                    if (resultCode==ConstantUtil.RESULTCODE_SCREENING){
                        //接收排序筛选界面选中的值
                        String[] screeningIdStr = data.getStringArrayExtra(ConstantUtil.KEY_POSITION);
                        browseId = screeningIdStr[0];
                        likedId = screeningIdStr[1];
                        refresh();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancleMyToast();
    }
}
