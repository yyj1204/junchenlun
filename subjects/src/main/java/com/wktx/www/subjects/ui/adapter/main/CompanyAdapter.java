package com.wktx.www.subjects.ui.adapter.main;

import android.content.Context;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.main.CompanyListInfoData;
import com.wktx.www.subjects.utils.GlideUtil;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yyj on 2018/1/26.
 * 公司搜索 适配器
 */
public class CompanyAdapter extends BaseQuickAdapter<CompanyListInfoData, BaseViewHolder> {
    private Context context;

    public CompanyAdapter(Context context) {
        super(R.layout.item_rv_search_company, null);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CompanyListInfoData item) {
        helper.setText(R.id.tv_companyName,item.getNickname());
        helper.setText(R.id.tv_address,item.getAddress_from());
        CircleImageView ivHead = helper.getView(R.id.iv_head);
        if (TextUtils.isEmpty(item.getHead_pic())){
            ivHead.setImageResource(R.drawable.img_mine_head);
        }else {
            GlideUtil.loadImage(item.getHead_pic(),R.drawable.img_mine_head,ivHead);
        }
    }
}
