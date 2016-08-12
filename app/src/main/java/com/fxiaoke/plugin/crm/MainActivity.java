package com.fxiaoke.plugin.crm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fxiaoke.plugin.crm.stickycalendar.StickyCalendar;
import com.fxiaoke.plugin.crm.stickycalendar.utils.DateBean;
import com.fxiaoke.plugin.crm.stickycalendar.utils.SpecialCalendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements StickyCalendar.IStickyCalendarListener {

    private StickyCalendar mCalendar;
    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    static List list = new ArrayList();
    static {
        try {
            list.add(format.parse("2016-8-11").getTime());
            list.add(format.parse("2016-8-12").getTime());
            list.add(format.parse("2016-8-14").getTime());
            list.add(format.parse("2016-8-16").getTime());
            list.add(format.parse("2016-8-19").getTime());
            list.add(format.parse("2016-8-20").getTime());
            list.add(format.parse("2016-8-21").getTime());
            list.add(format.parse("2016-8-25").getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCalendar = (StickyCalendar) findViewById(R.id.calendar);
        addExtraViews();
        mCalendar.setCalendarListener(this);
        upodateEventDates();
    }

    private void addExtraViews(){
        TextView tv = new TextView(this);
        tv.setTextSize(32);
        tv.setGravity(Gravity.CENTER);
        tv.setText("这里可以加入自己的Fragment或者其它的View");
        LinearLayout ll = (LinearLayout) findViewById(mCalendar.getFragId());
        ll.addView(tv,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }


    @Override
    public void onChooseDate(Date date) {
        System.out.println("日期:"+new SimpleDateFormat("yyyy-MM-dd").format(date)+" 被点击");
    }

    @Override
    public void onPageSelected(SpecialCalendar.CalendarType type, int position, Date date) {
        System.out.println(type.name()+"选择:" + position + " "+new SimpleDateFormat("yyyy-MM-dd").format(date));
        upodateEventDates();
    }

    private void upodateEventDates(){
        mCalendar.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCalendar.updateEventDates(list);
            }
        },1000);
    }
}
