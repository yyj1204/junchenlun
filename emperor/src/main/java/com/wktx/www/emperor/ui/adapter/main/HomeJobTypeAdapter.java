package com.wktx.www.emperor.ui.adapter.main;

import android.content.Context;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.main.home.JobTypeInfoData;
import com.wktx.www.emperor.utils.GlideUtil;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yyj on 2018/3/24.
 * 首页顶部横向职业类型适配器
 */
public class HomeJobTypeAdapter extends BaseQuickAdapter<JobTypeInfoData, BaseViewHolder> {
    private Context mContext;

    public HomeJobTypeAdapter(Context context) {
        super(R.layout.item_rv_home_horizontal, null);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, JobTypeInfoData item) {
        helper.setText(R.id.tv_name, item.getName());
        //职业图标（最后两个案例、招聘指南图标是写死的）
        CircleImageView ivImg = helper.getView(R.id.iv_img);
        if (item.getPicture()==null||item.getPicture().equals("")){
            if (item.getName().equals("美工案例")){
                ivImg.setImageResource(R.drawable.ic_home_example);
            }else if (item.getName().equals("招聘指南")){
                ivImg.setImageResource(R.drawable.ic_home_guide);
            }else {
                ivImg.setImageResource(R.drawable.ic_home_guide);
            }
        }else {
            GlideUtil.loadImage(item.getPicture(),R.drawable.ic_home_guide,ivImg);
        }
    }
}
