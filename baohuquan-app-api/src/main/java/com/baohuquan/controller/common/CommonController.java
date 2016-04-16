package com.baohuquan.controller.common;

import com.baohuquan.anno.TokenRequired;
import com.baohuquan.model.ChannelMsg;
import com.baohuquan.service.CommonServiceIF;
import com.baohuquan.utils.ResponseCode;
import com.baohuquan.utils.ResponseWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wangdi5 on 2016/3/21.
 */
@Controller
@RequestMapping(value = "/common",produces = {"application/json;charset=UTF-8"})
public class CommonController {



    @Resource
    CommonServiceIF commonService;

    @TokenRequired
    @ResponseBody
    @RequestMapping(value="/static/msg")
    public String getStaticMsg(){

        long start = System.currentTimeMillis();
        ResponseWrapper responseWrapper = new ResponseWrapper();
        List<ChannelMsg> list =commonService.getChannelMsgs();
        responseWrapper.setCode(ResponseCode.SUCCESS.getCode());
        responseWrapper.setMsg(ResponseCode.SUCCESS.getMsg());
        responseWrapper.addValue("list", list);
        responseWrapper.setCost(System.currentTimeMillis()-start);
        return responseWrapper.toJSON();
    }



}
