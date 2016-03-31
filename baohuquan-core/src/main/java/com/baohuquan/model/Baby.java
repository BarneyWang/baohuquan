package com.baohuquan.model;

import java.util.Date;

/**
 * 佩戴人信息维护
 * Created by wangdi5 on 2016/3/20.
 */
public class Baby {


    /**
     * baby id
     */
    private int id;
    /**
     *
     * 设备地址
     */
    private String deviceAddr;

    private String babyAvatar;
    private String birthday;
    //unit:cm
    private int  tall;
    //unit:kg
    private float weight;
    /**
     * 佩戴人别名
     */
    private String nickName;

    /**
     * 性别
     */
    private int gender;

    private String birthplace;

    /**
     * 创建人id
     */
    private int uid;

    private Date createTime;

    public String getDeviceAddr() {
        return deviceAddr;
    }

    public void setDeviceAddr(String deviceAddr) {
        this.deviceAddr = deviceAddr;
    }

    public String getBabyAvatar() {
        return babyAvatar;
    }

    public void setBabyAvatar(String babyAvatar) {
        this.babyAvatar = babyAvatar;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getTall() {
        return tall;
    }

    public void setTall(int tall) {
        this.tall = tall;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Baby{" +
                "id=" + id +
                ", deviceAddr='" + deviceAddr + '\'' +
                ", babyAvatar='" + babyAvatar + '\'' +
                ", birthday='" + birthday + '\'' +
                ", tall=" + tall +
                ", weight=" + weight +
                ", nickName='" + nickName + '\'' +
                ", gender=" + gender +
                ", birthplace='" + birthplace + '\'' +
                ", uid=" + uid +
                ", createTime=" + createTime +
                '}';
    }
}
