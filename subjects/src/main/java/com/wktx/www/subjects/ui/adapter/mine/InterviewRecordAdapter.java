package com.wktx.www.subjects.ui.adapter.mine;

import android.content.Context;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.main.demand.DemandListInfoData;
import com.wktx.www.subjects.utils.GlideUtil;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yyj on 2018/1/26.
 * 面试记录适配器
 */
public class InterviewRecordAdapter extends BaseQuickAdapter<DemandListInfoData, BaseViewHolder> {
    private Context context;

    public InterviewRecordAdapter(Context context) {
        super(R.layout.item_rv_main_demand, null);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, DemandListInfoData item) {
        helper.setText(R.id.tv_position,item.getTow());
        //工作经验
        String workingYears = item.getWorking_years();
        if (workingYears.equals("未设置")){
            workingYears="经验不限";
        }
        helper.setText(R.id.tv_experience,workingYears);
        helper.setText(R.id.tv_demandSalary,"¥"+item.getBudget());
        helper.setText(R.id.tv_hireType,"("+item.getHire_type()+")");

        helper.setText(R.id.tv_category,item.getBgat_name());
        helper.setText(R.id.tv_platform,item.getBgap_name());
        helper.setText(R.id.tv_pattern,item.getDesign_pattern());
        //设计模式
        String design_pattern = item.getDesign_pattern();
        if (TextUtils.isEmpty(design_pattern)||design_pattern.equals("0")){
            helper.setGone(R.id.tv_patternTitle,false);
            helper.setGone(R.id.tv_pattern,false);
        }else {
            helper.setGone(R.id.tv_patternTitle,true);
            helper.setGone(R.id.tv_pattern,true);
        }

        helper.setText(R.id.tv_demandTitle,item.getTitle());
        helper.setText(R.id.tv_demandContent,item.getContent());
        helper.setText(R.id.tv_endTime,item.getEnd_time()+"截止");

        helper.setText(R.id.tv_companyName,item.getNickname());
        helper.setText(R.id.tv_storeName,item.getShop_name());
        helper.setText(R.id.tv_city,item.getAddress_from());
        helper.setText(R.id.tv_state,"面试时间："+item.getTime());
        CircleImageView ivHead = helper.getView(R.id.iv_head);
        if (TextUtils.isEmpty(item.getHead_pic())){
            ivHead.setImageResource(R.drawable.img_mine_head);
        }else {
            GlideUtil.loadImage(item.getHead_pic(),R.drawable.img_mine_head,ivHead);
        }
    }
}
