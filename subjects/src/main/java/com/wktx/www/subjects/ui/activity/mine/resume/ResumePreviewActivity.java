package com.wktx.www.subjects.ui.activity.mine.resume;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.login.AccountInfoData;
import com.wktx.www.subjects.apiresult.mine.resume.ResumePreviewInfoData;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.presenter.mine.PreviewResumePresenter;
import com.wktx.www.subjects.ui.adapter.mine.PreviewResumeAdapter;
import com.wktx.www.subjects.ui.view.IView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.GlideUtil;
import com.wktx.www.subjects.utils.LoginUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.widget.MyLayoutManager;
import com.wktx.www.subjects.utils.ToastUtil;


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
        LinearLayout llCategory = (LinearLayout) hvResume1.findViewById(R.id.linear_category);
        LinearLayout llStyle = (LinearLayout) hvResume1.findViewById(R.id.linear_style);
        LinearLayout llSpeed = (LinearLayout) hvResume1.findViewById(R.id.linear_speed);
        TextView tvCategory = (TextView) hvResume1.findViewById(R.id.tv_category);
        TextView tvStyle = (TextView) hvResume1.findViewById(R.id.tv_style);
        TextView tvSpeed = (TextView) hvResume1.findViewById(R.id.tv_speed);

        //控件数据
        tvName.setText(resumeInfoData.getName());
        tvJob.setText(resumeInfoData.getTow_name());
        tvSalary.setText(resumeInfoData.getMonthly_money());
        tvCategory.setText(resumeInfoData.getBgat());
        tvStyle.setText(resumeInfoData.getBgas());
        tvSpeed.setText(resumeInfoData.getTyping_speed());
        //头像
        if (resumeInfoData.getPicture()==null||resumeInfoData.getPicture().equals("")){
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
            llCategory.setVisibility(View.VISIBLE);
            llStyle.setVisibility(View.VISIBLE);
            llSpeed.setVisibility(View.GONE);
        }else if (tow.equals("2")){//客服
            llCategory.setVisibility(View.VISIBLE);
            llStyle.setVisibility(View.GONE);
            llSpeed.setVisibility(View.VISIBLE);
        }else {//其他职位类型
            llCategory.setVisibility(View.VISIBLE);
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
        TextView tvCharacterIntroduce = (TextView) hvResume2.findViewById(R.id.tv_characterIntroduce);
        LinearLayout llResumeMaking = (LinearLayout) hvResume2.findViewById(R.id.linear_resumeMaking);
        ImageView ivResume = (ImageView) hvResume2.findViewById(R.id.iv_resume);

        //控件数据
        tvHighestEducation.setText(resumeInfoData.getHighest_education());
        tvWorkYears.setText(resumeInfoData.getWorking_years());
        tvBirthDate.setText(resumeInfoData.getDate_of_birth());
        tvCity.setText(resumeInfoData.getResidential_city());
        tvCharacterIntroduce.setText(resumeInfoData.getCharacter_introduction());
        //个性简历
        String resume_content = resumeInfoData.getResume_content();
        if (resume_content==null||resume_content.equals("")){
            llResumeMaking.setVisibility(View.VISIBLE);
            ivResume.setVisibility(View.GONE);
        }else {
            llResumeMaking.setVisibility(View.GONE);
            ivResume.setVisibility(View.VISIBLE);
            GlideUtil.loadImage(resume_content,R.drawable.img_loading,ivResume);
        }
    }


    /***
     * IView
     */
    @Override
    public AccountInfoData getUserInfo() {
        AccountInfoData userInfo = LoginUtil.getinit().getUserInfo();
        return userInfo;
    }
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
