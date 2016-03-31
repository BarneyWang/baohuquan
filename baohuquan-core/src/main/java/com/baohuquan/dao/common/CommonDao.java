package com.baohuquan.dao.common;

import com.baohuquan.model.ChannelMsg;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangdi5 on 2016/3/25.
 */
@Repository
public interface CommonDao {

    final static String CLOUMNS="id,is_active AS isActive,pic_url AS picUrl,url,create_time AS createTime";
    final static String INSERT_CLOUMNS="is_active AS isActive,pic_url AS picUrl,url,create_time AS createTime";



    @Select("SELECT " +CLOUMNS+ " FROM t_channel where is_active=0")
    List<ChannelMsg> getChannelMsgs();



}
