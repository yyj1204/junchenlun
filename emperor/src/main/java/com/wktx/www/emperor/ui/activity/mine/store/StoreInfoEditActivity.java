package com.wktx.www.emperor.ui.activity.mine.store;
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
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.mine.store.StoreConditionBean;
import com.wktx.www.emperor.apiresult.mine.store.StoreConditionInfoData;
import com.wktx.www.emperor.apiresult.mine.store.StoreInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.mine.store.StoreInfoEditPresenter;
import com.wktx.www.emperor.ui.activity.ImageActivity;
import com.wktx.www.emperor.ui.activity.mine.CompanyInfoActivity;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.GlideUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.mine.IStoreInfoEditView;
import com.wktx.www.emperor.utils.ToastUtil;
import com.wktx.www.emperor.widget.PopupPhoto;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 店铺信息---编辑（添加）店铺
 */
public class StoreInfoEditActivity extends ABaseActivity<IStoreInfoEditView,StoreInfoEditPresenter> implements IStoreInfoEditView ,
        EasyPermissions.PermissionCallbacks{
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.civ_storeLogo)
    CircleImageView ivLogo;
    @BindView(R.id.tv_storePlatform)
    TextView tvPlatform;
    @BindView(R.id.tv_storeCategory)
    TextView tvCategory;
    @BindView(R.id.et_storeName)
    EditText etStoreName;
    @BindView(R.id.et_storeUrl)
    EditText etStoreUrl;
    @BindView(R.id.bt_save)
    Button btSave;

    /**
     * 照片
     */
    private PopupPhoto popupPhoto;//更换公司logo弹窗
    private int themeId;//主题样式设置
    private int chooseMode = PictureMimeType.ofImage();//选择图片类型
    private static List<LocalMedia> selectList = new ArrayList<>();
    private String logoBase64Str="";//logo base64图片格式


    private OptionsPickerView pvCustomOptions;//自义定选择器

    private String storeId;//店铺ID
    private String platformId;//平台ID
    private String categoryId;//类目ID

    private ArrayList<StoreConditionBean> platformBeans = new ArrayList<>();//平台名称
    private ArrayList<StoreConditionBean> categoryBeans = new ArrayList<>();//类目名称

    private ArrayList<String> optionsItemStrs = new ArrayList<>();//选择器字符串集合

    private List<String> imageUrlList = new ArrayList<>();//图片url集合

    @OnClick({R.id.tb_IvReturn,R.id.linear_storeLogo,R.id.civ_storeLogo,R.id.linear_storePlatform,R.id.linear_storeCategory,R.id.bt_save})
    public void MyOnclick(View view) {
        //将输入法隐藏
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tvTitle.getWindowToken(), 0);
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.linear_storeLogo://店铺头像
                //触发展示相片来源popuwindow
                showLogoPopup();
                break;
            case R.id.civ_storeLogo://查看店铺头像大图
                if (TextUtils.isEmpty(logoBase64Str)){//网络图片
                    if (imageUrlList.size()==0){
                        return;
                    }
                    String[] imageUrls = imageUrlList.toArray(new String[imageUrlList.size()]);
                    Intent intent = new Intent(StoreInfoEditActivity.this, ImageActivity.class);
                    intent.putExtra(ConstantUtil.KEY_DATA, imageUrls);
                    intent.putExtra(ConstantUtil.KEY_POSITION, 0);
                    startActivity(intent);
                }else {//本地裁剪后的图片
                    PictureSelector.create(StoreInfoEditActivity.this).themeStyle(themeId).openExternalPreview(0, selectList);
                }
                break;
            case R.id.linear_storePlatform://选择平台
                if (platformBeans.size()!=0){
                    initCustomOptionPicker(R.id.tv_storePlatform,platformBeans);
                }
                break;
            case R.id.linear_storeCategory://选择类目
                if (categoryBeans.size()!=0) {
                    initCustomOptionPicker(R.id.tv_storeCategory, categoryBeans);
                }
                break;
            case R.id.bt_save:
                if (MyUtils.isFastClick()){
                    return;
                }
                //判断输入框格式
                if (TextUtils.isEmpty(getStoreName())){
                    ToastUtil.myToast("请输入店铺名称！");
                    etStoreName.requestFocus();
                }else if (TextUtils.isEmpty(getStoreUrl())){
                    ToastUtil.myToast("请输入店铺网址！");
                    etStoreUrl.requestFocus();
                }else if (TextUtils.isEmpty(platformId)){
                    ToastUtil.myToast("请选择平台！");
                }else if (TextUtils.isEmpty(categoryId)){
                    ToastUtil.myToast("请选择类目！");
                }else {//编辑（添加）店铺
                    btSave.setEnabled(false);
                    getPresenter().onStoreEdit(storeId,platformId,categoryId);
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storeinfo_edit);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        //照片选择主题样式
        themeId = R.style.picture_default_style;
        initData();
    }

    @Override
    protected StoreInfoEditPresenter createPresenter() {
        return new StoreInfoEditPresenter();
    }

    /**
     * 接收 StoreInfoActivity 、DemandReleaseActivity、StaffArrangeWorkActivity传过来店铺ID
     */
    private void initData() {
        storeId = getIntent().getStringExtra(ConstantUtil.KEY_POSITION);
        if (storeId.equals("0")){//添加店铺
            tvTitle.setText(R.string.title_storeinfo_add);
            btSave.setText("添加");
        }else {//编辑店铺信息---获取店铺信息
            tvTitle.setText(R.string.title_storeinfo_edit);
            btSave.setText("保存");
            getPresenter().onGetStoreInfo(storeId);
        }
        getPresenter().onGetStoreCondition();
    }

    /**
     * 更换公司logo弹窗
     */
    private void showLogoPopup() {
        popupPhoto = new PopupPhoto(StoreInfoEditActivity.this, StoreInfoEditActivity.this);
        popupPhoto.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupPhoto.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupPhoto.setClippingEnabled(false);
        popupPhoto.showPopupWindow(findViewById(R.id.set_act_parent));
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

    /**
     * logo弹窗---选择：相册||拍照
     */
    public void onAddPicClick(boolean b) {
        if (b) {//进入相册---以下是例子：不需要的api可以不写
            PictureSelector.create(StoreInfoEditActivity.this)
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
                    .circleDimmedLayer(true)//是否圆形裁剪 true or false
                    .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                    .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                    .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                    .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                    .cropCompressQuality(90)// 裁剪压缩质量 默认90 int
                    .selectionMedia(null)// 是否传入已选图片
                    .withAspectRatio(1,1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                    .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        } else {//单独拍照
            PictureSelector.create(StoreInfoEditActivity.this)
                    .openCamera(chooseMode)// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                    .theme(themeId)// 主题样式设置 具体参考 values/styles
                    .isCamera(true)// 是否显示拍照按钮
                    .previewImage(true)// 是否可预览图片
                    .enableCrop(true)// 是否裁剪 true or false
                    .compress(true)// 是否压缩
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .freeStyleCropEnabled(false)// 裁剪框是否可拖拽 true or false
                    .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                    .rotateEnabled(true)// 裁剪是否可旋转图片 true or false
                    .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                    .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                    .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                    .cropCompressQuality(90)// 裁剪压缩质量 默认90 int
                    .withAspectRatio(1,1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        }
    }

    /**
     * IStoreInfoEditView
     */
    @Override
    public String getLogoPic() {
        return logoBase64Str;
    }
    @Override
    public String getStoreName() {
        return etStoreName.getText().toString().trim();
    }
    @Override
    public String getStoreUrl() {
        return etStoreUrl.getText().toString().trim();
    }
    @Override//获取店铺信息回调
    public void onRequestSuccess(StoreInfoData tData) {
        platformId = tData.getBgap();
        categoryId = tData.getBgat();
        //设置圆形店铺logo
        if (TextUtils.isEmpty(tData.getShop_logo())) {
            ivLogo.setImageResource(R.drawable.img_mine_head);
        }else {
            imageUrlList.add(tData.getShop_logo());
            GlideUtil.loadImage(tData.getShop_logo(),R.drawable.img_mine_head,ivLogo);
        }
        tvPlatform.setText(tData.getBgap_name());
        tvCategory.setText(tData.getBgat_name());
        etStoreName.setText(tData.getShop_name());
        etStoreUrl.setText(tData.getShop_url());
    }
    @Override
    public void onRequestFailure(String result) {
        ToastUtil.myToast(result);
        finish();
    }
    @Override//获取店铺需要的选择参数（平台、类目）
    public void onConditionSuccessResult(StoreConditionInfoData result) {
        platformBeans = result.getBgap();
        categoryBeans = result.getBgat();
    }
    @Override
    public void onConditionFailureResult(String msg) {
        ToastUtil.myToast(msg);
        finish();
    }
    @Override//编辑（添加）店铺回调
    public void onStoreInfoEditResult(boolean isSuccess, String msg) {
        ToastUtil.myToast(msg);
        btSave.setEnabled(true);
        if (isSuccess){
            finish();
        }
    }

    /**
     * 自定义选择器
     */
    private void initCustomOptionPicker(final int id, final ArrayList<StoreConditionBean> list) {//条件选择器初始化，自定义布局
        /**
         * 注意事项：
         * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
         * 具体可参考demo 里面的两个自定义layout布局。
         */
        pvCustomOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String name = list.get(options1).getName();
                if (id == R.id.tv_storePlatform){
                    tvPlatform.setText(name);
                    platformId = list.get(options1).getId();
                }else if (id == R.id.tv_storeCategory) {
                    tvCategory.setText(name);
                    categoryId = list.get(options1).getId();
                }
            }
        })
                .setLayoutRes(R.layout.widget_custom_pickerview, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        TextView tvCancel = (TextView) v.findViewById(R.id.tv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.returnData();
                                pvCustomOptions.dismiss();
                            }
                        });
                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.dismiss();
                            }
                        });
                    }
                })
                .isDialog(false)
                .setOutSideCancelable(false)
                .build();
        optionsItemStrs.clear();
        for (int i = 0; i <list.size() ; i++) {
            optionsItemStrs.add(list.get(i).getName());
        }
        pvCustomOptions.setPicker(optionsItemStrs);//添加数据
        pvCustomOptions.show();
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
                                ivLogo.setImageBitmap(bitmap);
                                logoBase64Str = MyUtils.Bitmap2StrByBase64(bitmap);
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
        if (!EasyPermissions.hasPermissions(StoreInfoEditActivity.this, ConstantUtil.PERMS_CAMERA)){//检查是否获取该权限
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
