package com.wktx.www.subjects.ui.activity.mine.works;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.login.AccountInfoData;
import com.wktx.www.subjects.apiresult.mine.resume.ResumeInfoData;
import com.wktx.www.subjects.apiresult.mine.works.WorksListInfoData;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.presenter.mine.works.WorksListPresenter;
import com.wktx.www.subjects.ui.view.IView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.GlideUtil;
import com.wktx.www.subjects.utils.LoginUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人中心 --- 我的作品
 */
public class MyWorksActivity extends ABaseActivity<IView,WorksListPresenter> implements IView<List<WorksListInfoData>> {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ResumeInfoData resumeInfoData;//简历信息
    private BaseQuickAdapter<WorksListInfoData,BaseViewHolder> mAdapter;

    private int page = 1;
    private static final int PAGE_SIZE = 10;//请求每页的数据量
    private boolean isRefresh;//是下拉刷新还是加载更多  ：下拉刷新

    @OnClick({R.id.tb_IvReturn,R.id.linear_addWorks})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.linear_addWorks://上传作品
                if (MyUtils.isFastClick1()){
                    return;
                }
                //打开作品详情界面
                startWorksActivity(true,"");
            default:
                break;
        }
    }

    /**
     * 打开作品详情界面
     * isAdd：是新增还是编辑
     * worksId：作品id
     */
    private void startWorksActivity(boolean isAdd,String worksId) {
        Intent intent = new Intent(MyWorksActivity.this, WorksDetailsActivity.class);
        intent.putExtra(ConstantUtil.KEY_WHETHER, isAdd);
        intent.putExtra(ConstantUtil.KEY_POSITION, resumeInfoData.getId());
        intent.putExtra(ConstantUtil.KEY_DATA, worksId);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_works);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_my_works);

        initData();
        initRecycleView();
        initAdapter();
        initRefreshLayout();
    }

    @Override
    protected WorksListPresenter createPresenter() {
        return new WorksListPresenter();
    }


    /**
     * 接收 MyResumeActivity 传过来 resumeInfoData
     */
    private void initData() {
        resumeInfoData = (ResumeInfoData) getIntent().getSerializableExtra(ConstantUtil.KEY_DATA);
    }

    /**
     * 初始化 RecycleView
     */
    private void initRecycleView() {
        //设置刷新圈圈的颜色
        swipeRefreshLayout.setColorSchemeColors(Color.rgb(255, 179, 33));
        swipeRefreshLayout.setRefreshing(true);
        recyclerView.setPadding(20, 10, 20, 10);
        //设置网格布局
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MyWorksActivity.this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
    }


    /**
     * 为 RecyclerView 加载 Adapter
     */
    private void initAdapter() {
        mAdapter = new BaseQuickAdapter<WorksListInfoData,BaseViewHolder>(R.layout.item_rv_works,null){
            @Override
            protected void convert(BaseViewHolder helper, WorksListInfoData item) {
                helper.setText(R.id.tv_worksTitle,item.getTitle());
                ImageView ivWorksImage = helper.getView(R.id.iv_worksImage);
                if (item.getImage()==null||item.getImage().equals("")){
                    ivWorksImage.setImageResource(R.drawable.img_load_error);
                }else {
                    GlideUtil.loadImage(item.getImage(),R.drawable.img_loading,ivWorksImage);
                }
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
                //打开作品详情界面
               startWorksActivity(false, mAdapter.getData().get(position).getId());
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
        getPresenter().getInfo(resumeInfoData.getId(),page);
    }

    //加载更多
    private void loadMore() {
        isRefresh=false;
        getPresenter().getInfo(resumeInfoData.getId(),page);
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
    public void onRequestSuccess(List<WorksListInfoData> tData) {
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
                toastStr="暂无任何作品！";
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
