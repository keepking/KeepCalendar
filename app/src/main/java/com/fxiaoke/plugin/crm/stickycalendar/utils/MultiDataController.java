/*
 * Copyright (C) 2016 Facishare Technology Co., Ltd. All Rights Reserved.
 */

/**
 * 文件名 : MultiDataController.java
 * 包含类名列表 : MultiDataController
 * 版本信息 : Ver 1.0
 * 创建日期 : 2016年06月30日 17:43
 */
package com.fxiaoke.plugin.crm.stickycalendar.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 类名 : MultiDataController
 * 作者 : wangying
 * 实现的主要功能 :
 * 创建日期 : 2016年06月30日 17:43
 */
public class MultiDataController {
    public HashMap<String,Boolean> datas = new HashMap<>();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d");
    public void updateDateStatus(Date date, boolean status){
        datas.put(format.format(date),status);
    }

    public boolean checkDateStatus(Date date){
        Boolean result =  datas.get(format.format(date));
        if(result != null){
            return result;
        }
        return false;
    }

    public List<Long> getChoosedDates(){
        List<Long> dates = new ArrayList<>();
        for(String str : datas.keySet()){
            if(datas.get(str)){
                try {
                    dates.add(format.parse(str).getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return dates;
    }

    public void setDatas(List<Long> dates){
        for(Long time : dates){
            updateDateStatus(new Date(time),true);
        }
    }
}