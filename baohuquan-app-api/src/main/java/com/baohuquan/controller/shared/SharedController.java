package com.baohuquan.controller.shared;

import com.baohuquan.model.SharedBabyInfo;
import com.baohuquan.model.User;
import com.baohuquan.service.SharedBabyInfoServiceIF;
import com.baohuquan.service.UserServiceIF;
import com.baohuquan.utils.ResponseCode;
import com.baohuquan.utils.ResponseWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.regex.Pattern;

/**
 * Created by wangdi5 on 2016/3/27.
 */
@Controller
@RequestMapping(value="/shared", produces = {"application/json;charset=UTF-8"})
public class SharedController {


    @Resource
    SharedBabyInfoServiceIF sharedBabyInfoService;


    @Resource
    UserServiceIF userService;

    @RequestMapping(value = "/{babyid}")
    public String addFamilyMember(@PathVariable int babyid,
                                  @RequestParam("cellnumber") String cellNumber,
                                  @RequestParam("uid") int uid){
        long start = System.currentTimeMillis();
        ResponseWrapper responseWrapper = new ResponseWrapper();
        //验证号码是否合法
        if(!isPhone(cellNumber)){
            responseWrapper.setCode(ResponseCode.PARAMS_FAIL.getCode());
            responseWrapper.setMsg(ResponseCode.PARAMS_FAIL.getMsg());
            return responseWrapper.toJSON();
        }
        SharedBabyInfo info =sharedBabyInfoService.getInfoByBabyAndCell(babyid,cellNumber);
        //已经邀请过了
        if(info!=null){
            responseWrapper.setCode(ResponseCode.REPETITION_SHARED.getCode());
            responseWrapper.setMsg(ResponseCode.REPETITION_SHARED.getMsg());
            return responseWrapper.toJSON();
        }

        User user =userService.getUserByCell(cellNumber);
         if(user==null){
            sharedBabyInfoService.sharedBabyInfo(babyid,cellNumber,uid,null);
         }else{
             sharedBabyInfoService.sharedBabyInfo(babyid,cellNumber,uid,user.getId());
         }
        responseWrapper.setCode(ResponseCode.SUCCESS.getCode());
        responseWrapper.setMsg(ResponseCode.SUCCESS.getMsg());
        responseWrapper.setCost(System.currentTimeMillis()-start);
        return responseWrapper.toJSON();
    }


    @ResponseBody
    @RequestMapping(value = "/cancel/{babyid}")
    public String cancelShared(@PathVariable Integer babyid,
                               @RequestParam Integer uid){
        long start = System.currentTimeMillis();
        ResponseWrapper responseWrapper = new ResponseWrapper();
        int i = sharedBabyInfoService.cancelShared(babyid,uid);
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
