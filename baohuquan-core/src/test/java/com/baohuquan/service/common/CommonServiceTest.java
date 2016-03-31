package com.baohuquan.service.common;

import com.baohuquan.model.ChannelMsg;
import com.baohuquan.service.CommonServiceIF;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wangdi5 on 2016/3/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-test.xml")
public class CommonServiceTest {


    @Resource
    CommonServiceIF commonService;

    @Test
    public void test(){
        List<ChannelMsg> list =commonService.getChannelMsgs();
        for(ChannelMsg msg:list){
            System.out.println(msg.toString());
        }
    }
}
