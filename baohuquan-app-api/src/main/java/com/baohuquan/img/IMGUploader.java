package com.baohuquan.img;

import com.baohuquan.utils.MD5Utils;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.sun.xml.internal.ws.api.message.Packet.Status.Response;

/**
 * Created by bojack on 16/4/24.
 */
@Component
public class IMGUploader {
    private static final Logger log = LoggerFactory.getLogger(IMGUploader.class);
    //验证
    private Auth auth = null;
    //空间名字
    private String bucketName="";
    //返回链接
    private String url="";


    //创建上传对象
   static UploadManager uploadManager = new UploadManager();

    public IMGUploader(String secret,String access,String bucketName,String url) {
        this.bucketName=bucketName;
        this.url=this.url;
        this.auth = Auth.create(access,secret);
    }

    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public String getUpToken(){
        return auth.uploadToken(bucketName);
    }

    public  String uploadImg(byte[] bytes,String name,String format){

        StringBuffer urlSb=new StringBuffer();
        StringBuffer avatarName=new StringBuffer(bucketName);
         avatarName.append(MD5Utils.md5Hex(name+System.currentTimeMillis())).append(format);
        try {
            //调用put方法上传
            Response res = uploadManager.put(bytes, avatarName.toString(), getUpToken());
            //打印返回的信息
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
        return urlSb.toString();
    }


    public static void main(String[] args) {
        System.out.println(MD5Utils.md5Hex("1"+System.currentTimeMillis()));
    }






}

