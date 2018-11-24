package com.wktx.www.subjects.ui.activity.manage.leave;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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

import com.bigkoo.pickerview.TimePickerView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.manage.leave.LeaveListInfoData;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.presenter.manage.LeavePresenter;
import com.wktx.www.subjects.ui.activity.mine.PersonInfoActivity;
import com.wktx.www.subjects.ui.view.manage.ILeaveView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.DateUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.utils.ToastUtil;
import com.wktx.www.subjects.widget.PopupPhoto;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 管理我的工作---请假申请界面
 */
public class LeaveActivity extends ABaseActivity<ILeaveView,LeavePresenter> implements ILeaveView ,EasyPermissions.PermissionCallbacks{
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.tv_beginTime)
    TextView tvBeginTime;
    @BindView(R.id.tv_endTime)
    TextView tvEndTime;
    @BindView(R.id.et_leaveReason)
    EditText etLeaveReason;
    //照片
    @BindView(R.id.linear_add_leaveImg)
    LinearLayout llLeaveImg;
    @BindView(R.id.rela_leaveImg)
    RelativeLayout rlLeaveImg;
    @BindView(R.id.iv_leaveImg)
    ImageView ivLeaveImg;

    @BindView(R.id.bt_sure)
    Button btSure;//申请

    /**
     * 照片
     */
    private PopupPhoto popupPhoto;
    private int themeId;
    private int chooseMode = PictureMimeType.ofImage();
    private static List<LocalMedia> selectList = new ArrayList<>();
    private String imgUrl="";//图片路径

    //系统日历控件
    private Calendar curCalendar = Calendar.getInstance();
    private String beginDateStr;//项目开始时间"yyyy-MM-dd HH:mm"格式
    private String endDateStr;//项目结束时间"yyyy-MM-dd HH:mm"格式
    private long curDateLong;//当前时间的时间戳
    private long beginDateLong;//项目开始时间的时间戳
    private long endDateLong;//项目结束时间的时间戳

    private String hireId;//雇佣id

    @OnClick({R.id.tb_IvReturn,R.id.linear_beginTime,R.id.linear_endTime,R.id.linear_add_leaveImg,R.id.iv_leaveImg,R.id.iv_delete_leaveImg,R.id.bt_sure})
    public void MyOnclick(View view) {
        //将输入法隐藏
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tvTitle.getWindowToken(), 0);

        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            case R.id.linear_beginTime://开始时间
                pickDate(tvBeginTime,true,"选择开始时间");
                break;
            case R.id.linear_endTime://结束时间
                pickDate(tvEndTime,false,"选择结束时间");
                break;
            case R.id.linear_add_leaveImg://添加图片
                //触发展示相片来源popuwindow
                showPhotoPopupWindow();
                break;
            case R.id.iv_leaveImg://预览图片
                PictureSelector.create(LeaveActivity.this).themeStyle(themeId).openExternalPreview(0, selectList);
                break;
            case R.id.iv_delete_leaveImg://删除图片
                llLeaveImg.setVisibility(View.VISIBLE);
                rlLeaveImg.setVisibility(View.GONE);
                imgUrl="";
                break;
            case R.id.bt_sure://请假申请
                if (MyUtils.isFastClick()){
                    return;
                }

                //判断输入框格式
                if (TextUtils.isEmpty(getBeginTime())){
                    ToastUtil.myToast("请选择开始时间！");
                }else if (TextUtils.isEmpty(getEndTime())){
                    ToastUtil.myToast("请选择结束时间！");
                }else if (TextUtils.isEmpty(getReason())){
                    ToastUtil.myToast("请输入请假事由！");
                    etLeaveReason.requestFocus();
                }else {//提交申请
                    btSure.setEnabled(false);
                    getPresenter().leaveApply(hireId);
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.title_manage_leave);
        //照片选择主题样式
        themeId = R.style.picture_default_style;
        initData();
        //初始化开始结束时间
        initTime();
    }

    @Override
    protected LeavePresenter createPresenter() {
        return new LeavePresenter();
    }

    /**
     * 接收 ManageActivity 传递过来的雇佣id
     */
    private void initData() {
        hireId = getIntent().getStringExtra(ConstantUtil.KEY_POSITION);
    }

    /**
     * 初始化请假开始结束时间
     */
    private void initTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //开始时间
        Date beginDate = curCalendar.getTime();
        beginDateStr = sdf.format(beginDate);
        //将开始时间转为时间戳(方便pickDate方法做判断)
        curDateLong = Long.parseLong(DateUtil.getCustomType2Timestamp(beginDateStr,"yyyy-MM-dd HH:mm"))* 1000L;
        beginDateLong = curDateLong;
        tvBeginTime.setText(beginDateStr);

        //结束时间---推迟1天
        curCalendar.add(Calendar.DAY_OF_MONTH,1);
        Date endDate = curCalendar.getTime();
        endDateStr = sdf.format(endDate);
        //将结束时间转为时间戳(方便pickDate方法做判断)
        endDateLong = Long.parseLong(DateUtil.getCustomType2Timestamp(endDateStr,"yyyy-MM-dd HH:mm"))* 1000L;
        tvEndTime.setText(endDateStr);
    }

    /**
     * 照片弹窗---选择：相册||拍照
     */
    private void showPhotoPopupWindow() {
        popupPhoto = new PopupPhoto(LeaveActivity.this, LeaveActivity.this);
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
            PictureSelector.create(LeaveActivity.this)
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
            PictureSelector.create(LeaveActivity.this)
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

    //选择请假开始结束时间
    private void  pickDate(final TextView tvDate, final boolean isBegin, String titleStr) {
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String selectDateStr = sdf.format(date);
                long selectDateLong = date.getTime();

                //如果是开始时间，则选中的日期不能在当前日期之前
                if (isBegin){
                    if (selectDateLong >=curDateLong){
                        //选中的日期不能在结束时间之后
                        if (selectDateLong > endDateLong){
                            ToastUtil.myToast("开始时间需在结束时间之前!");
                        }else {
                            beginDateLong = selectDateLong;//赋值，用在结束时间做判断
                            tvDate.setText(selectDateStr);
                        }
                    }else {
                        ToastUtil.myToast("开始时间不能小于当前时间!");
                    }
                }else {//如果是结束时间，则选中的日期不能在开始时间之前
                    if(selectDateLong >= beginDateLong) {
                        endDateLong = selectDateLong;//赋值，用在开始日期做判断
                        tvDate.setText(selectDateStr);
                    }else {
                        ToastUtil.myToast("结束时间需在开始时间之后!");
                    }
                }
            }
        })
                .setType(new boolean[]{true, true, true, true, true, false})//默认全部显示,年月日时分秒
                .setTitleText(titleStr)//标题文字
                .setTitleSize(16)//标题文字大小
                .setContentSize(14)//滚轮文字大小
                .setSubmitColor(getResources().getColor(R.color.color_ffb321))
                .setCancelColor(Color.GRAY)
                .isCenterLabel(false)
                .setOutSideCancelable(false)
                .build();
        pvTime.setDate(DateUtil.getCustomType2Calendar(tvDate.getText().toString(),"yyyy-MM-dd HH:mm"));
        pvTime.show();
    }

    /**
     * ILeaveView
     * @return
     */
    //请假申请
    @Override
    public String getReason() {
        return etLeaveReason.getText().toString().trim();
    }
    @Override
    public String getBeginTime() {
        return tvBeginTime.getText().toString().trim();
    }
    @Override
    public String getEndTime() {
        return tvEndTime.getText().toString().trim();
    }
    @Override
    public String getLeaveImgUrl() {
        return imgUrl;
    }
    @Override
    public void onGetImgUrlResult(boolean isSuccess, String result) {
        if (isSuccess){
            imgUrl=result;
        }else {
            ToastUtil.myToast(result);
        }
    }
    @Override
    public void onLeaveResult(boolean isSuccess, String msg) {
        btSure.setEnabled(true);
        ToastUtil.myToast(msg);
        //成功-关闭界面
        if (isSuccess){
            finish();
        }
    }
    //获取请假记录回调
    @Override
    public void onRequestSuccess(List<LeaveListInfoData> tData) {
    }
    @Override
    public void onRequestFailure(String result) {
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
                                llLeaveImg.setVisibility(View.GONE);
                                rlLeaveImg.setVisibility(View.VISIBLE);
                                ivLeaveImg.setImageBitmap(bitmap);
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
        if (!EasyPermissions.hasPermissions(LeaveActivity.this, ConstantUtil.PERMS_CAMERA)){//检查是否获取该权限
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
