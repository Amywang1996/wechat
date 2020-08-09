package com.amy.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
@Data
public class User {
    private Long id;
    private String nickName;
    private String mobile;
    /**
     * 余额
     */
    private Double remainingSum;

    /**
     * 头像地址
     */
    private String headPortrait;
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;
    /**
     * 状态（0正常 1停用）
     */
    private String status;
    private String openId;
/*
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Double getRemainingSum() {
        return remainingSum;
    }

    public void setRemainingSum(Double remainingSum) {
        this.remainingSum = remainingSum;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }*/

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", remainingSum=" + remainingSum +
                ", headPortrait='" + headPortrait + '\'' +
                ", createTime=" + createTime +
                ", delFlag='" + delFlag + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
