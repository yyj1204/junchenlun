package com.wktx.www.subjects.ui.activity.manage.report;

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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.message.TaskListInfoData;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.presenter.manage.report.ReportReleasePresenter;
import com.wktx.www.subjects.ui.activity.ImageActivity;
import com.wktx.www.subjects.ui.adapter.mine.WorksDetailsAdapter;
import com.wktx.www.subjects.ui.view.manage.IReportReleaseView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.DateUtil;
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
 * 管理我的工作---发布报告
 */
public class ReportReleaseActivity extends ABaseActivity<IReportReleaseView,ReportReleasePresenter> implements IReportReleaseView,
        EasyPermissions.PermissionCallbacks{
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.tv_storeName)
    TextView tvStoreName;
    @BindView(R.id.tv_reportTime)
    TextView tvReportTime;
    @BindView(R.id.et_workContent)
    EditText etWorkContent;
    @BindView(R.id.et_storeState)
    EditText etStoreState;
    @BindView(R.id.et_operationPlan)
    EditText etOperationPlan;
    @BindView(R.id.et_needHelp)
    EditText etNeedHelp;
    @BindView(R.id.tv_imgNum)
    TextView tvImgNum;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.bt_sureRelease)//发布
            Button btSureRelease;

    private TaskListInfoData taskListInfoData;//任务安排详情

    private WorksDetailsAdapter hzAdapter;
    //多张数据表现图片集合
    private ArrayList<String> dataImgUrls = new ArrayList<>();

    /**
     * 照片
     */
    private PopupPhoto popupPhoto;
    private int themeId;
    //全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
    private int chooseMode = PictureMimeType.ofImage();
    //多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
    private int selectionMode = PictureConfig.MULTIPLE;
    private static List<LocalMedia> selectList = new ArrayList<>();


    @OnClick({R.id.tb_IvReturn,R.id.linear_imgAdd,R.id.bt_sureRelease})
    public void MyOnclick(View view) {
        //将输入法隐藏
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tvTitle.getWindowToken(), 0);
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            case R.id.linear_imgAdd://添加数据表现图片
                if (dataImgUrls.size()<9){
                    //触发展示相片来源popuwindow
                    showPhotoPopupWindow();
                }else {
                    ToastUtil.myToast("最多只能上传9张！");
                }
                break;
            case R.id.bt_sureRelease://确认发布
                if (MyUtils.isFastClick()){
                    return;
                }
                //判断输入框格式
                if (TextUtils.isEmpty(getWorkContent())){
                    ToastUtil.myToast("请填写工作内容！");
                    etWorkContent.requestFocus();
                }else if (TextUtils.isEmpty(getStoreState())){
                    ToastUtil.myToast("请填写店铺现状！");
                    etStoreState.requestFocus();
                }else if (TextUtils.isEmpty(getOperationPlan())){
                    ToastUtil.myToast("请填写往后计划！");
                    etOperationPlan.requestFocus();
                }else if (getDataImgUrls().size()==0) {
                    ToastUtil.myToast( "请上传数据表现图片！");
                }else {
                    btSureRelease.setEnabled(false);
                    getPresenter().releaseReport(taskListInfoData.getId());
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_release);
        ButterKnife.bind(this);
        initData();
        initHzRv();
        //照片选择主题样式
        themeId = R.style.picture_default_style;
    }

    @Override
    protected ReportReleasePresenter createPresenter() {
        return new ReportReleasePresenter();
    }

    /**
     * 接收 Message1TaskFragment 传递过来的安排工作详情
     */
    private void initData() {
        taskListInfoData = (TaskListInfoData) getIntent().getSerializableExtra(ConstantUtil.KEY_DATA);
        tvTitle.setText(R.string.title_report_release);
        tvStoreName.setText(taskListInfoData.getShop_name());
        //当前时间
        tvReportTime.setText(DateUtil.getCurrentDateNYR());
    }

    /**
     * 初始化横向 多张数据表现图片RecyclerView
     */
    private void initHzRv() {
        MyLayoutManager myLayoutManager = new MyLayoutManager(ReportReleaseActivity.this, LinearLayout.HORIZONTAL, false);
        recyclerView.setLayoutManager(myLayoutManager);
        hzAdapter = new WorksDetailsAdapter(ReportReleaseActivity.this);
        recyclerView.setAdapter(hzAdapter);
        //删除图片
        hzAdapter.setOnDeleteImgListener(new WorksDetailsAdapter.OnDeleteImgListener() {
            @Override
            public void onDeleteImg(int position) {
                dataImgUrls.remove(position);
                hzAdapter.setNewData(dataImgUrls);
                setTvImgNum();
            }
        });

        hzAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (MyUtils.isFastClick1()){
                    return;
                }
                //查看大图
                String[] imageUrls = dataImgUrls.toArray(new String[dataImgUrls.size()]);
                Intent intent = new Intent(ReportReleaseActivity.this, ImageActivity.class);
                intent.putExtra(ConstantUtil.KEY_DATA, imageUrls);
                intent.putExtra(ConstantUtil.KEY_POSITION, position);
                startActivity(intent);
            }
        });
    }


    //全局设置张数显示
    private void setTvImgNum() {
        tvImgNum.setText("数据表现（"+dataImgUrls.size()+"张）");
    }

    /**
     * 照片弹窗---选择：相册||拍照
     */
    private void showPhotoPopupWindow() {
        popupPhoto = new PopupPhoto(ReportReleaseActivity.this, ReportReleaseActivity.this);
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
            PictureSelector.create(ReportReleaseActivity.this)
                    .openGallery(chooseMode)// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                    .maxSelectNum(9)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .imageSpanCount(4)// 每行显示个数
                    .selectionMode(selectionMode)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
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
            PictureSelector.create(ReportReleaseActivity.this)
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
     * IReportReleaseView
     */
    @Override
    public String getWorkContent() {
        return etWorkContent.getText().toString().trim();
    }
    @Override
    public String getStoreState() {
        return etStoreState.getText().toString().trim();
    }
    @Override
    public String getOperationPlan() {
        return etOperationPlan.getText().toString().trim();
    }
    @Override
    public String getNeedHelp() {
        return etNeedHelp.getText().toString().trim();
    }
    @Override
    public ArrayList<String> getDataImgUrls() {
        return dataImgUrls;
    }
    @Override//获取图片路径回调
    public void onGetImgUrlResult(boolean isSuccess, String result) {
        if (isSuccess){
            dataImgUrls.add(result);
            hzAdapter.setNewData(dataImgUrls);
            setTvImgNum();
        }else {
            ToastUtil.myToast(result);
        }
    }
    @Override//发布报告回调
    public void onRequestSuccess(String tData) {
        btSureRelease.setEnabled(true);
        ToastUtil.myToast(tData);
        finish();
    }
    @Override
    public void onRequestFailure(String result) {
        btSureRelease.setEnabled(true);
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
                            for (int i = 0; i < selectList.size(); i++) {
                                LocalMedia media = selectList.get(i);
                                String path = media.getCompressPath();
                                Bitmap bitmap = BitmapFactory.decodeFile(path);
                                if (bitmap != null) {
                                    String base64Str = MyUtils.Bitmap2StrByBase64(bitmap);
                                    //获取图片路径
                                    getPresenter().getImgUrl(base64Str);
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
        if (!EasyPermissions.hasPermissions(ReportReleaseActivity.this, ConstantUtil.PERMS_CAMERA)){//检查是否获取该权限
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
