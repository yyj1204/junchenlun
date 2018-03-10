package com.wktx.www.emperor.ui.adapter.recruit;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.recruit.resume.WorksListInfoData;

/**
 * Created by yyj on 2018/1/26.
 * 简历---作品---作品详情适配器
 */

public class WorksDetailsAdapter extends BaseQuickAdapter<WorksListInfoData, BaseViewHolder>{
    private Context mContext;

    public WorksDetailsAdapter(Context context) {
        super(R.layout.item_rv_resume_works, null);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, WorksListInfoData item) {
        helper.setText(R.id.tv_worksTitle,item.getTitle());
        ImageView ivWorksImage = helper.getView(R.id.iv_worksImage);
        if (item.getImage().equals("")){
            //TODO 图片换
            ivWorksImage.setImageResource(R.drawable.recruit3);
        }else {
            Glide.with(mContext).load(item.getImage()).into(ivWorksImage);
        }
    }
}
