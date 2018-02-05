package com.wktx.www.subjects.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.utils.MyDividerUtil;
import com.wktx.www.subjects.widget.MyLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TrasactActivity extends AppCompatActivity {

    @BindView(R.id.tb_TvBarTitle)
    TextView mTvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView mVtRecycleView;
    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;

    @OnClick(R.id.tb_IvReturn)
    public void MyOnclick(View view) {
        finish();
    }

    List<String> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trasact);
        // 设置右滑动返回
        Slidr.attach(this);
        ButterKnife.bind(this);
        initVtRvData();
        mTvTitle.setText(R.string.title_transact);
        initVtRecycleView();
    }

    private void initVtRecycleView() {
        mVtRecycleView.addItemDecoration(MyDividerUtil.drawDivider(this, LinearLayout.VERTICAL, R.drawable.divider_recycler_gray));
        MyLayoutManager myLayoutManager = new MyLayoutManager(this, LinearLayout.VERTICAL, false);
        mVtRecycleView.setLayoutManager(myLayoutManager);
        mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_trasact, mDatas) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.trasactTvPrice, item);
            }
        };
        mVtRecycleView.setAdapter(mAdapter);
    }

    private void initVtRvData() {
        for (int i = 0; i < 5; i++) {
            mDatas.add("+"+(500 * i + 2000));
        }
    }
}
