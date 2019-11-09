package com.sparky.lirenadmin.entity;

import java.util.Date;

public class DailyRecord {
    private Long id;

    private Date recordDate;

    private String today;

    private String tomorrow;

    private Long managerNo;

    private Boolean isRewarded;

    private Date rewardTime;

    private Long shopNo;

    private Long creator;

    private Boolean isValid;

    private Date gmtCreate;

    private Date gmtModify;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today == null ? null : today.trim();
    }

    public String getTomorrow() {
        return tomorrow;
    }

    public void setTomorrow(String tomorrow) {
        this.tomorrow = tomorrow == null ? null : tomorrow.trim();
    }

    public Long getManagerNo() {
        return managerNo;
    }

    public void setManagerNo(Long managerNo) {
        this.managerNo = managerNo;
    }

    public Boolean getIsRewarded() {
        return isRewarded;
    }

    public void setIsRewarded(Boolean isRewarded) {
        this.isRewarded = isRewarded;
    }

    public Date getRewardTime() {
        return rewardTime;
    }

    public void setRewardTime(Date rewardTime) {
        this.rewardTime = rewardTime;
    }

    public Long getShopNo() {
        return shopNo;
    }

    public void setShopNo(Long shopNo) {
        this.shopNo = shopNo;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }
}