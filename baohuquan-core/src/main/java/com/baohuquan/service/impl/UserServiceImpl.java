package com.baohuquan.service.impl;

import com.baohuquan.dao.user.UserDao;
import com.baohuquan.model.User;
import com.baohuquan.service.UserServiceIF;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by wangdi5 on 2016/3/20.
 */
@Service
public class UserServiceImpl implements UserServiceIF{

    @Resource
    UserDao userDao;


    @Override
    public int saveUser(User user) {
        userDao.save(user);
       return user.getId();

    }

    @Override
    public User getUserByCell(String cellNumber) {
        User user = userDao.getUserByCell(cellNumber);
        return user;
    }

    @Override
    public int updateAvatar(int userId, String avatarUrl) {
        return userDao.updateAvatar(userId,avatarUrl);
    }

    @Override
    public int updateNickName(int userId, String nickName) {
        return userDao.updateNickName(userId, nickName);
    }

    @Override
    public int updateCellNumber(int uid,String area, String cellNumber) {

      return userDao.updateCellNumber(uid,area, cellNumber);

    }

    @Override
    public User getUser(Integer userId) {

        return userDao.getUser(userId);
    }

    @Override
    public int updateUserToken(int uid, String token) {
        return userDao.updateToken(uid,token);
    }

    @Override
    public int updateGender(Integer uid, Integer gender) {
        return  userDao.updateGender(uid, gender);
    }

    @Override
    public Date getUserUpload(Integer uid) {
        return userDao.getUserUploadTime(uid);
    }

    @Override
    public int updateUploadTime(Integer uid, Date time) {
        return userDao.updateUploadTime(uid,time);
    }


}
