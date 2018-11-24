package com.wktx.www.subjects.ui.activity.mine.resume;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.mine.resume.ResumePreviewInfoData;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.presenter.mine.PreviewResumePresenter;
import com.wktx.www.subjects.ui.activity.ImageActivity;
import com.wktx.www.subjects.ui.adapter.mine.PreviewResumeAdapter;
import com.wktx.www.subjects.ui.view.IView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.GlideUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.widget.MyLayoutManager;
import com.wktx.www.subjects.utils.ToastUtil;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResumePreviewActivity extends ABaseActivity<IView,PreviewResumePresenter> implements IView<ResumePreviewInfoData>{
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recycleView;
    private View hvResume1;
    private View hvResume2;

    private PreviewResumeAdapter adapter;

    private List<String> imageUrlList = new ArrayList<>();//图片url集合

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
        setContentView(R.layout.activity_resume_preview);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_resume_preview);

        initData();
        initRecycleView();
    }

    @Override
    protected PreviewResumePresenter createPresenter() {
        return new PreviewResumePresenter();
    }

    /**
     * 接收 MyResumeActivity 传过来 简历id
     */
    private void initData() {
        String resumeId = getIntent().getStringExtra(ConstantUtil.KEY_DATA);
        getPresenter().onGetResumeInfo(resumeId);
    }

    /**
     * 初始化 RecycleView
     */
    private void initRecycleView() {
        //设置分割线与垂直方向布局
        recycleView.addItemDecoration(MyUtils.drawDivider(this, LinearLayout.VERTICAL, R.drawable.divider_f0f0f0_2));
        MyLayoutManager myLayoutManager = new MyLayoutManager(this, LinearLayout.VERTICAL, false);
        recycleView.setLayoutManager(myLayoutManager);
        //加载 Adapter
        adapter = new PreviewResumeAdapter();
        //添加头部布局
        hvResume1 = getLayoutInflater().inflate(R.layout.item_hv_resume_preview1, (ViewGroup) recycleView.getParent(), false);
        hvResume2 = getLayoutInflater().inflate(R.layout.item_hv_resume_preview2, (ViewGroup) recycleView.getParent(), false);
        adapter.addHeaderView(hvResume1);
        adapter.addHeaderView(hvResume2);
        recycleView.setAdapter(adapter);
    }

    /**
     * 为 RecyclerView 添加头部控件
     */
    private void initHeadView1(ResumePreviewInfoData resumeInfoData) {
        //查找控件
        ImageView ivHead = (ImageView) hvResume1.findViewById(R.id.iv_head);
        ImageView ivSex = (ImageView) hvResume1.findViewById(R.id.iv_sex);
        TextView tvName = (TextView) hvResume1.findViewById(R.id.tv_name);
        TextView tvJob = (TextView) hvResume1.findViewById(R.id.tv_staffJob);
        TextView tvSalary = (TextView) hvResume1.findViewById(R.id.tv_salary);
        TextView tvCategory = (TextView) hvResume1.findViewById(R.id.tv_category);
        TextView tvPlatform = (TextView) hvResume1.findViewById(R.id.tv_platform);
        LinearLayout llStyle = (LinearLayout) hvResume1.findViewById(R.id.linear_style);
        LinearLayout llSpeed = (LinearLayout) hvResume1.findViewById(R.id.linear_speed);
        TextView tvStyle = (TextView) hvResume1.findViewById(R.id.tv_style);
        TextView tvSpeed = (TextView) hvResume1.findViewById(R.id.tv_speed);

        //控件数据
        tvName.setText(resumeInfoData.getName());
        tvJob.setText(resumeInfoData.getTow_name());
        tvSalary.setText(resumeInfoData.getMonthly_money());
        tvCategory.setText(resumeInfoData.getBgat());
        tvPlatform.setText(resumeInfoData.getBgap());
        tvStyle.setText(resumeInfoData.getBgas());
        tvSpeed.setText(resumeInfoData.getTyping_speed());
        //头像
        if (TextUtils.isEmpty(resumeInfoData.getPicture())){
            if (resumeInfoData.getSex().equals("1")){
                ivHead.setImageResource(R.drawable.img_head_man);
            }else if (resumeInfoData.getSex().equals("2")){
                ivHead.setImageResource(R.drawable.img_head_women);
            }else {
                ivHead.setImageResource(R.drawable.img_mine_head);
            }
        }else {
            GlideUtil.loadImage(resumeInfoData.getPicture(),R.drawable.img_mine_head,ivHead);
        }
        //性别 1:男 2:女
        if (resumeInfoData.getSex().equals("1")){
            ivSex.setImageResource(R.drawable.ic_sex_man);
        }else if (resumeInfoData.getSex().equals("2")){
            ivSex.setImageResource(R.drawable.ic_sex_women);
        }else {
            ivSex.setVisibility(View.GONE);
        }
        //根据工作类型，显示对应擅长内容
        String tow = resumeInfoData.getTow();
        if (tow.equals("1")){//美工
            llStyle.setVisibility(View.VISIBLE);
            llSpeed.setVisibility(View.GONE);
        }else if (tow.equals("2")){//客服
            llStyle.setVisibility(View.GONE);
            llSpeed.setVisibility(View.VISIBLE);
        }else {//其他职位类型
            llStyle.setVisibility(View.GONE);
            llSpeed.setVisibility(View.GONE);
        }
    }
    private void initHeadView2(ResumePreviewInfoData resumeInfoData) {
        //查找控件
        TextView tvHighestEducation = (TextView) hvResume2.findViewById(R.id.tv_highestEducation);
        TextView tvWorkYears = (TextView) hvResume2.findViewById(R.id.tv_workYears);
        TextView tvBirthDate = (TextView) hvResume2.findViewById(R.id.tv_birthDate);
        TextView tvCity = (TextView) hvResume2.findViewById(R.id.tv_city);
        TextView tvTags = (TextView) hvResume2.findViewById(R.id.tv_tags);
        TextView tvExperience = (TextView) hvResume2.findViewById(R.id.tv_experience);
        TextView tvCharacterIntroduce = (TextView) hvResume2.findViewById(R.id.tv_characterIntroduce);
        LinearLayout llResumeMaking = (LinearLayout) hvResume2.findViewById(R.id.linear_resumeMaking);
        ImageView ivResume = (ImageView) hvResume2.findViewById(R.id.iv_resume);

        //控件数据
        tvHighestEducation.setText(resumeInfoData.getHighest_education());
        if (TextUtils.isEmpty(resumeInfoData.getWorking_years())){
            tvWorkYears.setText("无经验");
        }else {
            tvWorkYears.setText(resumeInfoData.getWorking_years());
        }
        if (TextUtils.isEmpty(resumeInfoData.getDate_of_birth())){
            tvBirthDate.setText("未设置");
        }else {
            tvBirthDate.setText(resumeInfoData.getDate_of_birth());
        }
        if (TextUtils.isEmpty(resumeInfoData.getResidential_city())){
            tvCity.setText("未设置");
        }else {
            tvCity.setText(resumeInfoData.getResidential_city());
        }
        if (TextUtils.isEmpty(resumeInfoData.getCharacter_introduction())){
            tvCharacterIntroduce.setText("无");
        }else {
            tvCharacterIntroduce.setText(resumeInfoData.getCharacter_introduction());
        }
        if (resumeInfoData.getWork_experience()==null||resumeInfoData.getWork_experience().size()==0){
            tvExperience.setText("暂无工作经历");
        }else {
            tvExperience.setText("工作经历");
        }
        //个人标签
        List<String> tags = resumeInfoData.getTags();
        String tagsStr = "";
        if (tags.size()==0){
            tagsStr="无";
        }else {
            for (int i = 0; i <tags.size() ; i++) {
                if (i==0){
                    tagsStr=tags.get(i);
                }else {
                    tagsStr+="/"+tags.get(i);
                }
            }
        }
        tvTags.setText(tagsStr);
        //个性简历
        String resume_content = resumeInfoData.getResume_content();
        if (TextUtils.isEmpty(resume_content)){
            llResumeMaking.setVisibility(View.VISIBLE);
            ivResume.setVisibility(View.GONE);
        }else {
            imageUrlList.add(resume_content);
            llResumeMaking.setVisibility(View.GONE);
            ivResume.setVisibility(View.VISIBLE);
            GlideUtil.loadImage(resume_content,R.drawable.img_loading,ivResume);
        }

        ivResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyUtils.isFastClick()){
                    return;
                }
                //查看大图
                String[] imageUrls = imageUrlList.toArray(new String[imageUrlList.size()]);
                Intent intent = new Intent(ResumePreviewActivity.this, ImageActivity.class);
                intent.putExtra(ConstantUtil.KEY_DATA, imageUrls);
                intent.putExtra(ConstantUtil.KEY_POSITION, 0);
                startActivity(intent);
            }
        });
    }


    /***
     * IView
     */
    @Override
    public void onRequestSuccess(ResumePreviewInfoData tData) {
        adapter.setNewData(tData.getWork_experience());
        initHeadView1(tData);
        initHeadView2(tData);
    }
    @Override
    public void onRequestFailure(String result) {
         ToastUtil.myToast(result);
        finish();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancleMyToast();
    }
}
