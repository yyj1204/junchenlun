package com.wktx.www.emperor.ui.activity.recruit.resume;

import android.content.Intent;
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
import com.wktx.www.emperor.utils.GlideUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.IView;
import com.wktx.www.emperor.utils.ToastUtil;
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
    private String isActivity;//是哪个界面进来的
    private String resumeId;

    @OnClick({R.id.tb_IvReturn,R.id.bt_lookResume})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.bt_lookResume://查看简历
                if (MyUtils.isFastClick()){
                    return;
                }
                if (isActivity.equals(ConstantUtil.ACTIVITY_JL)){
                    finish();
                }else {
                    //将简历ID 传递给 ResumeActivity
                    Intent intent = new Intent(WorksDetailsActivity.this, ResumeActivity.class);
                    intent.putExtra(ConstantUtil.KEY_POSITION,resumeId);
                    startActivity(intent);
                }
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
     *接收 ResumeWorksFragment RecruitListFragment SearchActivity ArtistCaseActivity 传递过来的作品Id
     */
    private void initData() {
        isActivity = getIntent().getStringExtra(ConstantUtil.KEY_ISOK);
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
                GlideUtil.loadImage(item,R.drawable.img_loading,ivWorksDetails);
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

        resumeId = tData.getRid();
        tvTitle.setText(tData.getTitle());
        tvTag.setText(tData.getBgat());
        tvContent.setText(tData.getBrief_intro());
        mAdapter.setNewData(tData.getContent());
    }
    @Override
    public void onRequestFailure(String result) {
        ToastUtil.myToast(result);
        finish();
    }
}
