package com.wktx.www.emperor.ui.activity.mine.certification;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.login.AccountInfoData;
import com.wktx.www.emperor.apiresult.mine.CertificationData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.mine.certification.CertificationPersonalPresenter;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.Bitmap2Base64Util;
import com.wktx.www.emperor.utils.LoginUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.view.mine.certification.ICertificationPersonalView;
import com.wktx.www.emperor.widget.HeadPopup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 账户认证---个人认证
 */
public class CertificationPersonalActivity extends ABaseActivity<ICertificationPersonalView,CertificationPersonalPresenter>
          implements ICertificationPersonalView {
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
    private HeadPopup puWindow;
    private int themeId;
    private int chooseMode = PictureMimeType.ofImage();
    private int compressMode = PictureConfig.SYSTEM_COMPRESS_MODE;
    private static List<LocalMedia> selectList = new ArrayList<>();
    private int maxSelectNum = 1;

    private String frontBase64Str="";//正面照片Base64
    private String reverseBase64Str="";//反面照片Base64


    @OnClick({R.id.tb_IvReturn, R.id.linear_add_front,R.id.iv_delete_front,
            R.id.linear_add_reverse,R.id.iv_delete_reverse, R.id.bt_submitCertificate})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.linear_add_front://添加身份证正面照片
                isFront=true;
                //触发展示相片来源popuwindow
                showPhotoPopuWindow();
                break;
            case R.id.iv_delete_front://删除身份证正面照片
                llFront.setVisibility(View.VISIBLE);
                rlFront.setVisibility(View.GONE);
                frontBase64Str="";
                break;
            case R.id.linear_add_reverse://添加身份证反面照片
                isFront=false;
                //触发展示相片来源popuwindow
                showPhotoPopuWindow();
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
                    MyUtils.showToast(CertificationPersonalActivity.this,"请输入您的真实姓名！");
                    etName.requestFocus();
                }else if (TextUtils.isEmpty(getIdNumberStr())){
                    MyUtils.showToast(CertificationPersonalActivity.this,"请输入您的身份证号码！");
                    etIdNumber.requestFocus();
                }else if (TextUtils.isEmpty(getPositivePhotoStr())){
                    MyUtils.showToast(CertificationPersonalActivity.this,"请上传手持身份证的正面照片！");
                }else if (TextUtils.isEmpty(getReversePhotoStr())){
                    MyUtils.showToast(CertificationPersonalActivity.this,"请上传手持身份证的反面照片！");
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
        setContentView(R.layout.activity_certification_personal);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_certification_personal);
        themeId = R.style.picture_default_style;
    }

    @Override
    protected CertificationPersonalPresenter createPresenter() {
        return new CertificationPersonalPresenter();
    }

    private void showPhotoPopuWindow() {
        puWindow = new HeadPopup(CertificationPersonalActivity.this, CertificationPersonalActivity.this, selectList.size());
        puWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        puWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        puWindow.setClippingEnabled(false);
        puWindow.showPopupWindow(findViewById(R.id.toolbar));
        puWindow.setOnGetTypeClckListener(new HeadPopup.onGetTypeClckListener() {
            @Override
            public void getType(ConstantUtil.Type type) {
                if (type == ConstantUtil.Type.CAMERA) {
                    onAddPicClick(false);
                } else if (type == ConstantUtil.Type.PHONE) {
                    onAddPicClick(true);
                }
            }
            @Override
            public void getImgUri(Uri ImgUri, File file) {
            }
        });
    }

    public void onAddPicClick(boolean b) {
        boolean mode = false;
        if (b) {// 进入相册 以下是例子：不需要的api可以不写
            PictureSelector.create(CertificationPersonalActivity.this)
                    .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                    .maxSelectNum(1)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .imageSpanCount(4)// 每行显示个数
                    .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                    .previewImage(true)// 是否可预览图片
                    .compress(true)// 是否压缩
                    .compressGrade(Luban.THIRD_GEAR)// luban压缩档次，默认3档 Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                    .isCamera(false)// 是否显示拍照按钮
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    .compressMode(compressMode)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .isGif(false)// 是否显示gif图片
                    .enableCrop(true)// 是否裁剪 true or false
                    .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                    .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                    .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                    .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                    .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                    .selectionMedia(null)// 是否传入已选图片
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        } else {// 单独拍照
            PictureSelector.create(CertificationPersonalActivity.this)
                    .openCamera(chooseMode)// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                    .theme(themeId)// 主题样式设置 具体参考 values/styles
                    .maxSelectNum(maxSelectNum)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                    .previewImage(true)// 是否可预览图片
                    .compressGrade(Luban.THIRD_GEAR)// luban压缩档次，默认3档 Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                    .isCamera(true)// 是否显示拍照按钮
                    .compress(true)// 是否压缩
                    .compressMode(compressMode)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .isGif(false)// 是否显示gif图片
                    .enableCrop(true)// 是否裁剪 true or false
                    .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                    .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                    .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                    .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                    .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                    .selectionMedia(null)// 是否传入已选图片
                    .previewEggs(false)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
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
    public void onRequestSuccess(CertificationData tData) {
        MyUtils.showToast(CertificationPersonalActivity.this,"个人认证成功啦！");
        finish();
    }
    @Override
    public void onRequestFailure(String result) {
        btSubmitCertificate.setEnabled(true);
        MyUtils.showToast(CertificationPersonalActivity.this,result);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    case PictureConfig.CHOOSE_REQUEST:// 图片选择结果回调
                        selectList = PictureSelector.obtainMultipleResult(data);
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
                                }else {
                                    llReverse.setVisibility(View.GONE);
                                    rlReverse.setVisibility(View.VISIBLE);
                                    ivReverse.setImageBitmap(bitmap);
                                    reverseBase64Str = MyUtils.Bitmap2StrByBase64(bitmap);
                                }
                            }
                            // 例如 LocalMedia 里面返回三种path
                            // 1.media.getPath(); 为原图path
                            // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                            // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                            // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }
}