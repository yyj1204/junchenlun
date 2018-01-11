package com.wktx.www.subjects.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.Model.JsonBean;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.Utils.Bitmap2Base64Util;
import com.wktx.www.subjects.Utils.Contants;
import com.wktx.www.subjects.Utils.DateUtils;
import com.wktx.www.subjects.Utils.GetJsonDataUtil;
import com.wktx.www.subjects.Utils.SharedPreferenceUtil;
import com.wktx.www.subjects.Widget.HeadPoPuWindow;
import com.wktx.www.subjects.Widget.SexPoPuWindow;

import org.json.JSONArray;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 编辑个人信息
 */
public class PersonalInfoActivity extends AppCompatActivity {

    @BindView(R.id.tb_TvBarTitle)
    TextView mTvTitle;
    @BindView(R.id.civ_head)
    CircleImageView iv_head;
    @BindView(R.id.iv_sex_boy)
    ImageView iv_boy;
    @BindView(R.id.iv_sex_girl)
    ImageView iv_girl;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_education)
    TextView tv_education;
    @BindView(R.id.tv_birth)
    TextView tv_birth;
    @BindView(R.id.tv_city)
    TextView tv_city;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_qq)
    TextView tv_qq;
    @BindView(R.id.tv_wechat)
    TextView tv_wechat;


    private HeadPoPuWindow puWindow;
    private int themeId;
    private int chooseMode = PictureMimeType.ofImage();
    private int compressMode = PictureConfig.SYSTEM_COMPRESS_MODE;
    private static List<LocalMedia> selectList = new ArrayList<>();
    private int maxSelectNum = 1;

    //Handler的Message.what
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private Thread thread;
    private boolean isLoaded = false;//城市数据是否加载完
    //三级联动：省-市-区
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    //自定义条件选择器
    private OptionsPickerView pvCustomOptions;
    private ArrayList<String> educationList = new ArrayList<>();

    @OnClick({R.id.tb_IvReturn,R.id.linear_head,R.id.linear_name,R.id.linear_sex, R.id.linear_education,
            R.id.linear_birth,R.id.linear_city,R.id.linear_phone,R.id.linear_qq,R.id.linear_wechat})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.linear_head:
                //触发展示相片来源popuwindow
                showPhotoPopuWindow();
                break;
            case R.id.linear_name:
                startEditActivity("0","胡图图");
                break;
            case R.id.linear_sex:
                //触发展示性别选项popuwindow
                showSexPopuWindow();
                break;
            case R.id.linear_education:
                initCustomOptionPicker(educationList);
                break;
            case R.id.linear_birth:
                pickTime();
                break;
            case R.id.linear_city:
                if (isLoaded) {
                    ShowPickerView();
                } else {
                    Toast.makeText(PersonalInfoActivity.this, "数据暂未解析成功，请等待", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.linear_phone:
                startEditActivity("1","135123456789");
                break;
            case R.id.linear_qq:
                startEditActivity("2","987654321");
                break;
            case R.id.linear_wechat:
                startEditActivity("3","987654321");
                break;
            default:
                break;
        }
    }

    //打开编辑界面(姓名、电话、QQ、微信）
    private void startEditActivity(String type,String content) {
        Intent intent = new Intent(PersonalInfoActivity.this, PersonalInfoEditActivity.class);
        String[] str = {type,content};
        intent.putExtra("edit",str);
        startActivityForResult(intent,0);
    }

    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
//                        ToastUtil.toast(AddReceiveAdressActivity.this,"开始解析数据");
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
                case MSG_LOAD_SUCCESS:
//                    ToastUtil.toast(AddReceiveAdressActivity.this,"解析数据成功");
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
//                    ToastUtil.toast(AddReceiveAdressActivity.this,"解析数据失败");
                    break;
                default:
                    break;

            }
        }
    };

    private void initJsonData() {//解析数据
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        mTvTitle.setText(R.string.title_EditPersonalInfo);
        themeId = R.style.picture_default_style;
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        initPpData();
    }

    private void initPpData() {
        educationList.add("初中及以下");
        educationList.add("高中/中专");
        educationList.add("大专");
        educationList.add("本科");
        educationList.add("硕士及以上");
    }
    //性别弹窗
    private void showSexPopuWindow() {
        SexPoPuWindow sexPuWindow = new SexPoPuWindow(PersonalInfoActivity.this, PersonalInfoActivity.this);
        sexPuWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        sexPuWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        sexPuWindow.setClippingEnabled(false);
        sexPuWindow.showPopupWindow(findViewById(R.id.toolbar));
        sexPuWindow.setOnGetTextClckListener(new SexPoPuWindow.onGetTextClckListener() {
            @Override
            public void getText(String sex) {
                switch (sex) {
                    case "男":
                        iv_boy.setImageResource(R.drawable.ic_sex_yes);
                        iv_girl.setImageResource(R.drawable.ic_sex_no);
                        break;
                    case "女":
                        iv_boy.setImageResource(R.drawable.ic_sex_no);
                        iv_girl.setImageResource(R.drawable.ic_sex_yes);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    //头像弹窗
    private void showPhotoPopuWindow() {
        puWindow = new HeadPoPuWindow(PersonalInfoActivity.this, PersonalInfoActivity.this, selectList.size());
        puWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        puWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        puWindow.setClippingEnabled(false);
        puWindow.showPopupWindow(findViewById(R.id.toolbar));
        puWindow.setOnGetTypeClckListener(new HeadPoPuWindow.onGetTypeClckListener() {
            @Override
            public void getType(Contants.Type type) {
                if (type == Contants.Type.CAMERA) {
                    onAddPicClick(false);
                } else if (type == Contants.Type.PHONE) {
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
            PictureSelector.create(PersonalInfoActivity.this)
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
                    .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                    .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                    .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                    .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                    .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                    .selectionMedia(null)// 是否传入已选图片
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        } else {// 单独拍照
            PictureSelector.create(PersonalInfoActivity.this)
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
                    .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                    .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                    .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                    .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                    .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                    .selectionMedia(null)// 是否传入已选图片
                    .previewEggs(false)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        }
    }

    //自定义条件选择器
    private void initCustomOptionPicker(final ArrayList<String> list) {
        /**
         * @description
         *
         * 注意事项：
         * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
         * 具体可参考demo 里面的两个自定义layout布局。
         */
        //返回的分别是三个级别的选中位置
        pvCustomOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = list.get(options1);
                tv_education.setText(tx);
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        TextView ivCancel = (TextView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.returnData();
                                pvCustomOptions.dismiss();
                            }
                        });

                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.dismiss();
                            }
                        });
                    }
                })
                .setOutSideCancelable(false)// default is true
                .isDialog(false)
                .build();
        pvCustomOptions.setPicker(list);//添加数据
        pvCustomOptions.show();
    }

    //时间选择器
    private void pickTime() {
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
                tv_birth.setText(format.format(date));
            }
        })
                .setType(new boolean[]{true, true, false, false, false, false})//默认全部显示
                .setContentSize(18)//滚轮文字大小
                .setTitleSize(16)//标题文字大小
                .setTitleText("年月选择")//标题文字
                .setSubmitColor(getResources().getColor(R.color.colorPrimary))
                .setCancelColor(Color.BLACK)
                .isCenterLabel(false)
                .build();
        pvTime.setDate(DateUtils.getCalendarDate2(tv_birth.getText().toString()));
        pvTime.show();
    }

    //地址选择器
    private void ShowPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()+"-"+
                        options2Items.get(options1).get(options2);
                //选择器返回值赋值给 tv_city
                tv_city.setText(tx);
            }
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .setSubmitColor(getResources().getColor(R.color.colorPrimary))
                .setCancelColor(Color.BLACK)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器*/
        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        pvOptions.show();
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
                                iv_head.setImageBitmap(bitmap);
                            }
                            String s = Bitmap2Base64Util.Bitmap2StrByBase64(bitmap);
                            SharedPreferenceUtil.saveData(this, Contants.HEADBASE64STR, s, Contants.HEADBASE64_NAME);

                            //TODO 更新头像的请求操作
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

            //姓名、电话、QQ、微信
            if (requestCode==0){
                if (resultCode==0){
                    String[] infos = data.getStringArrayExtra("info");
                    int type = Integer.parseInt(infos[0]);
                    switch (type){
                        case 0:
                            tv_name.setText(infos[1]);
                            break;
                        case 1:
                            tv_phone.setText(infos[1]);
                            break;
                        case 2:
                            tv_qq.setText(infos[1]);
                            break;
                        case 3:
                            tv_wechat.setText(infos[1]);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }
}
