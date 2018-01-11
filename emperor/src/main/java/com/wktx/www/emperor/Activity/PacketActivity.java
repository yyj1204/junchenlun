package com.wktx.www.emperor.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

public class PacketActivity extends AppCompatActivity {
    @BindView(R.id.tb_TvBarTitle)
    TextView mTvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView mVtRecycleView;
    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;
    List<String> mDatas = new ArrayList<>();

    @OnClick({R.id.tb_IvReturn, R.id.packetTvRule})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.packetTvRule:
                startActivity(new Intent(this, PacketRuleActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packet);
        // 设置右滑动返回
        Slidr.attach(this);
        ButterKnife.bind(this);
        initVtRvData();
        mTvTitle.setText(R.string.title_red_packet);
        initVtRecycleView();
    }

    private void initVtRecycleView() {
        mVtRecycleView.addItemDecoration(MyDividerUtil.drawDivider(this, LinearLayout.VERTICAL, R.drawable.divider_recycler_gray_ten));
        MyLayoutManager myLayoutManager = new MyLayoutManager(this, LinearLayout.VERTICAL, false);
        mVtRecycleView.setLayoutManager(myLayoutManager);
        mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_packet, mDatas) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.packetTvPrice, item);
            }
        };
        mVtRecycleView.setAdapter(mAdapter);
    }

    private void initVtRvData() {
        for (int i = 0; i < 5; i++) {
            mDatas.add(i * 10 + 10 + "元");
        }
    }
}
