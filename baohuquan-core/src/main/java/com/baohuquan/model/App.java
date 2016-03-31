package com.baohuquan.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wangdi5 on 2016/3/25.
 */
public class App implements Serializable{


    private static final long serialVersionUID = -2229802995530873784L;

    private int id;
    private int os;
    private String appv;
    private int isForce;
    private Date createTime;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOs() {
        return os;
    }

    public void setOs(int os) {
        this.os = os;
    }

    public String getAppv() {
        return appv;
    }

    public void setAppv(String appv) {
        this.appv = appv;
    }

    public int getIsForce() {
        return isForce;
    }

    public void setIsForce(int isForce) {
        this.isForce = isForce;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "app{" +
                "id=" + id +
                ", os=" + os +
                ", appv='" + appv + '\'' +
                ", isForce=" + isForce +
                ", createTime=" + createTime +
                '}';
    }
}
