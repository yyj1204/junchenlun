package com.wktx.www.subjects.ui.activity.mine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.main.condition.ConditionBean;
import com.wktx.www.subjects.apiresult.mine.personinfo.PersonConditionInfoData;
import com.wktx.www.subjects.apiresult.mine.personinfo.PersonInfoData;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.model.ProvinceJsonBean;
import com.wktx.www.subjects.presenter.mine.PersonInfoPresenter;
import com.wktx.www.subjects.ui.activity.ImageActivity;
import com.wktx.www.subjects.ui.view.mine.IPersonInfoView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.DateUtil;
import com.wktx.www.subjects.utils.GetJsonDataUtil;
import com.wktx.www.subjects.utils.GlideUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.utils.ToastUtil;
import com.wktx.www.subjects.widget.PopupPhoto;
import com.wktx.www.subjects.widget.PopupSex;

import org.json.JSONArray;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 个人中心 --- 个人信息
 */
public class PersonInfoActivity extends ABaseActivity<IPersonInfoView,PersonInfoPresenter>
        implements IPersonInfoView ,EasyPermissions.PermissionCallbacks {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.civ_head)
    CircleImageView ivHead;
    @BindView(R.id.iv_sex_boy)
    ImageView ivBoy;
    @BindView(R.id.iv_sex_girl)
    ImageView ivGirl;
    @BindView(R.id.tv_education)
    TextView tvEducation;
    @BindView(R.id.tv_birth)
    TextView tvBirth;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_qq)
    EditText etQQ;
    @BindView(R.id.et_wechat)
    EditText etWechat;
    @BindView(R.id.et_character)
    EditText etCharacter;
    @BindView(R.id.bt_sureEdit)
    Button btSureEdit;//确认修改

    private long curDateLong;//当前时间的时间戳
    private String curDateStr;//当前时间
    private String headpicStr;//头像路径
    private String sexId;//性别

    private List<String> imageUrlList = new ArrayList<>();//图片url集合

    /**
     * 照片
     */
    private PopupPhoto popupPhoto;//更换头像弹窗
    private int themeId;//主题样式设置
    private int chooseMode = PictureMimeType.ofImage();//选择图片类型
    private static List<LocalMedia> selectList = new ArrayList<>();
    private String base64Str="";//base64图片格式;

    //自定义条件选择器
    private OptionsPickerView pvCustomOptions;
    private String educationId;//学历id
    private ArrayList<ConditionBean> educationBeans = new ArrayList<>();//学历集合
    private ArrayList<String> optionsItemStrs = new ArrayList<>();//选择器字符串集合

    //系统日历控件
    private Calendar curCalendar = Calendar.getInstance();

    /**
     * 地址选择器
     */
    private boolean isLoaded = false;//assets目录下的三级城市json数据是否解析完
    private Thread thread;
    //Handler的Message.what
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    //三级联动：省-市-区
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
    @OnClick({R.id.tb_IvReturn, R.id.linear_head,R.id.civ_head,R.id.linear_sex,R.id.linear_education,
            R.id.linear_birth,R.id.linear_city, R.id.bt_sureEdit})
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
            case R.id.linear_head://头像
                //触发展示相片来源popupwindow
                showPhotoPopup();
                break;
            case R.id.civ_head://查看头像大图
                if (TextUtils.isEmpty(base64Str)){//网络图片
                    if (imageUrlList.size()==0){
                        return;
                    }
                    String[] imageUrls = imageUrlList.toArray(new String[imageUrlList.size()]);
                    Intent intent = new Intent(PersonInfoActivity.this, ImageActivity.class);
                    intent.putExtra(ConstantUtil.KEY_DATA, imageUrls);
                    intent.putExtra(ConstantUtil.KEY_POSITION, 0);
                    startActivity(intent);
                }else {//本地裁剪后的图片
                    PictureSelector.create(PersonInfoActivity.this).themeStyle(themeId).openExternalPreview(0, selectList);
                }
                break;
            case R.id.linear_sex://性别
                //触发展示性别选项popupwindow
                showSexPopup();
                break;
            case R.id.linear_education://最高学历
                if (educationBeans.size()!=0){
                    ShowCustomPickerView(educationBeans);
                }
                break;
            case R.id.linear_birth://出生年月
                pickDate();
                break;
            case R.id.linear_city://所在城市
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
                    ToastUtil.myToast("请输入昵称！");
                    etName.requestFocus();
                }else if (TextUtils.isEmpty(getSex())) {
                    ToastUtil.myToast("请选择性别！");
                }else if (TextUtils.isEmpty(getBirth())) {
                    ToastUtil.myToast("请选择出生年月！");
                }else if (TextUtils.isEmpty(getAddress())) {
                    ToastUtil.myToast("请选择所在城市！");
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
                }else if (TextUtils.isEmpty(getCharacter())){
                    ToastUtil.myToast("请输入性格介绍！");
                    etCharacter.requestFocus();
                }else {//修改公司信息
                    btSureEdit.setEnabled(false);
                    getPresenter().changePersonInfo();
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.title_person_info);
        //照片选择主题样式
        themeId = R.style.picture_default_style;
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        initData();
        initTime();
    }

    @Override
    protected PersonInfoPresenter createPresenter() {
        return new PersonInfoPresenter();
    }

    private void initData() {
        //获取参数（学历）
        getPresenter().getConditionInfo();
        //获取个人信息
        getPresenter().getInfo();
    }

    /**
     * 初始化时间
     */
    private void initTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM");
        Date date = curCalendar.getTime();
        curDateStr = sdf.format(date);
        //将时间转为时间戳(只转日期不转时间，方便pickDate方法做判断)
        curDateLong = Long.parseLong(DateUtil.getCustomType2Timestamp(curDateStr,"yyyy.MM"))* 1000L;
        tvBirth.setText(curDateStr);
    }


    /**
     * 更换头像弹窗
     */
    private void showPhotoPopup() {
        popupPhoto = new PopupPhoto(PersonInfoActivity.this, PersonInfoActivity.this);
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

    /**
     * 头像弹窗---选择：相册||拍照
     */
    public void onAddPicClick(boolean b) {
        if (b) {//进入相册---以下是例子：不需要的api可以不写
            PictureSelector.create(PersonInfoActivity.this)
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
            PictureSelector.create(PersonInfoActivity.this)
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
     * 更换性别弹窗
     */
    private void showSexPopup() {
        PopupSex sexPuWindow = new PopupSex(PersonInfoActivity.this, PersonInfoActivity.this);
        sexPuWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        sexPuWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        sexPuWindow.setClippingEnabled(false);
        sexPuWindow.showPopupWindow(findViewById(R.id.toolbar));
        sexPuWindow.setOnGetTextClckListener(new PopupSex.onGetTextClckListener() {
            @Override
            public void getText(String sex) {
                switch (sex) {
                    case "男":
                        sexId="1";
                        ivBoy.setImageResource(R.drawable.ic_sex_yes);
                        ivGirl.setImageResource(R.drawable.ic_sex_no);
                        break;
                    case "女":
                        sexId="2";
                        ivBoy.setImageResource(R.drawable.ic_sex_no);
                        ivGirl.setImageResource(R.drawable.ic_sex_yes);
                        break;
                    default:
                        break;
                }
            }
        });
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
                tvCity.setText(pickerViewStr);
            }
        })
                .setTitleText("城市选择")
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
     * 自定义选择器
     */
    private void ShowCustomPickerView(final ArrayList<ConditionBean> list) {
        /**
         * 注意事项：
         * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
         * 具体可参考demo 里面的两个自定义layout布局。
         */
        pvCustomOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {


            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = list.get(options1).getName();
                educationId = list.get(options1).getId();
                tvEducation.setText(tx);
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
        for (int i = 0; i <list.size() ; i++) {
            optionsItemStrs.add(list.get(i).getName());
        }
        pvCustomOptions.setPicker(optionsItemStrs);//添加数据
        pvCustomOptions.show();
    }

    /**
     * 日期选择器
     */
    private void pickDate() {
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM");
                String selectDateStr = sdf.format(date);
                long selectDateLong = date.getTime();
                //选中的日期不能在当前日期之后
                if(selectDateLong <= curDateLong ) {
                    tvBirth.setText(selectDateStr);
                }else {
                    ToastUtil.myToast("出生年月不能超过当前日期!");
                }

            }
        })
                .setType(new boolean[]{true, true, false, false, false, false})//默认全部显示,年月日时分秒
                .setTitleText("年月选择")//标题文字
                .setTitleSize(16)//标题文字大小
                .setContentSize(14)//滚轮文字大小
                .setSubmitColor(getResources().getColor(R.color.color_ffb321))
                .setCancelColor(Color.GRAY)
                .isCenterLabel(false)
                .setOutSideCancelable(false)
                .build();

        pvTime.setDate(DateUtil.getCustomType2Calendar(tvBirth.getText().toString(),"yyyy.MM"));
        pvTime.show();
    }


    /**
     * IPersonInfoView
     */
    @Override
    public String getHeadPic() {
        return headpicStr;
    }
    @Override
    public String getNickName() {
        return etName.getText().toString().trim();
    }
    @Override
    public String getSex() {
        return sexId.equals("0")?"":sexId;
    }
    @Override
    public String getHightEducation() {
        return educationId;
    }
    @Override
    public String getBirth() {
        return tvBirth.getText().toString().trim();
    }
    @Override
    public String getAddress() {
        return tvCity.getText().toString().trim();
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
    public String getCharacter() {
        return etCharacter.getText().toString().trim();
    }
    @Override//获取个人信息参数回调(学历)
    public void onGetConditionSuccessResult(PersonConditionInfoData result) {
        educationBeans = result.getEducation();
    }
    @Override
    public void onGetConditionFailureResult(String result) {
        finish();
        ToastUtil.myToast(result);
    }
    @Override//获取图片路径回调
    public void onGetImgUrlResult(boolean isSuccess, String result) {
        if (isSuccess){
            headpicStr=result;
        }else {
            ToastUtil.myToast(result);
        }
    }
    @Override//编辑个人信息回调
    public void onChangePersonInfoResult(boolean isSuccess, String result) {
        btSureEdit.setEnabled(true);
        ToastUtil.myToast(result);
        //成功-关闭界面
        if (isSuccess){
            finish();
        }
    }
    @Override//获取个人信息回调
    public void onRequestSuccess(PersonInfoData tData) {
        //设置圆形头像
        headpicStr = tData.getPicture();
        if (TextUtils.isEmpty(headpicStr)) {
            if (tData.getSex().equals("1")){
                ivHead.setImageResource(R.drawable.img_head_man);
            }else if (tData.getSex().equals("2")){
                ivHead.setImageResource(R.drawable.img_head_women);
            }else {
                ivHead.setImageResource(R.drawable.img_mine_head);
            }
        }else {
            imageUrlList.add(tData.getPicture());
            GlideUtil.loadImage(headpicStr,R.drawable.img_mine_head,ivHead);
        }
        //设置昵称
        if (TextUtils.isEmpty(tData.getName())){
            etName.setText(MyUtils.setPhone(getUserInfo().getPhone()));
        }else {
            etName.setText(tData.getName());
        }
        //性别
        sexId = tData.getSex();
        if (sexId.equals("1")){
            ivBoy.setImageResource(R.drawable.ic_sex_yes);
        }else if (sexId.equals("2")){
            ivGirl.setImageResource(R.drawable.ic_sex_yes);
        }
        //最高学历
        educationId = tData.getHighest_education();
        for (int i = 0; i < educationBeans.size(); i++) {
            if (educationId.equals(educationBeans.get(i).getId())){
                tvEducation.setText(educationBeans.get(i).getName());
            }
        }
        //出生年月
        if (!TextUtils.isEmpty(tData.getDate_of_birth())){
            tvBirth.setText(tData.getDate_of_birth());
        }
        //设置所在城市
        tvCity.setText(tData.getResidential_city());
        //设置电话
        etPhone.setText(tData.getPhone());
        //设置QQ
        etQQ.setText(tData.getQq());
        //设置微信
        etWechat.setText(tData.getWechat());
        //设置性格介绍
        etCharacter.setText(tData.getCharacter_introduction());
    }
    @Override
    public void onRequestFailure(String result) {
        ToastUtil.myToast(result);
        finish();
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
                                ivHead.setImageBitmap(bitmap);
                                base64Str = MyUtils.Bitmap2StrByBase64(bitmap);
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
        if (!EasyPermissions.hasPermissions(PersonInfoActivity.this, ConstantUtil.PERMS_CAMERA)){//检查是否获取该权限
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
