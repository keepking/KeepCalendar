/*
 * Copyright (C) 2016 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.fxiaoke.plugin.crm.stickycalendar.view;

import com.fxiaoke.plugin.crm.R;
import com.fxiaoke.plugin.crm.stickycalendar.adapter.MonthViewAdapter;
import com.fxiaoke.plugin.crm.stickycalendar.utils.DateBean;
import com.fxiaoke.plugin.crm.stickycalendar.utils.OtherUtils;
import com.fxiaoke.plugin.crm.stickycalendar.utils.SpecialCalendar;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;


/**
 * 类名 : MonthView
 * 作者 : wangying
 * 实现的主要功能 :
 * 创建日期 : 2016年05月22日 19:38
 */
public class MonthView extends CalendarView {

    private MonthViewAdapter mAdapter;
    private OnCalendarClickListener onCalendarClickListener;

    public MonthView(Context context, int jumpMonth, int year, int month) {
        super(context);
        mAdapter = new MonthViewAdapter(context, jumpMonth, year, month);
        initCalendarValues();
        setCalendarValues();

    }

    private void initCalendarValues() {
        setCacheColorHint(getResources().getColor(android.R.color.transparent));
        setHorizontalSpacing(0);
        setVerticalSpacing(0);
        setNumColumns(7);
        setStretchMode(STRETCH_COLUMN_WIDTH);
        setSelector(R.color.transparent);
    }

    private void setCalendarValues() {
        setAdapter(mAdapter);
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DateBean dateBean = (DateBean) mAdapter.getItem(position);
                if (onCalendarClickListener != null) {
                    onCalendarClickListener.onCalendarClick(SpecialCalendar.CalendarType.MONTH,position, dateBean);
                }
            }
        });
    }

    public void refreshView(Context context, int jump, int year, int month, int day) {
        mAdapter = new MonthViewAdapter(context, jump, year, month);
        setCalendarValues();
    }


    public String getCurrentDay() {
        DateBean firstDateBean = mAdapter.getFirstDateBean();
        if (firstDateBean != null) {
            return OtherUtils.formatMonth(firstDateBean.getDate());
        }
        return null;
    }


    public void setOnCalendarClickListener(OnCalendarClickListener onCalendarClickListener) {
        this.onCalendarClickListener = onCalendarClickListener;
    }

    @Override
    public void refreshSelf() {
        mAdapter.notifyDataSetChanged();
    }
}
