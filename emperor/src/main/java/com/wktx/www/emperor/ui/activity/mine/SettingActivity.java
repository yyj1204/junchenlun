package com.wktx.www.emperor.ui.activity.mine;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.utils.DataCleanUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.utils.ToastUtil;
import com.wktx.www.emperor.widget.CustomDialog;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人中心 --- 设置
 */
public class SettingActivity extends AutoLayoutActivity {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.tv_cacheSize)
    TextView tvCacheSize;

    private CustomDialog customDialog;
    private String totalCacheSize;


    @OnClick({R.id.tb_IvReturn,R.id.linear_clearCache})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            case R.id.linear_clearCache://清理缓存
                if (MyUtils.isFastClick()){
                    return;
                }
                if (totalCacheSize.equals("0K")){
                    ToastUtil.myToast("暂无本地缓存数据！");
                    return;
                }
                showClearCacheDialog();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //沉浸式状态栏
        StatusBarUtil.setColor(SettingActivity.this, getResources().getColor(R.color.color_ffb321),0);
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_set);
        getCacheSize();
    }

    //获取缓存大小，并赋值
    private void getCacheSize() {
        try {
            totalCacheSize = DataCleanUtil.getTotalCacheSize(this);
            tvCacheSize.setText(totalCacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清理缓存对话框
     */
    private void showClearCacheDialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setTitle("清理数据");
        builder.setMessage("确定清理本地缓存数据？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                DataCleanUtil.clearAllCache(SettingActivity.this);
                getCacheSize();
                ToastUtil.myToast("本地缓存数据清理成功！");
            }
        });

        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        customDialog = builder.create();
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (customDialog!=null){
            customDialog.dismiss();
            customDialog=null;
        }

        ToastUtil.cancleMyToast();
    }
}
