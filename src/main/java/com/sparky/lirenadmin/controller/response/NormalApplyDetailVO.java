package com.sparky.lirenadmin.controller.response;

/**
 * @ClassName NormalApplyDetailVO
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/13
 * @Version v0.0.1
 */
public class NormalApplyDetailVO {

    private String title;
    private String content;
    private String auditEmp;
    private String ccList;
    private String picList;

    private String status;
    private String passedTime;

    private Boolean canRevert;

    public Boolean getCanRevert() {
        return canRevert;
    }

    public void setCanRevert(Boolean canRevert) {
        this.canRevert = canRevert;
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

    public String getAuditEmp() {
        return auditEmp;
    }

    public void setAuditEmp(String auditEmp) {
        this.auditEmp = auditEmp;
    }

    public String getCcList() {
        return ccList;
    }

    public void setCcList(String ccList) {
        this.ccList = ccList;
    }

    public String getPicList() {
        return picList;
    }

    public void setPicList(String picList) {
        this.picList = picList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPassedTime() {
        return passedTime;
    }

    public void setPassedTime(String passedTime) {
        this.passedTime = passedTime;
    }
}
