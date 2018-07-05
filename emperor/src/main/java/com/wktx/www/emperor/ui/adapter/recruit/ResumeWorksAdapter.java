package com.wktx.www.emperor.ui.adapter.recruit;
import android.content.Context;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.recruit.resume.WorksListInfoData;
import com.wktx.www.emperor.utils.GlideUtil;

/**
 * Created by yyj on 2018/1/26.
 * 简历---作品列表适配器
 */

public class ResumeWorksAdapter extends BaseQuickAdapter<WorksListInfoData, BaseViewHolder>{
    private Context mContext;

    public ResumeWorksAdapter(Context context) {
        super(R.layout.item_rv_resume_works, null);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, WorksListInfoData item) {
        helper.setText(R.id.tv_worksTitle,item.getTitle());
        ImageView ivWorksImage = helper.getView(R.id.iv_worksImage);
        if (item.getImage()==null||item.getImage().equals("")){
            ivWorksImage.setImageResource(R.drawable.img_load_error);
        }else {
            GlideUtil.loadImage(item.getImage(),R.drawable.img_loading,ivWorksImage);
        }
    }
}
