package com.sparky.lirenadmin.controller.response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SalesPerfApplyVO {

    private Long applyNo;
    private String status;
    private String auditor;
    private Date applyCreateTime;

    private String title;
    private String content;
    private BigDecimal targetAmount;
    private String customerName;
    private String completeTime;
    private Integer applyPoint;

    private List<String> ccEmpNames;

    public Long getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(Long applyNo) {
        this.applyNo = applyNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public Date getApplyCreateTime() {
        return applyCreateTime;
    }

    public void setApplyCreateTime(Date applyCreateTime) {
        this.applyCreateTime = applyCreateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public Integer getApplyPoint() {
        return applyPoint;
    }

    public void setApplyPoint(Integer applyPoint) {
        this.applyPoint = applyPoint;
    }

    public List<String> getCcEmpNames() {
        return ccEmpNames;
    }

    public void setCcEmpNames(List<String> ccEmpNames) {
        this.ccEmpNames = ccEmpNames;
    }
}
