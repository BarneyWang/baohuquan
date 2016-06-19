package com.baohuquan.service.impl;

import com.baohuquan.dao.temps.MonthTempsDao;
import com.baohuquan.model.MonthTemps;
import com.baohuquan.service.MonthTempsServiceIF;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wangdi5 on 2016/6/19.
 */
@Service
public class MonthTempsServiceImpl implements MonthTempsServiceIF {


    @Resource
    MonthTempsDao monthTempsDao;


    @Override
    public MonthTemps getMonthTemps(int year, int month, int baby) {
        return null;
    }

    @Override
    public int saveMonthTemps(MonthTemps monthTemps) {
        return 0;
    }

    @Override
    public int updateMonthTemps(int id, String monthTemps) {
        return 0;
    }
}
