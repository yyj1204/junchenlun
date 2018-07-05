package com.wktx.www.emperor.ui.adapter.mine;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.mine.browsingrecord.BrowsingRecordInfoData;
import com.wktx.www.emperor.utils.GlideUtil;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yyj on 2018/1/26.
 * 浏览记录、面试记录列表适配器
 */

public class BrowsingListAdapter extends BaseQuickAdapter<BrowsingRecordInfoData, BaseViewHolder>{
    private Context mContext;

    public BrowsingListAdapter(Context context) {
        super(R.layout.item_rv_mycollect, null);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, BrowsingRecordInfoData item) {
        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_city,item.getResidential_city());
        helper.setText(R.id.tv_category,item.getBgat());
        helper.setText(R.id.tv_salary,item.getMonthly_money()+"元/月");
        helper.setText(R.id.tv_staffJob,item.getTow_name());
        helper.setText(R.id.tv_speed,"(打字速度"+item.getTyping_speed()+"字/分)");

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
        //工作类型
        String tow = item.getTow();
        if (tow.equals("2")){//客服
            helper.setGone(R.id.tv_speed,true);
        }else {//其他工作类型
            helper.setGone(R.id.tv_speed,false);
        }
    }
}
