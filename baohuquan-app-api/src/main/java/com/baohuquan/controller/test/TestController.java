package com.baohuquan.controller.test;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by wangdi5 on 2016/3/19.
 */
@Controller
@RequestMapping(value = "/test", produces = {"application/json;charset=UTF-8"})
public class TestController {



    @ResponseBody
    @RequestMapping(value = "/t")
    public String test(String id){

        System.out.println(id);
        Map map = Maps.newHashMap();
        return new JSONObject(map).toJSONString();

    }
}
