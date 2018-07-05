package com.wktx.www.subjects.ui.activity.main;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.jaeger.library.StatusBarUtil;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.utils.ToastUtil;
import com.zhy.autolayout.AutoLayoutActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 职位招聘片段---筛选界面（期望薪资）
 */
public class ScreeningSalaryActivity extends AutoLayoutActivity {
    @BindView(R.id.et_minSalary)
    EditText etMinSalary;
    @BindView(R.id.et_maxSalary)
    EditText etMaxSalary;

    private String[] screeningStr;

    @OnClick({R.id.tv,R.id.tv_cancel,R.id.tv_sure})
    public void MyOnclick(View view) {
        //将输入法隐藏
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etMinSalary.getWindowToken(), 0);

        switch (view.getId()) {
            case R.id.tv:
            case R.id.tv_cancel:
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                break;
            case R.id.tv_sure:
                if (MyUtils.isFastClick1()){
                    return;
                }
                //如果最低薪资与最高薪资都不为空
                if (!TextUtils.isEmpty(getMinSalary())&!TextUtils.isEmpty(getMaxSalary())){
                    double minSalaryDou = Double.parseDouble(getMinSalary());
                    double maxSalaryDou = Double.parseDouble(getMaxSalary());
                    //最高薪资要大于最低薪资
                    if (maxSalaryDou<minSalaryDou){
                         ToastUtil.myToast("最高薪资不能低于最低薪资！");
                        etMaxSalary.requestFocus();
                        return;
                    }
                }
                //如果最低薪资与最高薪资都为空
                if (TextUtils.isEmpty(getMinSalary())&TextUtils.isEmpty(getMaxSalary())){
                     ToastUtil.myToast("请输入期望薪资！");
                    etMinSalary.requestFocus();
                }else {//将筛选的结果传递回 MainFragment 去检索
                    screeningStr=new String[]{getMinSalary(),getMaxSalary()};
                    Intent intent = new Intent();
                    intent.putExtra(ConstantUtil.KEY_POSITION,screeningStr);
                    setResult(ConstantUtil.RESULTCODE_SCREENING,intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screening_salary);
        //沉浸式状态栏
        StatusBarUtil.setColor(ScreeningSalaryActivity.this, getResources().getColor(R.color.color_ffb321),0);
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        //设置右滑动返回
        Slidr.attach(this);
        initData();
    }

    /**
     *接收 MainFragment 传递过来的检索条件结果（最低、最高薪资）
     */
    private void initData() {
        Bundle bundle = getIntent().getExtras();
        String minSalary = bundle.getString(ConstantUtil.KEY_DATA);//最低薪资
        String maxSalary = bundle.getString(ConstantUtil.KEY_POSITION);//最高薪资
        etMinSalary.setText(minSalary);
        etMaxSalary.setText(maxSalary);
        setEtListener(etMinSalary);
        setEtListener(etMaxSalary);
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

    private String getMinSalary(){
        return etMinSalary.getText().toString().trim();
    }

    private String getMaxSalary(){
        return etMaxSalary.getText().toString().trim();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancleMyToast();
    }
}
