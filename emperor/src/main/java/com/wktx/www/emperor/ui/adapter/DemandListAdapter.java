package com.wktx.www.emperor.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.recruit.demand.DemandListData;
import com.wktx.www.emperor.apiresult.recruit.demand.DemandListInfoData;

/**
 * Created by yyj on 2018/1/26.
 * 需求列表适配器
 */

public class DemandListAdapter extends BaseQuickAdapter<DemandListInfoData, BaseViewHolder>{

    public DemandListAdapter() {
        super(R.layout.item_rv_demand, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, DemandListInfoData item) {
        helper.setText(R.id.tv_demandTitle, item.getTitle());
        String status = item.getStatus();
        if (status.equals("0")){//未响应
            helper.setGone(R.id.tv_demandStatus,true);
        }else if (status.equals("1")){//已响应
            helper.setGone(R.id.tv_demandStatus,false);
        }
    }
}
