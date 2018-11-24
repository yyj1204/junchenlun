package com.wktx.www.subjects.ui.adapter.manage;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.message.TaskListInfoData;
import com.wktx.www.subjects.utils.DateUtil;
import com.wktx.www.subjects.utils.GlideUtil;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yyj on 2018/6/26.
 * 管理我的工作- 工作安排列表适配器
 */
public class WorkManageAdapter extends BaseQuickAdapter<TaskListInfoData, BaseViewHolder> {
    private Context context;
    private String currentTime;

    public WorkManageAdapter(Context context) {
        super(R.layout.item_rv_manage_work, null);
        this.context = context;
        currentTime = DateUtil.getCurrentDateNYRSFM2();
    }

    @Override
    protected void convert(final BaseViewHolder helper, TaskListInfoData item) {
        String endTime = item.getEnd_time();
        helper.setText(R.id.tv_demandTitle,item.getDemand_title());
        helper.setText(R.id.tv_demandContent,item.getDemand_content());
        helper.setText(R.id.tv_storeName,item.getShop_name());
        helper.setText(R.id.tv_endTime,endTime+"截止");

        //今日工作报告
        TextView tvReportState = helper.getView(R.id.tv_reportState);
        //截止日期跟当前日期比较
        boolean isBigger = DateUtil.compareDate2Bigger3(endTime, currentTime, "yyyy-MM-dd HH:mm:ss");
        if (isBigger){//如果isBigger=true，说明截止日期未到
            if (item.getReport_id().equals("0")){
                tvReportState.setText("提交报告");
            }else {
                tvReportState.setText("查看报告");
            }
            tvReportState.setBackgroundResource(R.drawable.shape_solid_ffb321_10);
        }else {
            tvReportState.setText("工作已结束");
            tvReportState.setBackgroundResource(R.drawable.shape_solid_cccccc_10);
        }

        //头像
        CircleImageView ivHead = helper.getView(R.id.iv_head);
        if (TextUtils.isEmpty(item.getShop_logo())){
            ivHead.setImageResource(R.drawable.img_mine_head);
        }else {
            GlideUtil.loadImage(item.getShop_logo(),R.drawable.img_mine_head,ivHead);
        }
    }
}
