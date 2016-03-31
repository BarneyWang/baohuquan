package com.baohuquan.service;

import com.baohuquan.model.User;

/**
 * Created by wangdi5 on 2016/3/20.
 */
public interface UserServiceIF {


   int saveUser(User user);

    User getUserByCell(String phone_number);

    int updateAvatar(int userId, String avatarUrl);

    int updateNickName(int userId, String nickName);

    int updateCellNumber(int uid, String cellNumber);

    User getUser(Integer userId);
}
