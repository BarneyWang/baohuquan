package com.baohuquan.utils;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by wangdi5 on 2016/5/21.
 */
public class ImgUploadUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImgUploadUtils.class);

    public static final String ACCESSKEY = "7Y14CWfSAhKzpLjfWDteTudmRIHBYfDR0a8lQ5LJ";
    public static final String SECRETKEY = "tznFaAtnqx_F5hK9qoR3w-IZXhPpfD862LI77vqY";

    public static final String bucketName = "baohuquan";
    //    public static final String path = "/../..";
    private static UploadManager uploadManager = new UploadManager();
    private static Auth auth = Auth.create(ACCESSKEY, SECRETKEY);


    public static String getToken() {
        return auth.uploadToken(bucketName);
    }

    public static JSONObject uploadImg(String fileName, byte[] bytes) {
        Response res = null;
        String url="";
        JSONObject o=null;
        try {
            res = uploadManager.put(bytes, fileName, getToken());
             o = JSONObject.parseObject(res.bodyString());
        } catch (QiniuException e) {
            Response r = e.response;
            //请求失败时打印的异常的信息
            try {
                 LOGGER.info("[上传图片]error"+r.bodyString());
                  o = JSONObject.parseObject(r.bodyString());
                 return o;
                //响应的文本信息
            } catch (QiniuException e1) {
                //ignore
            }
        }
        return o;
    }

//    public static void main(String[] args) {
//        File file = new File("D://1//16742390_big_51.jpg");
//        try {
//
//            FileInputStream fis = new FileInputStream(file);
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            byte[] b = new byte[1024];
//            int n;
//            while((n=fis.read(b))!=-1){
//                bos.write(n);
//            }
//            fis.close();
//            bos.close();
//            uploadImg("1232131231.jpg",bos.toByteArray());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) {
        File file = new File("D://1//16742390_big_51.jpg");
        try {
            Response res = uploadManager.put(file, "16742390_big_51.jpg", getToken());
            System.out.println(res.bodyString());
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            System.out.println(r.toString());
            try {
                //响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException e1) {
                //ignore
            }
        }

    }
}
