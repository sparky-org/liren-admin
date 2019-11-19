package com.sparky.lirenadmin.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @ClassName ServiceRecordDetailVO
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/19
 * @Version v0.0.1
 */
@ApiModel
public class ServiceRecordDetailVO {

    @ApiModelProperty("执行操作需要此字段")
    private Long serviceRecordNo;

    private String status;
    private String auditor;
    private Date applyCreateTime;

    private String title;
    private String content;
    private String serviceItemName;
    private String customerName;
    private String completeTime;
    private Integer applyPoint;

    private List<String> ccEmpNames;

    public Long getServiceRecordNo() {
        return serviceRecordNo;
    }

    public void setServiceRecordNo(Long serviceRecordNo) {
        this.serviceRecordNo = serviceRecordNo;
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

    public String getServiceItemName() {
        return serviceItemName;
    }

    public void setServiceItemName(String serviceItemName) {
        this.serviceItemName = serviceItemName;
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
