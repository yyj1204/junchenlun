package com.wktx.www.subjects.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.widget.MyLayoutManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorkDetailActivity extends AppCompatActivity {

    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;
    @BindView(R.id.recyclerView)
    RecyclerView mVtRecycleView;
    @BindView(R.id.tb_TvBarTitle)
    TextView mTvTitle;
    @OnClick({R.id.tb_IvReturn})
    public void MyOnclick(View view) {
        finish();
    }

    ArrayList<String> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_detail);
        ButterKnife.bind(this);
        mTvTitle.setText(R.string.title_workdetail);
        initData();
        initVtRecycleView();

    }

    private void initData() {
        mDatas.add("1.负责店铺的日常运营");
        mDatas.add("2.活动规划");
        mDatas.add("3.直通车钻展等大型活动的规划");
    }

    private void initVtRecycleView() {
//        mVtRecycleView.addItemDecoration(MyDividerUtil.drawDivider(this,LinearLayout.VERTICAL, R.drawable.divider_recycler_gray_three));// 添加分割线。
        MyLayoutManager myLayoutManager = new MyLayoutManager(this, LinearLayout.VERTICAL, false);
        mVtRecycleView.setLayoutManager(myLayoutManager);
        mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_textview_white, mDatas) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.item_text, item);
            }
        };
        initHeader();
        mVtRecycleView.setAdapter(mAdapter);
    }

    private void initHeader() {
        View Hv = getLayoutInflater().inflate(R.layout.item_workdetail_hv, (ViewGroup) mVtRecycleView.getParent(), false);
        mAdapter.setHeaderView(Hv);
    }
}
