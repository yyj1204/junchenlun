package com.wktx.www.emperor.ui.adapter;

import android.content.Context;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.recruit.resume.ResumeInfoData;
import com.wktx.www.emperor.utils.LogUtil;
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
        helper.setText(R.id.tv_workCategory,item.getBgat());//擅长类目
        helper.setText(R.id.tv_workType,item.getPosition());//工作类型
        helper.setText(R.id.tv_workIntroduction,item.getIntroduction());//工作内容
    }
}
