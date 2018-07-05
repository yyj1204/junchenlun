package com.wktx.www.subjects.ui.activity.mine.certification;
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
import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.login.AccountInfoData;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.presenter.mine.CertificationPresenter;
import com.wktx.www.subjects.ui.view.mine.ICertificationView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.LogUtil;
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
 * 个人中心 --- 个人认证
 */
public class CertificationActivity extends ABaseActivity<ICertificationView,CertificationPresenter>
        implements ICertificationView,EasyPermissions.PermissionCallbacks {

    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    //正面照片
    @BindView(R.id.linear_add_front)
    LinearLayout llFront;
    @BindView(R.id.rela_photo_front)
    RelativeLayout rlFront;
    @BindView(R.id.iv_photo_front)
    ImageView ivFront;
    //反面照片
    @BindView(R.id.linear_add_reverse)
    LinearLayout llReverse;
    @BindView(R.id.rela_photo_reverse)
    RelativeLayout rlReverse;
    @BindView(R.id.iv_photo_reverse)
    ImageView ivReverse;

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_idNumber)
    EditText etIdNumber;
    @BindView(R.id.bt_submitCertificate)
    Button btSubmitCertificate;//提交认证

    private boolean isFront;//true：是正面照片，false：是反面照片
    private PopupPhoto popupPhoto;
    private int themeId;
    private int chooseMode = PictureMimeType.ofImage();
    private static List<LocalMedia> selectList = new ArrayList<>();

    private String frontBase64Str="";//正面照片Base64
    private String reverseBase64Str="";//反面照片Base64

    @OnClick({R.id.tb_IvReturn, R.id.linear_add_front,R.id.iv_delete_front,
            R.id.linear_add_reverse,R.id.iv_delete_reverse, R.id.bt_submitCertificate})
    public void MyOnclick(View view) {
        //将输入法隐藏
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tvTitle.getWindowToken(), 0);
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.linear_add_front://添加身份证正面照片
                isFront=true;
                //触发展示相片来源popuwindow
                showPhotoPopupWindow();
                break;
            case R.id.iv_delete_front://删除身份证正面照片
                llFront.setVisibility(View.VISIBLE);
                rlFront.setVisibility(View.GONE);
                frontBase64Str="";
                break;
            case R.id.linear_add_reverse://添加身份证反面照片
                isFront=false;
                //触发展示相片来源popuwindow
                showPhotoPopupWindow();
                break;
            case R.id.iv_delete_reverse://删除身份证反面照片
                llReverse.setVisibility(View.VISIBLE);
                rlReverse.setVisibility(View.GONE);
                reverseBase64Str="";
                break;
            case R.id.bt_submitCertificate://提交认证
                if (MyUtils.isFastClick()){
                    return;
                }

                //判断输入框格式
                if (TextUtils.isEmpty(getNameStr())){
                     ToastUtil.myToast("请输入您的真实姓名！");
                    etName.requestFocus();
                }else if (TextUtils.isEmpty(getIdNumberStr())){
                     ToastUtil.myToast("请输入您的身份证号码！");
                    etIdNumber.requestFocus();
                }else if (TextUtils.isEmpty(getPositivePhotoStr())){
                     ToastUtil.myToast("请上传手持身份证的正面照片！");
                }else if (TextUtils.isEmpty(getReversePhotoStr())){
                     ToastUtil.myToast("请上传手持身份证的反面照片！");
                }else {//注册
                    btSubmitCertificate.setEnabled(false);
                    getPresenter().onCertification();
                }
                break;
            default:
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_certification);
        //照片选择主题样式
        themeId = R.style.picture_default_style;
    }

    @Override
    protected CertificationPresenter createPresenter() {
        return new CertificationPresenter();
    }

    /**
     * 照片弹窗---选择：相册||拍照
     */
    private void showPhotoPopupWindow() {
        popupPhoto = new PopupPhoto(CertificationActivity.this, CertificationActivity.this, selectList.size());
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
            PictureSelector.create(CertificationActivity.this)
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
            PictureSelector.create(CertificationActivity.this)
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
     * ICertificationPersonalView
     */
    @Override
    public AccountInfoData getUserInfo() {
        AccountInfoData userInfo = LoginUtil.getinit().getUserInfo();
        return userInfo;
    }
    @Override
    public String getNameStr() {
        return etName.getText().toString().trim();
    }
    @Override
    public String getIdNumberStr() {
        return etIdNumber.getText().toString().trim();
    }
    @Override
    public String getPositivePhotoStr() {
        return frontBase64Str;
    }
    @Override
    public String getReversePhotoStr() {
        return reverseBase64Str;
    }
    @Override
    public void onRequestSuccess(String result) {
         ToastUtil.myToast(result);
        finish();
    }
    @Override
    public void onRequestFailure(String result) {
        btSubmitCertificate.setEnabled(true);
         ToastUtil.myToast(result);
    }
    //重新认证界面
    @Override
    public void onGetImgUrlResult(boolean isSuccess, String result) {
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
                                if (isFront){//如果是身份证正面照片
                                    llFront.setVisibility(View.GONE);
                                    rlFront.setVisibility(View.VISIBLE);
                                    ivFront.setImageBitmap(bitmap);
                                    frontBase64Str = MyUtils.Bitmap2StrByBase64(bitmap);
                                    LogUtil.error("base64",frontBase64Str);
                                }else {//如果是身份证反面照片
                                    llReverse.setVisibility(View.GONE);
                                    rlReverse.setVisibility(View.VISIBLE);
                                    ivReverse.setImageBitmap(bitmap);
                                    reverseBase64Str = MyUtils.Bitmap2StrByBase64(bitmap);
                                }
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
        if (!EasyPermissions.hasPermissions(CertificationActivity.this, ConstantUtil.PERMS_CAMERA)){//检查是否获取该权限
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
