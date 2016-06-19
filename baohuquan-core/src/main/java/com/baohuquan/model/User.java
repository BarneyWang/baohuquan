package com.baohuquan.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 * Created by wangdi on 2016/3/20.
 */
public class User  implements Serializable{


    private static final long serialVersionUID = 7239350957650937080L;

    private int id;
    private String area;
    private String cellNumber;
    private String nickName;
    private Integer gender;
    private String avatarUrl;
    private Date createTime;
    private String token;
    private String firstUploadTime="0";


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFirstUploadTime() {
        return firstUploadTime;
    }

    public void setFirstUploadTime(String firstUploadTime) {
        this.firstUploadTime = firstUploadTime;
    }
}
