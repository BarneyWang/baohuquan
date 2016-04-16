package com.baohuquan.dao.user;

import com.baohuquan.model.ThirdUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Created by wangdi5 on 2016/3/20.
 */
@Repository
public interface ThirdUserDao {

    final static String INSERT_CLOUMNS="user_id ,type,open_id,gender,nick_name,avatar_url,create_time";
    final static String CLOUMNS="id,user_id AS userId,type type,open_id AS openId,gender,nick_name  AS nickName,avatar_url AS avatarUrl,create_time AS createTime";



    @Options(useGeneratedKeys = true,keyProperty="id")
    @Insert("insert into t_third_user("+INSERT_CLOUMNS+") values(#{userId},#{type},#{openId},#{gender},#{nickName},#{avatarUrl},#{createTime})")
    int save(ThirdUser thirdUser);


    @Select("select "+CLOUMNS+" from t_third_user where type = #{type} and open_id = #{openId}")
    ThirdUser getThirdUser(@Param(value = "type") byte type,
                           @Param(value = "openId") String openId);

    @Select("select "+CLOUMNS+" from t_third_user where user_id = #{uid}")
    ThirdUser getThirdUserByUid(@Param(value = "uid")Integer uid);
}
