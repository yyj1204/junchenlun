package com.wktx.www.subjects.ui.adapter.message;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.message.TaskListInfoData;
import com.wktx.www.subjects.utils.GlideUtil;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yyj on 2018/1/26.
 * 工作消息-任务 适配器
 */
public class TaskAdapter extends BaseQuickAdapter<TaskListInfoData, BaseViewHolder> {
    private Context context;
    public TaskAdapter(Context context) {
        super(R.layout.item_rv_message1task, null);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskListInfoData item) {
        helper.setText(R.id.tv_demandTitle,item.getDemand_title());
        helper.setText(R.id.tv_demandContent,item.getDemand_content());
        helper.setText(R.id.tv_storeName,item.getShop_name());
        helper.setText(R.id.tv_endTime,item.getEnd_time()+"截止");
        //解雇
        ImageView ivHireState = helper.getView(R.id.iv_hireState);
        //如果解雇id不=0，说明工作消息界面里有解雇订单
        if (!item.getDismissal_id().equals("0")){
            ivHireState.setVisibility(View.VISIBLE);
            if (item.getStatus().equals("5")){//被解雇
                ivHireState.setImageResource(R.drawable.ic_manage_state5);
            }else {//解雇中
                ivHireState.setImageResource(R.drawable.ic_manage_state5ing);
            }
        }else {
            ivHireState.setVisibility(View.GONE);
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
