package com.baohuquan.service;

import com.baohuquan.model.Baby;

import java.util.List;

/**
 * Created by wangdi5 on 2016/3/20.
 */
public interface BabyServiceIF {

    /**
     * 添加信息
     * @param b
     * @return
     */
    int addBabyInfo(Baby b);

    /**
     * 删除信息
     * @param id
     * @return
     */
    int deleteBabyInfo(int id,int uid);

    /**
     * 修改信息
     * @param b
     * @return
     */
    int updateBabyInfo(Baby b);

    /**
     * 获得baby信息
     * @param uid
     * @return
     */
    List<Baby> getBabyInfo(int uid);



    Baby getBabyInfoByBabyId(int babyId);
}
