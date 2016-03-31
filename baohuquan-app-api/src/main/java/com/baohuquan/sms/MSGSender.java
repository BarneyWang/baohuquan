package com.baohuquan.sms;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by wangdi5 on 2016/3/21.
 */
@Component
public class MSGSender {

    private static final Logger log = LoggerFactory.getLogger(MSGSender.class);
    private static CCPRestSmsSDK SENDER = new CCPRestSmsSDK();

    public MSGSender(String smsUrl,String smsPort,String accountId,String token,String appId){
        SENDER.init(smsUrl,smsPort);
        SENDER.setAccount(accountId,token);
        SENDER.setAppId(appId);
    }


    public  boolean sendSMS(String captcha,String phone,String expire) {
        Map<String,Object> result = SENDER.sendTemplateSMS(phone, "16886", new String[]{captcha});
        if ("000000".equals(result.get("statusCode"))) {
            log.info(phone + " SENDER sended,captcha:" + captcha + ",expire:" + expire + "ms");
            return true;
        }else {
            log.info(phone + " SENDER msg fail");
            return false;
        }
    }

}
