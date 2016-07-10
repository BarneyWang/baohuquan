package com.baohuquan.service.baby;

import com.baohuquan.model.Temps;
import com.baohuquan.service.TempsServiceIF;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by wangdi5 on 2016/3/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-test.xml")
public class TempServiceTest {


    @Resource
    TempsServiceIF tempsService;


    @Test
    public void test(){
        Temps temps = new Temps();
        temps = tempsService.saveTemps(1,new Date(),3700,30);

        System.out.println(temps.toString());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        temps = tempsService.saveTemps(1,new Date(),3701,30);

        System.out.println(temps.toString());

        temps = tempsService.getByDay(1,new Date());

        System.out.println(temps.toString());



    }
}
