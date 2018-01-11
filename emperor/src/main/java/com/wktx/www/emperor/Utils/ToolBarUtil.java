package com.wktx.www.emperor.Utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.wktx.www.emperor.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/9/30.
 */

public class ToolBarUtil extends LinearLayout {

    @BindView(R.id.tb_ivReturn)
    ImageView mIvReturn;
    @BindView(R.id.tb_tvBarTitle)
    TextView mTvTitle;
    @BindView(R.id.tb_ivRight)
    ImageView mIvRight;
    @BindView(R.id.tb_tvBarSubmit)
    TextView mTvSubmit;
    private Context mContext;

    @OnClick({R.id.tb_ivReturn, R.id.tb_ivRight, R.id.tb_tvBarSubmit})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_ivReturn:
                Activity activity = (Activity) mContext;
                activity.finish();
                break;
            case R.id.tb_ivRight:
                break;
        }
    }

    public ToolBarUtil(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.include_toolbar, this, true);
        ButterKnife.bind(this);
    }


    public void initToolBar(int rt, int title, int img, int name) {
        mTvTitle.setText("你是不是傻");
        if (name != 0) {
            mTvSubmit.setText(name);
        } else {
            mTvSubmit.setVisibility(View.GONE);
        }
        if (img != 0) {
            mIvRight.setImageResource(img);

        } else {
            mIvRight.setVisibility(View.GONE);
        }
        if (rt == 0) {
            mIvReturn.setVisibility(View.GONE);
        }
        if (rt == 0) {
            mIvReturn.setVisibility(View.GONE);
        }
    }

    public ToolBarUtil(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ToolBarUtil(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.widget_title_layout, this, true);
        ButterKnife.bind(this);
    }
}
