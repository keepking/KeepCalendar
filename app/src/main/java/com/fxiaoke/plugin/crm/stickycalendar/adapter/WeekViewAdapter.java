/*
 * Copyright (C) 2016 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.fxiaoke.plugin.crm.stickycalendar.adapter;

import java.util.Calendar;

import com.fxiaoke.plugin.crm.R;
import com.fxiaoke.plugin.crm.stickycalendar.StickyCalendar;
import com.fxiaoke.plugin.crm.stickycalendar.utils.DateBean;
import com.fxiaoke.plugin.crm.stickycalendar.utils.SpecialCalendar;

import android.content.Context;
import android.text.TextUtils;

/**
 * 类名 : WeekViewAdapter
 * 作者 : wangying
 * 实现的主要功能 :
 * 创建日期 : 2016年06月21日 11:41
 */
public class WeekViewAdapter extends CalendarAdapter {
    private DateBean firstDateBean;
    private int firstDatePosition;

    public WeekViewAdapter(Context context, int jumpWeek, int year_c, int month_c, int day_c) {
        super(context);
        dateBeans = SpecialCalendar.getWeekDate(jumpWeek, year_c, month_c, day_c);
        firstDateBean = dateBeans[0];
        firstDatePosition = 0;
    }

    public DateBean getFirstDateBean() {
        return firstDateBean;
    }

    public int getFirstDatePosition() {
        return firstDatePosition;
    }

    @Override
    public void updateItemView(ViewHolder viewHolder, int position) {
        DateBean dateBean = dateBeans[position];
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateBean.getDate());
        String tag = dateBean.getTag();
        viewHolder.txDate.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));

        if (!TextUtils.isEmpty(tag)) {
            //可以根据不同的标签，显示不同颜色
            viewHolder.txDate.setBackgroundResource(R.drawable.calendar_item_event_bg);
        } else {
            viewHolder.txDate.setBackgroundColor(res.getColor(android.R.color.transparent));
        }

        if (dateBean.isHasEvent()) {
            //可以根据不同的标签，显示不同颜色
            if(TextUtils.isEmpty(dateBean.getTag())){
                viewHolder.dot.setBackgroundResource(R.drawable.orange_circle_point2);
            }else {
                //可以根据tag拓展
            }
        }else {
            viewHolder.dot.setBackgroundColor(res.getColor(R.color.transparent));
        }

        if (dateBean.isIsChoosed()) {
            //选中状态
            viewHolder.txDate.setTextColor(res.getColor(android.R.color.white));
            viewHolder.txDate.setBackgroundResource(R.drawable.orange_circle_point2);
        }else {
            viewHolder.txDate.setTextColor(res.getColor(R.color.calendar_text_color));
            viewHolder.txDate.setBackgroundColor(res.getColor(R.color.transparent));
        }

        if (dateBean.isCurrentDay()) {
            viewHolder.txDate.setText("今");
            //今天
            if(dateBean.isIsChoosed()){
                viewHolder.txDate.setTextColor(res.getColor(android.R.color.white));
            }else {
                viewHolder.txDate.setTextColor(res.getColor(R.color.calendar_date_bg));
            }
        }
    }

}
