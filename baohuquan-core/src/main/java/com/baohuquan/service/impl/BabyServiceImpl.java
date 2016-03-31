package com.baohuquan.service.impl;

import com.baohuquan.dao.baby.BabyDao;
import com.baohuquan.model.Baby;
import com.baohuquan.service.BabyServiceIF;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wangdi5 on 2016/3/20.
 */
@Service
public class BabyServiceImpl implements BabyServiceIF {


    @Resource
    BabyDao babyDao;

    /**
     * 添加信息
     *
     * @param b
     * @return
     */
    @Override
    public int addBabyInfo(Baby b) {
        return babyDao.addBabyInfo(b);
    }

    /**
     * 删除信息
     *
     * @param id
     * @return
     */
    @Override
    public int deleteBabyInfo(int id) {
        return babyDao.deleteBabyInfo(id);
    }

    /**
     * 修改信息
     *
     * @param b
     * @return
     */
    @Override
    public int updateBabyInfo(Baby b) {
        return babyDao.updateBabyInfo(b);
    }

    /**
     * 获得baby信息
     *
     * @param uid
     * @return
     */
    @Override
    public List<Baby> getBabyInfo(int uid) {
        return babyDao.getBabyInfo(uid);
    }

    @Override
    public Baby getBabyInfoByBabyId(int babyId) {
        return babyDao.getBabyInfoByBabyId(babyId);
    }
}
