package com.wktx.www.emperor.ui.fragment.resume;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hedgehog.ratingbar.RatingBar;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.recruit.resume.ResumeInfoData;
import com.wktx.www.emperor.ui.adapter.ResumeAdapter;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.widget.MyLayoutManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 美工简历---简历
 */
public class ResumeFragment extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView recycleView;
    private View hvResume1;
    private View hvResume2;

    private ResumeInfoData resumeInfoData;//简历内容
    private ResumeAdapter mAdapter;
    private int is_collection;//是否收藏 0:否 1:是

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

    /**
     *接收 ArtistResumeActivity 传递过来的简历内容
     */
    private void initData() {
        resumeInfoData = (ResumeInfoData) getArguments().getSerializable(ConstantUtil.KEY_DATA);
    }

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
        LinearLayout llCategory = (LinearLayout) hvResume1.findViewById(R.id.linear_category);
        LinearLayout llStyle = (LinearLayout) hvResume1.findViewById(R.id.linear_style);
        LinearLayout llSpeed = (LinearLayout) hvResume1.findViewById(R.id.linear_speed);
        TextView tvCategory = (TextView) hvResume1.findViewById(R.id.tv_category);
        TextView tvStyle = (TextView) hvResume1.findViewById(R.id.tv_style);
        TextView tvSpeed = (TextView) hvResume1.findViewById(R.id.tv_speed);
        RatingBar rbServiceAttitude = (RatingBar) hvResume1.findViewById(R.id.rb_serviceAttitude);//服务态度
        RatingBar rbCommunicateAbility = (RatingBar) hvResume1.findViewById(R.id.rb_communicateAbility);//沟通能力
        RatingBar rbResponseSpeed = (RatingBar) hvResume1.findViewById(R.id.rb_responseSpeed);//响应速度
        LinearLayout llCollect = (LinearLayout) hvResume1.findViewById(R.id.linear_collect);//收藏
        final ImageView ivCollect = (ImageView) hvResume1.findViewById(R.id.iv_collect);
        final TextView tvCollect = (TextView) hvResume1.findViewById(R.id.tv_collect);

        //控件数据
        tvName.setText(resumeInfoData.getName());
        tvCategory.setText(resumeInfoData.getBgat());
        tvStyle.setText(resumeInfoData.getBgas());
        tvSpeed.setText(resumeInfoData.getTyping_speed());
        rbServiceAttitude.setStar(Float.parseFloat(resumeInfoData.getService_attitude()));
        rbCommunicateAbility.setStar(Float.parseFloat(resumeInfoData.getAbility()));
        rbResponseSpeed.setStar(Float.parseFloat(resumeInfoData.getResponse_speed()));
        //头像
        if (!resumeInfoData.getPicture().equals("")){
            Glide.with(getContext()).load(resumeInfoData.getPicture()).into(ivHead);
        }else {
            if (resumeInfoData.getSex().equals("1")){
                ivHead.setImageResource(R.drawable.icon_head_man);
            }else if (resumeInfoData.getSex().equals("2")){
                ivHead.setImageResource(R.drawable.icon_head_woman);
            }
        }
        //性别 1:男 2:女
        if (resumeInfoData.getSex().equals("1")){
            ivSex.setImageResource(R.drawable.icon_sex_man);
        }else if (resumeInfoData.getSex().equals("2")){
            ivSex.setImageResource(R.drawable.icon_sex_woman);
        }
        //根据工作类型，显示对应擅长内容
        String tow = resumeInfoData.getTow();
        if (tow.equals("2")){//客服
            llCategory.setVisibility(View.GONE);
            llStyle.setVisibility(View.GONE);
            llSpeed.setVisibility(View.VISIBLE);
        }else {
            llCategory.setVisibility(View.VISIBLE);
            llStyle.setVisibility(View.VISIBLE);
            llSpeed.setVisibility(View.GONE);
        }
        //是否收藏
        is_collection = resumeInfoData.getIs_collection();
        if (is_collection ==0){//否
            ivCollect.setSelected(false);
            tvCollect.setText("收藏");
        }else if (is_collection ==1){//是
            ivCollect.setSelected(true);
            tvCollect.setText("已收藏");
        }
        llCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (is_collection ==0){
                    is_collection =1;
                    ivCollect.setSelected(true);
                    tvCollect.setText("已收藏");
                }else if (is_collection ==1){
                    is_collection =0;
                    ivCollect.setSelected(false);
                    tvCollect.setText("收藏");
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
        if (!resume_content.equals("")){
            llResumeMaking.setVisibility(View.GONE);
            ivResume.setVisibility(View.VISIBLE);
            Glide.with(getContext()).load(resumeInfoData.getPicture()).into(ivResume);
        }else {
            llResumeMaking.setVisibility(View.VISIBLE);
            ivResume.setVisibility(View.GONE);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
