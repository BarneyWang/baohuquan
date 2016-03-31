package com.baohuquan.dao;

import com.baohuquan.dao.temps.TempsDao;
import com.baohuquan.dao.temps.TestBean;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by wangdi5 on 2016/3/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-test.xml")
public class TempsDaoTest {


    @Resource
    TempsDao tempsDao;

    @Test
    public void test(){
        List<TestBean> list = Lists.newArrayList();
        TestBean b1 = new TestBean();
        b1.setCreateTime(new Date());
        b1.setName("11");
        list.add(b1);


        TestBean b2 = new TestBean();
        b1.setCreateTime(new Date());
        b1.setName("11");



    }
}
