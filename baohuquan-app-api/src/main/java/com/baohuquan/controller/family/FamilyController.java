package com.baohuquan.controller.family;

import com.baohuquan.utils.ResponseCode;
import com.baohuquan.utils.ResponseWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.regex.Pattern;

/**
 * Created by wangdi5 on 2016/3/21.
 */
@Deprecated
@Controller
@RequestMapping(value="/family", produces = {"application/json;charset=UTF-8"})
public class FamilyController {


    @RequestMapping(value = "/{uid}/add")
    public String addFamilyMember(@PathVariable Integer uid,
                                  @RequestParam("cell_number") String cellNumber){
        long start = System.currentTimeMillis();
        ResponseWrapper responseWrapper = new ResponseWrapper();
        //验证号码是否合法
        if(!isPhone(cellNumber)){
            responseWrapper.setCode(ResponseCode.PARAMS_FAIL.getCode());
            responseWrapper.setMsg(ResponseCode.PARAMS_FAIL.getMsg());
            return responseWrapper.toJSON();
        }
        //TODO 是否重复邀请
        responseWrapper.setCode(ResponseCode.SUCCESS.getCode());
        responseWrapper.setMsg(ResponseCode.SUCCESS.getMsg());
        responseWrapper.setCost(System.currentTimeMillis()-start);
        return responseWrapper.toJSON();
    }






    private boolean isPhone(String phoneNumber) {
        Pattern p = Pattern.compile("^1[0-9][0-9][0-9]{8}$");
        return p.matcher(phoneNumber).matches();
    }
}
