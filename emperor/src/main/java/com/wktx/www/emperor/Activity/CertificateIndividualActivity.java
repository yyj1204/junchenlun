package com.wktx.www.emperor.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.Bitmap2Base64Util;
import com.wktx.www.emperor.utils.SharedPreferenceUtil;
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
public class CertificateIndividualActivity extends AppCompatActivity {

    @BindView(R.id.tb_TvBarTitle)
    TextView mTvTitle;
    //正面照片
    @BindView(R.id.linear_add_front)
    LinearLayout linear_front;
    @BindView(R.id.rela_photo_front)
    RelativeLayout rela_front;
    @BindView(R.id.iv_photo_front)
    ImageView iv_photo_front;
    //反面照片
    @BindView(R.id.linear_add_contrary)
    LinearLayout linear_contrary;
    @BindView(R.id.rela_photo_contrary)
    RelativeLayout rela_contrary;
    @BindView(R.id.iv_photo_contrary)
    ImageView iv_photo_contrary;

    private boolean isFront;//true：是正面照片，false：是反面照片
    private HeadPopup puWindow;
    private int themeId;
    private int chooseMode = PictureMimeType.ofImage();
    private int compressMode = PictureConfig.SYSTEM_COMPRESS_MODE;
    private static List<LocalMedia> selectList = new ArrayList<>();
    private int maxSelectNum = 1;


    @OnClick({R.id.tb_IvReturn, R.id.linear_add_front,R.id.iv_delete_front,
            R.id.linear_add_contrary,R.id.iv_delete_contrary, R.id.tv_certificate_submit})
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
                linear_front.setVisibility(View.VISIBLE);
                rela_front.setVisibility(View.GONE);
                break;
            case R.id.linear_add_contrary://添加身份证反面照片
                isFront=false;
                //触发展示相片来源popuwindow
                showPhotoPopuWindow();
                break;
            case R.id.iv_delete_contrary://删除身份证正面照片
                linear_contrary.setVisibility(View.VISIBLE);
                rela_contrary.setVisibility(View.GONE);
                break;
            case R.id.tv_certificate_submit://提交认证
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate_individual);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        mTvTitle.setText(R.string.title_certificate_individual);
        themeId = R.style.picture_default_style;
    }

    private void showPhotoPopuWindow() {
        puWindow = new HeadPopup(CertificateIndividualActivity.this, CertificateIndividualActivity.this, selectList.size());
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
            PictureSelector.create(CertificateIndividualActivity.this)
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
            PictureSelector.create(CertificateIndividualActivity.this)
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    case PictureConfig.CHOOSE_REQUEST:
                        // 图片选择结果回调
                        selectList = PictureSelector.obtainMultipleResult(data);
                        if (selectList != null) {
                            LocalMedia media = selectList.get(0);
                            String path = media.getCompressPath();
                            Bitmap bitmap = BitmapFactory.decodeFile(path);
                            if (bitmap != null) {
                                if (isFront){//如果是正面照片
                                    linear_front.setVisibility(View.GONE);
                                    rela_front.setVisibility(View.VISIBLE);
                                    iv_photo_front.setImageBitmap(bitmap);
                                }else {
                                    linear_contrary.setVisibility(View.GONE);
                                    rela_contrary.setVisibility(View.VISIBLE);
                                    iv_photo_contrary.setImageBitmap(bitmap);
                                }

                            }
                            String s = Bitmap2Base64Util.Bitmap2StrByBase64(bitmap);
                            SharedPreferenceUtil.saveData(this, ConstantUtil.HEADBASE64STR_KEY, s, ConstantUtil.HEADBASE64_NAME);

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
