package com.wktx.www.emperor.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 管理我的员工---暂停工作
 */
public class StaffPauseWorkActivity extends AppCompatActivity {

    @BindView(R.id.tb_TvBarTitle)
    TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_pause_work);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        mTvTitle.setText(R.string.title_staff_pausework);
    }

    @OnClick({R.id.tb_IvReturn,R.id.rela_pausework_minus,R.id.rela_pausework_add,R.id.tv_pausework_sure})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            case R.id.rela_pausework_minus://减号
                break;
            case R.id.rela_pausework_add://加号
                break;
            case R.id.tv_pausework_sure://确定
                break;
            default:
                break;
        }
    }
}
