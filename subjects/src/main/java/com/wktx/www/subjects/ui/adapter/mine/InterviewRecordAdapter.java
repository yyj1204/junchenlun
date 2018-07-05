package com.wktx.www.subjects.ui.adapter.mine;

import android.content.Context;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.main.position.PositionListInfoData;
import com.wktx.www.subjects.utils.GlideUtil;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yyj on 2018/1/26.
 * 面试记录适配器
 */
public class InterviewRecordAdapter extends BaseQuickAdapter<PositionListInfoData, BaseViewHolder> {
    private Context context;

    public InterviewRecordAdapter(Context context) {
        super(R.layout.item_rv_main_position, null);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, PositionListInfoData item) {
        helper.setText(R.id.tv_demandTitle,item.getTitle());
        helper.setText(R.id.tv_demandSalary,"¥"+item.getBudget());
        helper.setText(R.id.tv_category,item.getBgat_name());
        helper.setText(R.id.tv_platform,item.getBgap_name());
        helper.setText(R.id.tv_demandContent,item.getContent());
        helper.setText(R.id.tv_storeName,item.getShop_name());
        helper.setText(R.id.tv_state,item.getTime());
        CircleImageView ivHead = helper.getView(R.id.iv_head);
        if (item.getShop_logo()==null||item.getShop_logo().equals("")){
            ivHead.setImageResource(R.drawable.img_mine_head);
        }else {
            GlideUtil.loadImage(item.getShop_logo(),R.drawable.img_mine_head,ivHead);
        }
    }
}
