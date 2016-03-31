package com.baohuquan.service.baby;

import com.baohuquan.constant.Gender;
import com.baohuquan.model.Baby;
import com.baohuquan.service.BabyServiceIF;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by wangdi5 on 2016/3/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-test.xml")
public class BabyServiceTest {


    @Resource
    BabyServiceIF babyService;

    @Test
    public void test(){
        Baby baby = new Baby();
        baby.setNickName("xxx");
        baby.setGender(Gender.X.getGender());
        baby.setCreateTime(new Date());
        baby.setBirthplace("ddd");
        baby.setBirthday("2015-02-11 00:00:00");
        baby.setTall(123);
        baby.setWeight(12);
        baby.setUid(1);
        baby.setBabyAvatar("http://www.google.com");


       Integer i= babyService.addBabyInfo(baby);
        Assert.assertNotNull(i);
        Assert.assertEquals(1,1);
        List<Baby> l= babyService.getBabyInfo(1);
        Assert.assertNotNull(l);
        Assert.assertEquals(3,l.get(0).getId());

        baby.setTall(12);
        babyService.updateBabyInfo(baby);

//        babyService.deleteBabyInfo(l.get(0).getId());


    }
}
