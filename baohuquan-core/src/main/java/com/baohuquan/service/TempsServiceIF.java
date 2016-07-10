package com.baohuquan.service;

import com.alibaba.fastjson.JSONObject;
import com.baohuquan.model.Temps;

import java.util.Date;
import java.util.List;

/**
 * Created by wangdi5 on 2016/3/21.
 */
public interface TempsServiceIF {


    /**
     * 按天获得
     * @param babyId
     * @param date
     * @return
     */
    public Temps getByDay(int babyId,Date date);


    public List<Temps> getByMonth(int babyId,int month,int year);

    /**
     * 温度
     * @param babyId
     * @param day
     * @param temp
     * @return
     */
    public Temps saveTemps(int babyId, Date day, int temp,int uid);


    /**
     * 获取所有的
     * @param babyid
     * @return
     */
    List<Temps> get(int babyid);

    /**
     * 获得最后一个
     * @param babyid
     * @return
     */
    JSONObject getLastTemps(int babyid);
}
