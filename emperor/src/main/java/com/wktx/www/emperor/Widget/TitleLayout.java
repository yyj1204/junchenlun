package com.wktx.www.emperor.Widget;

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
 * Created by Administrator on 2016/11/30 0030.
 */
public class TitleLayout extends LinearLayout {

    @BindView(R.id.title_layout)
    LinearLayout mTitleLayout;
    @BindView(R.id.title_search)
    TextView mTitleSearch;
    @BindView(R.id.tb_right_img)
    ImageView mTitleNewInfo;
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
        mTitleLayout.setBackgroundColor(color);
    }

    public void setSearchDrawableBackground(int shapeDrawable) {
        mTitleSearch.setBackgroundResource(shapeDrawable);
    }


}
