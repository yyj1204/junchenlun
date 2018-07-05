package com.wktx.www.subjects.ui.adapter.mine;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.mine.resume.WorkExperienceBean;

/**
 * Created by yyj on 2018/1/26.
 * 我的简历 --- 工作经历 适配器
 */
public class ResumeExperienceAdapter extends BaseQuickAdapter<WorkExperienceBean, BaseViewHolder> {

    public ResumeExperienceAdapter() {
        super(R.layout.item_rv_resume_experience, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkExperienceBean item) {
        helper.setText(R.id.tv_companyName,item.getCompany());
        helper.setText(R.id.tv_workTime,item.getWork_date().getStart_date()+"-"+item.getWork_date().getEnd_date());
    }
}
