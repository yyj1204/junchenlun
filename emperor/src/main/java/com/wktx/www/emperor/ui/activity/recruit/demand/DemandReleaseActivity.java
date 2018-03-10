package com.wktx.www.emperor.ui.activity.recruit.demand;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.login.AccountInfoData;
import com.wktx.www.emperor.apiresult.recruit.demand.DemandReleaseConditionInfoData;
import com.wktx.www.emperor.apiresult.recruit.retrievalcondition.Bean;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.recruit.demand.DemandReleasePresenter;
import com.wktx.www.emperor.utils.LoginUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.view.recruit.IDemandReleaseView;

import java.util.ArrayList;

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
    @BindView(R.id.tv_demandPlatfrom)
    TextView tvPlatfrom;
    @BindView(R.id.tv_demandCategory)
    TextView tvCategory;
    @BindView(R.id.tv_demandPattern)
    TextView tvPattern;
    @BindView(R.id.bt_sureRelease)
    Button btRelease;

    private OptionsPickerView pvCustomOptions;//自义定选择器

    private ArrayList<Bean> storeBeans = new ArrayList<>();//店铺名称
    private ArrayList<Bean> platformBeans = new ArrayList<>();//平台名称
    private ArrayList<Bean> categoryBeans = new ArrayList<>();//类目名称
    private ArrayList<Bean> patternBeans = new ArrayList<>();//需求模式名称
    private String storeId;//店铺Id
    private String platformId;//平台Id
    private String categoryId;//类目Id
    private String patternId;//需求模式Id

    private ArrayList<String> optionsItemStrs = new ArrayList<>();//选择器字符串集合

    @OnClick({R.id.tb_IvReturn,R.id.linear_demandStore,R.id.linear_demandPlatfrom,R.id.linear_demandCategory,R.id.linear_demandPattern,R.id.bt_sureRelease})
    public void MyOnclick(View view) {
        switch (view.getId()){
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.linear_demandStore://选择店铺
                if (platformBeans.size()!=0){//TODO storeBeans
                    initCustomOptionPicker(R.id.tv_demandStore,platformBeans);
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
            case R.id.bt_sureRelease://确认发布
                if (MyUtils.isFastClick()){
                    return;
                }
                //判断输入框格式
                if (TextUtils.isEmpty(getDemandTitle())){
                    MyUtils.showToast(DemandReleaseActivity.this,"请输入需求标题！");
                    etTitle.requestFocus();
                }else if (TextUtils.isEmpty(getDemandContent())){
                    MyUtils.showToast(DemandReleaseActivity.this,"请输入需求详情！");
                    etContent.requestFocus();
                }else if (TextUtils.isEmpty(getDemandBudget())){
                    MyUtils.showToast(DemandReleaseActivity.this,"请输入预算金额！");
                    etBudget.requestFocus();
                }else if (TextUtils.isEmpty(storeId)){
                    MyUtils.showToast(DemandReleaseActivity.this,"请选择店铺！");
                }else if (TextUtils.isEmpty(platformId)){
                    MyUtils.showToast(DemandReleaseActivity.this,"请选择平台！");
                }else if (TextUtils.isEmpty(categoryId)){
                    MyUtils.showToast(DemandReleaseActivity.this,"请选择类目！");
                }else if (TextUtils.isEmpty(patternId)){
                    MyUtils.showToast(DemandReleaseActivity.this,"请选择需求！");
                }else {//发布需求
                    btRelease.setEnabled(false);
                    getPresenter().onDemandRelease(platformId,categoryId,patternId);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demand_release);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_demand_release);
        //预算输入框（小数点限制两位数）
        setEtListener(etBudget);
    }

    @Override
    protected DemandReleasePresenter createPresenter() {
        return new DemandReleasePresenter();
    }

    //输入框的监听事件
    private void setEtListener(EditText et) {
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
                            etBudget.setText(s);
                            etBudget.setSelection(s.length());
                        }
                    }

                    //判断字符串的第一位是小数点，则在小数点前面加个0
                    if (s.toString().trim().substring(0).equals(".")) {
                        s = "0" + s;
                        etBudget.setText(s);
                        etBudget.setSelection(2);
                    }

                    //判断字符串第一位是0
                    if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                        //如果第二位不是小数点，限制不能输入
                        if (!s.toString().substring(1, 2).equals(".")) {
                            etBudget.setText(s.subSequence(0, 1));
                            etBudget.setSelection(1);
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
     * IDemandReleaseView
     */
    @Override
    public AccountInfoData getUserInfo() {
        AccountInfoData userInfo = LoginUtil.getinit().getUserInfo();
        return userInfo;
    }
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
    @Override//需求发布是否成功的回调
    public void onDemandReleaseResult(boolean isSuccess, String msg) {
        MyUtils.showToast(DemandReleaseActivity.this,msg);
        btRelease.setEnabled(true);
        if (isSuccess){
            finish();
        }
    }
    @Override//获取需求需要的选择参数（店铺、平台、类目、需求模式）
    public void onRequestSuccess(DemandReleaseConditionInfoData tData) {
        platformBeans = tData.getBgap();
        categoryBeans = tData.getBgat();
        patternBeans = tData.getDesign_pattern();
    }
    @Override
    public void onRequestFailure(String result) {
        finish();
        MyUtils.showToast(DemandReleaseActivity.this,result);
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
                .setLayoutRes(R.layout.layout_custom_pickerview, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        TextView tvCancel = (TextView) v.findViewById(R.id.iv_cancel);
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
}
