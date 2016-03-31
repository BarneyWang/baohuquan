package com.baohuquan.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wangdi5 on 2016/3/25.
 */
public class ChannelMsg  implements Serializable{

    private static final long serialVersionUID = -5946784764896195950L;

    private int id;
    private String picUrl;
    private String url;
    private int isActive;
    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ChannelMsg{" +
                "id=" + id +
                ", picUrl='" + picUrl + '\'' +
                ", url='" + url + '\'' +
                ", isActive=" + isActive +
                ", createTime=" + createTime +
                '}';
    }
}
