package com.baohuquan.dao.test.provider;

/**
 * Created by wangdi5 on 2016/4/16.
 */
public class TestSQLProvider {
    public String test(final String name) {
//        return new SQL()
//        {
//            {
//                UPDATE("t_sharedbabyinfo");
//                FROM("t_")
//                SET("receiver ="+receiver);
//                WHERE(" id in ("+ids+")");
//            }
//        } .toString();

        System.out.println("-------------------------------1");
        return "UPDATE t_sharedbabyinfo SET receiver ="+2 + " WHERE id in (+11)";
    }
}
