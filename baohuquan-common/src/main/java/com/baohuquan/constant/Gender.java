package com.baohuquan.constant;

/**
 * Created by wangdi5 on 2016/3/20.
 */
public enum Gender {

    Male(0),Female(1),X(2);

    private int gender;

    private  Gender(int gender) {
         this.gender= gender;
    }


    public int getGender(){
        return this.gender;
    }
}
