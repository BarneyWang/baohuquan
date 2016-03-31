package com.baohuquan.service.impl;

import com.baohuquan.dao.user.UserDao;
import com.baohuquan.model.User;
import com.baohuquan.service.UserServiceIF;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    public int updateCellNumber(int uid, String cellNumber) {

      return userDao.updateCellNumber(uid, cellNumber);

    }

    @Override
    public User getUser(Integer userId) {

        return userDao.getUser(userId);
    }
}
