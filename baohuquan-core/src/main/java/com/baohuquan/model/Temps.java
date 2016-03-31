package com.baohuquan.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wangdi5 on 2016/3/21.
 */
public class Temps  implements Serializable{


    private static final long serialVersionUID = -1646439776966277998L;

    private int id;

    /**
     * babyId
     */
    private int babyId;
    /**
     * 上传温度日期 格式 yyyy-MM-dd
     */
    private String tempsDate;
    /**
     * 温度
     */
    private String temps;

    /**
     * 创建日期
     */
    private Date createTime;

    /**
     * 测量时间
     */
    private Date getTime;

    /**
     * 当天最高温度
     */
    private int highTemp;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getTempsDate() {
        return tempsDate;
    }

    public void setTempsDate(String tempsDate) {
        this.tempsDate = tempsDate;
    }

    public String getTemps() {
        return temps;
    }

    public void setTemps(String temps) {
        this.temps = temps;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getBabyId() {
        return babyId;
    }

    public void setBabyId(int babyId) {
        this.babyId = babyId;
    }

    public int getHighTemp() {
        return highTemp;
    }

    public void setHighTemp(int highTemp) {
        this.highTemp = highTemp;
    }

    public Date getGetTime() {
        return getTime;
    }

    public void setGetTime(Date getTime) {
        this.getTime = getTime;
    }

    @Override
    public String toString() {
        return "Temps{" +
                "id=" + id +
                ", babyId=" + babyId +
                ", tempsDate='" + tempsDate + '\'' +
                ", temps='" + temps + '\'' +
                ", createTime=" + createTime +
                ", getTime=" + getTime +
                ", highTemp=" + highTemp +
                '}';
    }
}
