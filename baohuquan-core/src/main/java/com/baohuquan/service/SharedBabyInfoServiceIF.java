package com.baohuquan.service;

import com.baohuquan.model.Baby;
import com.baohuquan.model.SharedBabyInfo;

import java.util.List;

/**
 * Created by wangdi5 on 2016/3/27.
 */
public interface SharedBabyInfoServiceIF {


    /**
     *
     * @param babyId
     * @param recevierCell
     * @param uid 分享者id
     * @return
     */
    int sharedBabyInfo(int babyId,String recevierCell,int uid,Integer recevier);

    /**
     *
     *
     * @return
     */
//    int recevierAccpet(String recevierCell,int babyId,int recevier);


    /**
     * 获得分享信息
     * @return
     */
    List<Baby> getSharedInfos(int revevier);


    /**
     * 解除分享
     * @param babyId
     * @param uid
     * @return
     */
    int  cancelShared(int babyId,int uid);


    /**
     * 查询通过手机号和babyid
     * @param babyId
     * @param cellPhonenumber
     * @return
     */
    SharedBabyInfo getInfoByBabyAndCell(int babyId,String cellPhonenumber);

    int isHaveSharedInfo(String cellNumber,int receiver);
}
