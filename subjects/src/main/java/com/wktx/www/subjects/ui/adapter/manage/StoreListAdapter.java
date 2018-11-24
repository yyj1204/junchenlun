package com.wktx.www.subjects.ui.adapter.manage;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.manage.companystore.StoreListInfoData;
import com.wktx.www.subjects.utils.GlideUtil;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yyj on 2018/1/26.
 * 公司店铺列表适配器
 */
public class StoreListAdapter extends BaseQuickAdapter<StoreListInfoData, BaseViewHolder> {

    public StoreListAdapter() {
        super(R.layout.item_rv_manage_storelist, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, StoreListInfoData item) {
        helper.setText(R.id.tv_storeName,item.getShop_name());
        helper.setText(R.id.tv_storeUrl,"网址："+item.getShop_url());
        helper.setText(R.id.tv_category,"平台："+item.getBgap()+"     类目："+item.getBgat());
        CircleImageView ivLogo = helper.getView(R.id.iv_logo);
        if (TextUtils.isEmpty(item.getShop_logo())){
            ivLogo.setImageResource(R.drawable.img_mine_head);
        }else {
            GlideUtil.loadImage(item.getShop_logo(),R.drawable.img_mine_head,ivLogo);
        }
    }
}
