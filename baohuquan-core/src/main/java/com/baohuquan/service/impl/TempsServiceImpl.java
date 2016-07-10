package com.baohuquan.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baohuquan.dao.temps.TempsDao;
import com.baohuquan.model.Temps;
import com.baohuquan.service.TempsServiceIF;
import com.baohuquan.service.UserServiceIF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by wangdi5 on 2016/3/21.
 */
@Service
public class TempsServiceImpl implements TempsServiceIF {


    private static final Logger LOGGER = LoggerFactory.getLogger(TempsServiceImpl.class);


    @Resource
    TempsDao tempsDao;

    @Resource
    UserServiceIF userService;



    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private static final String HIGHTEMPKEY = "baohuquan_hightemp_%s_%s";

    public static final int TEN_MIN=1000*60*10;

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
    public Temps saveTemps(int babyId, Date day, int temp,int uid) {
        String strDay = sdf.format(day);
        String key = String.format(HIGHTEMPKEY, babyId, strDay);
        //之前的值
        Temps preHighTemp  = tempsDao.getTemp(babyId, strDay);
        //检查是否是头一次上传数据
        int isFirst=userService.getUserUpload(uid)==null?userService.updateUploadTime(uid,day):0;
        JSONArray array = new JSONArray();
        //今天第一次上传是
            if (preHighTemp == null) {
                //生成144个temps
                Long time = getStartTime(day);
                JSONArray arr = new JSONArray();
                for (int i = 0; i <144 ; i++) {
                    JSONObject o = new JSONObject();
                    long x = time+TEN_MIN*i;
                    int y=0;
                    if(x==day.getTime()){
                        y=temp;
                    }
                    o.put(String.valueOf((x)),y);
                    arr.add(o);
                }
                Temps temps = new Temps();
                temps.setCreateTime(new Date());
                temps.setBabyId(babyId);
                temps.setHighTemp(temp);
                temps.setTemps(arr.toJSONString());
                temps.setTempsDate(sdf.format(day));
                temps.setGetTime(day);
                int i = tempsDao.saveTemp(temps);
                temps.setId(i);

                return temps;
            }
        array = JSONArray.parseArray(preHighTemp.getTemps());
        JSONObject tempO = new JSONObject();
        int location = 0;
        for (int i = 0; i < array.size(); i++) {
           JSONObject o= (JSONObject) array.get(i);
           String tempKey = o.keySet().toArray()[0].toString();
            if(tempKey.equalsIgnoreCase(String.valueOf(day.getTime()))){
                tempO.put(tempKey,temp);
               location=i;
                array.remove(i);
                break;
            }
        }
        array.add(location,tempO);
        int highTemp = preHighTemp.getHighTemp() >= temp ? preHighTemp.getHighTemp() : temp;
        int i = tempsDao.updateTemps(preHighTemp.getId(), array.toJSONString(), highTemp);
        return preHighTemp;

    }



//    private void handleMonthTemps(int babyId,Date day,int highTemp){
//        Calendar todayStart = Calendar.getInstance();
//        todayStart.setTime(day);
//        int year = todayStart.get(Calendar.YEAR);
//        int month = todayStart.get(Calendar.MONTH);
//        MonthTemps monthTemps = monthTempsDao.getMonthTemps(year,month,babyId);
//        if(monthTemps==null){
//
//        }
//    }

    private Long getStartTime(Date date){
        Calendar todayStart = Calendar.getInstance();
        todayStart.setTime(date);
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime().getTime();
    }

//    private String map2JSONArray(final SortedMap<String,Integer> map){
//        JSONArray array = new JSONArray();
//        for (String key : map.keySet()){
//            JSONObject o = new JSONObject();
//            o.put(key,map.get(key));
//            array.add(o);
//        }
//
//           return array.toJSONString();
//    }

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

    /**
     * 获得最后一个
     *
     * @param babyId
     * @return
     */
    @Override
    public JSONObject getLastTemps(int babyId) {
        String tempDate = sdf.format(new Date());
        JSONObject reObject =new JSONObject();
        Temps temps = tempsDao.getTemp(babyId, tempDate);
        if(temps==null)
            return null;
        JSONArray arr = JSONArray.parseArray(temps.getTemps());

        if(arr==null)
            return null;
        for (int i = arr.size() - 1; i >= 0; i--) {
            JSONObject o = (JSONObject) arr.get(i);
            String str=String.valueOf(o.keySet().toArray()[0]);
            Integer x = (Integer) o.get(str);
            if(x!=0){
                reObject.put("key",str);
                reObject.put("value",x);
                break;
            }
        }
        return reObject;
    }


}
