package com.baohuquan.controller.baby;

import com.alibaba.fastjson.JSONObject;
import com.baohuquan.anno.TokenRequired;
import com.baohuquan.model.Temps;
import com.baohuquan.service.TempsServiceIF;
import com.baohuquan.utils.ResponseCode;
import com.baohuquan.utils.ResponseWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 温度上传
 * Created by wangdi5 on 2016/3/21.
 */
@Controller
@RequestMapping(value = "/temps", produces = {"application/json;charset=UTF-8"})
public class TempsController {


    @Resource
    TempsServiceIF tempsService;


    /**
     *
     * @param babyid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getlast/{babyid}")
    public String getLastTemps(@PathVariable int babyid){
        long start = System.currentTimeMillis();
        ResponseWrapper responseWrapper = new ResponseWrapper();
        JSONObject temp = tempsService.getLastTemps(babyid);
        responseWrapper.setCode(ResponseCode.SUCCESS.getCode());
        responseWrapper.setMsg(ResponseCode.SUCCESS.getMsg());
        responseWrapper.addValue("time", temp.getString("key"));
        responseWrapper.addValue("temp", temp.getInteger("value"));
        responseWrapper.setCost(System.currentTimeMillis() - start);
        return responseWrapper.toJSON();
    }

    @TokenRequired
    @ResponseBody
    @RequestMapping(value = "/download/{babyid}")
    public String getTemps(@PathVariable int babyid) {
        long start = System.currentTimeMillis();
        ResponseWrapper responseWrapper = new ResponseWrapper();
        List<Temps> temps = tempsService.get(babyid);
        responseWrapper.setCode(ResponseCode.SUCCESS.getCode());
        responseWrapper.setMsg(ResponseCode.SUCCESS.getMsg());
        responseWrapper.addValue("data", temps);
        responseWrapper.setCost(System.currentTimeMillis() - start);
        return responseWrapper.toJSON();
    }


    @TokenRequired
    @ResponseBody
    @RequestMapping(value = "/get/day/{babyid}")
    public String getTempsInDay(@PathVariable int babyid,
                                @RequestParam("year") int year,
                                @RequestParam("month") int month,
                                @RequestParam("day") int day) {
        long start = System.currentTimeMillis();
        ResponseWrapper responseWrapper = new ResponseWrapper();
        Calendar cal = Calendar.getInstance();
        cal.set(year,month-1,day);
        Temps temps = tempsService.getByDay(babyid, cal.getTime());
        responseWrapper.setCode(ResponseCode.SUCCESS.getCode());
        responseWrapper.setMsg(ResponseCode.SUCCESS.getMsg());
        responseWrapper.addValue("data", temps);
        responseWrapper.setCost(System.currentTimeMillis() - start);
        return responseWrapper.toJSON();
    }

    @TokenRequired
    @ResponseBody
    @RequestMapping(value = "/get/month/{babyid}")
    public String getTempsInMonth(@PathVariable int babyid,
                                  @RequestParam("year") int year,
                                  @RequestParam("month") int month) {
        long start = System.currentTimeMillis();
        ResponseWrapper responseWrapper = new ResponseWrapper();
        List<Temps> tempsList = tempsService.getByMonth(babyid,month,year);
        Map<String, Object> data = Maps.newLinkedHashMap();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH,month-1);
        int dayCount=cal.getActualMaximum(Calendar.DATE);
        String prefix=year+"-";
        if(month<=9){
            prefix+="0"+month+"-";
        }else{
            prefix+=month+"-";
        }

        for (int i = 1; i <=dayCount; i++) {
            String t = prefix;
            if(i<10){
                t+="0"+i;
            }else{
                t+=i;
            }
            data.put(t,0);
        }
        for(Temps temps : tempsList){

            data.put(temps.getTempsDate(),temps.getHighTemp());
        }
        responseWrapper.setCode(ResponseCode.SUCCESS.getCode());
        responseWrapper.setMsg(ResponseCode.SUCCESS.getMsg());
        responseWrapper.setData(data);
        responseWrapper.setCost(System.currentTimeMillis() - start);
        return responseWrapper.toJSON();
    }


    @TokenRequired
    @ResponseBody
    @RequestMapping(value = "/upload/{babyid}", method = {RequestMethod.POST,RequestMethod.GET})
    public String uploadTemps(@PathVariable int babyid,
                              @RequestParam Long time,
                              @RequestParam Integer temp,
                              @RequestParam Integer uid) {
        long start = System.currentTimeMillis();
        ResponseWrapper responseWrapper = new ResponseWrapper();
        tempsService.saveTemps(babyid, new Date(time), temp,uid);
        responseWrapper.setCode(ResponseCode.SUCCESS.getCode());
        responseWrapper.setMsg(ResponseCode.SUCCESS.getMsg());
        responseWrapper.setCost(System.currentTimeMillis() - start);
        return responseWrapper.toJSON();
    }

    @TokenRequired
    @RequestMapping(value = "/history/upload/{babyid}")
    public void uploadHistoryTemps(@PathVariable int babyid,
                                   @RequestParam Integer uid,
                                   @RequestBody TempUpload[] tempUploads) {

        long start = System.currentTimeMillis();
        ResponseWrapper responseWrapper = new ResponseWrapper();

        for (TempUpload temp : tempUploads) {
            tempsService.saveTemps(babyid, new Date(temp.getTime()), temp.getTemp(),uid);
        }
        responseWrapper.setCode(ResponseCode.SUCCESS.getCode());
        responseWrapper.setMsg(ResponseCode.SUCCESS.getMsg());
        responseWrapper.setCost(System.currentTimeMillis() - start);


    }





    private List<Map<String, Object>> toJson(List<Temps> list) {
        List<Map<String, Object>> result = Lists.newArrayList();
        for (Temps temps : list) {
            Map<String, Object> map = toMap(temps);
            result.add(map);
        }
        return result;
    }

    private Map<String, Object> toMap(Temps temps) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("highTemp", temps.getHighTemp());
        map.put("time", temps.getGetTime());
        map.put("date", temps.getTempsDate());
        return map;

    }

    private class TempUpload {

        //上传时间
        private long time;
        //温度
        private int temp;

        public int getTemp() {
            return temp;
        }

        public void setTemp(int temp) {
            this.temp = temp;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }
    }


}
