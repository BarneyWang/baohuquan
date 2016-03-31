package com.baohuquan.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 分享
 * Created by wangdi5 on 2016/3/27.
 */
public class SharedBabyInfo  implements Serializable{


    private static final long serialVersionUID = 7319458802081725846L;


    private int id;
    /**
     * 被邀请人的手机号
     */
    private String receiverCell;
    /**
     * 发送人(uid)
     */
    private int sharer;
    /**
     * 接收人(uid)
     */
    private Integer receiver;


    /**
     * 分享的babyID
     * */
    private int babyId;

    /**
     * 创建时间
     */
    private Date createTime;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReceiverCell() {
        return receiverCell;
    }

    public void setReceiverCell(String receiverCell) {
        this.receiverCell = receiverCell;
    }

    public int getSharer() {
        return sharer;
    }

    public void setSharer(int sharer) {
        this.sharer = sharer;
    }

    public Integer getReceiver() {
        return receiver;
    }

    public void setReceiver(Integer receiver) {
        this.receiver = receiver;
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


    @Override
    public String toString() {
        return "SharedBabyInfo{" +
                "id=" + id +
                ", receiverCell='" + receiverCell + '\'' +
                ", sharer=" + sharer +
                ", receiver=" + receiver +
                ", babyId=" + babyId +
                ", createTime=" + createTime +
                '}';
    }
}
