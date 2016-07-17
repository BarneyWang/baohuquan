package com.baohuquan.service.impl;

import com.baohuquan.cache.XMemcached;
import com.baohuquan.constant.Constants;
import com.baohuquan.dao.shared.SharedBabyInfoDao;
import com.baohuquan.model.Baby;
import com.baohuquan.model.SharedBabyInfo;
import com.baohuquan.service.BabyServiceIF;
import com.baohuquan.service.SharedBabyInfoServiceIF;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by wangdi5 on 2016/3/27.
 */
@Service
public class SharedBabyInfoServiceImpl implements SharedBabyInfoServiceIF {


    private final static String SHAREDCELLKEY = "baohuquan_sharedcell_%s_%s";
    private final static String SHAREDINFOKEY = "baohuquan_sharedinfo_%s";


    @Resource
    SharedBabyInfoDao sharedBabyInfoDao;

    @Resource
    BabyServiceIF babyService;


    /**
     * @param babyId
     * @param recevierCell
     * @param uid          分享者id
     * @param recevier
     * @return
     */
    @Override
    public int sharedBabyInfo(int babyId, String recevierCell, int uid, Integer recevier) {

        SharedBabyInfo info = new SharedBabyInfo();
        info.setCreateTime(new Date());
        info.setReceiver(recevier);
        info.setReceiverCell(recevierCell);
        info.setSharer(uid);
        info.setBabyId(babyId);
        int i = sharedBabyInfoDao.saveSharedInfo(info);

        return info.getId();
    }


    /**
     * @param receiverCell
     * @param receiver
     * @return
     */
//    @Override
//    public int recevierAccpet(String receiverCell, int babyId, int receiver) {
//
//        return sharedBabyInfoDao.recevierAccpet(receiverCell, babyId, receiver);
//    }

    /**
     * 获得分享信息
     *
     * @param receiver
     * @return
     */
    @Override
    public List<Baby> getSharedInfos(int receiver) {
        String key = String.format(SHAREDINFOKEY, receiver);
        List<Baby> l = XMemcached.get(key);
        if (CollectionUtils.isEmpty(l)) {
            l = Lists.newArrayList();
            List<SharedBabyInfo> infos = sharedBabyInfoDao.getSharedBabyInfo(receiver);
            for (SharedBabyInfo info : infos) {
                Baby b = babyService.getBabyInfoByBabyId(info.getBabyId());
                l.add(b);
            }
            XMemcached.set(key, Constants.EXPIRE_DAY, l);
        }
        return l;
    }

    /**
     * 解除分享
     *
     * @param babyId
     * @param uid
     * @return
     */
    @Override
    public int cancelShared(int babyId, int uid) {
        List<SharedBabyInfo> infos = sharedBabyInfoDao.getInfoByBaby(babyId);
        int i = 0;
        sharedBabyInfoDao.deleteByBabyId(babyId);
        //删除缓存
        if (!CollectionUtils.isEmpty(infos)) {
            for (SharedBabyInfo info : infos) {
                String key = String.format(SHAREDINFOKEY, info.getReceiver());
                XMemcached.delete(key);
                i++;
            }
        }
        return i;
    }

    /**
     * 解除所有
     *
     * @param babyId
     * @return
     */
    @Override
    public int cancelSharedByBabyId(int babyId) {
        return 0;
    }

    /**
     * 查询通过手机号和babyid
     *
     * @param babyId
     * @param cellPhonenumber
     * @return
     */
    @Override
    public SharedBabyInfo getInfoByBabyAndCell(int babyId, String cellPhonenumber) {
        String key = String.format(SHAREDCELLKEY, cellPhonenumber, babyId);
        SharedBabyInfo info = XMemcached.get(key);
        if (info == null) {
            info = sharedBabyInfoDao.getInfoByBabyAndCell(babyId, cellPhonenumber);
        }
        return info;
    }

//    @Override
//    public int isHaveSharedInfo(String cellNumber, int receiver) {
//
//        List<SharedBabyInfo> infos = sharedBabyInfoDao.getInfoByBabyByCell(cellNumber);
//        if (!CollectionUtils.isEmpty(infos)) {
//            StringBuilder builder = new StringBuilder();
//            for (SharedBabyInfo info : infos) {
//                builder.append(info.getId()).append(",");
//            }
//            builder.deleteCharAt(builder.length()-1);
//            return sharedBabyInfoDao.recevierAccpet(builder.toString(),receiver);
//        }
//        return 0;
//
//    }

    @Override
    public int isHaveSharedInfo(String cellNumber, int receiver) {

        List<SharedBabyInfo> infos = sharedBabyInfoDao.getInfoByBabyByCell(cellNumber);
        int i=0;
        if (!CollectionUtils.isEmpty(infos)) {
            for(SharedBabyInfo info : infos){
            sharedBabyInfoDao.recevierAccpet(info.getId(),receiver);
                  i++;
            }
        }
        return i;

    }
}
