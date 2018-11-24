package com.wktx.www.emperor.ui.activity.staff;
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
import com.hedgehog.ratingbar.RatingBar;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.staff.StaffEvaluatePresenter;
import com.wktx.www.emperor.ui.activity.ImageActivity;
import com.wktx.www.emperor.ui.adapter.staff.StaffEvaluateAdapter;
import com.wktx.www.emperor.ui.view.staff.IStaffEvaluateView;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.utils.ToastUtil;
import com.wktx.www.emperor.widget.MyLayoutManager;
import com.wktx.www.emperor.widget.PopupPhoto;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 管理我的员工---评价员工界面
 */
public class StaffEvaluateActivity extends ABaseActivity<IStaffEvaluateView,StaffEvaluatePresenter> implements IStaffEvaluateView,EasyPermissions.PermissionCallbacks {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    //评价
    @BindView(R.id.rb_serviceAttitude)
    RatingBar rbServiceAttitude;
    @BindView(R.id.rb_workAbility)
    RatingBar rbWorkAbility;
    @BindView(R.id.rb_responseSpeed)
    RatingBar rbResponseSpeed;
    @BindView(R.id.et_evaluate)
    EditText etEvaluate;
    @BindView(R.id.tv_imgNum)
    TextView tvImgNum;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.bt_sureEvaluate)
    Button btSureEvaluate;


    private String hireId;//雇佣id

    private String attitudeStar="0";//工作态度星星数
    private String abilityStar="0";//工作能力星星数
    private String speedStar="0";//工作效率星星数

    private StaffEvaluateAdapter hzAdapter;
    //多张评价图片集合
    private ArrayList<String> evaluateImgUrls = new ArrayList<>();

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

    @OnClick({R.id.tb_IvReturn,R.id.linear_imgAdd,R.id.bt_sureEvaluate})
    public void MyOnclick(View view) {
        //将输入法隐藏
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tvTitle.getWindowToken(), 0);
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            case R.id.linear_imgAdd://添加数据表现图片
                if (evaluateImgUrls.size()<9){
                    //触发展示相片来源popuwindow
                    showPhotoPopupWindow();
                }else {
                    ToastUtil.myToast("最多只能上传9张！");
                }
                break;
            case R.id.bt_sureEvaluate://确认评价
                if (MyUtils.isFastClick()){
                    return;
                }
                //判断输入框格式
                if (getAttitude().equals("0")) {
                    ToastUtil.myToast("请给工作态度评分！");
                }else if (getAbility().equals("0")) {
                    ToastUtil.myToast("请给工作能力评分！");
                }else if (getSpeed().equals("0")){
                    ToastUtil.myToast("请给工作效率评分！");
                }else if (TextUtils.isEmpty(getEvaluateContent())){
                    ToastUtil.myToast("请输入评价留言！");
                    etEvaluate.requestFocus();
                }else {//确认评价
                    btSureEvaluate.setEnabled(false);
                    getPresenter().onEvaluateStaff(hireId);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_evaluate);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.title_staff_evaluate);
        initData();
        initRbListener();
        initHzRv();
        //照片选择主题样式
        themeId = R.style.picture_default_style;
    }

    @Override
    protected StaffEvaluatePresenter createPresenter() {
        return new StaffEvaluatePresenter();
    }

    /**
     * 接收 StaffManageActivity 传递过来的雇佣id
     */
    private void initData() {
        hireId = getIntent().getStringExtra(ConstantUtil.KEY_POSITION);
    }

    /**
     * 评分控件点击事件
     */
    private void initRbListener() {
        rbServiceAttitude.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float RatingCount) {
                attitudeStar=RatingCount+"";
            }
        });
        rbWorkAbility.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float RatingCount) {
                abilityStar=RatingCount+"";
            }
        });
        rbResponseSpeed.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float RatingCount) {
                speedStar=RatingCount+"";
            }
        });
    }

    /**
     * 初始化横向 多张评价图片RecyclerView
     */
    private void initHzRv() {
        MyLayoutManager myLayoutManager = new MyLayoutManager(StaffEvaluateActivity.this, LinearLayout.HORIZONTAL, false);
        recyclerView.setLayoutManager(myLayoutManager);
        hzAdapter = new StaffEvaluateAdapter(StaffEvaluateActivity.this);
        recyclerView.setAdapter(hzAdapter);
        //删除图片
        hzAdapter.setOnDeleteImgListener(new StaffEvaluateAdapter.OnDeleteImgListener() {
            @Override
            public void onDeleteImg(int position) {
                evaluateImgUrls.remove(position);
                hzAdapter.setNewData(evaluateImgUrls);
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
                String[] imageUrls = evaluateImgUrls.toArray(new String[evaluateImgUrls.size()]);
                Intent intent = new Intent(StaffEvaluateActivity.this, ImageActivity.class);
                intent.putExtra(ConstantUtil.KEY_DATA, imageUrls);
                intent.putExtra(ConstantUtil.KEY_POSITION, position);
                startActivity(intent);
            }
        });
    }

    //全局设置张数显示
    private void setTvImgNum() {
        tvImgNum.setText("作品内容（"+evaluateImgUrls.size()+"张）");
    }

    /**
     * 照片弹窗---选择：相册||拍照
     */
    private void showPhotoPopupWindow() {
        popupPhoto = new PopupPhoto(StaffEvaluateActivity.this, StaffEvaluateActivity.this);
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
            PictureSelector.create(StaffEvaluateActivity.this)
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
            PictureSelector.create(StaffEvaluateActivity.this)
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
     * IStaffEvaluateView
     */
    @Override
    public String getAttitude() {
        return attitudeStar;
    }
    @Override
    public String getAbility() {
        return abilityStar;
    }
    @Override
    public String getSpeed() {
        return speedStar;
    }
    @Override
    public String getEvaluateContent() {
        return etEvaluate.getText().toString().trim();
    }
    @Override
    public ArrayList<String> getEvaluateImgUrls() {
        return evaluateImgUrls;
    }
    @Override//获取图片路径回调
    public void onGetImgUrlResult(boolean isSuccess, String result) {
        if (isSuccess){
            evaluateImgUrls.add(result);
            hzAdapter.setNewData(evaluateImgUrls);
            setTvImgNum();
        }else {
            ToastUtil.myToast(result);
        }
    }
    @Override
    public void onRequestSuccess(String tData) {
        ToastUtil.myToast(tData);
        btSureEvaluate.setEnabled(true);
        finish();
    }
    @Override
    public void onRequestFailure(String result) {
        ToastUtil.myToast(result);
        btSureEvaluate.setEnabled(true);
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
        if (!EasyPermissions.hasPermissions(StaffEvaluateActivity.this, ConstantUtil.PERMS_CAMERA)){//检查是否获取该权限
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
