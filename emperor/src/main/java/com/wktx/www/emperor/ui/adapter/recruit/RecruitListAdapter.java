package com.wktx.www.emperor.ui.adapter.recruit;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.wktx.www.emperor.ui.activity.recruit.resume.WorksDetailsActivity;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.recruit.recruitlist.RecruitListInfoData;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.GlideUtil;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yyj on 2018/1/26.
 * 招聘列表（搜索）适配器
 */

public class RecruitListAdapter extends BaseQuickAdapter<RecruitListInfoData, BaseViewHolder>{
    private Context mContext;

    public RecruitListAdapter(Context context) {
        super(R.layout.item_rv_recruit, null);
        this.mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, RecruitListInfoData item) {
        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_salary,item.getMonthly_money());
        helper.setText(R.id.tv_category,item.getBgat());
        helper.setText(R.id.tv_style,item.getBgas());
        helper.setText(R.id.tv_speed,item.getTyping_speed());
        helper.setText(R.id.tv_staffJob,item.getTow_name());
        //根据工作类型，显示对应擅长内容
        String tow = item.getTow();
        if (tow.equals("1")) {//美工
            helper.setGone(R.id.linear_category, true);
            helper.setGone(R.id.linear_style, true);
            helper.setGone(R.id.linear_speed, false);
        }else if (tow.equals("2")){//客服
            helper.setGone(R.id.linear_category,true);
            helper.setGone(R.id.linear_style,false);
            helper.setGone(R.id.linear_speed,true);
        }else{//其他职位类型
            helper.setGone(R.id.linear_category,true);
            helper.setGone(R.id.linear_style,false);
            helper.setGone(R.id.linear_speed,false);
        }
        //工作经验
        String working_years = item.getWorking_years();
        if (working_years.equals("0")){
            helper.setText(R.id.tv_workYears,"无经验");
            helper.setText(R.id.tv_workExperience,"无经验");
        }else if (working_years.equals("1")){
            helper.setText(R.id.tv_workYears,"一年以内");
            helper.setText(R.id.tv_workExperience,"一年以内");
        }else if (working_years.equals("2")){
            helper.setText(R.id.tv_workYears,"一年");
            helper.setText(R.id.tv_workExperience,"一年");
        }else if (working_years.equals("3")){
            helper.setText(R.id.tv_workYears,"两年");
            helper.setText(R.id.tv_workExperience,"两年");
        }else if (working_years.equals("4")){
            helper.setText(R.id.tv_workYears,"三年");
            helper.setText(R.id.tv_workExperience,"三年");
        }else if (working_years.equals("5")){
            helper.setText(R.id.tv_workYears,"四年");
            helper.setText(R.id.tv_workExperience,"四年");
        }else if (working_years.equals("6")){
            helper.setText(R.id.tv_workYears,"五年");
            helper.setText(R.id.tv_workExperience,"五年");
        }else if (working_years.equals("7")){
            helper.setText(R.id.tv_workYears,"五年以上");
            helper.setText(R.id.tv_workExperience,"五年以上");
        }
        //工作状态
        if (item.getIs_job_hunting().equals("0")){
            helper.setText(R.id.tv_jobStatus,"已在工作");
        }else if (item.getIs_job_hunting().equals("1")){
            helper.setText(R.id.tv_jobStatus,"找工作中");
        }
        //头像
        CircleImageView ivHead = helper.getView(R.id.iv_head);
        if (item.getPicture()==null||item.getPicture().equals("")){
            if (item.getSex().equals("1")){
                ivHead.setImageResource(R.drawable.img_head_man);
            }else if (item.getSex().equals("2")){
                ivHead.setImageResource(R.drawable.img_head_woman);
            }else {
                ivHead.setImageResource(R.drawable.img_mine_head);
            }
        }else {
            GlideUtil.loadImage(item.getPicture(),R.drawable.img_mine_head,ivHead);
        }
        //性别 1:男 2:女
        ImageView ivSex = helper.getView(R.id.iv_sex);
        if (item.getSex().equals("1")){
            ivSex.setVisibility(View.VISIBLE);
            ivSex.setImageResource(R.drawable.ic_sex_man);
        }else if (item.getSex().equals("2")){
            ivSex.setVisibility(View.VISIBLE);
            ivSex.setImageResource(R.drawable.ic_sex_woman);
        }else {
            ivSex.setVisibility(View.GONE);
        }
        //作品展示
        final List<RecruitListInfoData.ResumeWorksBean> workBeans = item.getResume_works();
        if (workBeans.size()!=0){
            NineGridImageView nglWorks = helper.getView(R.id.ngl_works);
            NineGridImageViewAdapter<RecruitListInfoData.ResumeWorksBean> adapter = new NineGridImageViewAdapter<RecruitListInfoData.ResumeWorksBean>() {
                @Override
                protected void onDisplayImage(Context context, ImageView imageView, RecruitListInfoData.ResumeWorksBean s) {
                    if (imageView != null && s != null) {
                        GlideUtil.loadImage(s.getImage(),R.drawable.img_loading,imageView);
                    }
                }
                @Override
                protected ImageView generateImageView(Context context) {
                    return super.generateImageView(context);
                }
                @Override
                protected void onItemImageClick(Context context, ImageView imageView, int index, List<RecruitListInfoData.ResumeWorksBean> list) {
                    //将作品ID 传递给 WorksDetailsActivity
                    RecruitListInfoData.ResumeWorksBean info =  workBeans.get(index);
                    Intent intent = new Intent(mContext, WorksDetailsActivity.class);
                    intent.putExtra(ConstantUtil.KEY_POSITION,info.getId());
                    intent.putExtra(ConstantUtil.KEY_ISOK,ConstantUtil.ACTIVITY_ZP);
                    mContext.startActivity(intent);
                }
            };
            nglWorks.setAdapter(adapter);
            nglWorks.setImagesData(workBeans);
            nglWorks.setSingleImgSize(450);
        }
    }
}
