package com.sparky.lirenadmin.entity;

import java.util.Date;

public class Appointment {
    private Long id;

    private String customerPhone;

    private Long serviceNo;

    private Date appointTime;

    private String remark;

    private Integer rewardPoint;

    private Long serviceEmpNo;

    private Long appointEmpNo;

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

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone == null ? null : customerPhone.trim();
    }

    public Long getServiceNo() {
        return serviceNo;
    }

    public void setServiceNo(Long serviceNo) {
        this.serviceNo = serviceNo;
    }

    public Date getAppointTime() {
        return appointTime;
    }

    public void setAppointTime(Date appointTime) {
        this.appointTime = appointTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getRewardPoint() {
        return rewardPoint;
    }

    public void setRewardPoint(Integer rewardPoint) {
        this.rewardPoint = rewardPoint;
    }

    public Long getServiceEmpNo() {
        return serviceEmpNo;
    }

    public void setServiceEmpNo(Long serviceEmpNo) {
        this.serviceEmpNo = serviceEmpNo;
    }

    public Long getAppointEmpNo() {
        return appointEmpNo;
    }

    public void setAppointEmpNo(Long appointEmpNo) {
        this.appointEmpNo = appointEmpNo;
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