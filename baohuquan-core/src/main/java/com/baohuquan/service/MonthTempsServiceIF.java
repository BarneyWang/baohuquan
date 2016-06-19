package com.baohuquan.service;

import com.baohuquan.model.MonthTemps;

/**
 * Created by wangdi5 on 2016/6/19.
 */
public interface MonthTempsServiceIF {



    MonthTemps getMonthTemps(int year,int month,int baby);

    int saveMonthTemps(MonthTemps monthTemps);

    int updateMonthTemps(int id,String monthTemps);
}
