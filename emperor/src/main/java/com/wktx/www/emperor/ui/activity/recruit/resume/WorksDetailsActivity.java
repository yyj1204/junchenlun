package com.wktx.www.emperor.ui.activity.recruit.resume;

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
import com.wktx.www.emperor.apiresult.login.AccountInfoData;
import com.wktx.www.emperor.apiresult.recruit.resume.WorksDetailsInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.recruit.resume.WorksDetailsPresenter;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.view.IView;
import com.wktx.www.emperor.widget.MyLayoutManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 简历---作品---作品详情界面
 */
public class WorksDetailsActivity extends ABaseActivity<IView,WorksDetailsPresenter> implements IView<WorksDetailsInfoData> {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recycleView;
    private View fvWorksInfo;

    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;

    @OnClick({R.id.tb_IvReturn,R.id.bt_lookResume})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn:
            case R.id.bt_lookResume:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_works_details);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_works_details);
        initData();
        initRecycleView();
    }

    @Override
    protected WorksDetailsPresenter createPresenter() {
        return new WorksDetailsPresenter();
    }

    /**
     *接收 ResumeWorksFragment 传递过来的作品Id
     */
    private void initData() {
        String worksId = getIntent().getStringExtra(ConstantUtil.KEY_POSITION);
        getPresenter().onGetWorksInfo(worksId);
    }

    private void initRecycleView() {
        MyLayoutManager myLayoutManager = new MyLayoutManager(this, LinearLayout.VERTICAL, false);
        recycleView.setLayoutManager(myLayoutManager);
        mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_rv_works_details, null) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                ImageView ivWorksDetails = helper.getView(R.id.iv_worksDetails);
                Glide.with(WorksDetailsActivity.this).load(item).into(ivWorksDetails);
            }
        };
        fvWorksInfo = getLayoutInflater().inflate(R.layout.item_fv_works_details, (ViewGroup) recycleView.getParent(), false);
        mAdapter.addFooterView(fvWorksInfo);
        recycleView.setAdapter(mAdapter);
    }

    /**
     * IView
     */
    @Override
    public AccountInfoData getUserInfo() {
        return null;
    }
    @Override
    public void onRequestSuccess(WorksDetailsInfoData tData) {
        TextView tvTitle = fvWorksInfo.findViewById(R.id.tv_worksTitle);
        TextView tvTag = fvWorksInfo.findViewById(R.id.tv_worksTag);
        TextView tvContent = fvWorksInfo.findViewById(R.id.tv_worksContent);

        tvTitle.setText(tData.getTitle());
        tvTag.setText(tData.getBgat());
        tvContent.setText(tData.getBrief_intro());
        mAdapter.setNewData(tData.getContent());
    }
    @Override
    public void onRequestFailure(String result) {
        finish();
        MyUtils.showToast(WorksDetailsActivity.this,result);
    }
}
