package com.wktx.www.emperor.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.emperor.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateStoreActivity extends AppCompatActivity {
    @BindView(R.id.tb_TvBarTitle)
    TextView mTvTitle;
    @BindView(R.id.upstoreTvPlatfrom)
    TextView tvPlatFrom;
    @BindView(R.id.upstoreTvSort)
    TextView tvSort;
    private OptionsPickerView pvCustomOptions;
    private String tx;

    @OnClick({R.id.tb_IvReturn, R.id.upStoreLlPlatform,R.id.upStoreLlSort})
    public void MyOnclick(View view) {
        switch (view.getId()) {
            case R.id.tb_IvReturn:
                finish();
                break;
            case R.id.upStoreLlSort:
                initCustomOptionPicker(R.id.upstoreTvSort,sortList);
                pvCustomOptions.show();
                break;
            case R.id.upStoreLlPlatform:
                initCustomOptionPicker(R.id.upstoreTvPlatfrom, platfromList);
                pvCustomOptions.show();
                break;
            default:
                break;
        }
    }

    private ArrayList<String> platfromList = new ArrayList<>();
    private ArrayList<String> sortList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_store);
        ButterKnife.bind(this);
        // 设置右滑动返回
        Slidr.attach(this);
        mTvTitle.setText(R.string.title_updatestore);
        initPpData();
    }

    private void initPpData() {
        platfromList.add("淘宝");
        platfromList.add("天猫");
        platfromList.add("阿里巴巴");
        platfromList.add("京东");
        platfromList.add("苏宁");
        sortList.add("鞋服");
        sortList.add("生活电器");
        sortList.add("3c数码");
        sortList.add("母婴");
        sortList.add("食品");
    }

    private void initCustomOptionPicker(final int id, final ArrayList<String> list) {//条件选择器初始化，自定义布局
        /**
         * @description
         *
         * 注意事项：
         * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
         * 具体可参考demo 里面的两个自定义layout布局。
         */
        pvCustomOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                //返回的分别是三个级别的选中位置
                tx = list.get(options1);
                if (id == R.id.upstoreTvPlatfrom) {
                    tvPlatFrom.setText(tx);
                } else {
                    tvSort.setText(tx);
                }
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        TextView ivCancel = (TextView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.returnData();
                                pvCustomOptions.dismiss();
                            }
                        });

                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.dismiss();
                            }
                        });
                    }
                })
                .isDialog(false)
                .build();
        pvCustomOptions.setPicker(list);//添加数据

    }
}
