package com.wktx.www.emperor.ui.activity.mine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.mine.companyinfo.CompanyInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.mine.CompanyInfoPresenter;
import com.wktx.www.emperor.ui.activity.ImageActivity;
import com.wktx.www.emperor.ui.activity.mine.authent.AuthentStoreDetailsActivity;
import com.wktx.www.emperor.ui.activity.recruit.resume.WorksDetailsActivity;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.model.ProvinceJsonBean;
import com.wktx.www.emperor.utils.GetJsonDataUtil;
import com.wktx.www.emperor.utils.GlideUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.mine.ICompanyInfoView;
import com.wktx.www.emperor.utils.ToastUtil;
import com.wktx.www.emperor.widget.PopupPhoto;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 个人中心---公司信息
 */
public class CompanyInfoActivity extends ABaseActivity<ICompanyInfoView,CompanyInfoPresenter>
        implements ICompanyInfoView ,EasyPermissions.PermissionCallbacks{
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.civ_logo)
    ImageView ivLogo;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_qq)
    EditText etQQ;
    @BindView(R.id.et_wechat)
    EditText etWechat;
    @BindView(R.id.et_introduce)
    EditText etIntroduce;
    @BindView(R.id.bt_sureEdit)
    Button btSureEdit;//确认修改

    private boolean isEditApi=false;//是否修改信息接口请求

    private List<String> imageUrlList = new ArrayList<>();//图片url集合

    /**
     * 照片
     */
    private PopupPhoto popupPhoto;//更换公司logo弹窗
    private int themeId;//主题样式设置
    private int chooseMode = PictureMimeType.ofImage();//选择图片类型
    private static List<LocalMedia> selectList = new ArrayList<>();
    private String logoBase64Str="";//logo base64图片格式

    /**
     * 地址选择器
     */
    private boolean isLoaded = false;//assets目录下的三级城市json数据是否解析完
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private ArrayList<ProvinceJsonBean> options1Items = new ArrayList<>();//一级
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();//二级
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();//三级

    /**
     * 解析assets目录下的三级城市province.json数据
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA://解析开始
                    if (thread == null) {//如果已创建就不再重新创建子线程了
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 写子线程中的操作,解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;
                case MSG_LOAD_SUCCESS://解析成功
                    isLoaded = true;
                    break;
                case MSG_LOAD_FAILED://解析失败
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 解析 assets 目录下的Json数据
     * 注意：Json文件仅供参考，实际使用可自行替换文件
     * 关键逻辑在于循环体
     */
    private void initJsonData() {
        String JsonData = new GetJsonDataUtil().getJson(this, "region.json");//获取assets目录下的json文件数据
        ArrayList<ProvinceJsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体
        /**
         * 添加省份数据
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        //1.遍历省份
        for (int p = 0; p < jsonBean.size(); p++) {
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有城市的所有地区列表（第三极）

            //2.遍历该省份的所有城市
            for (int c = 0; c < jsonBean.get(p).getCityList().size(); c++) {
                String CityName = jsonBean.get(p).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(p).getCityList().get(c).getArea() == null
                        || jsonBean.get(p).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    //3.遍历该城市对应地区所有数据
                    for (int a = 0; a < jsonBean.get(p).getCityList().get(c).getArea().size(); a++) {
                        String AreaName = jsonBean.get(p).getCityList().get(c).getArea().get(a);
                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }
            //添加城市数据
            options2Items.add(CityList);
            //添加地区数据
            options3Items.add(Province_AreaList);
        }
        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);
    }

    /**
     * Gson 解析
     */
    public ArrayList<ProvinceJsonBean> parseData(String result) {
        ArrayList<ProvinceJsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                ProvinceJsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), ProvinceJsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    /**
     * 普通点击事件
     */
    @OnClick({R.id.tb_IvReturn, R.id.linear_logo,R.id.civ_logo,R.id.linear_address, R.id.bt_sureEdit})
    public void MyOnclick(View view) {
        if (MyUtils.isFastClick1()){
            return;
        }

        //将输入法隐藏
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tvTitle.getWindowToken(), 0);
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.linear_logo://公司logo
                //触发展示相片来源popuwindow
                showLogoPopup();
                break;
            case R.id.civ_logo://查看logo大图
                if (TextUtils.isEmpty(logoBase64Str)){//网络图片
                    if (imageUrlList.size()==0){
                        return;
                    }
                    String[] imageUrls = imageUrlList.toArray(new String[imageUrlList.size()]);
                    Intent intent = new Intent(CompanyInfoActivity.this, ImageActivity.class);
                    intent.putExtra(ConstantUtil.KEY_DATA, imageUrls);
                    intent.putExtra(ConstantUtil.KEY_POSITION, 0);
                    startActivity(intent);
                }else {//本地裁剪后的图片
                    PictureSelector.create(CompanyInfoActivity.this).themeStyle(themeId).openExternalPreview(0, selectList);
                }
                break;
            case R.id.linear_address://公司地址
                if (isLoaded) {
                    ShowPickerView();//地址选择器
                } else {
                    ToastUtil.myToast("数据暂未解析成功，请等待");
                }
                break;
            case R.id.bt_sureEdit://确认修改
                if (MyUtils.isFastClick()){
                    return;
                }
                //判断输入框格式
                if (TextUtils.isEmpty(getNickName())){
                    ToastUtil.myToast("请输入公司名称！");
                    etName.requestFocus();
                }else if (TextUtils.isEmpty(getAddress())) {
                    ToastUtil.myToast("请选择公司地址！");
                }else if (TextUtils.isEmpty(getPhone())){
                    ToastUtil.myToast("请输入手机号码！");
                    etPhone.requestFocus();
                }else if (!MyUtils.checkMobileNumber(getPhone())) {
                    ToastUtil.myToast("手机号码输入不正确！");
                    etPhone.requestFocus();
                }else if (TextUtils.isEmpty(getQQ())){
                    ToastUtil.myToast("请输入QQ号码！");
                    etQQ.requestFocus();
                }else if (TextUtils.isEmpty(getWechat())){
                    ToastUtil.myToast("请输入微信号码！");
                    etWechat.requestFocus();
                }else if (TextUtils.isEmpty(getIntroduce())){
                    ToastUtil.myToast("请输入公司简介！");
                    etIntroduce.requestFocus();
                }else {//修改公司信息
                    isEditApi=true;
                    btSureEdit.setEnabled(false);
                    getPresenter().onEditCompanyInfo();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_info);
        ButterKnife.bind(this);
        //设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_company_info);
        //照片选择主题样式
        themeId = R.style.picture_default_style;
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        initData();
    }


    @Override
    protected CompanyInfoPresenter createPresenter() {
        return new CompanyInfoPresenter();
    }

    private void initData() {
        getPresenter().onGetCompanyInfo();
    }

    /**
     * 更换公司logo弹窗
     */
    private void showLogoPopup() {
        popupPhoto = new PopupPhoto(CompanyInfoActivity.this, CompanyInfoActivity.this);
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
            PictureSelector.create(CompanyInfoActivity.this)
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
            PictureSelector.create(CompanyInfoActivity.this)
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
     * 三级联动---地址选择器
     */
    private void ShowPickerView() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String pickerViewStr = options1Items.get(options1).getPickerViewText()+"-"+
                        options2Items.get(options1).get(options2) +"-"+
                        options3Items.get(options1).get(options2).get(options3);
                //选择器返回值赋值给 tvAddress
                tvAddress.setText(pickerViewStr);
            }
        })
                .setTitleText("地址选择")
                .setDividerColor(getResources().getColor(R.color.color_f0f0f0))
                .setTextColorCenter(getResources().getColor(R.color.color_000000)) //设置选中项文字颜色
                .setContentTextSize(14)
                .setOutSideCancelable(false)// 默认为 true
                .setSubmitColor(getResources().getColor(R.color.color_ffb321))
                .setCancelColor(getResources().getColor(R.color.color_000000))
                .build();
        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }


    /**
     * ICompanyInfoView
     */
    @Override
    public String getHeadPic() {
        return logoBase64Str;
    }
    @Override
    public String getNickName() {
        return etName.getText().toString().trim();
    }
    @Override
    public String getAddress() {
        return tvAddress.getText().toString().trim();
    }
    @Override
    public String getPhone() {
        return etPhone.getText().toString().trim();
    }
    @Override
    public String getQQ() {
        return etQQ.getText().toString().trim();
    }
    @Override
    public String getWechat() {
        return etWechat.getText().toString().trim();
    }
    @Override
    public String getIntroduce() {
        return etIntroduce.getText().toString().trim();
    }
    @Override//获取公司信息与修改公司信息共用一个回调方法
    public void onRequestSuccess(CompanyInfoData tData) {
        if (isEditApi){//如果是修改信息请求，按钮可重新点击
            btSureEdit.setEnabled(true);
            ToastUtil.myToast("公司信息修改成功！");
            finish();
        }else {
            //设置圆形公司logo
            if (TextUtils.isEmpty(tData.getHead_pic())) {
                ivLogo.setImageResource(R.drawable.img_mine_head);
            }else {
                imageUrlList.add(tData.getHead_pic());
                GlideUtil.loadImage(tData.getHead_pic(),R.drawable.img_mine_head,ivLogo);
            }
            //设置公司昵称
            if (TextUtils.isEmpty(tData.getNickname())){
                etName.setText(MyUtils.setPhone(getUserInfo().getPhone()));
            }else {
                etName.setText(tData.getNickname());
            }
            //设置公司地址
            tvAddress.setText(tData.getAddress_from());
            //设置电话
            etPhone.setText(tData.getPhone());
            //设置QQ
            etQQ.setText(tData.getQq());
            //设置微信
            etWechat.setText(tData.getWeixin());
            //设置公司简介
            etIntroduce.setText(tData.getRemark());
        }
    }

    @Override//获取公司信息与修改公司信息共用一个回调方法
    public void onRequestFailure(String result) {
        ToastUtil.myToast(result);
        if (isEditApi){//如果是修改信息请求错误，按钮可重新点击
            btSureEdit.setEnabled(true);
        }else {//如果是获取信息请求错误，关闭界面
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
        if (!EasyPermissions.hasPermissions(CompanyInfoActivity.this, ConstantUtil.PERMS_CAMERA)){//检查是否获取该权限
            //第二个参数是被拒绝后再次申请该权限的解释
            //第三个参数是请求码
            //第四个参数是要申请的权限
            EasyPermissions.requestPermissions(this,"拍照需要摄像头权限",ConstantUtil.PERMS_CODE_CAMERA,ConstantUtil.PERMS_CAMERA);
        }else {
            onAddPicClick(false);//打开相机
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //终止消息队列，防止内存泄漏
        if (mHandler!=null){
            mHandler.removeCallbacksAndMessages(null);
        }
        ToastUtil.cancleMyToast();
    }
}
