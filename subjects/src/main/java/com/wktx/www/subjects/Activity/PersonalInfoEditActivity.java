package com.wktx.www.subjects.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.View;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 编辑个人信息---（姓名、电话、QQ、微信）
 */
public class PersonalInfoEditActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.et_edit)
    TextView et_edit;
    private String[] strings;

    @OnClick({R.id.iv_cancel,R.id.iv_save})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.iv_cancel:
                finish();
                break;
            case R.id.iv_save:
                Intent intent = new Intent();
                String[] str = {strings[0],et_edit.getText().toString().trim()};
                intent.putExtra("info",str);
                setResult(0,intent);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info_edit);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        //接收个人信息传递过来的信息
        strings = getIntent().getStringArrayExtra("edit");
        initUI();
    }

    private void initUI() {
        int type = Integer.parseInt(strings[0]);
        switch (type){
            case 0:
                setUI("姓名");
                break;
            case 1:
                setUI("电话");
                break;
            case 2:
                setUI("QQ");
                break;
            case 3:
                setUI("微信");
                break;
            default:
                break;
        }
    }

    private void setUI(String type) {
        tv_title.setText(type);
        SpannableString hintStr = new SpannableString("请输入"+type);
        et_edit.setHint(hintStr);
        et_edit.setText(strings[1]);
    }
}
