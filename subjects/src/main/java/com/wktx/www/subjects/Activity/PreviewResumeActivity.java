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
import com.wktx.www.subjects.utils.MyDividerUtil;
import com.wktx.www.subjects.widget.MyLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PreviewResumeActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView mVtRecycleView;
    @BindView(R.id.tb_TvBarTitle)
    TextView mTvTitle;
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
    List<String> mDatas = new ArrayList<>();
    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_resume);
        ButterKnife.bind(this);
        mTvTitle.setText(R.string.title_PreviewResume);
        initVtRvData();
        initVtRecycleView();
    }
    private void initVtRvData() {
        for (int i = 0; i < 5; i++) {
            mDatas.add("胡图图" + i);
        }
    }


    private void initVtRecycleView() {
        mVtRecycleView.addItemDecoration(MyDividerUtil.drawDivider(this, LinearLayout.VERTICAL, R.drawable.divider_recycler_gray));// 添加分割线。
        MyLayoutManager myLayoutManager = new MyLayoutManager(this, LinearLayout.VERTICAL, false);
        mVtRecycleView.setLayoutManager(myLayoutManager);
        mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_vt_resume_dplr_ten, mDatas) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
            }
        };
        View hvResume = this.getLayoutInflater().inflate(R.layout.hv_vt_preview_resume, (ViewGroup) mVtRecycleView.getParent(), false);
        mAdapter.setHeaderView(hvResume);
        mVtRecycleView.setAdapter(mAdapter);
    }
}
