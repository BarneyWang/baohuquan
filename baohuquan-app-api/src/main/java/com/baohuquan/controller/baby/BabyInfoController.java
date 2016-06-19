package com.baohuquan.controller.baby;

import com.baohuquan.anno.TokenRequired;
import com.baohuquan.model.Baby;
import com.baohuquan.service.BabyServiceIF;
import com.baohuquan.service.SharedBabyInfoServiceIF;
import com.baohuquan.utils.ResponseCode;
import com.baohuquan.utils.ResponseWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 佩戴人信息
 * Created by wangdi5 on 2016/3/21.
 */
@Controller
@RequestMapping(value = "/baby", produces = {"application/json;charset=UTF-8"})
public class BabyInfoController {

    @Resource
    BabyServiceIF babyService;

    @Resource
    SharedBabyInfoServiceIF sharedBabyInfoService;


    @TokenRequired
    @ResponseBody
    @RequestMapping(value="/add")
    public String addBabyInfo(Baby b){
        long start = System.currentTimeMillis();
        ResponseWrapper responseWrapper = new ResponseWrapper();
        b.setCreateTime(new Date());
        int i =babyService.addBabyInfo(b);
        responseWrapper.setCode(ResponseCode.SUCCESS.getCode());
        responseWrapper.setMsg(ResponseCode.SUCCESS.getMsg());
        responseWrapper.addValue("id", b.getId());
        responseWrapper.setCost(System.currentTimeMillis()-start);
        return responseWrapper.toJSON();
    }


    @TokenRequired
    @ResponseBody
    @RequestMapping(value="/update/{babyid}")
    public String updateBabyInfo(@PathVariable Integer babyid, Baby b){
        long start = System.currentTimeMillis();
        ResponseWrapper responseWrapper = new ResponseWrapper();
        b.setId(babyid);
        int i =babyService.updateBabyInfo(b);
        responseWrapper.setCode(ResponseCode.SUCCESS.getCode());
        responseWrapper.setMsg(ResponseCode.SUCCESS.getMsg());
        responseWrapper.addValue("id",b.getId());
        responseWrapper.setCost(System.currentTimeMillis()-start);
        return responseWrapper.toJSON();
    }

    @TokenRequired
    @ResponseBody
    @RequestMapping(value="/get/{uid}")
    public String getBabyInfo(@PathVariable Integer uid){
        long start = System.currentTimeMillis();
        ResponseWrapper responseWrapper = new ResponseWrapper();
        List<Baby> l=babyService.getBabyInfo(uid);
        l.addAll(sharedBabyInfoService.getSharedInfos(uid));
        responseWrapper.setCode(ResponseCode.SUCCESS.getCode());
        responseWrapper.setMsg(ResponseCode.SUCCESS.getMsg());
        responseWrapper.addValue("data",l);
        responseWrapper.setCost(System.currentTimeMillis() - start);
        return responseWrapper.toJSON();
    }


    @TokenRequired
    @ResponseBody
    @RequestMapping(value="/del/{id}")
    public String delBabyInfo(@PathVariable Integer id,@RequestParam("uid") Integer uid){
        long start = System.currentTimeMillis();
        ResponseWrapper responseWrapper = new ResponseWrapper();
        int l=babyService.deleteBabyInfo(id,uid);
        responseWrapper.setCode(ResponseCode.SUCCESS.getCode());
        responseWrapper.setMsg(ResponseCode.SUCCESS.getMsg());
        responseWrapper.setCost(System.currentTimeMillis() - start);
        return responseWrapper.toJSON();
    }






}
