package com.baohuquan.dao.app;

import com.baohuquan.model.App;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Created by wangdi5 on 2016/3/25.
 */
@Repository
public interface AppDao {

    final static String CLOUMNS="id,is_force AS isForce,appv,os,create_time AS createTime";
    final static String INSERT_CLOUMNS="is_force,appv,os,create_time";


    @Select("SELECT "+CLOUMNS+" FROM t_app order by id  limit 1")
    public App getCurrentVersion();

}
