package com.wktx.www.emperor.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.ui.activity.recruit.resume.ArtistResumeActivity;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.widget.MyLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorksDetailActivity extends AppCompatActivity {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView mVtRecycleView;
    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;
    List<String> mDatas = new ArrayList<>();
    ArrayList<String> imgList = new ArrayList<>();

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
        setContentView(R.layout.activity_works_detail);
        // 设置右滑动返回
        Slidr.attach(this);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.title_works_detail);
        initImgList();
        initVtRvData();
        initVtRecycleView();
    }

    private void initImgList() {
        imgList.add("https://img11.static.yhbimg.com/yhb-img01/2017/05/22/11/018bf3b2c3dc4d4299da7351ded61167fa.jpg");
        imgList.add("https://img10.static.yhbimg.com/yhb-img01/2017/05/18/10/015afe6e5be1ba8f274cbcb2a697d92e4e.jpg");
        imgList.add("https://img10.static.yhbimg.com/yhb-img01/2017/05/22/10/01e0e2a25bfc1795412e2cb749292615eb.jpg");
    }

    private void initVtRecycleView() {
        mVtRecycleView.addItemDecoration(MyUtils.drawDivider(this, LinearLayout.VERTICAL, R.drawable.divider_f0f0f0_2));
        MyLayoutManager myLayoutManager = new MyLayoutManager(this, LinearLayout.VERTICAL, false);
        mVtRecycleView.setLayoutManager(myLayoutManager);
        mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_works_detail, mDatas) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                ImageView mIv = helper.getView(R.id.worksDetailIv);
                Glide.with(WorksDetailActivity.this).load(item).into(mIv);
            }
        };
        View fvWorksInfo = getLayoutInflater().inflate(R.layout.item_fv_worksinfo, (ViewGroup) mVtRecycleView.getParent(), false);
        TextView checkResume = (TextView) fvWorksInfo.findViewById(R.id.worksDetailTvCheckResume);
        checkResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WorksDetailActivity.this, ArtistResumeActivity.class));
            }
        });
        mAdapter.setFooterView(fvWorksInfo);
        mVtRecycleView.setAdapter(mAdapter);
    }

    private void initVtRvData() {
        Random random = new Random();
        for (int j = 0; j < 8; j++) {
            int i = random.nextInt(3);
            mDatas.add(imgList.get(i));
        }
    }
}
