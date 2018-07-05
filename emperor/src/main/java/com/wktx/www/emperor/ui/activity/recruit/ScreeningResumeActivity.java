package com.wktx.www.emperor.ui.activity.recruit;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.recruit.retrievalcondition.Bean;
import com.wktx.www.emperor.apiresult.recruit.retrievalcondition.RetrievalConditionInfoData;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.zhy.autolayout.AutoLayoutActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 招聘片段---筛选界面（工作经验、性别）
 */
public class ScreeningResumeActivity extends AutoLayoutActivity {
    @BindView(R.id.tv_jobName)
    TextView tvJobName;
    @BindView(R.id.tagflow_experience)
    TagFlowLayout tagflowExperience;
    @BindView(R.id.tv_sexMan)
    TextView tvMan;
    @BindView(R.id.tv_sexWoman)
    TextView tvWoman;

    private int jobTypePosition;//工作类型位标
    private RetrievalConditionInfoData conditionInfoData;//检索条件
    private List<Bean> experienceBeans;//工作经验
    private String experienceId;//工作经验ID
    private String sexId;//性别ID

    @OnClick({R.id.tv,R.id.tv_sexMan,R.id.tv_sexWoman,R.id.tv_cancel,R.id.tv_sure})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tv:
            case R.id.tv_cancel:
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                break;
            case R.id.tv_sexMan://男
                sexId="1";
                tvMan.setSelected(true);
                tvWoman.setSelected(false);
                break;
            case R.id.tv_sexWoman://女
                sexId="2";
                tvMan.setSelected(false);
                tvWoman.setSelected(true);
                break;
            case R.id.tv_sure:
                if (MyUtils.isFastClick1()){
                    return;
                }
                //将筛选的结果传递回 RecruitListFragment 去检索
                String[] screeningIdStr={experienceId,sexId};
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
        setContentView(R.layout.activity_screening_resume);
        //沉浸式状态栏
        StatusBarUtil.setColor(ScreeningResumeActivity.this, getResources().getColor(R.color.color_ffb321),0);
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        //设置右滑动返回
        Slidr.attach(this);
        initData();
        initView();
        initFlowLayout();
    }

    /**
     *接收 RecruitListFragment 传递过来的检索条件结果（工作经验、性别）
     */
    private void initData() {
        Bundle bundle = getIntent().getExtras();
        jobTypePosition = bundle.getInt(ConstantUtil.KEY_POSITION);
        experienceId = bundle.getString("experience");
        sexId = bundle.getString("sex");
        conditionInfoData = (RetrievalConditionInfoData) bundle.getSerializable(ConstantUtil.KEY_DATA);
        experienceBeans = conditionInfoData.getBottom().getWorking_years();
    }

    private void initView() {
        tvJobName.setText(conditionInfoData.getTop().getTow().get(jobTypePosition).getName()+"筛选");
        //性别预先设置选中状态
        if(sexId.equals("1")){
            tvMan.setSelected(true);
            tvWoman.setSelected(false);
        }else if (sexId.equals("2")){
            tvMan.setSelected(false);
            tvWoman.setSelected(true);
        }
    }

    /**
     * 初始化流式布局
     */
    private void initFlowLayout() {
        //tag赋值
        TagAdapter<Bean> tagAdapter = new TagAdapter<Bean>(experienceBeans) {
            @Override
            public View getView(FlowLayout parent, int position, Bean s) {
                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.item_flowlayout_screening,
                        tagflowExperience, false);
                tv.setTextSize(12);
                tv.setText(s.getName());
                return tv;
            }
        };
        //根据 RecruitListFragment 传过来的数据，给tag预先设置选中状态
        for (int i = 0; i <experienceBeans.size() ; i++) {
            if (experienceBeans.get(i).getId().equals(experienceId)){
                tagAdapter.setSelectedList(i);
                break;
            }
        }
        tagflowExperience.setAdapter(tagAdapter);
        //tag点击事件
        tagflowExperience.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                return true;
            }
        });
        //设置监听事件的回调
        tagflowExperience.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                //都没选中
                if (selectPosSet.size()==0){
                    experienceId="0";
                }else {//选中一个
                    for (Integer item:selectPosSet) {
                        experienceId = experienceBeans.get(item).getId();
                    }
                }
            }
        });
    }
}
