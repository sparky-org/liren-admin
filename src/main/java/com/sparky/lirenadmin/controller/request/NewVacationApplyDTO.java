package com.sparky.lirenadmin.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @ClassName NewVacationApplyDTO
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/13
 * @Version v0.0.1
 */
@ApiModel
public class NewVacationApplyDTO {

    @ApiModelProperty("申请人ID")
    private Long applyEmpNo;
    @ApiModelProperty("开始日期")
    private Date begin;
    @ApiModelProperty("结束日期")
    private Date end;
    @ApiModelProperty("审批人id")
    private Long auditEmpNo;
    @ApiModelProperty("抄送人id，多个，逗号隔开")
    private String ccList;
    @ApiModelProperty("请假原因")
    private String reason;
    @ApiModelProperty("附件图片，地址逗号隔开。上传地址：/file/upload")
    private String attachemntPicList;

    public Long getApplyEmpNo() {
        return applyEmpNo;
    }

    public void setApplyEmpNo(Long applyEmpNo) {
        this.applyEmpNo = applyEmpNo;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Long getAuditEmpNo() {
        return auditEmpNo;
    }

    public void setAuditEmpNo(Long auditEmpNo) {
        this.auditEmpNo = auditEmpNo;
    }

    public String getCcList() {
        return ccList;
    }

    public void setCcList(String ccList) {
        this.ccList = ccList;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAttachemntPicList() {
        return attachemntPicList;
    }

    public void setAttachemntPicList(String attachemntPicList) {
        this.attachemntPicList = attachemntPicList;
    }
}
