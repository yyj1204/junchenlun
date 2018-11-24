package com.wktx.www.emperor.ui.activity.recruit.resume;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.recruit.resume.WorksDetailsInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.recruit.resume.WorksDetailsPresenter;
import com.wktx.www.emperor.ui.activity.ImageActivity;
import com.wktx.www.emperor.ui.view.IView;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.utils.ToastUtil;
import com.wktx.www.emperor.widget.MyLayoutManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.ZOOM_FOCUS_CENTER_IMMEDIATE;

/**
 * 简历---作品---作品详情界面
 * 暂不用
 */
public class WorksDetails1Activity extends ABaseActivity<IView,WorksDetailsPresenter> implements IView<WorksDetailsInfoData> {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recycleView;
    private View fvWorksInfo;

    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;
    private String isActivity;//是哪个界面进来的
    private String resumeId;

    private List<String> imageUrlList = new ArrayList<>();//图片url集合
    private File imageCacheFile;

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
                Intent intent = new Intent(WorksDetails1Activity.this, ResumeActivity.class);
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
        setContentView(R.layout.activity_works_details1);
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
        mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_rv_works_details1, null) {
            @SuppressLint("CheckResult")
            @Override
            protected void convert(final BaseViewHolder helper, String item) {
                final SubsamplingScaleImageView imageView = helper.getView(R.id.imageView);
                imageView.setZoomEnabled(false);//关闭缩放功能

                RequestOptions options = new RequestOptions();
                options.centerCrop()
                        .dontAnimate()//禁用过渡动画
                        .placeholder(R.drawable.img_loading)//加载中显示的图片
                        .error(R.drawable.img_load_error)//加载失败时显示的图片
                        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);

                Glide.with(WorksDetails1Activity.this)
                        .load(item)
                        .apply(options)
                        //下载图片保存到本地
                        .downloadOnly(new SimpleTarget<File>() {
                            @Override
                            public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                                imageCacheFile = resource;
                                // 将保存的图片地址给SubsamplingScaleImageView,这里注意设置ImageViewState设置初始显示比例
                                //获取图片的宽高与屏幕的宽高
                                int sWidth = BitmapFactory.decodeFile(resource.getAbsolutePath()).getWidth();
                                int sHeight = BitmapFactory.decodeFile(resource.getAbsolutePath()).getHeight();
                                WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                                int width = wm.getDefaultDisplay().getWidth();
                                int height = wm.getDefaultDisplay().getHeight();
                                //如果图片高度大于屏幕高度，且图片高度大于图片宽度的3倍
                                if (sHeight >= height && sHeight / sWidth >=3) {
                                    imageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);
                                    imageView.setImage(ImageSource.uri(Uri.fromFile(resource)), new ImageViewState(0F, new PointF(0, 0), 0));
                                }else {
                                    imageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
                                    imageView.setImage(ImageSource.uri(Uri.fromFile(resource)));
                                    imageView.setDoubleTapZoomStyle(ZOOM_FOCUS_CENTER_IMMEDIATE);
                                }
                            }
                        });


                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (MyUtils.isFastClick()){
                            return;
                        }
                        //查看大图
                        String[] imageUrls = imageUrlList.toArray(new String[imageUrlList.size()]);
                        Intent intent = new Intent(WorksDetails1Activity.this, ImageActivity.class);
                        intent.putExtra(ConstantUtil.KEY_DATA, imageUrls);
                        intent.putExtra(ConstantUtil.KEY_POSITION, helper.getLayoutPosition());
                        startActivity(intent);
                    }
                });
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
        //清除Glide硬盘缓存的图片文件
        if(imageCacheFile != null){
            imageCacheFile.delete();
        }
    }
}
