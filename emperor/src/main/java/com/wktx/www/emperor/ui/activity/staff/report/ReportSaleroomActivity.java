package com.wktx.www.emperor.ui.activity.staff.report;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;
import com.wktx.www.emperor.apiresult.staff.report.ReportSaleroomInfoData;
import com.wktx.www.emperor.basemvp.ABaseActivity;
import com.wktx.www.emperor.presenter.staff.report.ReportSaleroomPresenter;
import com.wktx.www.emperor.ui.activity.ImageActivity;
import com.wktx.www.emperor.ui.adapter.DropDownListAdapter;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.GlideUtil;
import com.wktx.www.emperor.utils.MyUtils;
import com.wktx.www.emperor.ui.view.IView;
import com.wktx.www.emperor.utils.ToastUtil;
import com.wktx.www.emperor.widget.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 管理我的员工---工作报告---销售额
 */
public class ReportSaleroomActivity extends ABaseActivity<IView,ReportSaleroomPresenter> implements IView<ReportSaleroomInfoData> {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;

    private String hireId;//雇佣id
    private String monthId="0";//月份Id

    private DropDownListAdapter monthListAdapter;//条件筛选适配器
    private String[] tabTexts = {"月份选择"};
    private List<ReportSaleroomInfoData.ListBean> monthListBeans = new ArrayList<>();//月份集合

    private List<String> monthListStrs = new ArrayList<>();//月份名称
    private List<View> popupViews = new ArrayList<>();//多条件筛选的弹窗集合
    private ListView monthListView;
    private TextView tvTotal;
    private TextView tvLastmonth;
    private ImageView ivImage;

    private boolean isFirst=true;//是否第一次请求
    private List<String> imageUrlList = new ArrayList<>();//图片url集合

    @OnClick({R.id.tb_IvReturn})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn://返回
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_saleroom);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        tvTitle.setText(R.string.title_report_saleroom);
        initData();
        initView();
    }

    @Override
    protected ReportSaleroomPresenter createPresenter() {
        return new ReportSaleroomPresenter();
    }

    /**
     * 接收 StaffWorkListActivity 传递过来的雇佣id
     */
    private void initData() {
        hireId = getIntent().getStringExtra(ConstantUtil.KEY_POSITION);
        getPresenter().onGetSaleroomInfo(hireId,monthId);
    }

    private void initView() {
        //月份列表
        monthListView = new ListView(ReportSaleroomActivity.this);
        monthListView.setDividerHeight(0);
        monthListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (MyUtils.isFastClick1()){
                    return;
                }
                monthId = monthListBeans.get(position).getId();
                monthListAdapter.setCheckItem(position);
                dropDownMenu.setTabText(monthListStrs.get(position));
                dropDownMenu.closeMenu();
                getPresenter().onGetSaleroomInfo(hireId,monthId);
                isFirst=false;
            }
        });
        //添加
        popupViews.add(monthListView);
        //展示条件筛选结果的内容控件
        View contentView =  getLayoutInflater().inflate(R.layout.layout_report_saleroom, null);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        tvTotal = (TextView) contentView.findViewById(R.id.tv_saleroomTotal);
        tvLastmonth = (TextView) contentView.findViewById(R.id.tv_saleroomLastmonth);
        ivImage = (ImageView) contentView.findViewById(R.id.iv_img);
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyUtils.isFastClick1()){
                    return;
                }
                //预览图片
                String[] imageUrls = imageUrlList.toArray(new String[imageUrlList.size()]);
                Intent intent = new Intent(ReportSaleroomActivity.this, ImageActivity.class);
                intent.putExtra(ConstantUtil.KEY_DATA, imageUrls);
                intent.putExtra(ConstantUtil.KEY_POSITION, 0);
                startActivity(intent);
            }
        });
        dropDownMenu.setDropDownMenu(Arrays.asList(tabTexts), popupViews, contentView);
    }

    /**
     * IView
     */
    @Override
    public void onRequestSuccess(ReportSaleroomInfoData tData) {
        //字符串数据都为空，说明上月没数据，即该员工是当月雇佣的
        if (TextUtils.isEmpty(tData.getTotal_sales())){
            tvTotal.setText("0");
        }else {
            tvTotal.setText(tData.getTotal_sales());
        }
        if (TextUtils.isEmpty(tData.getLast_month_sales())){
            tvLastmonth.setText("0");
        }else {
            tvLastmonth.setText(tData.getLast_month_sales());
        }
        //数据图片
        if (TextUtils.isEmpty(tData.getLast_sales_data())){
            ivImage.setImageResource(R.drawable.img_nothing);
        }else {
            imageUrlList.clear();
            imageUrlList.add(tData.getLast_sales_data());
            GlideUtil.loadImage(tData.getLast_sales_data(),R.drawable.img_loading,ivImage);
        }
        //第一次请求时才更新月份列表
        if (isFirst){
            monthListBeans = tData.getList();
            for (int i = 0; i < monthListBeans.size() ; i++) {
                monthListStrs.add(monthListBeans.get(i).getName());
            }
            monthListAdapter = new DropDownListAdapter(ReportSaleroomActivity.this, monthListStrs);
            monthListView.setAdapter(monthListAdapter);
        }
    }
    @Override
    public void onRequestFailure(String result) {
        ToastUtil.myToast(result);
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancleMyToast();
    }
}
