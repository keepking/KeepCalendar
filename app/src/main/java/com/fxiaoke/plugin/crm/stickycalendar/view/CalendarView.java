/*
 * Copyright (C) 2016 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.fxiaoke.plugin.crm.stickycalendar.view;

import com.fxiaoke.plugin.crm.stickycalendar.utils.DateBean;
import com.fxiaoke.plugin.crm.stickycalendar.utils.SpecialCalendar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;


/**
 * 类名 : CalendarView
 * 作者 : wangying
 * 实现的主要功能 :
 * 创建日期 : 2016年05月22日 19:38
 */
public class CalendarView extends GridView {
    public CalendarView(Context context) {
        super(context);
    }

    public interface OnCalendarClickListener {
        void onCalendarClick(SpecialCalendar.CalendarType type, int position, DateBean dateBean);
    }

    /**
     * 刷新UI
     */
    public void refreshSelf(){}
}
