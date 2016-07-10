package com.baohuquan.service;

import com.baohuquan.model.User;

import java.util.Date;

/**
 * Created by wangdi5 on 2016/3/20.
 */
public interface UserServiceIF {


   int saveUser(User user);

    User getUserByCell(String phone_number);

    int updateAvatar(int userId, String avatarUrl);

    int updateNickName(int userId, String nickName);

    int updateCellNumber(int uid,String area, String cellNumber);

    User getUser(Integer userId);

    int updateUserToken(int uid,String token);


    int updateGender(Integer uid, Integer gender);


    Date getUserUpload(Integer uid);

    int updateUploadTime(Integer uid, Date time);
}
