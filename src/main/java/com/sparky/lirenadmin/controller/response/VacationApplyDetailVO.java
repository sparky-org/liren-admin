package com.sparky.lirenadmin.controller.response;

import java.util.Date;

/**
 * @ClassName VacationApplyDetailVO
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/13
 * @Version v0.0.1
 */
public class VacationApplyDetailVO {

    private String status;
    private String auditEmp;
    private String createTime;

    private Long applyNo;
    private String beginDate;
    private String endDate;
    private Integer days;
    private String reason;
    private String ccList;
    private String applyEmpName;
    private String applyEmpJob;
    private String picList;

    private Boolean canRevert;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuditEmp() {
        return auditEmp;
    }

    public void setAuditEmp(String auditEmp) {
        this.auditEmp = auditEmp;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(Long applyNo) {
        this.applyNo = applyNo;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCcList() {
        return ccList;
    }

    public void setCcList(String ccList) {
        this.ccList = ccList;
    }

    public String getApplyEmpName() {
        return applyEmpName;
    }

    public void setApplyEmpName(String applyEmpName) {
        this.applyEmpName = applyEmpName;
    }

    public String getApplyEmpJob() {
        return applyEmpJob;
    }

    public void setApplyEmpJob(String applyEmpJob) {
        this.applyEmpJob = applyEmpJob;
    }

    public String getPicList() {
        return picList;
    }

    public void setPicList(String picList) {
        this.picList = picList;
    }

    public Boolean getCanRevert() {
        return canRevert;
    }

    public void setCanRevert(Boolean canRevert) {
        this.canRevert = canRevert;
    }
}
