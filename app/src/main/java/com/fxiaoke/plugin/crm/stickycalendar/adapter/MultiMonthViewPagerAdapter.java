/*
 * Copyright (C) 2016 Facishare Technology Co., Ltd. All Rights Reserved.
 */

/**
 * 文件名 : MonthViewPagerAdapter.java
 * 包含类名列表 : MonthViewPagerAdapter
 * 版本信息 : Ver 1.0
 * 创建日期 : 2016年05月22日 19:38
 */
package com.fxiaoke.plugin.crm.stickycalendar.adapter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.fxiaoke.plugin.crm.stickycalendar.utils.DateBean;
import com.fxiaoke.plugin.crm.stickycalendar.utils.MultiDataController;
import com.fxiaoke.plugin.crm.stickycalendar.view.CalendarView;
import com.fxiaoke.plugin.crm.stickycalendar.view.MultiMonthView;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * 类名 : MonthViewPagerAdapter
 * 作者 : wangying
 * 实现的主要功能 :
 * 创建日期 : 2016年05月22日 19:38
 */
public class MultiMonthViewPagerAdapter extends CalendarViewPagerAdapter {

    private MultiDataController mMultiDataController;

    public MultiMonthViewPagerAdapter(Context context, List<View> lists, int INIT_PAGER_INDEX, Calendar calendar,
                                      ViewPager viewPager, MultiDataController dataController) {
        super(context, lists, INIT_PAGER_INDEX, calendar, viewPager);
        mMultiDataController = dataController;
    }

    @Override
    public void go2Date(Date date) {
        int targetPageIndex = getTargetPageIndex(date);
        mViewPager.setCurrentItem(targetPageIndex,false);
        chooseDate(date);
    }

    @Override
    public void go2DatePage(Date date) {
        int targetPageIndex = getTargetPageIndex(date);
        mViewPager.setCurrentItem(targetPageIndex,false);
    }

    public void updateChoosedDate(DateBean dateBean){
        if(dateBean.isIsChoosed()){
            dateBean.setIsChoosed(false);
        }else {
            dateBean.setIsChoosed(true);
        }
        go2Date(dateBean.getDate());
    }

    public void chooseDate(Date date){
        for(int i=0;i<3;++i){
            MultiMonthViewAdapter adapter = ((MultiMonthViewAdapter)((CalendarView)viewLists.get(i)).getAdapter());
            DateBean[] dateBeens = adapter.getDateBeans();
            for(DateBean dateBean : dateBeens){
                if(mMultiDataController.checkDateStatus(dateBean.getDate())){
                    dateBean.setIsChoosed(true);
                }else {
                    dateBean.setIsChoosed(false);
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void refreshView(View child, int jump) {
        MultiMonthView monthView = (MultiMonthView) child;
        monthView.refreshView(context, jump, year, month, day);
    }

    private int getTargetPageIndex(Date date) {

        Calendar next = Calendar.getInstance();
        next.setTime(date);
        int nextYear = next.get(Calendar.YEAR);
        int nextMonth = next.get(Calendar.MONTH);

        //需要判断index是否越界，一般不会
        int offsetPages = nextMonth - month + (nextYear - year) * 12;
        return initPageIndex + offsetPages;
    }

    public Calendar getCurrMonth(int position) {
        MultiMonthView monthView = (MultiMonthView) viewLists.get(position % count);
        MultiMonthViewAdapter adapter = (MultiMonthViewAdapter) monthView.getAdapter();
        Calendar c = Calendar.getInstance();
        if(adapter.getFirstDateBean() == null){
            return Calendar.getInstance();
        }
        c.setTime(adapter.getFirstDateBean().getDate());
        return c;
    }
}
