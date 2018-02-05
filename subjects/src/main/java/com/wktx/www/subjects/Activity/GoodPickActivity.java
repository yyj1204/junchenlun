package com.wktx.www.subjects.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.utils.ToastUtil;
import com.wktx.www.subjects.widget.MyLayoutManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoodPickActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView mVtRecycleView;

    @OnClick({R.id.linearLayout, R.id.relativelayout, R.id.goodPickTvcancel, R.id.goodPickTvSure})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.linearLayout:
                finish();
                break;
            case R.id.relativelayout:
                break;
            case R.id.goodPickTvcancel:
                finish();
                break;
            case R.id.goodPickTvSure:
                ToastUtil.toast(this, "确定");
                break;
            default:
                break;
        }
    }

    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;
    ArrayList<String> mDatas = new ArrayList<>();
    private String[] category = {"不限", "鞋服", "生活电器", "3c数码", "母婴", "食品"};

    private void initVtRvData() {
        for (int i = 0; i < 6; i++) {
            mDatas.add(category[i]);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_pick);
        ButterKnife.bind(this);
        initVtRvData();
        initVtRecycleView();
    }

    private void initVtRecycleView() {
        MyLayoutManager myLayoutManager = new MyLayoutManager(this, LinearLayout.VERTICAL, false);
        mVtRecycleView.setLayoutManager(myLayoutManager);
        mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_good_pick, mDatas) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.goodPickTvName, item);
            }
        };
        mVtRecycleView.setAdapter(mAdapter);
    } 
}
