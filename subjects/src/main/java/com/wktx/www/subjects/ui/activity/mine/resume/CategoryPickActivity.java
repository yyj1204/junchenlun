package com.wktx.www.subjects.ui.activity.mine.resume;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jaeger.library.StatusBarUtil;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.main.condition.ConditionBean;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.LogUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.widget.MyLayoutManager;
import com.wktx.www.subjects.utils.ToastUtil;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 参数多选界面
 */
public class CategoryPickActivity extends AutoLayoutActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    private BaseQuickAdapter<ConditionBean, BaseViewHolder> mAdapter;
    private ArrayList<ConditionBean> list;
    private ArrayList<ConditionBean> selectedLists = new ArrayList<>();//选中集合

    @OnClick({R.id.tv_cancel, R.id.tv_sure})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_sure:
                if (selectedLists.size()==0){
                    ToastUtil.myToast("当前选择不能为空！");
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra(ConstantUtil.KEY_DATA,selectedLists);
                setResult(ConstantUtil.RESULTCODE_PICK,intent);
                finish();
                break;
            default:
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_pick);
        //沉浸式状态栏
        StatusBarUtil.setColor(CategoryPickActivity.this, getResources().getColor(R.color.color_ffb321),0);
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        initData();
        initVtRecycleView();
    }

    /**
     * 接收 ApplyPosition1Activity、WorksDetailsActivity 传过来多选数据
     */
    private void initData() {
        selectedLists = (ArrayList<ConditionBean>) getIntent().getSerializableExtra(ConstantUtil.KEY_POSITION);
        list = (ArrayList<ConditionBean>) getIntent().getSerializableExtra(ConstantUtil.KEY_DATA);
        if (selectedLists.size()!=0){
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j <selectedLists.size() ; j++){
                    if (list.get(i).getId().equals(selectedLists.get(j).getId())){
                        list.get(i).setSelected(true);
                        break;
                    }else {
                        list.get(i).setSelected(false);
                    }
                }
            }
        }
    }

    private void initVtRecycleView() {
        MyLayoutManager myLayoutManager = new MyLayoutManager(this, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(myLayoutManager);
        mAdapter = new BaseQuickAdapter<ConditionBean, BaseViewHolder>(R.layout.item_rv_categorypick, list) {
            @Override
            protected void convert(BaseViewHolder helper, final ConditionBean item) {
                //选中按钮
                final CheckBox checkbox = helper.getView(R.id.checkbox);
                checkbox.setText("  "+item.getName());
                //先设置一次CheckBox的选中监听器，传入参数null
                checkbox.setOnCheckedChangeListener(null);
                //初始化设置CheckBox的选中状态
                checkbox.setChecked(item.isSelected());

                //再设置一次CheckBox的选中监听器，当CheckBox的选中状态发生改变时，把改变后的状态储存
                checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b){//选中，添加到集合(最多只能添加3个)
                            if (selectedLists.size()<3){
                                checkbox.setChecked(true);
                                item.setSelected(true);
                                selectedLists.add(item);
                            }else {
                                item.setSelected(false);
                                checkbox.setChecked(false);
                                ToastUtil.myToast("最多选择三个！");
                            }
                        }else {//取消选中，从集合中删除
                            Iterator<ConditionBean> sListIterator = selectedLists.iterator();
                            while(sListIterator.hasNext()) {
                                ConditionBean e = sListIterator.next();
                                if (e.getId().equals(item.getId())) {
                                    sListIterator.remove();
                                }
                            }
                            checkbox.setChecked(false);
                            item.setSelected(false);
                        }
                    }
                });
            }
        };
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancleMyToast();
    }
}
