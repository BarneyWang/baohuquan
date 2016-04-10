package com.baohuquan.utils;

import org.apache.commons.lang.StringUtils;

import java.util.Random;

import static com.baohuquan.constant.Constants.TOKEN_ENCRYPTION;
import static com.baohuquan.constant.Constants.PASSWORD_ENCRYPTION;

/**
 * Created by wangdi5 on 2016/3/23.
 */
public class TokenUtil {


    /**
     * decode uid
     * @param token
     * @return
     */
    public static Integer getUserIdFromToken(String token) {
        String r_token = token.charAt(token.length() - 2) + token.substring(0, token.length() - 2);
        DESUtil desUtil = new DESUtil(TOKEN_ENCRYPTION);
        String str = desUtil.decryptStr(r_token);
        if (StringUtils.isBlank(str) || str.length() <= TOKEN_ENCRYPTION.length()) {
            return null;
        }
        String idStr = str.substring(TOKEN_ENCRYPTION.length());
        return Integer.valueOf(idStr);
    }

    /**
     * encode token
     * @param userId
     * @return
     */
    public static String generalToken(Integer userId) {
        DESUtil desUtil = new DESUtil(TOKEN_ENCRYPTION);
        String token = desUtil.encryptStr(TOKEN_ENCRYPTION + userId);
        Random random = new Random();
        token = token.substring(1) + token.charAt(0) + random.nextInt(10);
        return token;
    }



    /**
     * encode token
     * @param password
     * @return
     */
    public static String generatePassword(String password) {
        DESUtil desUtil = new DESUtil(PASSWORD_ENCRYPTION);
        String token = desUtil.encryptStr(PASSWORD_ENCRYPTION + password);
        return token;
    }

    public static void main(String[] args) {
        System.out.println(generatePassword("123456789011").length());

    }


}
