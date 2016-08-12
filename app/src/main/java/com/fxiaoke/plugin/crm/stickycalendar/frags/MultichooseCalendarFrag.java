/*
 * Copyright (C) 2016 Facishare Technology Co., Ltd. All Rights Reserved.
 */

/**
 * 文件名 : MultichooseCalendarFrag.java
 * 包含类名列表 : MultichooseCalendarFrag
 * 版本信息 : Ver 1.0
 * 创建日期 : 2016年06月28日 13:29
 */
package com.fxiaoke.plugin.crm.stickycalendar.frags;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.fxiaoke.plugin.crm.R;
import com.fxiaoke.plugin.crm.stickycalendar.adapter.MultiMonthViewPagerAdapter;
import com.fxiaoke.plugin.crm.stickycalendar.utils.DateBean;
import com.fxiaoke.plugin.crm.stickycalendar.utils.MultiDataController;
import com.fxiaoke.plugin.crm.stickycalendar.utils.OtherUtils;
import com.fxiaoke.plugin.crm.stickycalendar.utils.SpecialCalendar;
import com.fxiaoke.plugin.crm.stickycalendar.view.CalendarView;
import com.fxiaoke.plugin.crm.stickycalendar.view.MultiMonthView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 类名 : MultichooseCalendarFrag
 * 作者 : wangying
 * 实现的主要功能 :
 * 创建日期 : 2016年06月28日 13:29
 */
public class MultichooseCalendarFrag extends Fragment {
    private int INIT_MONTH_PAGER_INDEX = 240; //月视图左右可滑动的页数
    private List<View> monthViews = new ArrayList<>();
    private ViewPager mMonthPager;
    private MultiMonthViewPagerAdapter mMonthViewPagerAdapter;
    private TextView txToday;
    private TextView btnToday;

    private OnMultiCalendarListener mMultiCalendarListener;

    public void setMultiCalendarListener(OnMultiCalendarListener listener) {
        this.mMultiCalendarListener = listener;
    }


    /**
     * 点击某个日期回调
     */
    CalendarView.OnCalendarClickListener mCalendarClickListener = new CalendarView
            .OnCalendarClickListener() {
        @Override
        public void onCalendarClick(SpecialCalendar.CalendarType type, int position, DateBean dateBean) {
            System.out.println(dateBean.toString());
            if(dateBean.isIsChoosed()){
                mMultiDataController.updateDateStatus(dateBean.getDate(),false);
            }else {
                mMultiDataController.updateDateStatus(dateBean.getDate(),true);
            }
            mMonthViewPagerAdapter.updateChoosedDate(dateBean);
            if (isCurrMonth(dateBean.getDate())) {
                btnToday.setVisibility(View.GONE);
            }
            txToday.setText(OtherUtils.formatDate(dateBean.getDate()));

        }
    };

    MultiDataController mMultiDataController = new MultiDataController();

    private TextView tvConfirm;
    private TextView tvReset;
    private ViewGroup mRootContainer;
    private ViewGroup mVG1, mVG2, mVG3; //用于吞点击事件

    public static boolean isCurrMonth(Date date) {
        Calendar cur = Calendar.getInstance();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (cur.get(Calendar.YEAR) == c.get(Calendar.YEAR) && cur.get(Calendar.MONTH) == c.get(Calendar.MONTH)) {
            return true;
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootContainer = (ViewGroup) inflater.inflate(R.layout.layout_calendar_multi_choose, null);
        txToday = (TextView) mRootContainer.findViewById(R.id.tx_today);
        btnToday = (TextView) mRootContainer.findViewById(R.id.btn_today);
        tvConfirm = (TextView) mRootContainer.findViewById(R.id.btn_confirm);
        tvReset = (TextView) mRootContainer.findViewById(R.id.btn_reset);
        mVG1 = (ViewGroup) mRootContainer.findViewById(R.id.calendar_container);
        mVG2 = (ViewGroup) mRootContainer.findViewById(R.id.rl_calendar_title_container);
        mVG3 = (ViewGroup) mRootContainer.findViewById(R.id.ll_week);
        mMonthPager = (ViewPager) mRootContainer.findViewById(R.id.month_pager);
        swallowClickEvent();

        mRootContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnToday.setVisibility(View.GONE);
        btnToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateBean dateBean = new DateBean();
                dateBean.setDate(Calendar.getInstance().getTime());
                dateBean.setMonthType(0);
                dateBean.setIsChoosed(false);
                //mMultiDataController.updateDateStatus(dateBean.getDate(),true);
                //mMonthViewPagerAdapter.updateChoosedDate(dateBean);
                mMonthViewPagerAdapter.go2Date(dateBean.getDate());
                txToday.setText(OtherUtils.formatDate(dateBean.getDate()));
            }
        });

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提取数据
                if(mMultiDataController != null){
                    if(mMultiCalendarListener != null){
                        mMultiCalendarListener.onConfirm(mMultiDataController.getChoosedDates());
                        dismiss();
                    }
                }

            }
        });

        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMultiCalendarListener != null){
                    mMultiCalendarListener.onReset();
                    dismiss();
                }
            }
        });

        initCalendar();
        return mRootContainer;
    }

    private void initCalendar() {
        Calendar calendar = Calendar.getInstance();
        txToday.setText(OtherUtils.formatDate(calendar.getTime()));
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        for (int i = 0; i < 3; i++) {
            MultiMonthView monthView = new MultiMonthView(getActivity(), i, year, month,mMultiDataController);
            monthView.setOnCalendarClickListener(mCalendarClickListener);
            monthViews.add(monthView);
        }
        mMonthViewPagerAdapter =
                new MultiMonthViewPagerAdapter(getActivity(), monthViews, INIT_MONTH_PAGER_INDEX, calendar,
                        mMonthPager,mMultiDataController);
        mMonthPager.setAdapter(mMonthViewPagerAdapter);
        mMonthPager.setCurrentItem(INIT_MONTH_PAGER_INDEX);
        mMonthPager.setOnPageChangeListener(new OnMonthPagerChangeListener());
    }

    private void swallowClickEvent() {
        mVG1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mVG2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mVG3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    class OnMonthPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            txToday.setText(OtherUtils.formatDate(mMonthViewPagerAdapter.getCurrMonth(position).getTime()));
            Calendar cur = mMonthViewPagerAdapter.getCurrMonth(position);
            if (isCurrMonth(cur.getTime())) {
                btnToday.setVisibility(View.GONE);
            } else {
                btnToday.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    public interface OnMultiCalendarListener{
        void onConfirm(List<Long> dates);
        void onReset();
    }

    private void dismiss(){
        getActivity().getSupportFragmentManager().beginTransaction().remove(MultichooseCalendarFrag.this)
                .commitAllowingStateLoss();
    }

    public void initChoosedDates(List<Long> dates){
        mMultiDataController.setDatas(dates);
    }
}