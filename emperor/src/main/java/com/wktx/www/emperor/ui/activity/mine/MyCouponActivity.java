package com.wktx.www.emperor.ui.activity.mine;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.mine.coupon.CouponInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.mine.MyCouponPresenter;
import com.wktx.www.emperor.ui.adapter.mine.CouponListAdapter;
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
 * 个人中心---我的红包
 */
public class MyCouponActivity extends ABaseActivity<IView,MyCouponPresenter> implements IView<List<CouponInfoData>> {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    //RecyclerView 适配器
    private CouponListAdapter adapter;

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
        setContentView(R.layout.activity_my_coupon);
        // 设置右滑动返回
        Slidr.attach(this);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.title_my_coupon);
        initRecycleView();
        initAdapter();
        initHeadView();
        initRefreshLayout();
        refresh();
    }

    @Override
    protected MyCouponPresenter createPresenter() {
        return new MyCouponPresenter();
    }

    /**
     * 初始化 RecycleView
     */
    private void initRecycleView() {
        //设置刷新圈圈的颜色
        swipeRefreshLayout.setColorSchemeColors(Color.rgb(255, 179, 33));
        swipeRefreshLayout.setRefreshing(true);
        //设置分割线与垂直方向布局
        recyclerView.addItemDecoration(MyUtils.drawDivider(MyCouponActivity.this, LinearLayout.VERTICAL, R.drawable.divider_f0f0f0_20));
        MyLayoutManager manager = new MyLayoutManager(MyCouponActivity.this, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
    }

    /**
     * 为 RecyclerView 加载 Adapter
     */
    private void initAdapter() {
        adapter = new CouponListAdapter(MyCouponActivity.this);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 为 RecyclerView 添加头部控件（使用规则）
     */
    private void initHeadView() {
        View hvUseRule = getLayoutInflater().inflate(R.layout.item_hv_mycoupon, (ViewGroup) recyclerView.getParent(), false);
        View tvUseRule = hvUseRule.findViewById(R.id.tv_useRule);
        adapter.addHeaderView(hvUseRule);
        tvUseRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyUtils.isFastClick1()){
                    return;
                }
                Intent intent = new Intent(MyCouponActivity.this, WebExplainActivity.class);
                intent.putExtra(ConstantUtil.KEY_WEB_EXPLAIN,ConstantUtil.EX_COUPONRULE);
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
        getPresenter().onGetCouponList();
    }

    /**
     * IView
     */
    @Override
    public void onRequestSuccess(List<CouponInfoData> tData) {
        recyclerView.setBackgroundResource(R.color.color_f0f0f0);
        adapter.setNewData(tData);
        swipeRefreshLayout.setRefreshing(false);
    }
    @Override
    public void onRequestFailure(String result) {
        if (result.equals("")){//没数据
            recyclerView.setBackgroundResource(R.drawable.img_nothing_packet);
            ToastUtil.myToast("暂无任何优惠券！");
        }else {
            recyclerView.setBackgroundResource(R.drawable.img_nothing_net);
            ToastUtil.myToast(result);
        }
        adapter.setNewData(null);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancleMyToast();
    }
}
