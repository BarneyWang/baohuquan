package com.baohuquan.service.share;

import com.baohuquan.service.SharedBabyInfoServiceIF;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by wangdi5 on 2016/3/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-test.xml")
public class SharedBabyInfoServiceTest {


    @Resource
    SharedBabyInfoServiceIF sharedBabyInfoService;



    @Test
    public void test(){
//        int i = sharedBabyInfoService.sharedBabyInfo(6,"12898622527",1,null);
//        System.out.println(i);

          sharedBabyInfoService.isHaveSharedInfo("18618721415",2);

    }
}
