package com.wktx.www.emperor.ui.activity.staff;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.mine.store.StoreListInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.staff.StaffArrangeWorkPresenter;
import com.wktx.www.emperor.ui.activity.mine.store.StoreInfoEditActivity;
import com.wktx.www.emperor.ui.activity.recruit.demand.DemandReleaseActivity;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.DateUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.staff.IStaffArrangeWorkView;
import com.wktx.www.emperor.utils.ToastUtil;
import com.wktx.www.emperor.widget.CustomDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 管理我的员工---安排工作
 */
public class StaffArrangeWorkActivity extends ABaseActivity<IStaffArrangeWorkView,StaffArrangeWorkPresenter> implements IStaffArrangeWorkView {

    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.et_demandTitle)
    EditText etTitle;
    @BindView(R.id.et_demandContent)
    EditText etContent;
    @BindView(R.id.tv_store)
    TextView tvStore;
    @BindView(R.id.tv_endTime)
    TextView tvEndTime;
    @BindView(R.id.bt_sureArrange)
    Button btArrange;

    private String hireId;//雇佣id
    private String storeId;//店铺Id

    private OptionsPickerView pvCustomOptions;//自义定选择器
    private List<StoreListInfoData> storeBeans = new ArrayList<>();//店铺名称
    private ArrayList<String> optionsItemStrs = new ArrayList<>();//选择器字符串集合

    //系统日历控件
    private Calendar curCalendar = Calendar.getInstance();
    private String endDateStr;//工作截止日期"yyyy-MM-dd HH:mm"格式
    private long curDateLong;//当前时间的时间戳

    private CustomDialog customDialog;

    @OnClick({R.id.tb_IvReturn,R.id.linear_store,R.id.linear_endTime,R.id.bt_sureArrange})
    public void MyOnclick(View view) {
        //将输入法隐藏
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tvTitle.getWindowToken(), 0);
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            case R.id.linear_store://选择店铺
                if (storeBeans.size()!=0){
                    initCustomOptionPicker(R.id.tv_store,storeBeans);
                }
                break;
            case R.id.linear_endTime://截止日期
                pickDate(tvEndTime,"选择工作截止时间");
                break;
            case R.id.bt_sureArrange://安排工作
                if (MyUtils.isFastClick()){
                    return;
                }
                //判断输入框格式
                if (TextUtils.isEmpty(getDemandTitle())){
                    ToastUtil.myToast("请输入需求标题！");
                    etTitle.requestFocus();
                }else if (TextUtils.isEmpty(getDemandContent())){
                    ToastUtil.myToast("请输入需求详情！");
                    etContent.requestFocus();
                }else if (TextUtils.isEmpty(storeId)){
                    ToastUtil.myToast("请选择店铺！");
                }else {//工作安排
                    btArrange.setEnabled(false);
                    getPresenter().onArrangeWork(hireId);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().onGetStoreCondition();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_arrangework);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.title_staff_arrangework);
        initData();
        initUI();
    }

    @Override
    protected StaffArrangeWorkPresenter createPresenter() {
        return new StaffArrangeWorkPresenter();
    }

    /**
     * 接收 StaffManageActivity 传递过来的雇佣id
     */
    private void initData() {
        hireId = getIntent().getStringExtra(ConstantUtil.KEY_POSITION);
    }

    /**
     * 初始化控件
     */
    private void initUI() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //截止日期
        Date endDate = curCalendar.getTime();
        endDateStr = sdf.format(endDate);
        //将截止日期转为时间戳(只转日期不转时间，方便pickDate方法做判断)
        curDateLong = Long.parseLong(DateUtil.getCustomType2Timestamp(endDateStr,"yyyy-MM-dd HH:mm"))* 1000L;
        tvEndTime.setText(endDateStr);
    }


    /**
     * IStaffArrangeWorkView
     */
    @Override
    public String getDemandTitle() {
        return etTitle.getText().toString().trim();
    }
    @Override
    public String getDemandContent() {
        return etContent.getText().toString().trim();
    }
    @Override
    public String getStoreId() {
        return storeId;
    }
    @Override
    public String getEndTime() {
        return tvEndTime.getText().toString().trim();
    }
    @Override//工作安排发布是否成功的回调
    public void onArrangeWorkResult(boolean isSuccess, String msg) {
        ToastUtil.myToast(msg);
        btArrange.setEnabled(true);
        if (isSuccess){
            finish();
        }
    }
    @Override
    public void onRequestSuccess(List<StoreListInfoData> tData) {
        storeBeans=tData;
    }
    @Override
    public void onRequestFailure(String result) {
        if (result.equals("")){
            showAddStoreDialog();
        }else {
            ToastUtil.myToast(result);
            finish();
        }
    }

    /**
     * 添加店铺对话框
     */
    private void showAddStoreDialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setTitle("系统提示");
        builder.setMessage("暂无任何店铺，是否添加？");
        builder.setPositiveButton("添加店铺", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //打开店铺详情界面
                Intent intent = new Intent(StaffArrangeWorkActivity.this, StoreInfoEditActivity.class);
                intent.putExtra(ConstantUtil.KEY_POSITION,"0");
                startActivity(intent);
            }
        });

        builder.setNegativeButton("取消安排",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });

        customDialog = builder.create();
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.show();
    }

    /**
     * 自定义选择器
     */
    private void initCustomOptionPicker(final int id, final List<StoreListInfoData> list) {//条件选择器初始化，自定义布局
        /**
         * 注意事项：
         * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
         * 具体可参考demo 里面的两个自定义layout布局。
         */
        pvCustomOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String name = list.get(options1).getShop_name();
                if (id == R.id.tv_store) {
                    tvStore.setText(name);
                    storeId = list.get(options1).getId();
                }

            }
        })
                .setLayoutRes(R.layout.widget_custom_pickerview, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        TextView tvCancel = (TextView) v.findViewById(R.id.tv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.returnData();
                                pvCustomOptions.dismiss();
                            }
                        });
                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.dismiss();
                            }
                        });
                    }
                })
                .isDialog(false)
                .setOutSideCancelable(false)
                .build();
        optionsItemStrs.clear();
        for (int i = 0; i <list.size() ; i++) {
            optionsItemStrs.add(list.get(i).getShop_name());
        }
        pvCustomOptions.setPicker(optionsItemStrs);//添加数据
        pvCustomOptions.show();
    }

    //选择工作截止日期
    private void  pickDate(final TextView tvDate,String titleStr) {
        TimePickerView pvTime = new TimePickerView.Builder(StaffArrangeWorkActivity.this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String selectDateStr = sdf.format(date);
                long selectDateLong = date.getTime();
                if(selectDateLong >= curDateLong) {
                    tvDate.setText(selectDateStr);
                }else {
                    ToastUtil.myToast("截止日期需在当前时间之后!");
                }
            }
        })
                .setType(new boolean[]{true, true, true, true, true, false})//默认全部显示,年月日时分秒
                .setTitleText(titleStr)//标题文字
                .setTitleSize(16)//标题文字大小
                .setContentSize(14)//滚轮文字大小
                .setSubmitColor(StaffArrangeWorkActivity.this.getResources().getColor(R.color.color_ffb321))
                .setCancelColor(Color.GRAY)
                .isCenterLabel(false)
                .setOutSideCancelable(false)
                .build();

        pvTime.setDate(DateUtil.getCustomType2Calendar(tvDate.getText().toString(),"yyyy-MM-dd HH:mm"));
        pvTime.show();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        if (customDialog!=null){
            customDialog.dismiss();
            customDialog=null;
        }
        ToastUtil.cancleMyToast();
    }

}
