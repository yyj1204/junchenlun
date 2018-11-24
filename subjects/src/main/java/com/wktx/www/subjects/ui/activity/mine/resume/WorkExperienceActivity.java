package com.wktx.www.subjects.ui.activity.mine.resume;

import android.content.Context;
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
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.main.condition.ConditionBean;
import com.wktx.www.subjects.apiresult.mine.resume.ResumeInfoData;
import com.wktx.www.subjects.apiresult.mine.resume.WorkExperienceBean;
import com.wktx.www.subjects.apiresult.main.condition.ConditionInfoData;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.presenter.mine.resume.WorkExperiencePresenter;
import com.wktx.www.subjects.ui.view.mine.resume.IWorkExperienceView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.DateUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.utils.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的简历 --- 工作经历 --- 详情
 */
public class WorkExperienceActivity extends ABaseActivity<IWorkExperienceView,WorkExperiencePresenter> implements IWorkExperienceView {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.et_positionName)
    EditText etPositionName;
    @BindView(R.id.et_companyName)
    EditText etCompanyName;
    @BindView(R.id.et_companyUrl)
    EditText etCompanyUrl;
    @BindView(R.id.et_salary)
    EditText etSalary;
    @BindView(R.id.tv_platform)
    TextView tvPlatform;
    @BindView(R.id.tv_category)
    TextView tvCategory;
    @BindView(R.id.et_workContent)
    EditText etWorkContent;
    @BindView(R.id.tv_beginTime)
    TextView tvBeginTime;
    @BindView(R.id.tv_endTime)
    TextView tvEndTime;
    @BindView(R.id.bt_save)
    Button btSave;
    @BindView(R.id.bt_delete)
    Button btDelete;

    private boolean isAdd;//是否新增
    private int position;//位标
    private ResumeInfoData resumeInfoData;//简历信息
    private ArrayList<WorkExperienceBean> workExperienceList;//工作经历集合

    //系统日历控件
    private Calendar curCalendar = Calendar.getInstance();
    private String beginDateStr;//开始时间"yyyy-MM"格式
    private String endDateStr;//结束时间"yyyy-MM"格式
    private long curDateLong;//当前时间的时间戳
    private long beginDateLong;//开始时间的时间戳
    private long endDateLong;//结束时间的时间戳

    private OptionsPickerView pvCustomOptions;//自义定选择器
    private String platformId;//平台ID
    private String categoryId;//类目ID
    private ArrayList<ConditionBean> platformBeans = new ArrayList<>();//平台名称
    private ArrayList<ConditionBean> categoryBeans = new ArrayList<>();//类目名称
    private ArrayList<String> optionsItemStrs = new ArrayList<>();//选择器字符串集合


    @OnClick({R.id.tb_IvReturn,R.id.linear_platform,R.id.linear_category,R.id.tv_beginTime,R.id.tv_endTime,R.id.bt_save,R.id.bt_delete})
    public void MyOnclick(View view) {
        //将输入法隐藏
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tvTitle.getWindowToken(), 0);
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.linear_platform://平台
                ShowCustomPickerView(tvPlatform,platformBeans);
                break;
            case R.id.linear_category://类目
                ShowCustomPickerView(tvCategory,categoryBeans);
                break;
            case R.id.tv_beginTime://开始时间
                pickDate(tvBeginTime,true,"选择开始时间");
                break;
            case R.id.tv_endTime://结束时间
                pickDate(tvEndTime,false,"选择结束时间");
                break;
            case R.id.bt_save://保存
                if (MyUtils.isFastClick()){
                    return;
                }
                //判断输入框格式
                if (TextUtils.isEmpty(getPositionName())){
                    ToastUtil.myToast("请输入职位名称！");
                    etPositionName.requestFocus();
                }else if (TextUtils.isEmpty(getCompanyName())){
                    ToastUtil.myToast("请输入公司名称！");
                    etCompanyName.requestFocus();
                }else if (TextUtils.isEmpty(getCompanyURL())){
                    ToastUtil.myToast("请输入公司网址！");
                    etCompanyUrl.requestFocus();
                }else if (TextUtils.isEmpty(getSalary())){
                    ToastUtil.myToast("请输入薪资！");
                    etSalary.requestFocus();
                }else if (TextUtils.isEmpty(getWorkContent())){
                    ToastUtil.myToast("请输入工作简介！");
                    etWorkContent.requestFocus();
                }else if (TextUtils.isEmpty(platformId)){
                    ToastUtil.myToast("请选择平台！");
                }else if (TextUtils.isEmpty(categoryId)){
                    ToastUtil.myToast("请选择类目！");
                }else {
                    //将新增或者编辑后的工作经历实体类，添加到工作经历列表里
                    WorkExperienceBean workExperienceBean = new WorkExperienceBean();
                    WorkExperienceBean.WorkDateBean workDateBean = new WorkExperienceBean.WorkDateBean();
                    workExperienceBean.setBgap(platformId);
                    workExperienceBean.setBgat(categoryId);
                    workExperienceBean.setPosition(getPositionName());
                    workExperienceBean.setCompany(getCompanyName());
                    workExperienceBean.setStore(getCompanyURL());
                    workExperienceBean.setMonthly_money(getSalary());
                    workExperienceBean.setIntroduction(getWorkContent());
                    workDateBean.setStart_date(getBeginTime());
                    workDateBean.setEnd_date(getEndTime());
                    workExperienceBean.setWork_date(workDateBean);
                    //如果是新增，直接添加
                    if (isAdd){
                        workExperienceList.add(workExperienceBean);
                    }else {//如果是编辑，编辑后的数据替换原来的数据
                        workExperienceList.set(position,workExperienceBean);
                    }
                    btSave.setEnabled(false);
                    getPresenter().changeWorkExperience(resumeInfoData.getId(),workExperienceList);
                }
                break;
            case R.id.bt_delete://删除
                if (MyUtils.isFastClick()){
                    return;
                }
                btDelete.setEnabled(false);
                workExperienceList.remove(position);
                getPresenter().changeWorkExperience(resumeInfoData.getId(),workExperienceList);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_experience);
        ButterKnife.bind(this);
        //初始化开始结束时间
        initTime();
        initData();
        //薪资输入框（小数点限制两位数）
        setEtListener(etSalary);
    }

    @Override
    protected WorkExperiencePresenter createPresenter() {
        return new WorkExperiencePresenter();
    }

    /**
     * 接收 MyResumeActivity 传过来 ResumeInfoData(是新增还是编辑以及工作经历位标)
     */
    private void initData() {
        isAdd = getIntent().getBooleanExtra(ConstantUtil.KEY_WHETHER,false);
        position = getIntent().getIntExtra(ConstantUtil.KEY_POSITION, 0);
        resumeInfoData = (ResumeInfoData) getIntent().getSerializableExtra(ConstantUtil.KEY_DATA);
        workExperienceList = resumeInfoData.getWork_experience();
        if (isAdd){//添加工作经历
            tvTitle.setText(R.string.title_experience_add);
            btSave.setText("添加");
            btDelete.setVisibility(View.GONE);
        }else {//编辑工作经历
            tvTitle.setText(R.string.title_experience_edit);
            btSave.setText("保存");
            btDelete.setVisibility(View.VISIBLE);
        }

        //获取参数信息（平台、风格、类目、职位）
        getPresenter().getInfo();
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
     * 初始化开始结束时间
     */
    private void initTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
        //开始时间
        Date beginDate = curCalendar.getTime();
        beginDateStr = sdf.format(beginDate);
        //将开始时间转为时间戳(只转日期不转时间，方便pickDate方法做判断)
        curDateLong = Long.parseLong(DateUtil.getCustomType2Timestamp(beginDateStr,"yyyy/MM"))* 1000L;
        beginDateLong = curDateLong;
        tvBeginTime.setText(beginDateStr);

        //结束时间---推迟日历一个月
        Date endDate = curCalendar.getTime();
        endDateStr = sdf.format(endDate);
        //将结束时间转为时间戳(只转日期不转时间，方便pickDate方法做判断)
        endDateLong = Long.parseLong(DateUtil.getCustomType2Timestamp(endDateStr,"yyyy/MM"))* 1000L;
        tvEndTime.setText(endDateStr);
    }

    /**
     * 选择开始结束时间
     */
    private void  pickDate(final TextView tvDate, final boolean isBegin, String titleStr) {
        TimePickerView pvTime = new TimePickerView.Builder(WorkExperienceActivity.this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
                String selectDateStr = sdf.format(date);
                long selectDateLong = date.getTime();

                //如果是开始时间，则选中的日期不能在当前日期之后
                if (isBegin){
                    if(selectDateLong <= curDateLong ) {
                        //判断开始日期不能在结束日期之后
                        if (selectDateLong > endDateLong){
                            ToastUtil.myToast("开始时间需在结束时间之前!");
                        }else {
                            beginDateLong = selectDateLong;//赋值，用在结束日期做判断
                            tvDate.setText(selectDateStr);
                        }
                    }else {
                        ToastUtil.myToast("开始时间不能超过当前日期!");
                    }
                }else {//如果是结束时间，则选中的日期不能在当前日期之后
                    if(selectDateLong <= curDateLong ) {
                        //判断结束日期不能在开始日期之前
                        if(selectDateLong >= beginDateLong) {
                            endDateLong = selectDateLong;//赋值，用在开始日期做判断
                            tvDate.setText(selectDateStr);
                        }else {
                            ToastUtil.myToast("结束时间需在开始时间之后!");
                        }
                    }else {
                        ToastUtil.myToast("结束时间不能超过当前日期!");
                    }
                }
            }
        })
                .setType(new boolean[]{true, true, false, false, false, false})//默认全部显示,年月日时分秒
                .setTitleText(titleStr)//标题文字
                .setTitleSize(16)//标题文字大小
                .setContentSize(14)//滚轮文字大小
                .setSubmitColor(WorkExperienceActivity.this.getResources().getColor(R.color.color_ffb321))
                .setCancelColor(Color.GRAY)
                .isCenterLabel(false)
                .setOutSideCancelable(false)
                .build();

        pvTime.setDate(DateUtil.getCustomType2Calendar(tvDate.getText().toString(),"yyyy/MM"));
        pvTime.show();
    }

    /**
     * 自定义选择器
     */
    private void ShowCustomPickerView(final TextView tv, final ArrayList<ConditionBean> list) {
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
                tv.setText(tx);
                if (tv == tvPlatform){//平台
                    platformId = list.get(options1).getId();
                }else if (tv == tvCategory){//类目
                    categoryId = list.get(options1).getId();
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
     * IWorkExperienceView
     */
    @Override
    public String getPositionName() {
        return etPositionName.getText().toString().trim();
    }
    @Override
    public String getCompanyName() {
        return etCompanyName.getText().toString().trim();
    }
    @Override
    public String getCompanyURL() {
        return etCompanyUrl.getText().toString().trim();
    }
    @Override
    public String getSalary() {
        return etSalary.getText().toString().trim();
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
    public String getWorkContent() {
        return etWorkContent.getText().toString().trim();
    }
    @Override
    public void onRequestSuccess(ConditionInfoData tData) {
        platformBeans = tData.getBgapList();
        categoryBeans = tData.getBgatList();
        //如果是编辑，则设置控件数据---工作经历信息
        if (!isAdd){
            WorkExperienceBean workExperienceBean = workExperienceList.get(position);
            etPositionName.setText(workExperienceBean.getPosition());
            etCompanyName.setText(workExperienceBean.getCompany());
            etCompanyUrl.setText(workExperienceBean.getStore());
            etSalary.setText(workExperienceBean.getMonthly_money());
            //平台
            platformId = workExperienceBean.getBgap();
            for (int i = 0; i <platformBeans.size() ; i++) {
                if (platformBeans.get(i).getId().equals(platformId)){
                    tvPlatform.setText(platformBeans.get(i).getName());
                }
            }
            //类目
            categoryId = workExperienceBean.getBgat();
            for (int i = 0; i <categoryBeans.size() ; i++) {
                if (categoryBeans.get(i).getId().equals(categoryId)){
                    tvCategory.setText(categoryBeans.get(i).getName());
                }
            }
            tvBeginTime.setText(workExperienceBean.getWork_date().getStart_date());
            tvEndTime.setText(workExperienceBean.getWork_date().getEnd_date());
            etWorkContent.setText(workExperienceBean.getIntroduction());
        }
    }
    @Override
    public void onRequestFailure(String result) {
        finish();
        ToastUtil.myToast(result);
    }
    @Override
    public void onChangeWorkExperienceResult(boolean isSuccess, String result) {
        btSave.setEnabled(true);
        btDelete.setEnabled(true);
        ToastUtil.myToast(result);
        //成功-关闭界面，失败-工作经历列表恢复原来数据
        if (isSuccess){
            finish();
        }else {
            workExperienceList=resumeInfoData.getWork_experience();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancleMyToast();
    }
}
