package com.wktx.www.emperor.ui.activity.recruit.resume;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.recruit.resume.WorksDetailsInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.recruit.resume.WorksDetailsPresenter;
import com.wktx.www.emperor.ui.activity.ImageActivity;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.ImageDownloadTask;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.IView;
import com.wktx.www.emperor.utils.ToastUtil;
import com.wktx.www.emperor.widget.MyLayoutManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    private List<String> imageUrlList = new ArrayList<>();//图片url集合

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
                //将简历ID 传递给 ResumeActivity
                Intent intent = new Intent(WorksDetailsActivity.this, ResumeActivity.class);
                intent.putExtra(ConstantUtil.KEY_POSITION,resumeId);
                startActivity(intent);
                //如果是简历界面进来，先把简历界面关闭，再重新进入简历界面
                if (isActivity.equals(ConstantUtil.ACTIVITY_JL)){
                    ResumeActivity.instance.finish();
                    finish();
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
        tvTitle.setText(R.string.title_works_details);
        initData();
        initRecycleView();
    }

    @Override
    protected WorksDetailsPresenter createPresenter() {
        return new WorksDetailsPresenter();
    }

    /**
     *接收 ResumeWorksFragment RecruitListFragment SearchActivity CasesActivity 传递过来的作品Id
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
            @SuppressLint("CheckResult")
            @Override
            protected void convert(final BaseViewHolder helper, String item) {
                final ImageView imageView = helper.getView(R.id.imageView);
                ImageDownloadTask imgtask =new ImageDownloadTask();
                /**这里是获取手机屏幕的分辨率用来处理 图片 溢出问题的。begin*/
                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                imgtask.setDisplayWidth(dm.widthPixels);
                imgtask.setDisplayHeight(dm.heightPixels);
                imgtask.execute(item,imageView);
            }
        };
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (MyUtils.isFastClick1()){
                    return;
                }
                //查看大图
                String[] imageUrls = imageUrlList.toArray(new String[imageUrlList.size()]);
                Intent intent = new Intent(WorksDetailsActivity.this, ImageActivity.class);
                intent.putExtra(ConstantUtil.KEY_DATA, imageUrls);
                intent.putExtra(ConstantUtil.KEY_POSITION, position);
                startActivity(intent);
            }
        });
        fvWorksInfo = getLayoutInflater().inflate(R.layout.item_fv_works_details, (ViewGroup) recycleView.getParent(), false);
        mAdapter.addFooterView(fvWorksInfo);
        recycleView.setAdapter(mAdapter);
    }

    /**
     * IView
     */
    @Override
    public void onRequestSuccess(WorksDetailsInfoData tData) {
        TextView tvTitle = fvWorksInfo.findViewById(R.id.tv_worksTitle);
        TextView tvTag = fvWorksInfo.findViewById(R.id.tv_worksTag);
        TextView tvContent = fvWorksInfo.findViewById(R.id.tv_worksContent);

        resumeId = tData.getRid();
        tvTitle.setText(tData.getTitle());
        tvTag.setText(tData.getBgat());
        tvContent.setText(tData.getBrief_intro());

        imageUrlList = tData.getContent();
        mAdapter.setNewData(imageUrlList);
    }
    @Override
    public void onRequestFailure(String result) {
        ToastUtil.myToast(result);
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
