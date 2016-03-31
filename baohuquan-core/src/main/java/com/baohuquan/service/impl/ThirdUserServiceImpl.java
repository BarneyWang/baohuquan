package com.baohuquan.service.impl;

import com.baohuquan.dao.user.ThirdUserDao;
import com.baohuquan.model.ThirdUser;
import com.baohuquan.service.ThirdUserServiceIF;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wangdi5 on 2016/3/20.
 */
@Service
public class ThirdUserServiceImpl implements ThirdUserServiceIF {

    @Resource
    ThirdUserDao thirdUserDao;

    @Override
    public void saveThirdUser(ThirdUser thirdUser) {
        thirdUserDao.save(thirdUser);
    }

    @Override
    public ThirdUser getThirdUser(Byte type, String openid) {
        return thirdUserDao.getThirdUser(type,openid);
    }
}
