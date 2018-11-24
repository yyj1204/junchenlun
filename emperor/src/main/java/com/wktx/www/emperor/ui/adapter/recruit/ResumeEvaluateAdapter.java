package com.wktx.www.emperor.ui.adapter.recruit;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hedgehog.ratingbar.RatingBar;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.recruit.resume.EvaluateInfoData;
import com.wktx.www.emperor.ui.activity.ImageActivity;
import com.wktx.www.emperor.ui.activity.recruit.resume.WorksDetailsActivity;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.DateUtil;
import com.wktx.www.emperor.utils.GlideUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.utils.MyUtils;

import java.util.List;

/**
 * Created by yyj on 2018/1/26.
 * 简历---评价列表适配器
 */

public class ResumeEvaluateAdapter extends BaseQuickAdapter<EvaluateInfoData, BaseViewHolder>{
    private Context mContext;

    public ResumeEvaluateAdapter(Context context) {
        super(R.layout.item_rv_resume_evaluate, null);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, EvaluateInfoData item) {
        if (TextUtils.isEmpty(item.getNickname())){
            helper.setText(R.id.tv_employerName,"匿名用户");
        }else {
            helper.setText(R.id.tv_employerName,item.getNickname());
        }
        helper.setText(R.id.tv_time, DateUtil.getTimestamp2CustomType(item.getAdd_time(), "yyyy.MM.dd HH:mm:ss"));
        //评价等级
        RatingBar rbServiceAttitude = helper.getView(R.id.rb_serviceAttitude);
        RatingBar rbDesignAbility = helper.getView(R.id.rb_designAbility);
        RatingBar rbResponseSpeed = helper.getView(R.id.rb_responseSpeed);
        rbServiceAttitude.setStar(Float.parseFloat(item.getService_attitude()));
        rbDesignAbility.setStar(Float.parseFloat(item.getAbility()));
        rbResponseSpeed.setStar(Float.parseFloat(item.getResponse_speed()));
        //评价内容
        if (TextUtils.isEmpty(item.getEvaluation_content())){
            helper.setText(R.id.tv_evaluateContent,"没有留下任何评价！");
        }else {
            helper.setText(R.id.tv_evaluateContent,item.getEvaluation_content());
        }
        //评价图片
        List<String> imageBeans = item.getImages();
        NineGridImageView nglWorks = helper.getView(R.id.ngl_evaluateImages);
        if (imageBeans.size()!=0){
            nglWorks.setVisibility(View.VISIBLE);
            NineGridImageViewAdapter<String> adapter = new NineGridImageViewAdapter<String>() {
                @Override
                protected void onDisplayImage(Context context, ImageView imageView, String s) {
                    if (imageView != null && s != null) {
                        GlideUtil.loadImage(s,R.drawable.img_loading,imageView);
                    }
                }
                @Override
                protected ImageView generateImageView(Context context) {
                    return super.generateImageView(context);
                }
                @Override
                protected void onItemImageClick(Context context, ImageView imageView, int index, List<String> list) {
                    if (MyUtils.isFastClick1()){
                        return;
                    }
                    //查看大图
                    String[] imageUrls = list.toArray(new String[list.size()]);
                    Intent intent = new Intent(mContext, ImageActivity.class);
                    intent.putExtra(ConstantUtil.KEY_DATA, imageUrls);
                    intent.putExtra(ConstantUtil.KEY_POSITION,index);
                    mContext.startActivity(intent);
                }
            };
            nglWorks.setAdapter(adapter);
            nglWorks.setImagesData(imageBeans);
            nglWorks.setSingleImgSize(450);
        }else {
            nglWorks.setVisibility(View.GONE);
        }
    }
}
