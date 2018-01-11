package com.wktx.www.emperor.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.Utils.ToastUtil;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity {
    @OnClick({R.id.tb_IvReturn,R.id.searchIvDelete})
    public void MyOnclick(View view)
    {
        switch (view.getId())
        {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.searchIvDelete:
                ToastUtil.toast(this,"删除历史记录");
                break;
            default:
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        // 设置右滑动返回
        Slidr.attach(this);
        ButterKnife.bind(this);
    }



}
