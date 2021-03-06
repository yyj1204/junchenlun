package com.wktx.www.subjects.ui.activity.message;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.message.CompanyDemandListInfoData;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.presenter.message.CompanyDemandPresenter;
import com.wktx.www.subjects.ui.activity.main.PositionDetailsActivity;
import com.wktx.www.subjects.ui.adapter.message.CompanyDemandAdapter;
import com.wktx.www.subjects.ui.view.IView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.widget.MyLayoutManager;
import com.wktx.www.subjects.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 对我感兴趣公司-公司招聘需求列表
 */
public class CompanyDemandActivity extends ABaseActivity<IView,CompanyDemandPresenter> implements IView<List<CompanyDemandListInfoData>> {
    public static CompanyDemandActivity instance = null;
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private String isActivity;//是哪个界面进来的

    private CompanyDemandAdapter mAdapter;//RecyclerView 适配器

    private int page = 1;
    private static final int PAGE_SIZE = 10;//请求每页的数据量
    private boolean isRefresh;//是下拉刷新还是加载更多  false：加载更多，true：下拉刷新

    private String companyId;//公司id

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
        setContentView(R.layout.activity_company_demand);
        instance = this;
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_company_demand);
        initData();
        initRecycleView();
        initAdapter();
        initRefreshLayout();
        refresh();
    }

    @Override
    protected CompanyDemandPresenter createPresenter() {
        return new CompanyDemandPresenter();
    }

    /**
     * 接收 DemandDetailsActivity Message2InterestedFragment 传递过来的公司id
     */
    private void initData() {
        isActivity = getIntent().getStringExtra(ConstantUtil.KEY_WHETHER);
        companyId = getIntent().getStringExtra(ConstantUtil.KEY_DATA);
    }

    /**
     * 初始化 RecycleView
     */
    private void initRecycleView() {
        //设置刷新圈圈的颜色
        swipeRefreshLayout.setColorSchemeColors(Color.rgb(255, 179, 33));
        swipeRefreshLayout.setRefreshing(true);
        //设置分割线与垂直方向布局
        recyclerView.addItemDecoration(MyUtils.drawDivider(CompanyDemandActivity.this, LinearLayout.VERTICAL, R.drawable.divider_f0f0f0_14));
        MyLayoutManager manager = new MyLayoutManager(CompanyDemandActivity.this, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
    }

    /**
     * 为 RecyclerView 加载 Adapter
     */
    private void initAdapter() {
        mAdapter = new CompanyDemandAdapter(CompanyDemandActivity.this);
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
                //关闭之前的职位招聘详情界面，重新创建
                if (isActivity!=null&&isActivity.equals(ConstantUtil.ACTIVITY_ZPXQ)){
                    PositionDetailsActivity.instance.finish();
                }
                //打开招聘详情界面（传递招聘需求id）
                Intent intent = new Intent(CompanyDemandActivity.this, PositionDetailsActivity.class);
                intent.putExtra(ConstantUtil.KEY_WHETHER,ConstantUtil.ACTIVITY_ZPLB);
                intent.putExtra(ConstantUtil.KEY_DATA,mAdapter.getData().get(position).getId());
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
        mAdapter.setEnableLoadMore(false);
        getPresenter().getInfo(companyId,page);
    }
    //加载更多
    private void loadMore() {
        isRefresh=false;
        getPresenter().getInfo(companyId,page);
    }

    /**
     * IView
     */
    @Override
    public void onRequestSuccess(List<CompanyDemandListInfoData> tData) {
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
                toastStr="暂无任何招聘需求！";
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
