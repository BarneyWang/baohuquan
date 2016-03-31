package com.baohuquan.controller.baby;

import com.baohuquan.model.Temps;
import com.baohuquan.service.TempsServiceIF;
import com.baohuquan.utils.ResponseCode;
import com.baohuquan.utils.ResponseWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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


    @ResponseBody
    @RequestMapping(value = "/get/{babyid}")
    public String getTempsInDay(@PathVariable int babyid,
                                @RequestParam("time") long time) {
        long start = System.currentTimeMillis();
        ResponseWrapper responseWrapper = new ResponseWrapper();
        Temps temps = tempsService.getByDay(babyid, new Date(time));
        responseWrapper.setCode(ResponseCode.SUCCESS.getCode());
        responseWrapper.setMsg(ResponseCode.SUCCESS.getMsg());
        responseWrapper.addValue("data", temps);
        responseWrapper.setCost(System.currentTimeMillis() - start);
        return responseWrapper.toJSON();
    }

    @ResponseBody
    @RequestMapping(value = "/get/{babyid}")
    public String getTempsInMonth(@PathVariable int babyid,
                                  @RequestParam("year") int year,
                                  @RequestParam("month") int month) {
        long start = System.currentTimeMillis();
        ResponseWrapper responseWrapper = new ResponseWrapper();
        List<Temps> tempsList = tempsService.getByMonth(babyid, year, month);
        Map<String, Object> data = Maps.newHashMap();
        data.put("data", toJson(tempsList));
        responseWrapper.setCode(ResponseCode.SUCCESS.getCode());
        responseWrapper.setMsg(ResponseCode.SUCCESS.getMsg());
        responseWrapper.setData(data);
        responseWrapper.setCost(System.currentTimeMillis() - start);
        return responseWrapper.toJSON();
    }


    @ResponseBody
    @RequestMapping(value = "/upload/{babyid}", method = {RequestMethod.POST})
    public String uploadTemps(@PathVariable int babyid,
                              @RequestParam("uid") int uid,
                              @RequestBody TempUpload temp) {
        long start = System.currentTimeMillis();
        ResponseWrapper responseWrapper = new ResponseWrapper();
//        JSONObject o = new JSONObject();
//        o.put("baby",1);
//        o.put("uid",1);
//        JSONArray a =new JSONArray();
//        JSONObject o1 =new JSONObject();
//        o1.put(String.valueOf(new Date().getTime()),371);
//        o1.put(String.valueOf(new Date().getTime()),371);
//        o1.put(String.valueOf(new Date().getTime()),371);
//        o1.put(String.valueOf(new Date().getTime()),371);
//        o1.put(String.valueOf(new Date().getTime()),371);
//        a.add(o1);
//        o.put("data",a);
        tempsService.saveTemps(babyid, new Date(temp.getTime()), temp.getTemp());

        responseWrapper.setCode(ResponseCode.SUCCESS.getCode());
        responseWrapper.setMsg(ResponseCode.SUCCESS.getMsg());
        responseWrapper.setCost(System.currentTimeMillis() - start);

        return responseWrapper.toJSON();
    }

    @RequestMapping(value = "/history/upload/{babyid}")
    public void uploadHistoryTemps(@PathVariable int babyid,
                                   @RequestParam("uid") int uid,
                                   @RequestBody TempUpload[] tempUploads) {

        long start = System.currentTimeMillis();
        ResponseWrapper responseWrapper = new ResponseWrapper();
//
//        JSONObject o = new JSONObject();
//        o.put("baby",1);
//        o.put("uid",1);
//        JSONArray a =new JSONArray();
//        JSONObject o1 =new JSONObject();
//        o1.put(String.valueOf(new Date().getTime()),371);
//        o1.put(String.valueOf(new Date().getTime()),371);
//        o1.put(String.valueOf(new Date().getTime()),371);
//        o1.put(String.valueOf(new Date().getTime()),371);
//        o1.put(String.valueOf(new Date().getTime()),371);
//        a.add(o1);

        for (TempUpload temp : tempUploads) {

            tempsService.saveTemps(babyid, new Date(temp.getTime()), temp.getTemp());
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
