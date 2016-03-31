package com.baohuquan.dao.shared;

import com.baohuquan.dao.shared.provider.SharedBabyInfoSQLProvider;
import com.baohuquan.model.SharedBabyInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangdi5 on 2016/3/27.
 */
@Repository
public interface SharedBabyInfoDao {


    final static String COLUMNS="id,sharer,baby_id AS babyId,receiver_cell AS receiverCell,receiver AS receiver,create_time AS createTime";
    final static String INSERT_COLUMNS="sharer,baby_id,receiver_cell,receiver,create_time";


    @Options(useGeneratedKeys = true,keyProperty="id")
    @Insert("INSERT INTO t_sharedbabyinfo("+ INSERT_COLUMNS+") values(#{sharer},#{babyId},#{receiverCell},#{receiver},#{createTime})")
    int saveSharedInfo(SharedBabyInfo info);

//    @Update("UPDATE t_sharedbabyinfo set receiver = #{receiver} where  id in ( #{ids} ) ")
    @UpdateProvider(type = SharedBabyInfoSQLProvider.class, method = "recevierAccpet")
    int recevierAccpet(@Param("ids") String ids);


    @Select("SELECT "+COLUMNS+" FROM t_sharedbabyinfo  where receiver = #{receiver}")
    List<SharedBabyInfo> getSharedBabyInfo(@Param("receiver") int receiver);


    @Delete("DELETE FROM t_sharedbabyinfo where baby_id = #{babyId}")
    int deleteByBabyId(@Param("babyId")  int babyId);


    @Select("SELECT "+COLUMNS+" FROM t_sharedbabyinfo  where baby_id = #{babyId} and receiver_cell = #{receiverCell}")
    SharedBabyInfo getInfoByBabyAndCell(@Param("babyId") int babyId,@Param("receiverCell") String receiverCell);

    @Select("SELECT "+COLUMNS+" FROM t_sharedbabyinfo  where baby_id = #{babyId}")
    List<SharedBabyInfo> getInfoByBaby(@Param("babyId") int babyId);

    @Select("SELECT "+COLUMNS+" FROM t_sharedbabyinfo  where receiver is null AND  receiver_cell = #{receiverCell} ")
    List<SharedBabyInfo> getInfoByBabyByCell(@Param("receiverCell") String receiverCell);


}
