package com.wktx.www.emperor.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.wktx.www.emperor.R;
import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 招聘顶部搜索框控件
 */
public class TitleLayout extends LinearLayout {

    @BindView(R.id.linear_titleSearch)
    LinearLayout llTitle;
    @BindView(R.id.tv_titleSearch)
    TextView tvSearch;
    @BindView(R.id.iv_titleRight)
    ImageView ivRight;

    private Context mContext;


    public TitleLayout(Context context) {
        this(context, null);
    }

    public TitleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.widget_title_layout, this, true);
        ButterKnife.bind(this);
    }

    public void setTitleLayoutColor(int color) {
        llTitle.setBackgroundColor(color);
    }

    public void setSearchDrawableBackground(int shapeDrawable) {
        tvSearch.setBackgroundResource(shapeDrawable);
    }
}
