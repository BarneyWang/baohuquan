package com.baohuquan.service.impl;

import com.baohuquan.cache.XMemcached;
import com.baohuquan.constant.Constants;
import com.baohuquan.dao.common.CommonDao;
import com.baohuquan.model.ChannelMsg;
import com.baohuquan.service.CommonServiceIF;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wangdi5 on 2016/3/25.
 */
@Service
public class CommonServiceImpl  implements CommonServiceIF{


    private static final String CHANNELS_KEY="baohuquan_channels";

    @Resource
    CommonDao commonDao;

    @Override
    public List<ChannelMsg> getChannelMsgs() {

        List<ChannelMsg> list = XMemcached.get(CHANNELS_KEY);
        if(list==null){
            list= commonDao.getChannelMsgs();
            XMemcached.set(CHANNELS_KEY, Constants.EXPIRE_DAY,list);
        }


        return list;
    }
}
