package com.wktx.www.subjects.ui.adapter.message;

import android.content.Context;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.message.InviteListInfoData;
import com.wktx.www.subjects.utils.GlideUtil;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yyj on 2018/1/26.
 * 互动消息-公司邀请 适配器
 */
public class InviteAdapter extends BaseQuickAdapter<InviteListInfoData, BaseViewHolder> {
    private Context context;
    public InviteAdapter(Context context) {
        super(R.layout.item_rv_message2invite, null);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, InviteListInfoData item) {
        helper.setText(R.id.tv_companyName,item.getNickname());
        helper.setText(R.id.tv_address,item.getAddress_from());
        helper.setText(R.id.tv_time,item.getAdd_time());
        helper.setText(R.id.tv_invite,"邀您加入");
        CircleImageView ivHead = helper.getView(R.id.iv_head);
        if (TextUtils.isEmpty(item.getHead_pic())){
            ivHead.setImageResource(R.drawable.img_mine_head);
        }else {
            GlideUtil.loadImage(item.getHead_pic(),R.drawable.img_mine_head,ivHead);
        }
    }
}
