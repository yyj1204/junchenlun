package com.wktx.www.subjects.ui.activity.mine.resume;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.mine.resume.ResumeInfoData;
import com.wktx.www.subjects.basemvp.ABaseActivity;
import com.wktx.www.subjects.presenter.mine.resume.EditTagsPresenter;
import com.wktx.www.subjects.ui.view.IView;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.MyUtils;
import com.wktx.www.subjects.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的简历 --- 个人标签编辑
 */
public class EditTagsActivity extends ABaseActivity<IView,EditTagsPresenter> implements IView<String> {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.et_tag)
    EditText etTag;
    @BindView(R.id.bt_save)
    Button btSave;
    @BindView(R.id.bt_delete)
    Button btDelete;

    private ResumeInfoData resumeInfoData;//简历信息
    private ArrayList<String> tags;//个人标签集合
    private int position;//标签位标

    @OnClick({R.id.tb_IvReturn,R.id.bt_save,R.id.bt_delete})
    public void MyOnclick(View view) {
        //将输入法隐藏
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tvTitle.getWindowToken(), 0);

        if (MyUtils.isFastClick()){
            return;
        }
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.bt_save://保存
                //判断输入框格式
                if (TextUtils.isEmpty(etTag.getText().toString().trim())) {
                    ToastUtil.myToast("请输入职位名称！");
                }else {
                    //将新增或者编辑后的个人标签，添加到个人标签列表里
                    //如果是新增，直接添加
                    if (position<0){
                        tags.add(etTag.getText().toString().trim());
                    }else {//如果是编辑，编辑后的数据替换原来的数据
                        tags.set(position,etTag.getText().toString().trim());
                    }
                    btSave.setEnabled(false);
                    getPresenter().changeTags(resumeInfoData.getId(),tags);
                }
                break;
            case R.id.bt_delete://删除
                btDelete.setEnabled(false);
                tags.remove(position);
                getPresenter().changeTags(resumeInfoData.getId(),tags);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tags);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_resume_tags);
        initData();
    }

    @Override
    protected EditTagsPresenter createPresenter() {
        return new EditTagsPresenter();
    }

    /**
     * 接收 MyResumeActivity 传递过来的简历信息,个人标签位标
     */
    private void initData() {
        position = getIntent().getIntExtra(ConstantUtil.KEY_POSITION,0);
        resumeInfoData = (ResumeInfoData) getIntent().getSerializableExtra(ConstantUtil.KEY_DATA);
        tags = resumeInfoData.getTags();
        if (position<0){//添加个人标签
            btSave.setText("添加");
            btDelete.setVisibility(View.GONE);
        }else {//编辑个人标签
            etTag.setText(tags.get(position));
            btSave.setText("修改");
            btDelete.setVisibility(View.VISIBLE);
        }
    }

    /**
     * IView
     * @return
     */
    @Override
    public void onRequestSuccess(String tData) {
        btSave.setEnabled(true);
        btDelete.setEnabled(true);
        ToastUtil.myToast(tData);
        //成功-关闭界面
        finish();
    }
    @Override
    public void onRequestFailure(String result) {
        btSave.setEnabled(true);
        btDelete.setEnabled(true);
        ToastUtil.myToast(result);
        //失败-工作经历列表恢复原来数据
        tags=resumeInfoData.getTags();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancleMyToast();
    }
}
