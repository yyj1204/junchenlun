package com.wktx.www.subjects.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.apiresult.manage.attendance.AttendanceRecordBean;
import com.zhy.autolayout.AutoLinearLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by yyj on 2017/12/13.
 * 自定义日历控件
 */

public class CustomCalendar extends AutoLinearLayout {
    private RelativeLayout rela_left;
    private RelativeLayout rela_right;
    private TextView tv_date;
    private RecyclerView rv_week;

//    private List<AttendanceRecordBean> attendanceList = new ArrayList<>();//出勤集合

    //系统日历控件
    private Calendar curDate = Calendar.getInstance();
    private String displayDateFormat;
    public CustomCalendarListener listener;
    private CalenderAdapter calenderAdapter;
    private ArrayList<Date> dayList;

    public CustomCalendar(Context context) {
        super(context);
    }

    public CustomCalendar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initControl(context,attrs);
    }

    public CustomCalendar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context,attrs);
    }

    /**
     * 初始化
     * @param context
     */
    private void initControl(Context context,AttributeSet attrs) {
        bindControl(context);
        bindControlEvent();

        //先初始化属性，再渲染控件
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CustomCalendar);
        try {
            //读取dateFormat字段
            String format = ta.getString(R.styleable.CustomCalendar_dateFormat);
            //赋值：默认值
            displayDateFormat=format;
            if (displayDateFormat==null){
                displayDateFormat="yyyy年MM月";
            }
        }finally {
            ta.recycle();
        }

        renderCalendar();
    }

    /**
     * 绑定控件
     * @param context
     */
    private void bindControl(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.widget_custom_calendar,this);

        rela_left=(RelativeLayout)findViewById(R.id.rela_calendar_left);
        rela_right=(RelativeLayout)findViewById(R.id.rela_calendar_right);
        tv_date=(TextView) findViewById(R.id.tv_calendar_date);
        rv_week=(RecyclerView)findViewById(R.id.rv_calendar_day);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 7);
        rv_week.setLayoutManager(gridLayoutManager);
    }

    /**
     * 绑定控件事件
     */
    private void bindControlEvent() {
        //向前翻一个月
        rela_left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                curDate.add(Calendar.MONTH,-1);
                renderCalendar();
            }
        });

        //向后翻一个月
        rela_right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                curDate.add(Calendar.MONTH,1);
                renderCalendar();
            }
        });
    }

    /**
     * 渲染控件
     */
    private void renderCalendar() {
        //头部年月
        SimpleDateFormat sdf = new SimpleDateFormat(displayDateFormat);
        tv_date.setText(sdf.format(curDate.getTime()));

        //日历当前月份的日期集合
        dayList = new ArrayList<>();
        //克隆当前系统日历控件：不干扰之前的系统日历控件
        Calendar calendar = (Calendar)curDate.clone();

        //将月份设置为当月的第一天
        calendar.set(Calendar.DAY_OF_MONTH,1);
        //当月之前有多少天
        //比如当月的1号是星期一，则calendar.get(Calendar.DAY_OF_WEEK)=1
        //则当月之前有1-1=0天
        int prevDays = calendar.get(Calendar.DAY_OF_WEEK)-1;
        //将日期向前移
        calendar.add(Calendar.DAY_OF_MONTH,-prevDays);

        //当月日历控件最多绘制42天
        int maxDayCount=6*7;
        //循环添加日期集合
        while (dayList.size()<maxDayCount){
            dayList.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH,1);
        }

        if (listener!=null){
            listener.onClick(curDate.getTime().getTime()/1000L);
        }
    }

    /**
     * 日历适配器
     */
    private class CalenderAdapter extends BaseQuickAdapter<Date,BaseViewHolder>{
        private List<AttendanceRecordBean> attendanceList;

        public CalenderAdapter(ArrayList<Date> dayList,List<AttendanceRecordBean> list) {
            super(R.layout.item_gv_calendar_day, dayList);
            this.attendanceList=list;
        }

        @Override
        protected void convert(BaseViewHolder helper, Date item) {
            int day = item.getDate();
            String dayStr="";
            if (day<10){
                dayStr="0"+String.valueOf(day);
            }else {
                dayStr=String.valueOf(day);
            }
            TextView tv_day = helper.getView(R.id.tv_calendar_day);
            tv_day.setText(dayStr);
            /**
             * 判断是不是系统日历控件当前月份日期：当月天数之外就是上个月的天数或者下个月的天数（区分显示）
             */
            if (item.getMonth()==curDate.getTime().getMonth()){//是当月的日期
                tv_day.setVisibility(VISIBLE);
                //判断出勤
                if (attendanceList!=null){
                    if (attendanceList.size()!=0){
                        for (int i = 0; i <attendanceList.size() ; i++) {
                            AttendanceRecordBean recordBean = attendanceList.get(i);
                            if (recordBean.getDay().equals(day+"")){
                                if (recordBean.getType().equals("1")){//出勤
                                    tv_day.setBackgroundResource(R.drawable.ic_calendar_date_bg);
                                }else {//TODO 其他类型背景待定（请假、暂停）
                                    tv_day.setBackground(null);
                                }
                                return;
                            }else {
                                tv_day.setBackground(null);
                            }
                        }
                    }
                }
            }else {//不是当月的日期
                tv_day.setVisibility(GONE);
            }
        }
    }


    //自定义长按监听事件
    public interface CustomCalendarListener{
        void onClick(long titleTime);//左右按钮事件
    }

    public void setData(List<AttendanceRecordBean> attendanceList){
        calenderAdapter = new CalenderAdapter(dayList,attendanceList);
        rv_week.setAdapter(calenderAdapter);
    }
}
