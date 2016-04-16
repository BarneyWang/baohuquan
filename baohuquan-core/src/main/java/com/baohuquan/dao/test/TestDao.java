package com.baohuquan.dao.test;

import com.baohuquan.dao.test.provider.TestSQLProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangdi5 on 2016/4/16.
 */
@Repository
public interface TestDao {

    @SelectProvider(type = TestSQLProvider.class, method = "test")
    List<String> get(@Param("name") String name);
}
