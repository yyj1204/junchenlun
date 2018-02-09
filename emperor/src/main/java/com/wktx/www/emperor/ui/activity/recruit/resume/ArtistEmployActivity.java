package com.wktx.www.emperor.ui.activity.recruit.resume;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.Activity.OrdersInfoActivity;
import com.wktx.www.emperor.R;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 美工简历---美工雇佣
 */
public class ArtistEmployActivity extends AutoLayoutActivity {
    @BindView(R.id.tb_TvBarTitle)
    TextView mTvTitle;

    @OnClick({R.id.tb_IvReturn,R.id.tv_order_sure})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            case R.id.tv_order_sure://确认下单
                startActivity(new Intent(this,OrdersInfoActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_employ);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        mTvTitle.setText(R.string.title_artist_employ);
    }
}
