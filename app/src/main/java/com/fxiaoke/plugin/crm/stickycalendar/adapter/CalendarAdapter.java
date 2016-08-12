/*
 * Copyright (C) 2016 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.fxiaoke.plugin.crm.stickycalendar.adapter;

import java.util.List;

import com.fxiaoke.plugin.crm.R;
import com.fxiaoke.plugin.crm.stickycalendar.utils.DateBean;
import com.fxiaoke.plugin.crm.stickycalendar.utils.OtherUtils;
import com.fxiaoke.plugin.crm.stickycalendar.utils.SpecialCalendar;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 类名 : CalendarAdapter
 * 作者 : wangying
 * 实现的主要功能 : 日历gridview中的每一个item显示的textview
 * 创建日期 : 2016年06月21日 11:41
 */
public abstract class CalendarAdapter extends BaseAdapter {
    protected Context context;

    public DateBean[] getDateBeans() {
        return dateBeans;
    }

    protected DateBean[] dateBeans;
    protected Resources res = null;

    public CalendarAdapter(Context context) {
        this.context = context;
        this.res = context.getResources();
    }

    @Override
    public int getCount() {
        return dateBeans.length;
    }

    @Override
    public Object getItem(int position) {
        return dateBeans[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public abstract void updateItemView(ViewHolder viewHolder, int position);

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_calendar, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        updateItemView(viewHolder, position);
        return convertView;
    }

    public void setEventDateList(List<Pair<String, String>> dateList) {
        if (dateList != null && dateList.size() > 0) {
            for (DateBean dateBean : dateBeans) {
                String formatDate = OtherUtils.formatDate(dateBean.getDate(), OtherUtils.DATE_PATTERN_2);
                for (Pair<String, String> pair : dateList) {
                    if (TextUtils.equals(pair.first, formatDate)) {
                        dateBean.setTag(pair.second);
                        break;
                    }
                }
            }
            notifyDataSetChanged();
        }
    }

    class ViewHolder {
        TextView txDate;
        View dot;
        public ViewHolder(View convertView) {
            txDate = (TextView) convertView.findViewById(R.id.tx_date);
            dot = convertView.findViewById(R.id.v_dot);
        }
    }

    public void updateEvents(List<Long> eventDates){
        SpecialCalendar.updateEvents(eventDates,dateBeans);
        notifyDataSetChanged();
    }
}
