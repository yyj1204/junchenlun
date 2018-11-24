package com.wktx.www.subjects.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wktx.www.subjects.R;
import com.wktx.www.subjects.ui.adapter.GuidePageAdapter;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 新手引导界面
 */
public class GuideActivity extends AutoLayoutActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.linear_point)
    ViewGroup vgPosition;//放置圆点
    @BindView(R.id.tv_start)//最后一页的按钮
            TextView tvStart;

    //图片资源的数组
    private int []imageIdArray = new int[]{R.drawable.img_guide1,R.drawable.img_guide2,R.drawable.img_guide3};
    //图片资源的集合
    private List<View> viewList = new ArrayList<>();

    //实例化原点View
    private ImageView ivPoint;
    private ImageView []ivPointArray;

    private LinearLayout.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);

        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideActivity.this,MainActivity.class));
                finish();
            }
        });

        //加载ViewPager
        initViewPager();
        //加载底部圆点
        initPoint();
    }
    /**
     * 加载图片ViewPager
     */
    private void initViewPager() {
        //获取一个Layout参数，设置为全屏
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);

        //循环创建View并加入到集合中
        for (int i = 0;i<imageIdArray.length;i++){
            //new ImageView并设置全屏和图片资源
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setBackgroundResource(imageIdArray[i]);

            //将ImageView加入到集合中
            viewList.add(imageView);
        }

        //View集合初始化好后，设置Adapter
        viewPager.setAdapter(new GuidePageAdapter(viewList));
        //设置滑动监听
        viewPager.setOnPageChangeListener(this);
    }

    /**
     * 加载底部圆点
     */
    private void initPoint() {
        //根据ViewPager的item数量实例化数组
        ivPointArray = new ImageView[viewList.size()];
        //循环新建底部圆点ImageView，将生成的ImageView保存到数组中
        int size = viewList.size();
        for (int i = 0;i<size;i++){
            ivPoint = new ImageView(this);
            layoutParams = new LinearLayout.LayoutParams(15,15);

            //第一个页面需要设置为选中状态，这里采用两张不同的图片
            if (i == 0){
                ivPoint.setBackgroundResource(R.drawable.shape_circle_ffb321);
            }else{
                layoutParams.leftMargin=20;
                ivPoint.setBackgroundResource(R.drawable.shape_circle_f0f0f0);
            }
            ivPoint.setLayoutParams(layoutParams);
            ivPoint.setPadding(30,0,30,0);//left,top,right,bottom
            ivPointArray[i] = ivPoint;

            //将数组中的ImageView加入到ViewGroup
            vgPosition.addView(ivPointArray[i]);
        }
    }


    /**
     * ViewPager.OnPageChangeListener
     * onPageScrolled()
     * onPageSelected()
     * onPageScrollStateChanged()
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }
    @Override//滑动后的监听
    public void onPageSelected(int position) {
        //循环设置当前页的标记图
        int length = imageIdArray.length;
        for (int i = 0;i<length;i++){
            ivPointArray[position].setBackgroundResource(R.drawable.shape_circle_ffb321);
            if (position != i){
                ivPointArray[i].setBackgroundResource(R.drawable.shape_circle_f0f0f0);
            }
        }

        //判断是否是最后一页，若是则显示按钮
        if (position == imageIdArray.length - 1){
            tvStart.setVisibility(View.VISIBLE);
        }else {
            tvStart.setVisibility(View.GONE);
        }
    }
    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
