package com.wktx.www.subjects.ui.activity.mine.resume;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.login.AccountInfoData;
import com.wktx.www.subjects.apiresult.main.condition.ConditionBean;
import com.wktx.www.subjects.apiresult.mine.resume.ResumeInfoData;
import com.wktx.www.subjects.apiresult.main.condition.ConditionInfoData;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.presenter.mine.resume.ResumePositionPresenter;
import com.wktx.www.subjects.ui.view.mine.resume.IResumePositionView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.LoginUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的简历 --- 应聘职位详情（新增应聘职位）
 */
public class ResumePositionActivity extends ABaseActivity<IResumePositionView,ResumePositionPresenter> implements IResumePositionView {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.tv_position)
    TextView tvPosition;
    @BindView(R.id.tv_category)
    TextView tvCategory;
    @BindView(R.id.tv_platform)
    TextView tvPlatform;
    @BindView(R.id.tv_experience)
    TextView tvExperience;
    @BindView(R.id.linear_style)
    LinearLayout llStyle;
    @BindView(R.id.tv_style)
    TextView tvStyle;
    @BindView(R.id.linear_speed)
    LinearLayout llSpeed;
    @BindView(R.id.et_speed)
    EditText etSpeed;
    @BindView(R.id.et_salary)
    EditText etSalary;
    @BindView(R.id.bt_save)
    Button btSave;
    @BindView(R.id.bt_delete)
    Button btDelete;

    private boolean isAdd;//是否新增
    private ResumeInfoData resumeInfoData;//简历信息

    private OptionsPickerView pvCustomOptions;//自义定选择器
    private String positionId;//职位ID
    private String categoryIds;//类目ID（多个）
    private String categoryStr="";//类目名称（多个）
    private String platformId;//平台ID
    private String experienceId;//经验ID
    private String styleIds;//风格ID（多个）
    private String styleStr="";//风格名称（多个）
    private ArrayList<ConditionBean> positionBeans = new ArrayList<>();//职位名称
    private ArrayList<ConditionBean> categoryBeans = new ArrayList<>();//类目名称
    private ArrayList<ConditionBean> platformBeans = new ArrayList<>();//平台名称
    private ArrayList<ConditionBean> styleBeans = new ArrayList<>();//风格名称
    private ArrayList<ConditionBean> experienceBeans = new ArrayList<>();//经验名称
    private ArrayList<String> optionsItemStrs = new ArrayList<>();//选择器字符串集合

    @OnClick({R.id.tb_IvReturn,R.id.linear_position,R.id.linear_category,R.id.linear_platform,
            R.id.linear_experience, R.id.linear_style,R.id.bt_save,R.id.bt_delete})
    public void MyOnclick(View view) {
        //将输入法隐藏
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tvTitle.getWindowToken(), 0);

        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.linear_position://职位类型
                ShowCustomPickerView(tvPosition,positionBeans);
                break;
            case R.id.linear_category://类目
                startPickActivity(categoryBeans,ConstantUtil.REQUESTCODE_CATEGORY);
                break;
            case R.id.linear_platform://平台
                ShowCustomPickerView(tvPlatform,platformBeans);
                break;
            case R.id.linear_experience://工作经验
                ShowCustomPickerView(tvExperience,experienceBeans);
                break;
            case R.id.linear_style://擅长风格
                startPickActivity(styleBeans,ConstantUtil.REQUESTCODE_STYLE);
                break;
            case R.id.bt_save://保存
                if (MyUtils.isFastClick()){
                    return;
                }
                //判断输入框格式
                if (TextUtils.isEmpty(positionId)){
                     ToastUtil.myToast("请选择职位类型！");
                }else if (TextUtils.isEmpty(categoryIds)){
                     ToastUtil.myToast("请选择类目！");
                }else if (TextUtils.isEmpty(platformId)){
                     ToastUtil.myToast("请选择平台！");
                }else if (TextUtils.isEmpty(experienceId)){
                     ToastUtil.myToast("请选择工作经验！");
                }else if (TextUtils.isEmpty(getSalary())) {
                     ToastUtil.myToast( "请填写期望薪资！");
                    etSalary.requestFocus();
                }else if (positionId.equals("1")&TextUtils.isEmpty(styleIds)){//美工
                     ToastUtil.myToast("请选择风格！");
                }else if (positionId.equals("2")&TextUtils.isEmpty(getSpeed())){//客服
                     ToastUtil.myToast("请填写字数！");
                    etSpeed.requestFocus();
                }else {
                    btSave.setEnabled(false);
                    getPresenter().changeApplyPosition(resumeInfoData.getId());
                }
                break;
            case R.id.bt_delete://删除
                if (MyUtils.isFastClick()){
                    return;
                }
                btDelete.setEnabled(false);
                getPresenter().deleteApplyPosition(resumeInfoData.getId());
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
        Intent intent = new Intent(ResumePositionActivity.this, CategoryPickActivity.class);
        intent.putExtra(ConstantUtil.KEY_DATA, beans);
        startActivityForResult(intent,requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_position);
        ButterKnife.bind(this);
        // 设置右滑动返回
//        Slidr.attach(this);
        initData();
        initUI();
    }

    @Override
    protected ResumePositionPresenter createPresenter() {
        return new ResumePositionPresenter();
    }

    /**
     * 接收 MyResumeActivity 传过来 ResumeInfoData(是新增还是编辑)
     */
    private void initData() {
        isAdd = getIntent().getBooleanExtra(ConstantUtil.KEY_WHETHER,false);
        resumeInfoData = (ResumeInfoData) getIntent().getSerializableExtra(ConstantUtil.KEY_DATA);
        //获取参数信息（平台、风格、类目、职位）
        getPresenter().getInfo();
    }

    private void initUI() {
        if (isAdd){//添加应聘职位
            tvTitle.setText(R.string.title_position_add);
            btSave.setText("添加");
            btDelete.setVisibility(View.GONE);
        }else {//编辑应聘职位---获取应聘职位信息
            tvTitle.setText(R.string.title_position_edit);
            btSave.setText("保存");
            btDelete.setVisibility(View.VISIBLE);
        }
        //打字速度、期望薪资输入框（小数点限制两位数）
        setEtListener(etSpeed);
        setEtListener(etSalary);
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
                if (tv == tvPosition){//职位
                    positionId = list.get(options1).getId();
                    changePositionState();
                }else if (tv == tvPlatform){//平台
                    platformId = list.get(options1).getId();
                }else if (tv == tvExperience){//经验
                    experienceId = list.get(options1).getId();
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
        for (int i = 0; i < list.size(); i++) {
            optionsItemStrs.add(list.get(i).getName());
        }
        pvCustomOptions.setPicker(optionsItemStrs);//添加数据
        pvCustomOptions.show();
    }


    /**
     * 修改选中职位，显示对应参数布局
     */
    private void changePositionState() {
        //判断选中的职位类型，选中的是美工还是客服还是其他类型
        if (positionId.equals("1")){//美工-显示擅长风格
            llStyle.setVisibility(View.VISIBLE);
            llSpeed.setVisibility(View.GONE);
        }else if (positionId.equals("2")){//客服-显示打字速度
            llStyle.setVisibility(View.GONE);
            llSpeed.setVisibility(View.VISIBLE);
        }else {//其他职位类型，打字速度、擅长风格都隐藏
            llStyle.setVisibility(View.GONE);
            llSpeed.setVisibility(View.GONE);
        }
    }

    /**
     * IResumePositionView
     */
    @Override
    public AccountInfoData getUserInfo() {
        AccountInfoData userInfo = LoginUtil.getinit().getUserInfo();
        return userInfo;
    }
    @Override
    public String getPositionId() {
        return positionId;
    }
    @Override
    public String getCategoryIds() {
        return categoryIds;
    }
    @Override
    public String getPlatformId() {
        return platformId;
    }
    @Override
    public String getExperienceId() {
        return experienceId;
    }
    @Override
    public String getStyleIds() {
        return styleIds;
    }
    @Override
    public String getSpeed() {
        return etSpeed.getText().toString().trim();
    }
    @Override
    public String getSalary() {
        return etSalary.getText().toString().trim();
    }
    @Override
    public void onRequestSuccess(ConditionInfoData tData) {
        positionBeans = tData.getTowList();
        categoryBeans = tData.getBgatList();
        platformBeans = tData.getBgapList();
        styleBeans = tData.getBgasList();
        experienceBeans = tData.getWorkingYearsList();

        //如果是编辑，则设置控件数据
        if (!isAdd){
            //职位
            positionId = resumeInfoData.getTow().getId();
            tvPosition.setText(resumeInfoData.getTow().getName());
            changePositionState();
            //类目(多个)
            List<ConditionBean> categoryList = resumeInfoData.getBgatList();
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
            //平台
            platformId = resumeInfoData.getBgap().getId();
            tvPlatform.setText(resumeInfoData.getBgap().getName());
            //经验
            experienceId = resumeInfoData.getWorking_years().getId();
            tvExperience.setText(resumeInfoData.getWorking_years().getName());
            //风格（多个）
            List<ConditionBean> styleList = resumeInfoData.getBgasList();
            if (styleList.size()!=0){
                for (int i = 0; i <styleList.size() ; i++) {
                    if (i==0){
                        styleIds = styleList.get(i).getId();
                        styleStr = styleList.get(i).getName();
                    }else {
                        styleIds = styleIds+","+styleList.get(i).getId();
                        styleStr = styleStr +"/"+styleList.get(i).getName();
                    }
                }
            }
            tvStyle.setText(styleStr);
            //打字速度
            String typing_speed = resumeInfoData.getTyping_speed();
            String substring = typing_speed.substring(0, typing_speed.length() - 3);
            etSpeed.setText(substring);
            //薪资
            etSalary.setText(resumeInfoData.getMonthly_money());
        }
    }
    @Override
    public void onRequestFailure(String result) {
        finish();
         ToastUtil.myToast(result);
    }
    @Override
    public void onChangeApplyPositionResult(boolean isSuccess, String result) {
        btSave.setEnabled(true);
        btDelete.setEnabled(true);
         ToastUtil.myToast(result);
        //成功-关闭界面
        if (isSuccess){
            finish();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
            if (resultCode==ConstantUtil.RESULTCODE_PICK){
                ArrayList<ConditionBean> content = (ArrayList<ConditionBean>) data.getSerializableExtra(ConstantUtil.KEY_DATA);
                switch (requestCode){
                    case ConstantUtil.REQUESTCODE_CATEGORY://类目
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
                        break;
                    case ConstantUtil.REQUESTCODE_STYLE://风格
                        for (int i = 0; i <content.size() ; i++) {
                            if (i==0){
                                styleIds = content.get(i).getId();
                                styleStr = content.get(i).getName();
                            }else {
                                styleIds = styleIds+","+content.get(i).getId();
                                styleStr = styleStr +"/"+content.get(i).getName();
                            }
                        }
                        tvStyle.setText(styleStr);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancleMyToast();
    }
}
