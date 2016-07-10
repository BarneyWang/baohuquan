package com.baohuquan.dao.user;

import com.baohuquan.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by wangdi5 on 2016/3/20.
 */
@Repository
public interface UserDao {

     final static String CLOUMNS="id,area,cell_number AS cellNumber,nick_name  AS nickName,gender,avatar_url AS avatarUrl,create_time AS createTime,token,upload_time AS uploadTime";
     final static String INSERT_CLOUMNS="area,cell_number,nick_name,gender,avatar_url,create_time,token";


    @Options(useGeneratedKeys = true,keyProperty="id")
    @Insert("insert into t_user("+INSERT_CLOUMNS+") values(#{area},#{cellNumber},#{nickName},#{gender},#{avatarUrl},#{createTime},#{token})")
    int save(User u);

    @Select("select "+CLOUMNS+" from t_user where id=#{id}")
    User getUser(int id);

    @Select("select "+CLOUMNS+" from t_user where cell_number =#{cellNumber}")
    User getUserByCell(String cellNumber);

    @Update("update t_user set avatar_url=#{avatarUrl} where id=#{userId}")
    int updateAvatar(@Param("userId")int userId,@Param("avatarUrl") String avatarUrl);

    @Update("update t_user set nick_name=#{nickName} where id=#{userId}")
    int updateNickName(@Param("userId") int userId,@Param("nickName") String nickName);

    @Update("update t_user set cell_number=#{cellnumber},area=#{area} where id=#{userId}")
    int updateCellNumber(@Param("userId")int uid,@Param("area") String area ,@Param("cellnumber") String cellNumber);

    @Update("update t_user set token=#{token} where id=#{uid}")
    int updateToken(@Param("uid")int uid,@Param("token") String token);

    @Update("update t_user set gender=#{gender} where id=#{uid}")
    int updateGender(@Param("uid")Integer uid, @Param("gender")  Integer gender);

    @Select("select upload_time AS uploadTime from t_user where id=#{id}")
    Date getUserUploadTime(int id);

    @Update("update t_user set upload_time=#{uploadTime} where id= #{uid}")
    int updateUploadTime(@Param("uid") Integer uid, @Param("uploadTime")  Date uploadTime);
}
