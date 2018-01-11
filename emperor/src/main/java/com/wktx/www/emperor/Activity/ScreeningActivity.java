package com.wktx.www.emperor.Activity;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.Utils.ToastUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScreeningActivity extends AppCompatActivity {
    @BindView(R.id.linearLayout)
    LinearLayout mLinearLayout;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout mFlowLayout;
    @BindView(R.id.screeningTvWomen)
    TextView mTvWomen;
    @BindView(R.id.screeningTvMan)
    TextView mTvMan;
    private Drawable drawable_normal;
    private Drawable drawable_check;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.linearLayout, R.id.linearLayout1,R.id.screeningTvMan,R.id.screeningTvWomen,R.id.screeningTvSure,R.id.screeningTvCancel})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.linearLayout:
                finish();
                break;
            case R.id.linearLayout1:
                break;
            case R.id.screeningTvMan:
                mTvWomen.setBackground(drawable_normal);
                mTvMan.setBackground(drawable_check);
                break;
            case R.id.screeningTvWomen:
                mTvMan.setBackground(drawable_normal);
                mTvWomen.setBackground(drawable_check);
                break;
            case R.id.screeningTvCancel:
                ToastUtil.toast(ScreeningActivity.this,"取消");
                finish();
                break;
            case R.id.screeningTvSure:
                ToastUtil.toast(ScreeningActivity.this,"确定");
                finish();
                break;
            default:
                break;
        }
    }

    String[] mVals = {"一年以内", "一年", "两年", "三年", "四年", "五年"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screening);
        ButterKnife.bind(this);
        Slidr.attach(this);
        initFlowLayout();
    }

    private void initFlowLayout() {
        drawable_normal = getResources().getDrawable(R.drawable.normal_bg);
        drawable_check = getResources().getDrawable(R.drawable.checked_bg);
        mFlowLayout.setAdapter(new TagAdapter<String>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.item_textview,
                        mFlowLayout, false);
                tv.setTextSize(13);
                tv.setText(s);
                return tv;
            }
        });
        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                ToastUtil.toast(ScreeningActivity.this, mVals[position]);
                //view.setVisibility(View.GONE);
                return true;
            }
        });


        mFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
            }
        });
    }
}
