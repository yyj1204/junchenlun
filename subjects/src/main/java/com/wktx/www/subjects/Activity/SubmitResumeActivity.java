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

public class SubmitResumeActivity extends AppCompatActivity {
    @BindView(R.id.tb_TvBarTitle)
    TextView mTvTitle;
    @OnClick({R.id.tb_IvReturn})
    public void MyOnclick(View view) {
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_resume);
        ButterKnife.bind(this);
        Slidr.attach(this);
        mTvTitle.setText(R.string.title_SubmitResume);
    }
}
