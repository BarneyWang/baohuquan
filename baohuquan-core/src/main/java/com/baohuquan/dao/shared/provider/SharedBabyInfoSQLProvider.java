package com.baohuquan.dao.shared.provider;

/**
 * Created by wangdi5 on 2016/3/30.
 */
public class SharedBabyInfoSQLProvider {

    public String receiverAccpet(final String ids,final int receiver) {

        return "UPDATE t_sharedbabyinfo SET receiver ="+receiver+ " WHERE id in ("+ids+")";
    }
}
