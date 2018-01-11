package com.wktx.www.emperor.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.Utils.MyDividerUtil;
import com.wktx.www.emperor.Widget.MyLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 招聘-发布需求-需求列表
 */
public class DemandActivity extends AppCompatActivity {
    @BindView(R.id.tb_TvBarTitle)
    TextView mTvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView mVtRecycleView;
    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;

    @OnClick({R.id.tb_IvReturn})
    public void MyOnclick(View view) {
        finish();
    }

    List<String> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demand);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        initVtRvData();
        mTvTitle.setText(R.string.title_demand);
        initVtRecycleView();
    }

    private void initVtRecycleView() {
        mVtRecycleView.addItemDecoration(MyDividerUtil.drawDivider(this, LinearLayout.VERTICAL, R.drawable.divider_recycler_gray));
        MyLayoutManager myLayoutManager = new MyLayoutManager(this, LinearLayout.VERTICAL, false);
        mVtRecycleView.setLayoutManager(myLayoutManager);
        mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_rv_demand, mDatas) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.textView, item);
            }
        };
        View hvAddDemand = getLayoutInflater().inflate(R.layout.item_hv_demand, (ViewGroup) mVtRecycleView.getParent(), false);
        mAdapter.setHeaderView(hvAddDemand);
        hvAddDemand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DemandActivity.this, AddDemandActivity.class));
            }
        });
        mVtRecycleView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(DemandActivity.this, DemandDetailActivity.class));
            }
        });
    }

    private void initVtRvData() {
        for (int i = 0; i < 5; i++) {
            mDatas.add("整店装修" + i);
        }
    }
}
