package com.baohuquan.service.user;

import com.baohuquan.model.User;
import com.baohuquan.service.UserServiceIF;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by wangdi5 on 2016/3/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-test.xml")
public class UserServiceTest {

    @Resource
    UserServiceIF userService;


    @Test
    public void test(){

        User i =userService.getUserByCell("18611234444");
//        System.out.println(user.getId());
        Assert.assertNotNull(i);
        Assert.assertEquals(2, i.getId());

    }

    @Test
    public void testUpdate(){

        User i =userService.getUserByCell("18611234444");
        int i1=userService.updateNickName(i.getId(),"xxx1");
        int i2=userService.updateAvatar(i.getId(),"xxx1");

        Assert.assertEquals(1, i1);
        Assert.assertEquals(1, i2);
        User i3 =userService.getUserByCell("18611234444");

        Assert.assertEquals("xxx1", i3.getAvatarUrl());
        Assert.assertEquals("xxx1", i3.getNickName());

    }
}
