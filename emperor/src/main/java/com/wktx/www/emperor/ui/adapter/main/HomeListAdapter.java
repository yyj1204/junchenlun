package com.wktx.www.emperor.ui.adapter.main;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.main.home.ResumeListBean;
import com.wktx.www.emperor.utils.GlideUtil;

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
    }
}
