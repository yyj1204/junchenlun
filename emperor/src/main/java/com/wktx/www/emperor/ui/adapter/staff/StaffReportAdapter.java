package com.wktx.www.emperor.ui.adapter.staff;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.staff.report.StaffReportInfoData;
import com.wktx.www.emperor.ui.activity.staff.report.ReportListActivity;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.DateUtil;
import com.wktx.www.emperor.utils.GlideUtil;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yyj on 2018/1/26.
 * 我的员工---工作报告（店铺）列表适配器
 */

public class StaffReportAdapter extends BaseQuickAdapter<StaffReportInfoData, BaseViewHolder>{
    private Context mContext;
    private String hireId;

    public StaffReportAdapter(Context context,String hireId) {
        super(R.layout.item_rv_staffreport, null);
        this.mContext = context;
        this.hireId = hireId;
    }

    @Override
    protected void convert(BaseViewHolder helper, final StaffReportInfoData item) {
        //店铺名称
        helper.setText(R.id.tv_storeName,item.getShop_name());
        //需求标题
        helper.setText(R.id.tv_demandTitle,item.getDemand_title());
        helper.setText(R.id.tv_time, DateUtil.getTimestamp2CustomType(item.getEnd_time(),"yyyy-MM-dd"));
        //店铺头像
        CircleImageView ivLogo = helper.getView(R.id.civ_storeLogo);
        if (item.getShop_logo()==null||item.getShop_logo().equals("")){
            ivLogo.setImageResource(R.drawable.img_mine_head);
        }else {
            GlideUtil.loadImage(item.getShop_logo(),R.drawable.img_mine_head,ivLogo);
        }

        TextView tvEvaluateNo = helper.getView(R.id.tv_evaluateNo);
        TextView tvEvaluate = helper.getView(R.id.tv_evaluate);
        //待评价(雇佣id，任务报告id，评价类型0) ---ReportListActivity
        tvEvaluateNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ReportListActivity.class);
                intent.putExtra(ConstantUtil.KEY_POSITION,hireId);
                intent.putExtra(ConstantUtil.KEY_DATA,item.getId());
                intent.putExtra(ConstantUtil.KEY_ISOK,"0");
                mContext.startActivity(intent);
            }
        });
        //已评价(雇佣id，任务报告id，评价类型1) ---ReportListActivity
        tvEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ReportListActivity.class);
                intent.putExtra(ConstantUtil.KEY_POSITION,hireId);
                intent.putExtra(ConstantUtil.KEY_DATA,item.getId());
                intent.putExtra(ConstantUtil.KEY_ISOK,"1");
                mContext.startActivity(intent);
            }
        });
    }
}
