package com.baohuquan.controller.app;

import com.baohuquan.model.App;
import com.baohuquan.service.AppServiceIF;
import com.baohuquan.utils.ResponseCode;
import com.baohuquan.utils.ResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 初始化app同步
 * Created by wangdi5 on 2016/3/20.
 */
@Controller
@RequestMapping(value = "/init", produces = {"application/json;charset=UTF-8"})
public class InitController {



    private  static final Logger LOGGER = LoggerFactory.getLogger(InitController.class);


    @Resource
    AppServiceIF appService;

    @ResponseBody
    @RequestMapping(value = "/app")
    public String initApp(String appv,String appfrom,
                          @RequestParam(value = "lc") String deviceId){

        long start = System.currentTimeMillis();
        ResponseWrapper responseWrapper = new ResponseWrapper();
        LOGGER.warn("[app初始化]|lc="+deviceId+"|appv="+appv+"|appfrom="+appfrom);
        //系统当前app 是否需要强升
        App app = appService.getCurrentVersion();
        responseWrapper.setCode(ResponseCode.SUCCESS.getCode());
        responseWrapper.setMsg(ResponseCode.SUCCESS.getMsg());
        responseWrapper.addValue("current_appv",app.getAppv());
        responseWrapper.addValue("is_force",app.getIsForce());
        responseWrapper.addValue("publish_time",app.getCreateTime().getTime());
        responseWrapper.setCost(System.currentTimeMillis()-start);
        return  responseWrapper.toJSON();
    }


}
