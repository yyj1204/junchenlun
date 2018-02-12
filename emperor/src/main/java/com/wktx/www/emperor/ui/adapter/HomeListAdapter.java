package com.wktx.www.emperor.ui.adapter;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.home.home.ResumeListBean;
import com.wktx.www.emperor.utils.LogUtil;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yyj on 2018/1/26.
 * 首页适配器
 */
public class HomeListAdapter extends BaseQuickAdapter<ResumeListBean, BaseViewHolder> {
    private Context mContext;

    public HomeListAdapter(Context context) {
        super(R.layout.item_rv_home, null);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ResumeListBean item) {
        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_salary,item.getMonthly_money()+"元");
        helper.setText(R.id.tv_category,item.getBgat());
        helper.setText(R.id.tv_style,item.getBgas());
        helper.setText(R.id.tv_speed,item.getTyping_speed());
        //TODO 根据工作类型，显示对应擅长内容(没有工作类型做判断)
//        if (isServiceType){
//            helper.setGone(R.id.linear_category,false);
//            helper.setGone(R.id.linear_style,false);
//            helper.setGone(R.id.linear_speed,true);
//        }else {
//            helper.setGone(R.id.linear_category,true);
//            helper.setGone(R.id.linear_style,true);
//            helper.setGone(R.id.linear_speed,false);
//        }
        //工作经验
        String working_years = item.getWorking_years();
        if (working_years.equals("0")){
            helper.setText(R.id.tv_workYears,"未设置");
            helper.setText(R.id.tv_workExperience,"未设置");
            helper.setGone(R.id.tv_workYears,true);
            helper.setGone(R.id.tv_workExperience,true);
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
        if (!item.getPicture().equals("")){
            Glide.with(mContext).load(item.getPicture()).into(ivHead);
        }else {
            if (item.getSex().equals("1")){
                ivHead.setImageResource(R.drawable.icon_head_man);
            }else if (item.getSex().equals("2")){
                ivHead.setImageResource(R.drawable.icon_head_woman);
            }
        }
        //性别 1:男 2:女
        if (item.getSex().equals("1")){
            helper.setImageResource(R.id.iv_sex,R.drawable.icon_sex_man);
        }else if (item.getSex().equals("2")){
            helper.setImageResource(R.id.iv_sex,R.drawable.icon_sex_woman);
        }
    }
}
