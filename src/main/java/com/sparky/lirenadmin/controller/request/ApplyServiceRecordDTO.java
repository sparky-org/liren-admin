package com.sparky.lirenadmin.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("项目申报内容")
public class ApplyServiceRecordDTO {
    @ApiModelProperty("申请人编号")
    private Long empNo;

    @ApiModelProperty("申报标题")
    private String title;
    @ApiModelProperty("申报内容")
    private String content;
    @ApiModelProperty("项目编号")
    private Long serviceItemNo;
    @ApiModelProperty("顾客编号")
    private Long customerNo;
    @ApiModelProperty("完成时间:yyyy-mm-dd hh:mm:ss")
    private String completeTime;
    @ApiModelProperty("申请奖励")
    private Integer point;
    @ApiModelProperty("审批人")
    private Long auditor;
    @ApiModelProperty("抄送人编号，多个逗号分隔")
    private String ccEmpList;

    public Long getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Long empNo) {
        this.empNo = empNo;
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

    public Long getServiceItemNo() {
        return serviceItemNo;
    }

    public void setServiceItemNo(Long serviceItemNo) {
        this.serviceItemNo = serviceItemNo;
    }

    public Long getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(Long customerNo) {
        this.customerNo = customerNo;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Long getAuditor() {
        return auditor;
    }

    public void setAuditor(Long auditor) {
        this.auditor = auditor;
    }

    public String getCcEmpList() {
        return ccEmpList;
    }

    public void setCcEmpList(String ccEmpList) {
        this.ccEmpList = ccEmpList;
    }
}
