package com.baohuquan.dao.shared.provider;

/**
 * Created by wangdi5 on 2016/3/30.
 */
public class SharedBabyInfoSQLProvider {

    public String recevierAccpet(final String ids) {
//        return new SQL()
//        {
//            {
//                UPDATE("t_sharedbabyinfo");
//                SET("receiver ="+receiver);
//                WHERE(" id in ("+ids+")");
//            }
//        } .toString();

        System.out.println("-------------------------------1");
        return "UPDATE t_sharedbabyinfo SET receiver ="+2 + " WHERE id in ("+ids+")";
    }
}
