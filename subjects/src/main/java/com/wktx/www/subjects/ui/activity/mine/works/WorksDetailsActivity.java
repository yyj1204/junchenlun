package com.wktx.www.subjects.ui.activity.mine.works;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.login.AccountInfoData;
import com.wktx.www.subjects.apiresult.main.condition.ConditionBean;
import com.wktx.www.subjects.apiresult.mine.works.WorksDetailsInfoData;
import com.wktx.www.subjects.apiresult.mine.works.condition.WorksConditionInfoData;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.presenter.mine.works.WorksDetailsPresenter;
import com.wktx.www.subjects.ui.activity.mine.resume.CategoryPickActivity;
import com.wktx.www.subjects.ui.adapter.mine.WorksDetailsAdapter;
import com.wktx.www.subjects.ui.view.mine.resume.IWorksDetailsView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.LoginUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.widget.MyLayoutManager;
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
 * 我的作品 --- 作品详情（上传作品）
 */
public class WorksDetailsActivity extends ABaseActivity<IWorksDetailsView,WorksDetailsPresenter> implements IWorksDetailsView,
        EasyPermissions.PermissionCallbacks{
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.tv_designPattern)
    TextView tvDesignPattern;
    @BindView(R.id.tv_category)
    TextView tvCategory;
    @BindView(R.id.et_worksTitle)
    EditText etWorksTitle;
    @BindView(R.id.et_worksIntro)
    EditText etWorksIntro;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.bt_save)
    Button btSave;
    @BindView(R.id.bt_delete)
    Button btDelete;

    private boolean isAdd;//是否新增
    private String resumeId;//简历id
    private String worksId;//作品id

    private WorksDetailsAdapter hzAdapter;

    private OptionsPickerView pvCustomOptions;//自义定选择器
    private String categoryIds;//类目ID（多个）
    private String categoryStr="";//类目名称（多个）
    private String designPatternId;//设计类型ID
    private ArrayList<ConditionBean> categoryBeans = new ArrayList<>();//类目名称
    private ArrayList<ConditionBean> designPatternBeans = new ArrayList<>();//设计类型名称

    private ArrayList<String> optionsItemStrs = new ArrayList<>();//选择器字符串集合

    //多张作品集合
    private ArrayList<String> worksImgUrls = new ArrayList<>();

    /**
     * 照片
     */
    private PopupPhoto popupPhoto;
    private int themeId;
    private int chooseMode = PictureMimeType.ofImage();
    private static List<LocalMedia> selectList = new ArrayList<>();


    @OnClick({R.id.tb_IvReturn,R.id.linear_designPattern,R.id.linear_category,R.id.linear_imgAdd,R.id.bt_save,R.id.bt_delete})
    public void MyOnclick(View view) {
        //将输入法隐藏
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tvTitle.getWindowToken(), 0);

        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.linear_designPattern://设计类型
                ShowCustomPickerView(tvDesignPattern,designPatternBeans);
                break;
            case R.id.linear_category://类目
                startPickActivity(categoryBeans,ConstantUtil.REQUESTCODE_CATEGORY);
                break;
            case R.id.linear_imgAdd://添加作品图片
                //触发展示相片来源popuwindow
                showPhotoPopupWindow();
                break;
            case R.id.bt_save://保存
                if (MyUtils.isFastClick()){
                    return;
                }
                //判断输入框格式
                if (TextUtils.isEmpty(getWorksTitle())){
                     ToastUtil.myToast("请填写标题！");
                    etWorksTitle.requestFocus();
                }else if (TextUtils.isEmpty(categoryIds)){
                     ToastUtil.myToast("请选择类目！");
                }else if (TextUtils.isEmpty(designPatternId)){
                     ToastUtil.myToast("请选择设计类型！");
                }else if (TextUtils.isEmpty(getIntro())){
                     ToastUtil.myToast("请填写简介！");
                    etWorksIntro.requestFocus();
                }else if (getWorksImgUrls().size()==0) {
                     ToastUtil.myToast( "请上传作品图片！");
                }else {
                    btSave.setEnabled(false);
                    getPresenter().changeWorks(resumeId,worksId);
                }
                break;
            case R.id.bt_delete://删除
                if (MyUtils.isFastClick()){
                    return;
                }
                btDelete.setEnabled(false);
                getPresenter().deleteWorks(resumeId,worksId);
                break;
            default:
                break;
        }
    }


    /**
     * 打开多项选择弹窗界面
     */
    private void startPickActivity(ArrayList<ConditionBean> beans, int requestCode) {
        if (MyUtils.isFastClick1()){
            return;
        }
        Intent intent = new Intent(WorksDetailsActivity.this, CategoryPickActivity.class);
        intent.putExtra(ConstantUtil.KEY_DATA, beans);
        startActivityForResult(intent,requestCode);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_works_details);
        ButterKnife.bind(this);
        initData();
        initHzRv();
        //照片选择主题样式
        themeId = R.style.picture_default_style;
    }

    @Override
    protected WorksDetailsPresenter createPresenter() {
        return new WorksDetailsPresenter();
    }

    /**
     * 接收 MyWorksActivity 传过来简历id、作品ID（以及是新增还是编辑）
     */
    private void initData() {
        isAdd = getIntent().getBooleanExtra(ConstantUtil.KEY_WHETHER,false);
        resumeId = getIntent().getStringExtra(ConstantUtil.KEY_POSITION);
        worksId = getIntent().getStringExtra(ConstantUtil.KEY_DATA);
        //获取参数（类目、设计类型）
        getPresenter().getConditionInfo();
        if (isAdd) {//上传作品
            tvTitle.setText(R.string.title_works_upload);
            btSave.setText("上传");
            btDelete.setVisibility(View.GONE);
        }else {//编辑作品---获取作品信息
            tvTitle.setText(R.string.title_works_details);
            btSave.setText("保存");
            btDelete.setVisibility(View.VISIBLE);
            //获取作品详情
            getPresenter().getInfo(worksId);
        }
    }

    /**
     * 初始化横向 多张作品图片RecyclerView
     */
    private void initHzRv() {
        MyLayoutManager myLayoutManager = new MyLayoutManager(WorksDetailsActivity.this, LinearLayout.HORIZONTAL, false);
        recyclerView.setLayoutManager(myLayoutManager);
        hzAdapter = new WorksDetailsAdapter(WorksDetailsActivity.this);
        recyclerView.setAdapter(hzAdapter);
        //删除图片
        hzAdapter.setOnDeleteImgListener(new WorksDetailsAdapter.OnDeleteImgListener() {
            @Override
            public void onDeleteImg(int position) {
                worksImgUrls.remove(position);
                hzAdapter.setNewData(worksImgUrls);
            }
        });
    }

    /**
     * 照片弹窗---选择：相册||拍照
     */
    private void showPhotoPopupWindow() {
        popupPhoto = new PopupPhoto(WorksDetailsActivity.this, WorksDetailsActivity.this, selectList.size());
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
            PictureSelector.create(WorksDetailsActivity.this)
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
            PictureSelector.create(WorksDetailsActivity.this)
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
     * 自定义选择器
     */
    private void ShowCustomPickerView(final TextView tv , final ArrayList<ConditionBean> list) {
        /**
         * 注意事项：
         * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
         * 具体可参考demo 里面的两个自定义layout布局。
         */
        pvCustomOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                tv.setText(list.get(options1).getName());
                designPatternId = list.get(options1).getId();
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
                .setOutSideCancelable(false)
                .isDialog(false)
                .build();

        optionsItemStrs.clear();
        for (int i = 0; i < list.size(); i++) {
            optionsItemStrs.add(list.get(i).getName());
        }
        pvCustomOptions.setPicker(optionsItemStrs);//添加数据
        pvCustomOptions.show();
    }

    /**
     * IWorksDetailsView
     */
    @Override
    public AccountInfoData getUserInfo() {
        AccountInfoData userInfo = LoginUtil.getinit().getUserInfo();
        return userInfo;
    }
    @Override
    public String getWorksTitle() {
        return etWorksTitle.getText().toString().trim();
    }
    @Override
    public String getCategoryIds() {
        return categoryIds;
    }
    @Override
    public String getDesignPatternId() {
        return designPatternId;
    }
    @Override
    public String getCoverImgUrl() {
        return worksImgUrls.get(0);
    }
    @Override
    public String getIntro() {
        return etWorksIntro.getText().toString().trim();
    }
    @Override
    public ArrayList<String> getWorksImgUrls() {
        return worksImgUrls;
    }
    @Override//获取参数（类目、设计类型）回调
    public void onGetConditionSuccess(WorksConditionInfoData result) {
        categoryBeans = result.getBgatList();
        designPatternBeans = result.getDesignPatternList();
    }
    @Override
    public void onGetConditionFailure(String result) {
        finish();
         ToastUtil.myToast(result);
    }
    @Override//获取图片路径回调
    public void onGetImgUrlResult(boolean isSuccess, String result) {
        if (isSuccess){
            worksImgUrls.add(result);
            hzAdapter.setNewData(worksImgUrls);
        }else {
             ToastUtil.myToast(result);
        }
    }
    @Override//编辑作品回调（增删改）
    public void onChangeWorksResult(boolean isSuccess, String result) {
        btSave.setEnabled(true);
        btDelete.setEnabled(true);
         ToastUtil.myToast(result);
        //成功-关闭界面
        if (isSuccess){
            finish();
        }
    }
    @Override//获取作品详情回调
    public void onRequestSuccess(WorksDetailsInfoData tData) {
        etWorksTitle.setText(tData.getTitle());
        etWorksIntro.setText(tData.getBrief_intro());
        //类目(多个)
        List<ConditionBean> categoryList = tData.getBgatList();
        if (categoryList.size()!=0){
            for (int i = 0; i <categoryList.size() ; i++) {
                if (i==0){
                    categoryIds = categoryList.get(i).getId();
                    categoryStr = categoryList.get(i).getName();
                }else {
                    categoryIds = categoryIds+","+categoryList.get(i).getId();
                    categoryStr = categoryStr +"/"+categoryList.get(i).getName();
                }
            }
        }
        tvCategory.setText(categoryStr);
        //设计类型
        designPatternId = tData.getDesign_pattern().getId();
        tvDesignPattern.setText(tData.getDesign_pattern().getName());

        //多张作品图片
        worksImgUrls = tData.getContent();
        hzAdapter.setNewData(worksImgUrls);
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
                                String base64Str = MyUtils.Bitmap2StrByBase64(bitmap);
                                //获取图片路径
                                getPresenter().getImgUrl(base64Str);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }else if (resultCode==ConstantUtil.RESULTCODE_PICK){//类目
                ArrayList<ConditionBean> content = (ArrayList<ConditionBean>) data.getSerializableExtra(ConstantUtil.KEY_DATA);
                for (int i = 0; i <content.size() ; i++) {
                    if (i==0){
                        categoryIds = content.get(i).getId();
                        categoryStr = content.get(i).getName();
                    }else {
                        categoryIds = categoryIds+","+content.get(i).getId();
                        categoryStr = categoryStr +"/"+content.get(i).getName();
                    }
                }
                tvCategory.setText(categoryStr);
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
        if (!EasyPermissions.hasPermissions(WorksDetailsActivity.this, ConstantUtil.PERMS_CAMERA)){//检查是否获取该权限
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
