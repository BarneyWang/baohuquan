package com.baohuquan.service.impl;

import com.baohuquan.cache.XMemcached;
import com.baohuquan.constant.Constants;
import com.baohuquan.dao.app.AppDao;
import com.baohuquan.model.App;
import com.baohuquan.service.AppServiceIF;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wangdi5 on 2016/3/25.
 */
@Service
public class AppServiceImpl implements AppServiceIF {


    private  static final String APP_KEY = "baohuquan_app";

    @Resource
    AppDao appDao;


    @Override
    public App getCurrentVersion() {
         App app = XMemcached.get(APP_KEY);
        if(app==null){
            app = appDao.getCurrentVersion();
            XMemcached.set(APP_KEY, Constants.EXPIRE_HOUR,app);
        }
        return app;
    }
}
