package com.sparky.lirenadmin.entity;

import java.util.Date;

public class Apply {
    private Long id;

    private Long applyEmpNo;

    private Long auditEmpNo;

    private String applyContent;

    private String picList;

    private String origin;

    private Long originNo;

    private String auditStatus;

    private Date auditTime;

    private String remark;

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

    public Long getApplyEmpNo() {
        return applyEmpNo;
    }

    public void setApplyEmpNo(Long applyEmpNo) {
        this.applyEmpNo = applyEmpNo;
    }

    public Long getAuditEmpNo() {
        return auditEmpNo;
    }

    public void setAuditEmpNo(Long auditEmpNo) {
        this.auditEmpNo = auditEmpNo;
    }

    public String getApplyContent() {
        return applyContent;
    }

    public void setApplyContent(String applyContent) {
        this.applyContent = applyContent == null ? null : applyContent.trim();
    }

    public String getPicList() {
        return picList;
    }

    public void setPicList(String picList) {
        this.picList = picList == null ? null : picList.trim();
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin == null ? null : origin.trim();
    }

    public Long getOriginNo() {
        return originNo;
    }

    public void setOriginNo(Long originNo) {
        this.originNo = originNo;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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