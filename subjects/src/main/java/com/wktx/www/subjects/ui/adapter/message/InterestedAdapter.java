package com.wktx.www.subjects.ui.adapter.message;

import android.content.Context;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.message.InterestedListInfoData;
import com.wktx.www.subjects.utils.GlideUtil;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yyj on 2018/1/26.
 * 互动消息-对我感兴趣 适配器
 */
public class InterestedAdapter extends BaseQuickAdapter<InterestedListInfoData, BaseViewHolder> {
    private Context context;
    public InterestedAdapter(Context context) {
        super(R.layout.item_rv_message2interested, null);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, InterestedListInfoData item) {
        helper.setText(R.id.tv_companyName,item.getNickname());
        helper.setText(R.id.tv_time,item.getAdd_time());
        CircleImageView ivHead = helper.getView(R.id.iv_head);
        if (item.getHead_pic()==null||item.getHead_pic().equals("")){
            ivHead.setImageResource(R.drawable.img_mine_head);
        }else {
            GlideUtil.loadImage(item.getHead_pic(),R.drawable.img_mine_head,ivHead);
        }
    }
}
