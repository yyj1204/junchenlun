package com.wktx.www.emperor.ui.fragment.resume;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hedgehog.ratingbar.RatingBar;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.recruit.demand.DemandListInfoData;
import com.wktx.www.emperor.apiresult.recruit.resume.ResumeInfoData;
import com.wktx.www.emperor.basemvp.ABaseFragment;
import com.wktx.www.emperor.presenter.recruit.resume.ResumePresenter;
import com.wktx.www.emperor.ui.activity.ImageActivity;
import com.wktx.www.emperor.ui.activity.login.LoginActivity;
import com.wktx.www.emperor.ui.adapter.recruit.ResumeAdapter;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.GlideUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.recruit.IResumeView;
import com.wktx.www.emperor.utils.ToastUtil;
import com.wktx.www.emperor.widget.MyLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 美工简历---简历
 */
public class ResumeFragment extends ABaseFragment<IResumeView,ResumePresenter> implements IResumeView{
    @BindView(R.id.recyclerView)
    RecyclerView recycleView;
    private View hvResume1;
    private View hvResume2;
    //收藏
    private LinearLayout llCollect;
    private ImageView ivCollect;
    private TextView tvCollect;


    private ResumeInfoData resumeInfoData;//简历内容
    private ResumeAdapter mAdapter;
    private boolean isCollection;//是否收藏 false:否 true:是
    private List<String> imageHeadUrlList = new ArrayList<>();//头像图片url集合
    private List<String> imageResumeUrlList = new ArrayList<>();//简历图片url集合

    public ResumeFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resume, container, false);
        ButterKnife.bind(this, view);
        initData();
        initRecycleView();
        initHeadView1();
        initHeadView2();
        return view;
    }

    @Override
    protected ResumePresenter createPresenter() {
        return new ResumePresenter();
    }

    /**
     *接收 ArtistResumeActivity 传递过来的简历内容
     */
    private void initData() {
        resumeInfoData = (ResumeInfoData) getArguments().getSerializable(ConstantUtil.KEY_DATA);
    }

    /**
     * 初始化 RecycleView
     */
    private void initRecycleView() {
        //设置分割线与垂直方向布局
        recycleView.addItemDecoration(MyUtils.drawDivider(getActivity(), LinearLayout.VERTICAL, R.drawable.divider_f0f0f0_2));
        MyLayoutManager myLayoutManager = new MyLayoutManager(getActivity(), LinearLayout.VERTICAL, false);
        recycleView.setLayoutManager(myLayoutManager);
        //加载 Adapter
        mAdapter = new ResumeAdapter(getContext());
        mAdapter.setNewData(resumeInfoData.getWork_experience());
        //添加头部布局
        hvResume1 = getActivity().getLayoutInflater().inflate(R.layout.item_hv_resume_experience1, (ViewGroup) recycleView.getParent(), false);
        hvResume2 = getActivity().getLayoutInflater().inflate(R.layout.item_hv_resume_experience2, (ViewGroup) recycleView.getParent(), false);
        mAdapter.addHeaderView(hvResume1);
        mAdapter.addHeaderView(hvResume2);
        recycleView.setAdapter(mAdapter);
    }

    /**
     * 为 RecyclerView 添加头部控件
     */
    private void initHeadView1() {
        //查找控件
        ImageView ivHead = (ImageView) hvResume1.findViewById(R.id.iv_head);
        ImageView ivSex = (ImageView) hvResume1.findViewById(R.id.iv_sex);
        TextView tvName = (TextView) hvResume1.findViewById(R.id.tv_name);
        TextView tvJob = (TextView) hvResume1.findViewById(R.id.tv_staffJob);
        TextView tvCategory = (TextView) hvResume1.findViewById(R.id.tv_category);
        TextView tvPlatform = (TextView) hvResume1.findViewById(R.id.tv_platform);
        LinearLayout llStyle = (LinearLayout) hvResume1.findViewById(R.id.linear_style);
        LinearLayout llSpeed = (LinearLayout) hvResume1.findViewById(R.id.linear_speed);
        TextView tvStyle = (TextView) hvResume1.findViewById(R.id.tv_style);
        TextView tvSpeed = (TextView) hvResume1.findViewById(R.id.tv_speed);
        RatingBar rbServiceAttitude = (RatingBar) hvResume1.findViewById(R.id.rb_serviceAttitude);//服务态度
        RatingBar rbCommunicateAbility = (RatingBar) hvResume1.findViewById(R.id.rb_communicateAbility);//沟通能力
        RatingBar rbResponseSpeed = (RatingBar) hvResume1.findViewById(R.id.rb_responseSpeed);//响应速度
        //收藏
        llCollect = (LinearLayout) hvResume1.findViewById(R.id.linear_collect);
        ivCollect = (ImageView) hvResume1.findViewById(R.id.iv_collect);
        tvCollect = (TextView) hvResume1.findViewById(R.id.tv_collect);

        //控件数据
        tvName.setText(resumeInfoData.getName());
        tvJob.setText(resumeInfoData.getTow_name());
        tvCategory.setText(resumeInfoData.getBgat());
        tvPlatform.setText(resumeInfoData.getBgap());
        tvStyle.setText(resumeInfoData.getBgas());
        tvSpeed.setText(resumeInfoData.getTyping_speed());
        rbServiceAttitude.setStar(Float.parseFloat(resumeInfoData.getService_attitude()));
        rbCommunicateAbility.setStar(Float.parseFloat(resumeInfoData.getAbility()));
        rbResponseSpeed.setStar(Float.parseFloat(resumeInfoData.getResponse_speed()));
        //头像
        if (TextUtils.isEmpty(resumeInfoData.getPicture())){
            if (resumeInfoData.getSex().equals("1")){
                ivHead.setImageResource(R.drawable.img_head_man);
            }else if (resumeInfoData.getSex().equals("2")){
                ivHead.setImageResource(R.drawable.img_head_woman);
            }else {
                ivHead.setImageResource(R.drawable.img_mine_head);
            }
        }else {
            imageHeadUrlList.add(resumeInfoData.getPicture());
            GlideUtil.loadImage(resumeInfoData.getPicture(),R.drawable.img_mine_head,ivHead);
        }
        //性别 1:男 2:女
        if (resumeInfoData.getSex().equals("1")){
            ivSex.setImageResource(R.drawable.ic_sex_man);
        }else if (resumeInfoData.getSex().equals("2")){
            ivSex.setImageResource(R.drawable.ic_sex_woman);
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

        //是否收藏
        int is_collection = resumeInfoData.getIs_collection();
        if (is_collection ==0){//否
            isCollection=false;
            ivCollect.setSelected(isCollection);
            tvCollect.setText("收藏");
        }else if (is_collection ==1){//是
            isCollection=true;
            ivCollect.setSelected(isCollection);
            tvCollect.setText("已收藏");
        }

        ivHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyUtils.isFastClick()){
                    return;
                }
                if (TextUtils.isEmpty(resumeInfoData.getPicture())){
                    return;
                }
                //查看大图
                String[] imageUrls = imageHeadUrlList.toArray(new String[imageHeadUrlList.size()]);
                Intent intent = new Intent(getActivity(), ImageActivity.class);
                intent.putExtra(ConstantUtil.KEY_DATA, imageUrls);
                intent.putExtra(ConstantUtil.KEY_POSITION, 0);
                startActivity(intent);
            }
        });

        llCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyUtils.isFastClick()){
                    return;
                }
                //收藏（取消收藏）简历
                if (getUserInfo()!=null){
                    llCollect.setEnabled(false);
                    getPresenter().onCollectResume(resumeInfoData.getId());
                }else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        });
    }
    private void initHeadView2() {
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
            imageResumeUrlList.add(resume_content);
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
                String[] imageUrls = imageResumeUrlList.toArray(new String[imageResumeUrlList.size()]);
                Intent intent = new Intent(getActivity(), ImageActivity.class);
                intent.putExtra(ConstantUtil.KEY_DATA, imageUrls);
                intent.putExtra(ConstantUtil.KEY_POSITION, 0);
                startActivity(intent);
            }
        });
    }


    /**
     * IResumeView
     */
    @Override//收藏、取消收藏
    public void onInterviewResult(boolean isSuccess, String msg) {
        llCollect.setEnabled(true);
        ToastUtil.myToast(msg);
        if (isSuccess){
            isCollection=!isCollection;
            ivCollect.setSelected(isCollection);
            if (isCollection){
                tvCollect.setText("已收藏");
            }else{
                tvCollect.setText("收藏");
            }
        }

    }
    @Override
    public void onDemandListSuccessResult(List<DemandListInfoData> result) {
    }
    @Override
    public void onDemandListFailureResult(String msg) {
    }
    @Override
    public void onRequestSuccess(ResumeInfoData tData) {
    }
    @Override
    public void onRequestFailure(String result) {
    }
}
