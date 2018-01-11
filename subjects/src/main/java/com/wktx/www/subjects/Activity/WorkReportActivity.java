package com.wktx.www.subjects.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorkReportActivity extends AppCompatActivity {
    @BindView(R.id.tb_TvBarTitle)
    TextView mTvTitle;

    @OnClick({R.id.tb_IvReturn})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_report);
        ButterKnife.bind(this);
        Slidr.attach(this);
        mTvTitle.setText(R.string.title_SalesVolume);
    }
}
