package com.wktx.www.emperor.ui.activity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.jaeger.library.StatusBarUtil;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.FileUtils;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.utils.ToastUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.ZOOM_FOCUS_CENTER_IMMEDIATE;

/**
 * 查看大图界面
 */
public class ImageActivity extends Activity {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tv_imgNum)
    TextView tvNum;
    @BindView(R.id.tv_save)
    TextView tvSave;

    private String[] imageUrls= new String[]{};//图片url集合
    private String curImageUrl="";//当前图片

    private int curPosition = -1;//当前图片位标
    private int[] initialedPositions = null;//初始图片位标集合
    private View curPage;

    private File imageCacheFile;

    @OnClick({R.id.tv_save})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tv_save://TODO 保存（暂不需要）
                savePhotoToLocal();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_image);
        //沉浸式状态栏
        StatusBarUtil.setColor(ImageActivity.this, getResources().getColor(R.color.color_000000),0);
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        initData();
        initInitialedPositions();
        initUI();
    }

    /**
     * 接收 首页轮播图、简历详情、作品详情、报告详情、请假详情、公司信息、店铺信息、评价员工界面 传递过来的 图片url集合
     */
    private void initData() {
        imageUrls = getIntent().getStringArrayExtra(ConstantUtil.KEY_DATA);
        curPosition = getIntent().getIntExtra(ConstantUtil.KEY_POSITION,-1);
        curImageUrl = imageUrls[curPosition];
//        imageUrls = new String[]{imageUrlStrs[curPosition]};
        initialedPositions = new int[imageUrls.length];
    }

    /**
     * 初始化图片位标集合
     */
    private void initInitialedPositions() {
        for (int i = 0; i < initialedPositions.length; i++) {
            initialedPositions[i] = -1;
        }
    }

    /**
     * 初始化控件
     */
    private void initUI() {
        viewPager.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imageUrls.length;
            }
            @Override//View 是否和 Object有关联关系
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }
            @SuppressLint("CheckResult")
            @NonNull
            @Override//初始化一个item数据的时候的回调
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                //图片url不为空
                if (imageUrls[position] != null && !"".equals(imageUrls[position])) {
                    //创建查看大图控件
                    final SubsamplingScaleImageView view = new SubsamplingScaleImageView(ImageActivity.this);
                    view.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
                    view.setMinScale(0F);//最小显示比例
                    view.setMaxScale( 5.0F);//最大显示比例

                    RequestOptions options = new RequestOptions();
                    options.centerCrop()
                            .dontAnimate()//禁用过渡动画
                            .placeholder(R.drawable.img_loading)//加载中显示的图片
                            .error(R.drawable.img_load_error)//加载失败时显示的图片
                            .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);

                    Glide.with(ImageActivity.this)
                            .load(imageUrls[position])
                            .apply(options)
                            //下载图片保存到本地
                            .downloadOnly(new SimpleTarget<File>() {
                                @Override
                                public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                                    imageCacheFile = resource;
                                    // 将保存的图片地址给SubsamplingScaleImageView,这里注意设置ImageViewState设置初始显示比例
                                    view.setImage(ImageSource.uri(resource.getAbsolutePath()), new ImageViewState(0F, new PointF(0, 0), 0));
                                }

                                @Override
                                public void onStart() {
                                    super.onStart();
                                    view.setImage(ImageSource.resource(R.drawable.img_loading));
                                }

                                @Override
                                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                    super.onLoadFailed(errorDrawable);
                                    view.setImage(ImageSource.resource(R.drawable.img_load_error));
                                }
                            });

                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    });

                    container.addView(view);
                    return view;
                }
                return null;
            }
            @Override//销毁一个item数据的时候会回调
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                releaseOnePosition(position);
                container.removeView((View) object);
            }
            @Override//设置好当前显示item后的回调
            public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                curPage = (View) object;
            }
        });

        //获取当前图片位标
//        curPosition = returnClickedPosition() == -1 ? 0 : returnClickedPosition();
        //定位到当前图片位标片段
        viewPager.setCurrentItem(curPosition);
        //为当前view设置tag
        viewPager.setTag(curPosition);
        //初始设置页面的编号
        tvNum.setText((curPosition + 1) + "/" + imageUrls.length);
        //只有一张图片的话就不显示编号了
        if (imageUrls.length==1){
            tvNum.setVisibility(View.GONE);
        }else {
            tvNum.setVisibility(View.VISIBLE);
        }

        //设置ViewPager滑动状态监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override//屏幕滚动过程中不断被调用
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override//代表哪个页面被选中
            public void onPageSelected(int position) {
                curPosition = position;
                //设置页面的编号
                tvNum.setText((position + 1) + "/" + imageUrls.length);
                //为当前view设置tag
                viewPager.setTag(position);
            }
            @Override//手指操作屏幕的时候发生变化
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * 重置已销毁图片的位标=-1
     */
    private void releaseOnePosition(int position) {
        initialedPositions[position] = -1;
    }

    /**
     * 获取点中图片的位标（暂不用---如果多张图片链接一样的话位标无效）
     */
    private int returnClickedPosition() {
        if (imageUrls == null || curImageUrl == null) {
            return -1;
        }
        for (int i = 0; i < imageUrls.length; i++) {
            if (curImageUrl.equals(imageUrls[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 保存图片至本地
     */
    private void savePhotoToLocal() {
        PhotoView photoViewTemp = (PhotoView) curPage;
        if (photoViewTemp != null) {

            BitmapDrawable bitmapDrawable = (BitmapDrawable) photoViewTemp.getDrawable();
            if (bitmapDrawable == null) {
                return;
            }

            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap == null) {
                return;
            }

            FileUtils.savePhoto(this, bitmap, new FileUtils.SaveResultCallback() {
                @Override
                public void onSavedSuccess() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.myToast("保存成功");
                        }
                    });
                }

                @Override
                public void onSavedFailed() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.myToast("保存失败");
                        }
                    });
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        curPage = null;
        if (viewPager != null) {
            viewPager.removeAllViews();
            viewPager = null;
        }
        //清除Glide硬盘缓存的图片文件
        if(imageCacheFile != null){
            imageCacheFile.delete();
        }
        super.onDestroy();

        ToastUtil.cancleMyToast();
    }
}
