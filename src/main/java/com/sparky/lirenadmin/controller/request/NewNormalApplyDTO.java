package com.sparky.lirenadmin.controller.request;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName NewVacationApplyDTO
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/13
 * @Version v0.0.1
 */
public class NewNormalApplyDTO {

    @ApiModelProperty("申请人")
    private Long empNo;
    @ApiModelProperty("申请内容")
    private String content;
    @ApiModelProperty("审批人")
    private Long auditEmpNo;
    @ApiModelProperty("抄送人id，多个，逗号隔开")
    private String ccEmpList;
    @ApiModelProperty("附件图片，地址逗号隔开。上传地址：/file/upload")
    private String attachmentPicList;

    public Long getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Long empNo) {
        this.empNo = empNo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAuditEmpNo() {
        return auditEmpNo;
    }

    public void setAuditEmpNo(Long auditEmpNo) {
        this.auditEmpNo = auditEmpNo;
    }

    public String getCcEmpList() {
        return ccEmpList;
    }

    public void setCcEmpList(String ccEmpList) {
        this.ccEmpList = ccEmpList;
    }

    public String getAttachmentPicList() {
        return attachmentPicList;
    }

    public void setAttachmentPicList(String attachmentPicList) {
        this.attachmentPicList = attachmentPicList;
    }
}
