package com.baohuquan.service;

import com.baohuquan.model.ThirdUser;

/**
 * Created by wangdi5 on 2016/3/20.
 */
public interface ThirdUserServiceIF {



    void saveThirdUser(ThirdUser thirdUser);

    ThirdUser getThirdUser(Byte type, String openid);
}
