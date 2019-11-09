package com.sparky.lirenadmin.entity.po;

import java.util.Date;

/**
 * 员工查询我的任务返回对象，
 * 如果是管理员在管理员也是只可以看到他范围内的
 * 该对象包含待任务和已完成任务
 * 待办任务：该员工可见+未完成的任务//+完成人数未清零//
 * 已完成任务：该员工已完成的任务
 *
 * @ClassName MyTaskPO
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/8
 * @Version v0.0.1
 */
public class MyTaskPO {
    private Long id;

    private Long empNo;

    private String status;

    private String title;

    private String content;

    private Long pointNo;

    private Integer joinLimit;

    private String scope;

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

    public Long getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Long empNo) {
        this.empNo = empNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Long getPointNo() {
        return pointNo;
    }

    public void setPointNo(Long pointNo) {
        this.pointNo = pointNo;
    }

    public Integer getJoinLimit() {
        return joinLimit;
    }

    public void setJoinLimit(Integer joinLimit) {
        this.joinLimit = joinLimit;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
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

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
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
