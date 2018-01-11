package com.wktx.www.subjects.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 管理我的员工---员工续签
 */
public class StaffRenewActivity extends AppCompatActivity {

    @BindView(R.id.tb_TvBarTitle)
    TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_renew);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        mTvTitle.setText(R.string.title_staff_renew);
    }

    @OnClick({R.id.tb_IvReturn,R.id.rela_renew_minus,R.id.rela_renew_add,R.id.tv_renew})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            case R.id.rela_renew_minus://减号
                break;
            case R.id.rela_renew_add://加号
                break;
            case R.id.tv_renew://确认续签
                break;
            default:
                break;
        }
    }

}
