package com.baohuquan.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wangdi5 on 2016/6/19.
 */
public class MonthTemps implements Serializable {


    private static final long serialVersionUID = 4403675311016156813L;

    private int id;
    private int babyId;
    private int year;
    private int month;
    private String monthTemps;
    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBabyId() {
        return babyId;
    }

    public void setBabyId(int babyId) {
        this.babyId = babyId;
    }

    public String getMonthTemps() {
        return monthTemps;
    }

    public void setMonthTemps(String monthTemps) {
        this.monthTemps = monthTemps;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
