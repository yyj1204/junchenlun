package com.wktx.www.subjects.ui.activity.manage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.login.AccountInfoData;
import com.wktx.www.subjects.apiresult.manage.SaleroomInfoData;
import com.wktx.www.subjects.apiresult.manage.WorkDetailsInfoData;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.presenter.manage.SaleroomPresenter;
import com.wktx.www.subjects.ui.view.manage.ISaleroomView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.GlideUtil;
import com.wktx.www.subjects.utils.LoginUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.utils.ToastUtil;
import com.wktx.www.subjects.widget.PopupPhoto;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 管理我的工作---销售额
 */
public class ManageSaleroomActivity extends ABaseActivity<ISaleroomView,SaleroomPresenter> implements ISaleroomView ,EasyPermissions.PermissionCallbacks{
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.et_saleroomTotal)
    EditText etSaleroomTotal;
    @BindView(R.id.et_saleroomUlt)
    EditText etSaleroomUlt;

    //照片
    @BindView(R.id.linear_photo_add)
    LinearLayout llPhotoAdd;
    @BindView(R.id.rela_photo)
    RelativeLayout rlPhoto;
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;

    @BindView(R.id.bt_save)//保存
            Button btSave;

    /**
     * 照片
     */
    private PopupPhoto popupPhoto;
    private int themeId;
    private int chooseMode = PictureMimeType.ofImage();
    private static List<LocalMedia> selectList = new ArrayList<>();

    private WorkDetailsInfoData workDetailsInfoData;//工作信息
    private String salesImgUrl;//销售额图片地址

    @OnClick({R.id.tb_IvReturn, R.id.linear_photo_add,R.id.iv_photo_delete,R.id.bt_save})
    public void MyOnclick(View view) {
        //将输入法隐藏
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tvTitle.getWindowToken(), 0);
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            case R.id.linear_photo_add://添加销售额数据照片
                //触发展示相片来源popupwindow
                showPhotoPopupWindow();
                break;
            case R.id.iv_photo_delete://删除
                llPhotoAdd.setVisibility(View.VISIBLE);
                rlPhoto.setVisibility(View.GONE);
                salesImgUrl="";
                break;
            case R.id.bt_save://保存
                if (MyUtils.isFastClick()){
                    return;
                }

                //判断输入框格式
                if (TextUtils.isEmpty(getTotlaSales())){
                    ToastUtil.myToast("请输入销售总额！");
                    etSaleroomTotal.requestFocus();
                }else if (TextUtils.isEmpty(getUltSales())){
                    ToastUtil.myToast("请输入上月销售额！");
                    etSaleroomUlt.requestFocus();
                }else if (TextUtils.isEmpty(getSalesImgUrl())){
                    ToastUtil.myToast("请上传销售额数据图片！");
                }else {
                    btSave.setEnabled(false);
                    getPresenter().changeSales(workDetailsInfoData.getHire_id(),workDetailsInfoData.getSales_id());
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_saleroom);
        ButterKnife.bind(this);
        initData();
        tvTitle.setText(R.string.title_manage_saleroom);
        //照片选择主题样式
        themeId = R.style.picture_default_style;
    }

    @Override
    protected SaleroomPresenter createPresenter() {
        return new SaleroomPresenter();
    }

    /**
     * 接收ManageActivity 传递过来的 工作详情（雇佣id、销售额id）
     */
    private void initData() {
        workDetailsInfoData = (WorkDetailsInfoData) getIntent().getSerializableExtra(ConstantUtil.KEY_DATA);
        if (workDetailsInfoData.getSales_id().equals("0")) {//提交销售额
            btSave.setText("提交");
        }else {//编辑销售额---获取销售额信息
            btSave.setText("保存");
            //获取销售额详情
            getPresenter().getInfo(workDetailsInfoData.getSales_id());
        }
    }

    /**
     * 照片弹窗---选择：相册||拍照
     */
    private void showPhotoPopupWindow() {
        popupPhoto = new PopupPhoto(ManageSaleroomActivity.this, ManageSaleroomActivity.this, selectList.size());
        popupPhoto.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupPhoto.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupPhoto.setClippingEnabled(false);
        popupPhoto.showPopupWindow(findViewById(R.id.toolbar));
        popupPhoto.setOnGetTypeClckListener(new PopupPhoto.onGetTypeClckListener() {
            @Override
            public void getType(ConstantUtil.Type type) {
                if (type == ConstantUtil.Type.CAMERA) {//拍照
                    checkPerm();//android系统6.0以上先检查权限
                } else if (type == ConstantUtil.Type.PHONE) {//进入相册
                    onAddPicClick(true);
                }
            }
            @Override
            public void getImgUri(Uri ImgUri, File file) {
            }
        });
    }

    public void onAddPicClick(boolean b) {
        if (b) {//进入相册---以下是例子：不需要的api可以不写
            PictureSelector.create(ManageSaleroomActivity.this)
                    .openGallery(chooseMode)// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                    .maxSelectNum(1)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .imageSpanCount(4)// 每行显示个数
                    .previewImage(true)// 是否可预览图片
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    .enableCrop(true)// 是否裁剪 true or false
                    .compress(true)// 是否压缩
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .freeStyleCropEnabled(false)// 裁剪框是否可拖拽 true or false
                    .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                    .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                    .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                    .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                    .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                    .cropCompressQuality(90)// 裁剪压缩质量 默认90 int
                    .selectionMedia(null)// 是否传入已选图片
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        } else {//单独拍照
            PictureSelector.create(ManageSaleroomActivity.this)
                    .openCamera(chooseMode)// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                    .theme(themeId)// 主题样式设置 具体参考 values/styles
                    .isCamera(true)// 是否显示拍照按钮
                    .previewImage(true)// 是否可预览图片
                    .enableCrop(true)// 是否裁剪 true or false
                    .compress(true)// 是否压缩
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .freeStyleCropEnabled(false)// 裁剪框是否可拖拽 true or false
                    .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                    .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                    .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                    .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                    .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                    .cropCompressQuality(90)// 裁剪压缩质量 默认90 int
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        }
    }

    /**
     * ISaleroomView
     */
    @Override
    public AccountInfoData getUserInfo() {
        AccountInfoData userInfo = LoginUtil.getinit().getUserInfo();
        return userInfo;
    }
    @Override
    public String getTotlaSales() {
        return etSaleroomTotal.getText().toString().trim();
    }
    @Override
    public String getUltSales() {
        return etSaleroomUlt.getText().toString().trim();
    }
    @Override
    public String getSalesImgUrl() {
        return salesImgUrl;
    }
    @Override//获取图片路径回调
    public void onGetImgUrlResult(boolean isSuccess, String result) {
        if (isSuccess){
            salesImgUrl=result;
        }else {
            ToastUtil.myToast(result);
        }
    }
    @Override//编辑销售额回调（增改）
    public void onChangeSalesResult(boolean isSuccess, String result) {
        btSave.setEnabled(true);
        ToastUtil.myToast(result);
        //成功-关闭界面
        if (isSuccess){
            finish();
        }
    }
    @Override//获取销售额详情回调
    public void onRequestSuccess(SaleroomInfoData tData) {
        salesImgUrl = tData.getLast_sales_data();
        etSaleroomTotal.setText(tData.getTotal_sales());
        etSaleroomUlt.setText(tData.getLast_month_sales());
        llPhotoAdd.setVisibility(View.GONE);
        rlPhoto.setVisibility(View.VISIBLE);
        GlideUtil.loadImage(tData.getLast_sales_data(),R.drawable.img_loading,ivPhoto);
    }
    @Override
    public void onRequestFailure(String result) {
        finish();
        ToastUtil.myToast(result);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    case PictureConfig.CHOOSE_REQUEST:// 图片选择结果回调
                        selectList = PictureSelector.obtainMultipleResult(data);
                        // 例如 LocalMedia 里面返回三种path
                        // 1.media.getPath(); 为原图path
                        // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                        // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true（音频除外）
                        // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                        if (selectList != null) {
                            LocalMedia media = selectList.get(0);
                            String path = media.getCompressPath();
                            Bitmap bitmap = BitmapFactory.decodeFile(path);
                            if (bitmap != null) {
                                llPhotoAdd.setVisibility(View.GONE);
                                rlPhoto.setVisibility(View.VISIBLE);
                                ivPhoto.setImageBitmap(bitmap);
                                String base64Str = MyUtils.Bitmap2StrByBase64(bitmap);
                                //获取图片路径
                                getPresenter().getImgUrl(base64Str);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        }

        //android系统6.0以上权限界面返回
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            //TODO （拍照权限）不做操作
        }
    }

    /**
     * Android6.0以上系统通用动态获取权限
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //将结果传入EasyPermissions中
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * EasyPermissions.PermissionCallbacks 两个方法
     * Android6.0以上系统
     */
    @Override// 请求权限已经被授权
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        //打开相机
        onAddPicClick(false);
    }
    @Override// 请求权限被拒绝
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        switch (requestCode){
            case ConstantUtil.PERMS_CODE_CAMERA:
                if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
                    new AppSettingsDialog.Builder(this)
                            .setRationale("您未允许获取摄像头权限，您可在系统设置中开启")
                            .setPositiveButton("去设置")
                            .setNegativeButton("暂不")
                            .setTitle("权限设置")
                            .build()
                            .show();
                }

                break;
            default:
                break;
        }
    }

    /**
     * 检查权限
     */
    private void checkPerm() {
        //判断相机权限是否开启
        if (!EasyPermissions.hasPermissions(ManageSaleroomActivity.this, ConstantUtil.PERMS_CAMERA)){//检查是否获取该权限
            //第二个参数是被拒绝后再次申请该权限的解释
            //第三个参数是请求码
            //第四个参数是要申请的权限
            EasyPermissions.requestPermissions(this,"拍照需要摄像头权限",ConstantUtil.PERMS_CODE_CAMERA,ConstantUtil.PERMS_CAMERA);
        }else {
            onAddPicClick(false);//打开相机
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancleMyToast();
    }
}
