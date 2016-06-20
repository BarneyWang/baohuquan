package com.baohuquan.controller.app;

import com.alibaba.fastjson.JSONObject;
import com.baohuquan.anno.TokenRequired;
import com.baohuquan.utils.ImgUploadUtils;
import com.baohuquan.utils.MD5Utils;
import com.baohuquan.utils.ResponseCode;
import com.baohuquan.utils.ResponseWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import static com.baohuquan.constant.URIConstants.CDNURL_TEST;
/**
 * Created by wangdi5 on 2016/4/10.
 */
@Controller
@RequestMapping(value = "/upload")
public class UploadImgController {


    @TokenRequired
    @ResponseBody
    @RequestMapping(value="/image", produces = {"application/json;charset=UTF-8"})
    public String uploadWithKey(HttpServletRequest httpRequest,HttpServletResponse response) throws IOException {
        long start = System.currentTimeMillis();
        JSONObject o=null;
        ResponseWrapper responseWrapper = new ResponseWrapper();
        BASE64Decoder decoder = new BASE64Decoder();
        String image = httpRequest.getParameter("image");
        String name = httpRequest.getParameter("name");
        byte[] decode = decoder.decodeBuffer(image);
        String myFileName = MD5Utils.md5Hex(name)+"."+name.split("\\.")[1];
        o = ImgUploadUtils.uploadImg(myFileName,decode);
        responseWrapper.setCode(ResponseCode.SUCCESS.getCode());
        responseWrapper.setMsg(ResponseCode.SUCCESS.getMsg());
        responseWrapper.addValue("url",CDNURL_TEST+o.getString("key"));
        responseWrapper.setCost(System.currentTimeMillis()-start);
        return responseWrapper.toJSON();
    }






    @TokenRequired
    @ResponseBody
    @RequestMapping(value="/img", produces = {"application/json;charset=UTF-8"})
    public String upload(HttpServletRequest request,HttpServletResponse response){
        long start = System.currentTimeMillis();
        ResponseWrapper responseWrapper = new ResponseWrapper();
        JSONObject o=null;
        String imgUrl = "";
        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        //判断 request 是否有文件上传,即多部分请求
        if(multipartResolver.isMultipart(request)){
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
            //取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while(iter.hasNext()){
                //记录上传过程起始时的时间，用来计算上传时间
                int pre = (int) System.currentTimeMillis();
                //取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                if(file != null){
                    //取得当前上传文件的文件名称
                    String myFileName = file.getOriginalFilename();
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在
                    if(myFileName.trim() !=""){
                        myFileName = MD5Utils.md5Hex(myFileName)+"."+myFileName.split("\\.")[1];
                        try {
                             o = ImgUploadUtils.uploadImg(myFileName,file.getBytes());
                        } catch (Exception e) {
                            responseWrapper.setCode(ResponseCode.FAIL.getCode());
                            responseWrapper.setMsg(ResponseCode.FAIL.getMsg());
                            responseWrapper.addValue("error",o.getString("error"));
                            responseWrapper.setCost(System.currentTimeMillis()-start);
                            return responseWrapper.toJSON();
                        }
                    }
                }
            }
        }
        responseWrapper.setCode(ResponseCode.SUCCESS.getCode());
        responseWrapper.setMsg(ResponseCode.SUCCESS.getMsg());
        responseWrapper.addValue("url",CDNURL_TEST+o.getString("key"));
        responseWrapper.setCost(System.currentTimeMillis()-start);
        return responseWrapper.toJSON();
    }


    @RequestMapping(value="uploadtest")
    public ModelAndView uploadTest(HttpServletRequest request,HttpServletResponse response){


        return new ModelAndView("upload");
    }
}
