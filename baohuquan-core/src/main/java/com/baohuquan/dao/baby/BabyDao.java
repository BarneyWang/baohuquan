package com.baohuquan.dao.baby;

import com.baohuquan.model.Baby;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangdi5 on 2016/3/21.
 */
@Repository
public interface BabyDao {


    final static String CLOUMNS="id,uid,baby_avatar AS babyAvatar,tall,weight,gender,nick_name AS nickName,birthplace,create_time AS createTime,birthday";
    final static String INSERT_CLOUMNS="uid,baby_avatar,tall,weight,gender,nick_name,birthplace,create_time,birthday";

    @Options(useGeneratedKeys = true,keyProperty="id")
    @Insert("INSERT INTO t_baby("+INSERT_CLOUMNS+") values(#{uid},#{babyAvatar},#{tall},#{weight},#{gender},#{nickName},#{birthplace},#{createTime},#{birthday})")
    public int addBabyInfo(Baby baby);

    @Delete("DELETE FROM t_baby where uid=#{uid} and id= #{id}")
    public int deleteBabyInfo(@Param("id") int id,@Param("uid") int uid );

    @Select("SELECT "+ CLOUMNS+" FROM t_baby WHERE uid=#{uid}")
    public List<Baby> getBabyInfo(@Param("uid") int uid);

    @Update("UPDATE t_baby set birthday=#{birthday},baby_avatar=#{babyAvatar},weight=#{weight},gender=#{gender},tall=#{tall},birthplace=#{birthplace},nick_name=#{nickName} WHERE id=#{id}")
    public int updateBabyInfo(Baby baby);

    @Select("SELECT "+CLOUMNS+" FROM t_baby WHERE id = #{babyId}")
    Baby getBabyInfoByBabyId(@Param("babyId") int babyId);
}
