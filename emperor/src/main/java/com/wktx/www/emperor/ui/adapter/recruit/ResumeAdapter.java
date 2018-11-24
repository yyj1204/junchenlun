package com.wktx.www.emperor.ui.adapter.recruit;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.recruit.resume.ResumeInfoData;
/**
 * Created by yyj on 2018/1/26.
 * 简历适配器
 */
public class ResumeAdapter extends BaseQuickAdapter<ResumeInfoData.WorkExperienceBean, BaseViewHolder> {
    private Context mContext;

    public ResumeAdapter(Context context) {
        super(R.layout.item_rv_resume_experience, null);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ResumeInfoData.WorkExperienceBean item) {
        String start_date = item.getWork_date().getStart_date();
        String end_date = item.getWork_date().getEnd_date();
        helper.setText(R.id.tv_workDate,start_date+"-"+end_date);//工作时间
        helper.setText(R.id.tv_workCompany,item.getCompany());//公司名称
        //擅长平台
        TextView tvPlatform = helper.getView(R.id.tv_workPlatform);
        if (TextUtils.isEmpty(item.getBgap())||item.getBgap().equals("0")){
            tvPlatform.setVisibility(View.GONE);
        }else {
            tvPlatform.setText(item.getBgap());
            tvPlatform.setVisibility(View.VISIBLE);
        }
        //擅长类目
        TextView tvCategory = helper.getView(R.id.tv_workCategory);
        if (TextUtils.isEmpty(item.getBgat())||item.getBgat().equals("0")){
            tvCategory.setVisibility(View.GONE);
        }else {
            tvCategory.setText(item.getBgat());
            tvCategory.setVisibility(View.VISIBLE);
        }
        helper.setText(R.id.tv_storeUrl,"公司网址:"+item.getStore());//公司网址
        helper.setText(R.id.tv_workType,"职位:"+item.getPosition());//工作类型
        helper.setText(R.id.tv_workSalary,"薪资:"+item.getMonthly_money()+"元/月");//工作类型
        helper.setText(R.id.tv_workIntroduction,"工作内容:"+item.getIntroduction());//工作内容
    }
}
