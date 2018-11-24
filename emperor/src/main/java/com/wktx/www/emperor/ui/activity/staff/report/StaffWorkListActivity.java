package com.wktx.www.emperor.ui.activity.staff.report;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
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
import com.wktx.www.emperor.apiresult.mine.store.StoreListInfoData;
import com.wktx.www.emperor.apiresult.staff.report.StaffWorkListInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.staff.report.StaffWorkListPresenter;
import com.wktx.www.emperor.ui.activity.staff.StaffArrangeWorkActivity;
import com.wktx.www.emperor.ui.adapter.DropDownListAdapter;
import com.wktx.www.emperor.ui.adapter.staff.StaffWorkListAdapter;
import com.wktx.www.emperor.ui.view.staff.report.IStaffWorkListView;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.MyUtils;
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
 * 管理我的员工---工作报告---工作安排列表
 */
public class StaffWorkListActivity extends ABaseActivity<IStaffWorkListView,StaffWorkListPresenter> implements IStaffWorkListView {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private String hireId;//雇佣id
    private boolean isAdd;//true：安排工作按钮，false：工作报告、查看报告按钮
    private String storeId="0";//店铺Id

    private StaffWorkListAdapter mAdapter;
    private DropDownListAdapter storeListAdapter;//条件筛选适配器

    private String[] tabTexts = {"店铺选择"};
    private List<StoreListInfoData> storeListBeans = new ArrayList<>();//店铺集合

    private List<String> storeListStrs = new ArrayList<>();//店铺名称
    private List<View> popupViews = new ArrayList<>();//多条件筛选的弹窗集合
    private ListView storeListView;
    private View hvView;//recyclerView 头部控件

    @OnClick({R.id.tb_IvReturn})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
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
        setContentView(R.layout.activity_staff_worklist);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_staff_worklist);
        initData();
        initView();
        initRecycleView();
        initAdapter();
        initHeadView();
        initRefreshLayout();
    }

    @Override
    protected StaffWorkListPresenter createPresenter() {
        return new StaffWorkListPresenter();
    }

    /**
     * 接收 StaffManageActivity StaffAttendanceActivity传递过来的雇佣id,
     */
    private void initData() {
        hireId = getIntent().getStringExtra(ConstantUtil.KEY_POSITION);
        isAdd = getIntent().getBooleanExtra(ConstantUtil.KEY_ISOK,false);
        getPresenter().onGetCondition();//获取列表检索条件
    }

    private void initView() {
        //店铺列表
        storeListView = new ListView(StaffWorkListActivity.this);
        storeListView.setDividerHeight(0);
        storeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (MyUtils.isFastClick1()){
                    return;
                }

                if (position==0){
                    storeId="0";
                }else {
                    storeId = storeListBeans.get(position-1).getId();
                }
                storeListAdapter.setCheckItem(position);
                dropDownMenu.setTabText(storeListStrs.get(position));
                dropDownMenu.closeMenu();
                refresh();
            }
        });
        //添加
        popupViews.add(storeListView);
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
        recyclerView.addItemDecoration(MyUtils.drawDivider(this, LinearLayout.VERTICAL, R.drawable.divider_f0f0f0_14));
        MyLayoutManager myLayoutManager = new MyLayoutManager(this, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(myLayoutManager);
    }


    /**
     * 为 RecyclerView 加载 Adapter
     */
    private void initAdapter() {
        mAdapter = new StaffWorkListAdapter(StaffWorkListActivity.this,hireId);
        recyclerView.setAdapter(mAdapter);
        //子控件点击事件
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (MyUtils.isFastClick1()){
                    return;
                }
                //所有报告(雇佣id，工作安排id，评价类型"") --- StaffReportListActivity
                Intent intent = new Intent(StaffWorkListActivity.this, StaffReportListActivity.class);
                intent.putExtra(ConstantUtil.KEY_POSITION,hireId);
                intent.putExtra(ConstantUtil.KEY_DATA,mAdapter.getData().get(position).getId());
                intent.putExtra(ConstantUtil.KEY_ISOK,"");
                startActivity(intent);
            }
        });
    }

    /**
     * 为 RecyclerView 添加头部控件（销售额、安排工作）
     */
    private void initHeadView() {
        if (isAdd){//荣誉感是
            hvView = getLayoutInflater().inflate(R.layout.item_hv_worklist_addwork, (ViewGroup) recyclerView.getParent(), false);
        }else {
            hvView = getLayoutInflater().inflate(R.layout.item_hv_worklist_saleroom, (ViewGroup) recyclerView.getParent(), false);
        }
        mAdapter.addHeaderView(hvView);
        //销售额
        hvView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyUtils.isFastClick1()){
                    return;
                }

                if (isAdd){//打开安排工作界面
                    toActivity(StaffArrangeWorkActivity.class);
                }else {//打开销售额界面
                    toActivity(ReportSaleroomActivity.class);
                }
            }
        });
    }

    private void toActivity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra(ConstantUtil.KEY_POSITION,hireId);
        startActivity(intent);
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
        getPresenter().onGetReportTaskList(hireId);
    }


    /**
     * IStaffWorkListView
     */
    @Override
    public String getStoreId() {
        return storeId;
    }

    @Override
    public void onGetConditionSuccessResult(List<StoreListInfoData> result) {
        storeListBeans = result;
        storeListStrs.add("全部");
        for (int i = 0; i < storeListBeans.size() ; i++) {
            storeListStrs.add(storeListBeans.get(i).getShop_name());
        }
        storeListAdapter = new DropDownListAdapter(StaffWorkListActivity.this, storeListStrs);
        storeListView.setAdapter(storeListAdapter);
    }
    @Override
    public void onGetConditionFailureResult(String result) {
        if (result.equals("")){
            result="暂无任何店铺！";
        }
        ToastUtil.myToast(result);
    }
    @Override
    public void onRequestSuccess(List<StaffWorkListInfoData> tData) {
        recyclerView.setBackgroundResource(R.color.color_f0f0f0);
        mAdapter.setNewData(tData);
        swipeRefreshLayout.setRefreshing(false);
    }
    @Override
    public void onRequestFailure(String result) {
        if (result.equals("")){//没数据
            recyclerView.setBackgroundResource(R.drawable.img_nothing);
            ToastUtil.myToast("暂无任何工作安排！");
        }else {
            recyclerView.setBackgroundResource(R.drawable.img_nothing_net);
            ToastUtil.myToast(result);
        }
        mAdapter.setNewData(null);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancleMyToast();
    }
}
