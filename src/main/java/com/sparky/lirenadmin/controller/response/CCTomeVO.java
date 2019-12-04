package com.sparky.lirenadmin.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class CCTomeVO {
    @ApiModelProperty("员工编号")
    private Long applyNo;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("员工姓名")
    private String applyEmpName;
    @ApiModelProperty("审批人姓名")
    private String auditEmpName;
    @ApiModelProperty("状态")
    private String statusDesc;
    @ApiModelProperty("上次更新时间")
    private String lastModify;
    @ApiModelProperty("内容")
    private String content;

    public Long getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(Long applyNo) {
        this.applyNo = applyNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getApplyEmpName() {
        return applyEmpName;
    }

    public void setApplyEmpName(String applyEmpName) {
        this.applyEmpName = applyEmpName;
    }

    public String getAuditEmpName() {
        return auditEmpName;
    }

    public void setAuditEmpName(String auditEmpName) {
        this.auditEmpName = auditEmpName;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getLastModify() {
        return lastModify;
    }

    public void setLastModify(String lastModify) {
        this.lastModify = lastModify;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
