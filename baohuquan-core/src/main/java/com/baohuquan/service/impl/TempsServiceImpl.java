package com.baohuquan.service.impl;

import com.alibaba.fastjson.JSON;
import com.baohuquan.cache.XMemcached;
import com.baohuquan.constant.Constants;
import com.baohuquan.dao.temps.TempsDao;
import com.baohuquan.model.Temps;
import com.baohuquan.service.TempsServiceIF;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;

/**
 * Created by wangdi5 on 2016/3/21.
 */
@Service
public class TempsServiceImpl implements TempsServiceIF {


    private static final Logger LOGGER = LoggerFactory.getLogger(TempsServiceImpl.class);


    @Resource
    TempsDao tempsDao;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private static final String HIGHTEMPKEY = "baohuquan_hightemp_%s_%s";

    /**
     * 按天获得
     *
     * @param babyId
     * @param date
     * @return
     */
    @Override
    public Temps getByDay(int babyId, Date date) {
        String temp_date = sdf.format(date);
        Temps temps = tempsDao.getTemp(babyId, temp_date);
        return temps;
    }

    @Override
    public List<Temps> getByMonth(int babyId, int month, int year) {

        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1, 0, 0, 0);
        Date d1 = cal.getTime();
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DATE, 0);
        Date d2 = cal.getTime();
        LOGGER.info("[获得测量体温]|month=" + month + "|year=" + year + "|babyId=" + babyId + "|from" + d1 + "|to=" + d2);
        return tempsDao.getTempByTime(babyId,d1,d2);
    }

    /**
     * 温度
     *
     * @param babyId
     * @param day
     * @param temp
     * @return
     */
    @Override
    public Temps saveTemps(int babyId, Date day, int temp) {
        String strDay = sdf.format(day);
        String key = String.format(HIGHTEMPKEY, babyId, strDay);
        //之前的值
        Temps preHighTemp = XMemcached.get(key);
        SortedMap map = Maps.newTreeMap();
        //今天第一次上传是
        if (preHighTemp == null) {
            preHighTemp = tempsDao.getTemp(babyId, strDay);
            if (preHighTemp == null) {
                Temps temps = new Temps();
                temps.setCreateTime(new Date());
                temps.setBabyId(babyId);
                temps.setHighTemp(temp);
                map.put(day.getTime(), temp);
                String o = JSON.toJSONString(map);
                temps.setTemps(o);
                temps.setTempsDate(sdf.format(day));
                int i = tempsDao.saveTemp(temps);
                temps.setId(i);
                XMemcached.set(key, Constants.EXPIRE_DAY, temps);
                return temps;
            }

        }
        map = JSON.parseObject(preHighTemp.getTemps(), SortedMap.class);
        map.put(day.getTime(), temp);
        String o = JSON.toJSONString(map);
        int highTemp = preHighTemp.getHighTemp() >= temp ? preHighTemp.getHighTemp() : temp;
        int i = tempsDao.updateTemps(preHighTemp.getId(), JSON.toJSONString(map), highTemp);
        XMemcached.set(key, Constants.EXPIRE_DAY, preHighTemp);
        return preHighTemp;

    }

    /**
     * 获取所有的
     *
     * @param babyid
     * @return
     */
    @Override
    public List<Temps> get(int babyid) {
        List<Temps> temps = tempsDao.getTempByBaby(babyid);
        return temps;
    }


}
