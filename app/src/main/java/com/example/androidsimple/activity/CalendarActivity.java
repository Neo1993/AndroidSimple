package com.example.androidsimple.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.androidsimple.R;
import com.example.androidsimple.base.BaseActivity;
import com.example.androidsimple.widget.CustomDayView;
import com.example.androidsimple.widget.RippleView;
import com.ldf.calendar.component.CalendarAttr;
import com.ldf.calendar.component.CalendarViewAdapter;
import com.ldf.calendar.interf.OnSelectDateListener;
import com.ldf.calendar.model.CalendarDate;
import com.ldf.calendar.view.MonthPager;

import java.util.HashMap;

public class CalendarActivity extends BaseActivity {
    private MonthPager calendarView;
    private CalendarViewAdapter calendarViewAdapter;
//    private OnSelectDateListener onSelectDateListener;
    private CustomDayView customDayView;
    private RippleView rippleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        initView();
        initCalendarView();
        initMarkData();
        rippleView.startAnimation((int)(3*1000));
    }

    private void initView(){
        rippleView = findViewById(R.id.rippleView);
        calendarView = findViewById(R.id.calendarView);
        calendarView.setScrollable(false);

        customDayView = new CustomDayView(this, R.layout.view_custom_day);

//        onSelectDateListener = new OnSelectDateListener() {
//            @Override
//            public void onSelectDate(CalendarDate date) {
//
//            }
//
//            @Override
//            public void onSelectOtherMonth(int offset) {
////                calendarView.selectOtherMonth(offset);
//            }
//        };

    }

    private void initCalendarView() {
        calendarViewAdapter = new CalendarViewAdapter(this, null, CalendarAttr.WeekArrayType.Monday, customDayView);
        calendarView.setAdapter(calendarViewAdapter);
        calendarView.setCurrentItem(MonthPager.CURRENT_DAY_INDEX);
    }

    /**
     * 初始化标记数据，HashMap的形式，可自定义
     * 如果存在异步的话，在使用setMarkData之后调用 calendarAdapter.notifyDataChanged();
     */
    private void initMarkData() {
//        HashMap<String, String> markData = new HashMap<>();
//        markData.put("2019-4-2", "0");
//        markData.put("2019-4-1", "0");
//        markData.put("2019-4-20", "0");
//        markData.put("2019-4-30", "0");
//        markData.put("2019-5-25", "0");
//        markData.put("2019-5-28", "0");
//        markData.put("2019-3-25", "0");
//        markData.put("2019-3-24", "0");
//        calendarViewAdapter.setMarkData(markData);
        calendarViewAdapter.notifyDataChanged();
        calendarViewAdapter.notifyDataChanged(new CalendarDate());
    }

}
