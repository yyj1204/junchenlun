package com.wktx.www.emperor.ui.activity.main.artistcase;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.zhy.autolayout.AutoLayoutActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 美工案例---筛选界面（最多浏览、最多喜欢）
 */
public class ScreeningArtistActivity extends AutoLayoutActivity {

    @BindView(R.id.tagflow_sort)
    TagFlowLayout tagflowSort;

    private String browseId;//浏览ID
    private String likedId;//喜欢ID
    private List<String> sortStrs = new ArrayList<>();

    @OnClick({R.id.tv,R.id.tv_cancel,R.id.tv_sure})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tv:
            case R.id.tv_cancel:
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                break;
            case R.id.tv_sure:
                if (MyUtils.isFastClick1()){
                    return;
                }
                //将筛选的结果传递回 ArtistCaseActivity 去检索
                String[] screeningIdStr={browseId,likedId};
                Intent intent = new Intent();
                intent.putExtra(ConstantUtil.KEY_POSITION,screeningIdStr);
                setResult(ConstantUtil.RESULTCODE_SCREENING,intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screening_artist);
        //沉浸式状态栏
        StatusBarUtil.setColor(ScreeningArtistActivity.this, getResources().getColor(R.color.color_ffb321),0);
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        //设置右滑动返回
        Slidr.attach(this);
        initData();
        initFlowLayout();
    }

    /**
     *接收 ArtistCaseActivity 传递过来的筛选条件（最多浏览、最多喜欢）
     */
    private void initData() {
        Bundle bundle = getIntent().getExtras();
        browseId = bundle.getString(ConstantUtil.KEY_DATA);
        likedId = bundle.getString(ConstantUtil.KEY_INFO);
        sortStrs.add("按浏览");
        sortStrs.add("按喜欢");
    }
    /**
     * 初始化流式布局
     */
    private void initFlowLayout() {
        //tag赋值
        TagAdapter<String> tagAdapter = new TagAdapter<String>(sortStrs) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.item_flowlayout_screening, tagflowSort, false);
                tv.setTextSize(12);
                tv.setText(s);
                return tv;
            }
        };
        //根据 ArtistCaseActivity 传过来的数据，给tag预先设置选中状态
        if (browseId.equals("1")&likedId.equals("0")){
            tagAdapter.setSelectedList(0);
        }else if (browseId.equals("0")&likedId.equals("1")){
            tagAdapter.setSelectedList(1);
        }else if (browseId.equals("1")&likedId.equals("1")){
            tagAdapter.setSelectedList(0,1);
        }

        tagflowSort.setAdapter(tagAdapter);
        //tag点击事件
        tagflowSort.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                return true;
            }
        });
        //设置监听事件的回调
        tagflowSort.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                //都没选中
                if (selectPosSet.size()==0){
                    browseId = "0";
                    likedId = "0";
                }else if (selectPosSet.size()==2){//两个都选中
                    browseId = "1";
                    likedId = "1";
                }else {//选中其中一个
                    for (Integer item:selectPosSet) {
                        if (item==0){
                            browseId = "1";
                            likedId = "0";
                        }else if (item==1){
                            browseId = "0";
                            likedId = "1";
                        }
                    }
                }

                LogUtil.error("筛选","browseId="+browseId+"\nlikedId="+likedId);
            }
        });
    }

}
