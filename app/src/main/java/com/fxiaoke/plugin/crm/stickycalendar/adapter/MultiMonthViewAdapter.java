/*
 * Copyright (C) 2016 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.fxiaoke.plugin.crm.stickycalendar.adapter;

import java.util.Calendar;

import com.fxiaoke.plugin.crm.R;
import com.fxiaoke.plugin.crm.stickycalendar.utils.DateBean;
import com.fxiaoke.plugin.crm.stickycalendar.utils.SpecialCalendar;

import android.content.Context;
import android.text.TextUtils;

/**
 * 类名 : MonthViewAdapter
 * 作者 : wangying
 * 实现的主要功能 :
 * 创建日期 : 2016年06月21日 11:41
 */
public class MultiMonthViewAdapter extends CalendarAdapter {

    private DateBean firstDateBean;
    private int firstDatePosition;

    public MultiMonthViewAdapter(Context context, int jumpMonth, int year_c, int month_c) {
        super(context);
        dateBeans = SpecialCalendar.getMonthDate(jumpMonth, year_c, month_c);
        for(int i=0;i<7;++i){
            if (DateBean.CURRENT_MONTH == dateBeans[i].getMonthType()) {
                //如果是当前日期
                if (firstDateBean == null) {
                    firstDateBean = dateBeans[i];
                    firstDatePosition = i;
                }
            }
        }
    }

    public DateBean getFirstDateBean() {
        return firstDateBean;
    }

    @Override
    public void updateItemView(ViewHolder viewHolder, int position) {
        DateBean dateBean = dateBeans[position];
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateBean.getDate());
        String tag = dateBean.getTag();
        viewHolder.txDate.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));

        if (dateBean.isHasEvent()) {
            //可以根据不同的标签，显示不同颜色
            if(TextUtils.isEmpty(dateBean.getTag())){
                viewHolder.dot.setBackgroundColor(res.getColor(R.color.calendar_date_bg));
            }else {
                //可以根据tag拓展
            }
        }else {
            viewHolder.dot.setBackgroundColor(res.getColor(R.color.transparent));
        }

        if (dateBean.isIsChoosed()) {
            //选中状态
            if (DateBean.CURRENT_MONTH == dateBean.getMonthType()) {
                viewHolder.txDate.setTextColor(res.getColor(android.R.color.white));
                viewHolder.txDate.setBackgroundResource(R.drawable.orange_circle_point2);
            } else {
                viewHolder.txDate.setTextColor(res.getColor(R.color.calendar_text_color_disable));
                viewHolder.txDate.setBackgroundColor(res.getColor(R.color.transparent));
            }
        }else {
            if (DateBean.CURRENT_MONTH == dateBean.getMonthType()) {
                viewHolder.txDate.setTextColor(res.getColor(R.color.calendar_text_color));
            } else {
                viewHolder.txDate.setTextColor(res.getColor(R.color.calendar_text_color_disable));
            }
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
