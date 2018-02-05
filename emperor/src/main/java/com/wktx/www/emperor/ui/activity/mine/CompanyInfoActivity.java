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
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.Activity.UpdateNickNameActivity;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.login.AccountInfoData;
import com.wktx.www.emperor.apiresult.login.login8register.RegisterInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.mine.CompanyInfoPresenter;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.model.ProvinceJsonBean;
import com.wktx.www.emperor.Model1.Login.LoginInfoDataInfo;
import com.wktx.www.emperor.Model1.UpdateInfo.UpdateHeadInfo;
import com.wktx.www.emperor.Model1.UpdateInfo.UpdateHeadInfoData;
import com.wktx.www.emperor.Model1.UserInfo.UserInfoDataInfoUserinfo;
import com.wktx.www.emperor.utils.Bitmap2Base64Util;
import com.wktx.www.emperor.utils.GetJsonDataUtil;
import com.wktx.www.emperor.utils.GsonUtil;
import com.wktx.www.emperor.utils.LoginUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.utils.SaveObjectUtils;
import com.wktx.www.emperor.utils.SharedPreferenceUtil;
import com.wktx.www.emperor.view.mine.ICompanyInfoView;
import com.wktx.www.emperor.widget.HeadPopup;
import com.wktx.www.emperor.widget.LogoutPopup;
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
import okhttp3.Call;
/**
 * 个人中心---公司信息
 */
public class CompanyInfoActivity extends ABaseActivity<ICompanyInfoView,CompanyInfoPresenter> implements ICompanyInfoView {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.civ_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.linear_address)
    LinearLayout llAddress;
    @BindView(R.id.et_company_introduce)
    EditText etIntroduce;
    @BindView(R.id.bt_logout)
    Button btLogout;//退出登录

    private HeadPopup logoPopup;//更换公司logo弹窗
    private LogoutPopup logoutPopup;//退出登录弹窗

    /**
     * 照片
     */
    private int themeId;//主题样式设置
    private int chooseMode = PictureMimeType.ofImage();//选择图片类型
    private int compressMode = PictureConfig.SYSTEM_COMPRESS_MODE;//系统自带压缩
    private static List<LocalMedia> selectList = new ArrayList<>();

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



    private LoginInfoDataInfo getSaveInfo;
    private String token;
    private int user_id;
    private String state;
    private UserInfoDataInfoUserinfo savaInfo;


    @OnClick({R.id.tb_IvReturn, R.id.linear_logo, R.id.linear_name, R.id.linear_address, R.id.bt_logout})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.linear_logo://公司logo
                //触发展示相片来源popuwindow
                showLogoPopup();
                break;
            case R.id.linear_name://公司名称
                startActivityForResult(new Intent(this, UpdateNickNameActivity.class), ConstantUtil.REQUESTCODE_UPDATENAME);
                break;
            case R.id.linear_address://公司地址
                if (isLoaded) {
                    //将输入法隐藏，llAddress
                    InputMethodManager imm = (InputMethodManager) getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(llAddress.getWindowToken(), 0);
                    ShowPickerView();//地址选择器
                } else {
                    MyUtils.showToast(CompanyInfoActivity.this, "数据暂未解析成功，请等待");
                }
                break;
            case R.id.bt_logout://退出登录
                showLogoutPopup();
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
        tvTitle.setText(R.string.title_companyinfo);
        //设置右滑动返回
        Slidr.attach(this);
        //照片选择主题样式
        themeId = R.style.picture_default_style;
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        initData();
    }

    @Override
    protected CompanyInfoPresenter createPresenter() {
        return new CompanyInfoPresenter();
    }

    /**
     * ICompanyInfoView
     */
    @Override
    public AccountInfoData getUserInfo() {
        AccountInfoData userInfo = LoginUtil.getinit().getUserInfo();
        return userInfo;
    }
    @Override
    public void onRequestSuccess(RegisterInfoData tData) {

    }
    @Override
    public void onRequestFailure(String result) {

    }
    @Override
    public void onLogout(boolean isSuccess, String msg) {
        MyUtils.showToast(CompanyInfoActivity.this, msg);
        if (isSuccess){
            LoginUtil.getinit().logout();//将本地登录信息清除
            finish();
        }
    }

    /**
     * 更换头像弹窗
     */
    private void showLogoPopup() {
        logoPopup = new HeadPopup(CompanyInfoActivity.this, CompanyInfoActivity.this, selectList.size());
        logoPopup.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        logoPopup.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        logoPopup.setClippingEnabled(false);
        logoPopup.showPopupWindow(findViewById(R.id.set_act_parent));
        logoPopup.setOnGetTypeClckListener(new HeadPopup.onGetTypeClckListener() {
            @Override
            public void getType(ConstantUtil.Type type) {
                if (type == ConstantUtil.Type.CAMERA) {
                    onAddPicClick(false);
                } else if (type == ConstantUtil.Type.PHONE) {
                    onAddPicClick(true);
                }
            }
            @Override
            public void getImgUri(Uri ImgUri, File file) {
//TODO
            }
        });
    }

    /**
     * 退出登录弹窗
     */
    private void showLogoutPopup() {
        logoutPopup = new LogoutPopup(CompanyInfoActivity.this, CompanyInfoActivity.this);
        logoutPopup.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        logoutPopup.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        logoutPopup.setClippingEnabled(false);
        logoutPopup.showPopupWindow(ivLogo);
        logoutPopup.setOnGetTypeClckListener(new LogoutPopup.onGetTypeClckListener() {
            @Override
            public void getText(String sure) {
                switch (sure) {
                    case ConstantUtil.LOGOUT:
                        getPresenter().onLogout();
                        break;
                    default:
                        break;
                }
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
        } else {//单独拍照
            PictureSelector.create(CompanyInfoActivity.this)
                    .openCamera(chooseMode)// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                    .theme(themeId)// 主题样式设置 具体参考 values/styles
                    .maxSelectNum(1)// 最大图片选择数量
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

    /**
     * 三级联动---地址选择器
     */
    private void ShowPickerView() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String pickerViewStr = options1Items.get(options1).getPickerViewText()+"  "+
                        options2Items.get(options1).get(options2) +"  "+
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
     * 解析 assets 目录下的Json数据
     * 注意：Json文件仅供参考，实际使用可自行替换文件
     * 关键逻辑在于循环体
     */
    private void initJsonData() {
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    case PictureConfig.CHOOSE_REQUEST:
                        //图片选择结果回调
                        selectList = PictureSelector.obtainMultipleResult(data);
                        if (selectList != null) {
                            LocalMedia media = selectList.get(0);
                            String path = media.getCompressPath();
                            Bitmap bitmap = BitmapFactory.decodeFile(path);
                            if (bitmap != null) {
                                ivLogo.setImageBitmap(bitmap);
                            }
                            String s = Bitmap2Base64Util.Bitmap2StrByBase64(bitmap);
                            SharedPreferenceUtil.saveData(this, ConstantUtil.HEADBASE64STR_KEY, s, ConstantUtil.HEADBASE64_NAME);
                            //TODO 更换头像请求
//                            updateHeadImg(s);

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
            } else if (resultCode == ConstantUtil.RESULTCODE_UPDATENAME) {
                String sName = data.getStringExtra(ConstantUtil.NICKTEXT);
                tvName.setText(sName);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //终止消息队列，防止内存泄漏
        if (mHandler!=null){
            mHandler.removeCallbacksAndMessages(null);
        }
    }


    /**
     * ------------------------------------------------------------------------------------------
      */

    private void initData() {
        Intent intent = getIntent();
        UserInfoDataInfoUserinfo dataInfo = (UserInfoDataInfoUserinfo) intent.getSerializableExtra(ConstantUtil.INTENT_IDTOKEN);
        if (dataInfo != null) {
            String head_pic = dataInfo.getHead_pic();
            String nickname = dataInfo.getNickname();
            String sex = dataInfo.getSex();

            if (nickname != null) {
                tvName.setText(nickname);
            }
            if (head_pic != null) {
                //设置圆形图片
                Glide.with(this).load(head_pic).into(ivLogo);
            }
        }
        getSaveInfo = new SaveObjectUtils(this, ConstantUtil.LOGININFO).getObject(ConstantUtil.LOGININFO_OBJECT, LoginInfoDataInfo.class);
        if (getSaveInfo != null) {
            token = getSaveInfo.getToken();
            user_id = getSaveInfo.getUser_id();
            savaInfo = new SaveObjectUtils(CompanyInfoActivity.this, ConstantUtil.USERINFO_NAME).getObject(token, UserInfoDataInfoUserinfo.class);
//            getUserInfo(user_id, token);
        }
    }

    //更新信息的请求
    private void updateInfo(String name, String str) {
        SharedPreferenceUtil.saveData(this, ConstantUtil.THREADSTATE, name, ConstantUtil.THREADSTATE_NAME);
        Map<String, String> params = new HashMap<>();
        params.put("user_id", "" + user_id);
        params.put("token", token);
        params.put(name, str);
        OkHttpUtils.post()//
                .url(ConstantUtil.URL_EDITUSERINFO)//
                .params(params)//
                .build()//
                .execute(new MyStringCallback());
    }

    //退出登录请求
    private void outLogin() {
        SharedPreferenceUtil.saveData(this, ConstantUtil.THREADSTATE, ConstantUtil.STATEOUTLOGIN, ConstantUtil.THREADSTATE_NAME);
        Map<String, String> params = new HashMap<>();
        params.put("user_id", "" + user_id);
        params.put("token", token);
        OkHttpUtils.post()//
                .url(ConstantUtil.URL_USER_USER_INFO_LOGOUT)//
                .params(params)//
                .build()//
                .execute(new MyStringCallback());
    }

    //更新头像的请求
    private void updateHeadImg(String base64Head) {
        SharedPreferenceUtil.saveData(this, ConstantUtil.THREADSTATE, ConstantUtil.UPDATEHEAD, ConstantUtil.THREADSTATE_NAME);
        Map<String, String> params = new HashMap<>();
        params.put("user_id", "" + user_id);
        params.put("token", token);
        params.put("base64", base64Head);
        OkHttpUtils.post()//
                .url(ConstantUtil.URL_UPDATEHEAD)//
                .params(params)//
                .build()//
                .execute(new MyStringCallback());
    }

    //提交获取用户信息到后台验证
    public void getUserInfo(int user_id, String token) {
        SharedPreferenceUtil.saveData(this, ConstantUtil.THREADSTATE, ConstantUtil.GETUSERINFO, ConstantUtil.THREADSTATE_NAME);
        Map<String, String> params = new HashMap<>();
        params.put("user_id", "" + user_id);
        params.put("token", token);
        OkHttpUtils.post()//
                .url(ConstantUtil.URL_GETUSERINFO)//
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
            state = (String) SharedPreferenceUtil.getData(CompanyInfoActivity.this, ConstantUtil.THREADSTATE, "", ConstantUtil.THREADSTATE_NAME);
            if (response != null) {
                switch (state) {
//                    case ConstantUtil.GETUSERINFO:
//                        userInfo = GsonUtil.parseJSON(response, UserInfo.class);
//                        AccountInfoData data = userInfo.getData();
//                        UserInfoDataInfo info = data.getInfo();
//                        if (data != null & info != null) {
//                            UserInfoDataInfoUserinfo userinfo = info.getUserinfo();
//                            new SaveObjectUtils(CompanyInfoActivity.this, ConstantUtil.USERINFO_NAME).setObject(token, userinfo);
//                            String nickname = userinfo.getNickname();
//                            String head_pic = userinfo.getHead_pic();
//                            sex = userinfo.getSex();
//                            if (!nickname.equals("")) {
//                                tvNickName.setText(nickname);
//                            }
//                            if (!head_pic.equals("")) {
//                                //设置圆形图片
//                                Glide.with(CompanyInfoActivity.this).load(head_pic).into(head);
//                            }
//                            if (!CompanyInfoActivity.this.sex.equals("")) {
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
                    case ConstantUtil.UPDATEINFOSEX:
//                        UpdateSexInfo updateSexInfo = GsonUtil.parseJSON(response, UpdateSexInfo.class);
//                        UpdateSexInfoData data1 = updateSexInfo.getData();
//                        String msg = data1.getMsg();
//                        if (msg.equals("修改成功")) {
//                            savaInfo.setSex(sexId);
//                            new SaveObjectUtils(CompanyInfoActivity.this, ConstantUtil.USERINFO_NAME).setObject(token, savaInfo);
//                        }
//                        ToastUtil.toast(CompanyInfoActivity.this, msg);
                        break;
                    case ConstantUtil.UPDATEHEAD:
                        UpdateHeadInfo updateHeadInfo = GsonUtil.parseJSON(response, UpdateHeadInfo.class);
                        UpdateHeadInfoData updateHeadInfoData = updateHeadInfo.getData();
                        String url = updateHeadInfoData.getUrl();
                        if (url != null) {
                            //把返回信息对象写入内存
                            updateInfo("head_pic", url);
                            savaInfo.setHead_pic(url);
                            new SaveObjectUtils(CompanyInfoActivity.this, ConstantUtil.USERINFO_NAME).setObject(token, savaInfo);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
