package com.wktx.www.emperor.ui.activity.recruit.demand;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.mine.store.StoreListInfoData;
import com.wktx.www.emperor.apiresult.recruit.demand.DemandReleaseConditionInfoData;
import com.wktx.www.emperor.apiresult.recruit.retrievalcondition.Bean;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.recruit.demand.DemandReleasePresenter;
import com.wktx.www.emperor.ui.activity.mine.store.StoreInfoActivity;
import com.wktx.www.emperor.ui.activity.mine.store.StoreInfoEditActivity;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.DateUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.recruit.IDemandReleaseView;
import com.wktx.www.emperor.utils.ToastUtil;
import com.wktx.www.emperor.widget.CustomDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 招聘---需求列表---发布需求
 */
public class DemandReleaseActivity extends ABaseActivity<IDemandReleaseView,DemandReleasePresenter> implements IDemandReleaseView {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.et_demandTitle)
    EditText etTitle;
    @BindView(R.id.et_demandContent)
    EditText etContent;
    @BindView(R.id.et_demandBudget)
    EditText etBudget;
    @BindView(R.id.tv_demandStore)
    TextView tvStore;
    @BindView(R.id.tv_demandPosition)
    TextView tvPosition;
    @BindView(R.id.tv_demandExperience)
    TextView tvExperience;
    @BindView(R.id.tv_demandPlatfrom)
    TextView tvPlatfrom;
    @BindView(R.id.tv_demandCategory)
    TextView tvCategory;
    @BindView(R.id.tv_demandPattern)
    TextView tvPattern;
    //雇佣方式
    @BindView(R.id.tv_wayMonth)
    TextView tvWayMonth;
    @BindView(R.id.tv_wayCustom)
    TextView tvWayCustom;
    @BindView(R.id.tv_demandEndtime)
    TextView tvEndtime;

    @BindView(R.id.bt_sureRelease)
    Button btRelease;

    private OptionsPickerView pvCustomOptions;//自义定选择器

    private ArrayList<Bean> storeBeans = new ArrayList<>();//店铺名称
    private ArrayList<Bean> platformBeans = new ArrayList<>();//平台名称
    private ArrayList<Bean> categoryBeans = new ArrayList<>();//类目名称
    private ArrayList<Bean> patternBeans = new ArrayList<>();//设计模式名称
    private ArrayList<Bean> positionBeans = new ArrayList<>();//工作类型名称
    private ArrayList<Bean> experienceBeans = new ArrayList<>();//工作经验名称
    private String storeId;//店铺Id
    private String platformId;//平台Id
    private String categoryId;//类目Id
    private String patternId="0";//设计模式Id
    private String positionId;//工作类型Id
    private String experienceId;//工作经验Id

    private ArrayList<String> optionsItemStrs = new ArrayList<>();//选择器字符串集合

    private boolean isCustom=false;//雇佣方式  false：包月 ，true：定制

    //系统日历控件
    private Calendar curCalendar = Calendar.getInstance();
    private long curDateLong;//当前时间的时间戳


    private CustomDialog customDialog;

    @OnClick({R.id.tb_IvReturn,R.id.linear_demandStore,R.id.linear_demandPosition,R.id.linear_demandExperience,R.id.linear_demandPlatfrom,
            R.id.linear_demandCategory,R.id.linear_demandPattern,R.id.tv_wayMonth,R.id.tv_wayCustom,R.id.linear_demandEndtime,R.id.bt_sureRelease})
    public void MyOnclick(View view) {
        //将输入法隐藏
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tvTitle.getWindowToken(), 0);
        switch (view.getId()){
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.linear_demandStore://选择店铺
                if (storeBeans.size()!=0){
                    initCustomOptionPicker(R.id.tv_demandStore,storeBeans);
                }
                break;
            case R.id.linear_demandPosition://选择工作类型
                if (positionBeans.size()!=0){
                    initCustomOptionPicker(R.id.tv_demandPosition,positionBeans);
                }
                break;
            case R.id.linear_demandExperience://选择工作经验
                if (experienceBeans.size()!=0){
                    initCustomOptionPicker(R.id.tv_demandExperience,experienceBeans);
                }
                break;
            case R.id.linear_demandPlatfrom://选择平台
                if (platformBeans.size()!=0){
                    initCustomOptionPicker(R.id.tv_demandPlatfrom,platformBeans);
                }
                break;
            case R.id.linear_demandCategory://选择类目
                if (categoryBeans.size()!=0) {
                    initCustomOptionPicker(R.id.tv_demandCategory, categoryBeans);
                }
                break;
            case R.id.linear_demandPattern://选择需求模式
                if (patternBeans.size()!=0) {
                    initCustomOptionPicker(R.id.tv_demandPattern, patternBeans);
                }
                break;
            case R.id.tv_wayMonth://包月方式雇佣
                isCustom=false;
                tvWayMonth.setSelected(!isCustom);
                tvWayMonth.setBackground(getResources().getDrawable(R.drawable.shape_buttom_ffb321_38));
                tvWayCustom.setSelected(isCustom);
                tvWayCustom.setBackground(null);
                break;
            case R.id.tv_wayCustom://定制方式雇佣
                isCustom=true;
                tvWayMonth.setSelected(!isCustom);
                tvWayMonth.setBackground(null);
                tvWayCustom.setSelected(isCustom);
                tvWayCustom.setBackground(getResources().getDrawable(R.drawable.shape_buttom_ffb321_38));
                break;
            case R.id.linear_demandEndtime://选择截止时间
                pickDate(tvEndtime,"选择截止时间");
                break;
            case R.id.bt_sureRelease://确认发布
                if (MyUtils.isFastClick()){
                    return;
                }
                //判断输入框格式
                if (TextUtils.isEmpty(getDemandTitle())){
                    ToastUtil.myToast("请输入需求标题！");
                    etTitle.requestFocus();
                }else if (TextUtils.isEmpty(getDemandContent())){
                    ToastUtil.myToast("请输入需求详情！");
                    etContent.requestFocus();
                }else if (TextUtils.isEmpty(getDemandBudget())){
                    ToastUtil.myToast("请输入预算金额！");
                    etBudget.requestFocus();
                }else if (TextUtils.isEmpty(storeId)){
                    ToastUtil.myToast("请选择店铺！");
                }else if (TextUtils.isEmpty(positionId)){
                    ToastUtil.myToast("请选择工作类型！");
                }else if (TextUtils.isEmpty(experienceId)){
                    ToastUtil.myToast("请选择工作经验！");
                }else if (TextUtils.isEmpty(platformId)){
                    ToastUtil.myToast("请选择平台！");
                }else if (TextUtils.isEmpty(categoryId)){
                    ToastUtil.myToast("请选择类目！");
                }else {//发布需求
                    btRelease.setEnabled(false);
                    getPresenter().onDemandRelease(platformId,categoryId,storeId,patternId,positionId,experienceId,isCustom);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().onGetStoreCondition();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demand_release);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.title_demand_release);
        initData();
        //预算输入框（小数点限制两位数）
        setEtListener(etBudget);
        //初始化截止时间
        initTime();
        tvWayMonth.setSelected(true);
        tvWayCustom.setSelected(false);
    }

    private void initData() {
        getPresenter().onGetDemandCondition();
    }

    @Override
    protected DemandReleasePresenter createPresenter() {
        return new DemandReleasePresenter();
    }

    //输入框的监听事件
    private void setEtListener(final EditText et) {
        et.addTextChangedListener(new TextWatcher() {
            @Override// 输入文本之前的状态
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override// 输入文字中的状态
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //限制金额输入框格式
                if (s.toString().contains(".")) {
                    //判断小数点的位置大于倒3，将输入框的字符串截取到小数点后两位数
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                        et.setText(s);
                        et.setSelection(s.length());
                    }
                }

                //判断字符串的第一位是小数点，则在小数点前面加个0
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    et.setText(s);
                    et.setSelection(2);
                }

                //判断字符串第一位是0
                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    //如果第二位不是小数点，限制不能输入
                    if (!s.toString().substring(1, 2).equals(".")) {
                        et.setText(s.subSequence(0, 1));
                        et.setSelection(1);
                        return;
                    }
                }
            }
            @Override// 输入文字后的状态
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 初始化截止时间
     */
    private void initTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //当前时间
        Date curDateDate = curCalendar.getTime();
        String curDateStr = sdf.format(curDateDate);
        //将当前时间转为时间戳(只转日期不转时间，方便pickDate方法做判断)
        curDateLong = Long.parseLong(DateUtil.getCustomType2Timestamp(curDateStr,"yyyy-MM-dd"))* 1000L;
        tvEndtime.setText(curDateStr);

        //截止时间---推迟日历7天
        curCalendar.add(Calendar.DAY_OF_MONTH,+7);
        Date beginDate = curCalendar.getTime();
        String endDateStr = sdf.format(beginDate);
        //将截止时间转为时间戳(只转日期不转时间，方便pickDate方法做判断)
        Long endDateLong = Long.parseLong(DateUtil.getCustomType2Timestamp(endDateStr,"yyyy-MM-dd"))* 1000L;
        tvEndtime.setText(endDateStr);
    }

    /**
     * IDemandReleaseView
     */
    @Override
    public String getDemandTitle() {
        return etTitle.getText().toString().trim();
    }
    @Override
    public String getDemandContent() {
        return etContent.getText().toString().trim();
    }
    @Override
    public String getDemandBudget() {
        return etBudget.getText().toString().trim();
    }
    @Override
    public String getEndTime() {
        return tvEndtime.getText().toString().trim();
    }
    @Override
    public void onGetStoreConditionSuccessResult(List<StoreListInfoData> result) {
        for (int i = 0; i <result.size() ; i++) {
            Bean bean = new Bean();
            bean.setId(result.get(i).getId());
            bean.setName(result.get(i).getShop_name());
            storeBeans.add(bean);
        }
    }
    @Override
    public void onGetStoreConditionFailureResult(String result) {
        if (result.equals("")){//没有店铺
            showAddStoreDialog();
        }else {
            ToastUtil.myToast(result);
            finish();
        }
    }
    @Override//需求发布是否成功的回调
    public void onDemandReleaseResult(boolean isSuccess, String msg) {
        ToastUtil.myToast(msg);
        btRelease.setEnabled(true);
        if (isSuccess){
            finish();
        }
    }
    @Override//获取需求需要的选择参数（店铺、平台、类目、设计模式、工作类型、工作经验）
    public void onRequestSuccess(DemandReleaseConditionInfoData tData) {
        positionBeans = tData.getTows();
        //将工作经验的"未设置"替换成"不限"
        Bean cotitionBean = new Bean("0", "不限");
        experienceBeans = tData.getWorking_years();
        experienceBeans.set(0,cotitionBean);
        platformBeans = tData.getBgap();
        categoryBeans = tData.getBgat();
        patternBeans = tData.getDesign_pattern();
    }
    @Override
    public void onRequestFailure(String result) {
        ToastUtil.myToast(result);
        finish();
    }

    /**
     * 添加店铺对话框
     */
    private void showAddStoreDialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setTitle("系统提示");
        builder.setMessage("暂无任何店铺，是否添加？");
        builder.setPositiveButton("添加店铺", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //打开店铺详情界面
                Intent intent = new Intent(DemandReleaseActivity.this, StoreInfoEditActivity.class);
                intent.putExtra(ConstantUtil.KEY_POSITION,"0");
                startActivity(intent);
            }
        });

        builder.setNegativeButton("取消发布",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });

        customDialog = builder.create();
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.show();
    }

    /**
     * 自定义选择器
     */
    private void initCustomOptionPicker(final int id, final ArrayList<Bean> list) {//条件选择器初始化，自定义布局
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
                if (id == R.id.tv_demandStore){
                    tvStore.setText(name);
                    storeId = list.get(options1).getId();
                }else if (id == R.id.tv_demandPosition) {
                    tvPosition.setText(name);
                    positionId = list.get(options1).getId();
                }else if (id == R.id.tv_demandExperience) {
                    tvExperience.setText(name);
                    experienceId = list.get(options1).getId();
                }else if (id == R.id.tv_demandPlatfrom) {
                    tvPlatfrom.setText(name);
                    platformId = list.get(options1).getId();
                }else if (id == R.id.tv_demandCategory){
                    tvCategory.setText(name);
                    categoryId = list.get(options1).getId();
                }else if (id == R.id.tv_demandPattern){
                    tvPattern.setText(name);
                    patternId = list.get(options1).getId();
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

    //选择项目开始结束时间
    private void  pickDate(final TextView tvDate,String titleStr) {
        TimePickerView pvTime = new TimePickerView.Builder(DemandReleaseActivity.this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String selectDateStr = sdf.format(date);
                long selectDateLong = date.getTime();

                //选中的日期不能在当前时间之前
                if (selectDateLong<=curDateLong){
                    ToastUtil.myToast("截止时间需在今日之后!");
                }else {
                    tvDate.setText(selectDateStr);
                }
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})//默认全部显示,年月日时分秒
                .setTitleText(titleStr)//标题文字
                .setTitleSize(16)//标题文字大小
                .setContentSize(14)//滚轮文字大小
                .setSubmitColor(getResources().getColor(R.color.color_ffb321))
                .setCancelColor(Color.GRAY)
                .isCenterLabel(false)
                .setOutSideCancelable(false)
                .build();
        pvTime.setDate(DateUtil.getNYR2Calendar(tvDate.getText().toString()));
        pvTime.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (customDialog!=null){
            customDialog.dismiss();
            customDialog=null;
        }
        ToastUtil.cancleMyToast();
    }
}
