package com.wktx.www.subjects.ui.activity.mine.resume;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.mine.resume.ResumeInfoData;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.presenter.mine.resume.ResumeUploadPresenter;
import com.wktx.www.subjects.ui.activity.ImageActivity;
import com.wktx.www.subjects.ui.activity.mine.PersonInfoActivity;
import com.wktx.www.subjects.ui.activity.mine.works.WorksDetailsActivity;
import com.wktx.www.subjects.ui.view.mine.resume.IResumeUploadView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.GlideUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.utils.ToastUtil;
import com.wktx.www.subjects.widget.PopupPhoto;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 我的简历 --- 上传个性简历
 */
public class ResumeUploadActivity extends ABaseActivity<IResumeUploadView,ResumeUploadPresenter> implements IResumeUploadView ,EasyPermissions.PermissionCallbacks{
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;

    //照片
    @BindView(R.id.linear_add_resume)
    LinearLayout llResume;
    @BindView(R.id.rela_resume)
    RelativeLayout rlResume;
    @BindView(R.id.iv_resume)
    ImageView ivResume;

    @BindView(R.id.bt_sureUpload)
    Button btSureUpload;//确认上传

    private List<String> imageUrlList = new ArrayList<>();//图片url集合

    /**
     * 照片
     */
    private PopupPhoto popupPhoto;
    private int themeId;
    private int chooseMode = PictureMimeType.ofImage();
    private static List<LocalMedia> selectList = new ArrayList<>();
    private String base64Str="";//base64图片格式

    private ResumeInfoData resumeInfoData;//简历信息
    private String resumeImgUrl;//简历图片地址

    @OnClick({R.id.tb_IvReturn, R.id.linear_add_resume,R.id.iv_resume,R.id.iv_delete_resume,R.id.bt_sureUpload})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.linear_add_resume://添加简历图片
                //触发展示相片来源popuwindow
                showPhotoPopupWindow();
                break;
            case R.id.iv_resume://预览图片
                if (TextUtils.isEmpty(base64Str)){//网络图片
                    if (imageUrlList.size()==0){
                        return;
                    }
                    String[] imageUrls = imageUrlList.toArray(new String[imageUrlList.size()]);
                    Intent intent = new Intent(ResumeUploadActivity.this, ImageActivity.class);
                    intent.putExtra(ConstantUtil.KEY_DATA, imageUrls);
                    intent.putExtra(ConstantUtil.KEY_POSITION, 0);
                    startActivity(intent);
                }else {//本地裁剪后的图片
                    PictureSelector.create(ResumeUploadActivity.this).themeStyle(themeId).openExternalPreview(0, selectList);
                }
                break;
            case R.id.iv_delete_resume://删除简历图片
                llResume.setVisibility(View.VISIBLE);
                rlResume.setVisibility(View.GONE);
                base64Str="";
                resumeImgUrl="";
                break;
            case R.id.bt_sureUpload://上传
                if (MyUtils.isFastClick()){
                    return;
                }

                //如果base64Str为空，说明不传图片，即传空路径。
                if (TextUtils.isEmpty(base64Str)){
                    if (TextUtils.isEmpty(resumeImgUrl)){
                        btSureUpload.setEnabled(false);
                        getPresenter().changeImgUrl(resumeInfoData.getId(),"");
                    }else {
                        ToastUtil.myToast("个性简历未改变，无法重复上传！");
                    }
                }else {//如果base64Str不为空,先获取图片路径再上传
                    btSureUpload.setEnabled(false);
                    getPresenter().getInfo(base64Str);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_upload);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.title_resume_upload);
        //照片选择主题样式
        themeId = R.style.picture_default_style;
        initData();
    }

    @Override
    protected ResumeUploadPresenter createPresenter() {
        return new ResumeUploadPresenter();
    }

    /**
     * 接收 MyResumeActivity 传过来 resumeInfoData
     */
    private void initData() {
        resumeInfoData = (ResumeInfoData) getIntent().getSerializableExtra(ConstantUtil.KEY_DATA);
        resumeImgUrl = resumeInfoData.getResume_content();

        if (TextUtils.isEmpty(resumeImgUrl)){
            llResume.setVisibility(View.VISIBLE);
            rlResume.setVisibility(View.GONE);
        }else {
            imageUrlList.add(resumeImgUrl);
            llResume.setVisibility(View.GONE);
            rlResume.setVisibility(View.VISIBLE);
            GlideUtil.loadImageAuto(resumeImgUrl,R.drawable.img_loading,R.drawable.img_load_error,ivResume);
        }
    }

    /**
     * 照片弹窗---选择：相册||拍照
     */
    private void showPhotoPopupWindow() {
        popupPhoto = new PopupPhoto(ResumeUploadActivity.this, ResumeUploadActivity.this);
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
            PictureSelector.create(ResumeUploadActivity.this)
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
                    .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        } else {//单独拍照
            PictureSelector.create(ResumeUploadActivity.this)
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
     * IResumeUploadView
     */
    @Override//获取图片路径回调
    public void onRequestSuccess(String tData) {
        getPresenter().changeImgUrl(resumeInfoData.getId(),tData);
    }
    @Override
    public void onRequestFailure(String result) {
        btSureUpload.setEnabled(true);
        ToastUtil.myToast(result);
    }
    @Override//上传个性简历回调
    public void onChangeResumeImgResult(boolean isSuccess, String result) {
        btSureUpload.setEnabled(true);
        ToastUtil.myToast(result);
        //成功-关闭界面
        if (isSuccess){
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    case PictureConfig.CHOOSE_REQUEST://图片选择结果回调
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
                                llResume.setVisibility(View.GONE);
                                rlResume.setVisibility(View.VISIBLE);
                                ivResume.setImageBitmap(bitmap);
                                base64Str = MyUtils.Bitmap2StrByBase64(bitmap);
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
        if (!EasyPermissions.hasPermissions(ResumeUploadActivity.this, ConstantUtil.PERMS_CAMERA)){//检查是否获取该权限
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
