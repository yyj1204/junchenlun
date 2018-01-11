package com.wktx.www.subjects.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.Model.JsonBean;
import com.wktx.www.subjects.Model.Login.LoginInfoDataInfo;
import com.wktx.www.subjects.Model.UpdateInfo.UpdateHeadInfo;
import com.wktx.www.subjects.Model.UpdateInfo.UpdateHeadInfoData;
import com.wktx.www.subjects.Model.UserInfo.UserInfoDataInfoUserinfo;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.Utils.Bitmap2Base64Util;
import com.wktx.www.subjects.Utils.Contants;
import com.wktx.www.subjects.Utils.GetJsonDataUtil;
import com.wktx.www.subjects.Utils.GsonUtils;
import com.wktx.www.subjects.Utils.SaveObjectUtils;
import com.wktx.www.subjects.Utils.SharedPreferenceUtil;
import com.wktx.www.subjects.Widget.HeadPoPuWindow;
import com.wktx.www.subjects.Widget.OutPoPuWindow;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import org.json.JSONArray;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

/**
 * 个人中心---用户信息
 */
public class AccountProfileActivity extends AppCompatActivity {
    @BindView(R.id.accountIvPhoto)
    CircleImageView head;
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.account_nickname)
    TextView tvNickName;
    @BindView(R.id.accountLlAddress)
    LinearLayout lLAddress;
    @BindView(R.id.accountTvAddress)
    TextView tvAddress;
    private HeadPoPuWindow puWindow;
    private LoginInfoDataInfo getSaveInfo;
    private String token;
    private int user_id;
    private String state;
    private UserInfoDataInfoUserinfo savaInfo;
    OutPoPuWindow outPoPuWindow;
    private int maxSelectNum = 1;
    private int themeId;
    private int chooseMode = PictureMimeType.ofImage();
    private int compressMode = PictureConfig.SYSTEM_COMPRESS_MODE;
    private static List<LocalMedia> selectList = new ArrayList<>();
    private boolean isLoaded = false;
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private String tx;

    @OnClick({R.id.tb_IvReturn, R.id.btn_login_out, R.id.accountLlPhoto, R.id.accountLlNickName, R.id.accountLlAddress})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.btn_login_out:
                showOutPoPuWindow();
                break;
            case R.id.accountLlPhoto:
                //触发展示相片来源popuwindow
                showHeadPopuWindow();
                break;
            case R.id.accountLlNickName:
//                startActivityForResult(new Intent(this, UpdateNickNameActivity.class), Contants.RESULTCODE_UPDATENICK);
                break;
            case R.id.accountLlAddress:
                if (isLoaded) {
                    //将输入法隐藏，lLArea
                    InputMethodManager imm = (InputMethodManager) getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(lLAddress.getWindowToken(), 0);
                    ShowPickerView();
                } else {
                    Toast.makeText(AccountProfileActivity.this, "数据暂未解析成功，请等待", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;


        }
    }


    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case MSG_LOAD_DATA:
                    if (thread == null)
                    {//如果已创建就不再重新创建子线程了
//                        ToastUtil.toast(AddReceiveAdressActivity.this,"开始解析数据");
                        thread = new Thread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
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
    private void ShowPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                tx = options1Items.get(options1).getPickerViewText()+"  "+
                        options2Items.get(options1).get(options2) +"  "+
                        options3Items.get(options1).get(options2).get(options3);
                //选择器返回值赋值给tvArea
                tvAddress.setText(tx);
//                Toast.makeText(AddReceiveAdressActivity.this, tx, Toast.LENGTH_SHORT).show();
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
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

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


    //更新信息的请求
    private void updateInfo(String name, String str) {
        SharedPreferenceUtil.saveData(this, Contants.THREADSTATE, name, Contants.THREADSTATE_NAME);
        Map<String, String> params = new HashMap<>();
        params.put("user_id", "" + user_id);
        params.put("token", token);
        params.put(name, str);
        OkHttpUtils.post()//
                .url(Contants.URL_EDITUSERINFO)//
                .params(params)//
                .build()//
                .execute(new MyStringCallback());
    }

    //退出登录请求
    private void outLogin() {
        SharedPreferenceUtil.saveData(this, Contants.THREADSTATE, Contants.STATEOUTLOGIN, Contants.THREADSTATE_NAME);
        Map<String, String> params = new HashMap<>();
        params.put("user_id", "" + user_id);
        params.put("token", token);
        OkHttpUtils.post()//
                .url(Contants.URL_USER_USER_INFO_LOGOUT)//
                .params(params)//
                .build()//
                .execute(new MyStringCallback());
    }

    //更新头像的请求
    private void updateHeadImg(String base64Head) {
        SharedPreferenceUtil.saveData(this, Contants.THREADSTATE, Contants.UPDATEHEAD, Contants.THREADSTATE_NAME);
        Map<String, String> params = new HashMap<>();
        params.put("user_id", "" + user_id);
        params.put("token", token);
        params.put("base64", base64Head);
        OkHttpUtils.post()//
                .url(Contants.URL_UPDATEHEAD)//
                .params(params)//
                .build()//
                .execute(new MyStringCallback());
    }

    //提交获取用户信息到后台验证
    public void getUserInfo(int user_id, String token) {
        SharedPreferenceUtil.saveData(this, Contants.THREADSTATE, Contants.GETUSERINFO, Contants.THREADSTATE_NAME);
        Map<String, String> params = new HashMap<>();
        params.put("user_id", "" + user_id);
        params.put("token", token);
        OkHttpUtils.post()//
                .url(Contants.URL_GETUSERINFO)//
                .params(params)//
                .build()//
                .execute(new MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
            Log.e("HomeDetailFragment", "null");
        }

        @Override
        public void onResponse(String response, int id) {
            state = (String) SharedPreferenceUtil.getData(AccountProfileActivity.this, Contants.THREADSTATE, "", Contants.THREADSTATE_NAME);
            if (response != null) {
                switch (state) {
//                    case Contants.GETUSERINFO:
//                        userInfo = GsonUtils.parseJSON(response, UserInfo.class);
//                        UserInfoData data = userInfo.getData();
//                        UserInfoDataInfo info = data.getInfo();
//                        if (data != null & info != null) {
//                            UserInfoDataInfoUserinfo userinfo = info.getUserinfo();
//                            new SaveObjectUtils(AccountProfileActivity.this, Contants.USERINFO_NAME).setObject(token, userinfo);
//                            String nickname = userinfo.getNickname();
//                            String head_pic = userinfo.getHead_pic();
//                            sex = userinfo.getSex();
//                            if (!nickname.equals("")) {
//                                tvNickName.setText(nickname);
//                            }
//                            if (!head_pic.equals("")) {
//                                //设置圆形图片
//                                Glide.with(AccountProfileActivity.this).load(head_pic).into(head);
//                            }
//                            if (!AccountProfileActivity.this.sex.equals("")) {
//                                switch (sex) {
//                                    case "0":
//                                        tvSex.setText("保密");
//                                        break;
//                                    case "1":
//                                        tvSex.setText("男");
//                                        break;
//                                    case "2":
//                                        tvSex.setText("女");
//                                        break;
//                                    default:
//                                        break;
//                                }
//                            }
//                        }
//                        break;
                    case Contants.UPDATEINFOSEX:
//                        UpdateSexInfo updateSexInfo = GsonUtils.parseJSON(response, UpdateSexInfo.class);
//                        UpdateSexInfoData data1 = updateSexInfo.getData();
//                        String msg = data1.getMsg();
//                        if (msg.equals("修改成功")) {
//                            savaInfo.setSex(sexId);
//                            new SaveObjectUtils(AccountProfileActivity.this, Contants.USERINFO_NAME).setObject(token, savaInfo);
//                        }
//                        ToastUtil.toast(AccountProfileActivity.this, msg);
                        break;
                    case Contants.UPDATEHEAD:
                        UpdateHeadInfo updateHeadInfo = GsonUtils.parseJSON(response, UpdateHeadInfo.class);
                        UpdateHeadInfoData updateHeadInfoData = updateHeadInfo.getData();
                        String url = updateHeadInfoData.getUrl();
                        if (url != null) {
                            //把返回信息对象写入内存
                            updateInfo("head_pic", url);
                            savaInfo.setHead_pic(url);
                            new SaveObjectUtils(AccountProfileActivity.this, Contants.USERINFO_NAME).setObject(token, savaInfo);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_profile);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.title_account);
        // 设置右滑动返回
        Slidr.attach(this);
        themeId = R.style.picture_default_style;
        initData();
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
    }

    private void initData() {

        Intent intent = getIntent();
        UserInfoDataInfoUserinfo dataInfo = (UserInfoDataInfoUserinfo) intent.getSerializableExtra(Contants.INTENT_IDTOKEN);
        if (dataInfo != null) {
            String head_pic = dataInfo.getHead_pic();
            String nickname = dataInfo.getNickname();
            String sex = dataInfo.getSex();

            if (nickname != null) {
                tvNickName.setText(nickname);
            }
            if (head_pic != null) {
                //设置圆形图片
                Glide.with(this).load(head_pic).into(head);
            }
        }
        getSaveInfo = new SaveObjectUtils(this, Contants.LOGININFO).getObject(Contants.LOGININFO_OBJECT, LoginInfoDataInfo.class);
        if (getSaveInfo != null) {
            token = getSaveInfo.getToken();
            user_id = getSaveInfo.getUser_id();
            savaInfo = new SaveObjectUtils(AccountProfileActivity.this, Contants.USERINFO_NAME).getObject(token, UserInfoDataInfoUserinfo.class);
//            getUserInfo(user_id, token);
        }
    }


    private void showOutPoPuWindow() {
        outPoPuWindow = new OutPoPuWindow(AccountProfileActivity.this, AccountProfileActivity.this);
        outPoPuWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        outPoPuWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        outPoPuWindow.setClippingEnabled(false);
        outPoPuWindow.showPopupWindow(findViewById(R.id.set_act_parent));
        outPoPuWindow.setOnGetTypeClckListener(new OutPoPuWindow.onGetTypeClckListener() {

            @Override
            public void getText(String sure) {
                switch (sure) {
                    case Contants.OUT:
                        outLogin();
                        SharedPreferenceUtil.saveData(AccountProfileActivity.this, Contants.ISLOGIN, false, Contants.SP_ISLOGIN);
                        setResult(Contants.RESULT_OUTLOGINCODE, new Intent());
                        finish();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void onAddPicClick(boolean b) {
        boolean mode = false;
        if (b) {
            // 进入相册 以下是例子：不需要的api可以不写
            PictureSelector.create(AccountProfileActivity.this)
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
        } else {
            // 单独拍照
            PictureSelector.create(AccountProfileActivity.this)
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

    private void showHeadPopuWindow() {
        puWindow = new HeadPoPuWindow(AccountProfileActivity.this, AccountProfileActivity.this, selectList.size());
        puWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        puWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        puWindow.setClippingEnabled(false);
        puWindow.showPopupWindow(findViewById(R.id.set_act_parent));
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
                                head.setImageBitmap(bitmap);
                            }
                            String s = Bitmap2Base64Util.Bitmap2StrByBase64(bitmap);
                            SharedPreferenceUtil.saveData(this, Contants.HEADBASE64STR, s, Contants.HEADBASE64_NAME);
                            updateHeadImg(s);

                            // 例如 LocalMedia 里面返回三种path
                            // 1.media.getPath(); 为原图path
                            // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                            // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                            // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                        }
                        break;
                }
            } else if (resultCode == Contants.RESULTCODE_UPDATENICK) {
                String sName = data.getStringExtra(Contants.NICKTEXT);
                tvNickName.setText(sName);
            }
        }
    }
}
