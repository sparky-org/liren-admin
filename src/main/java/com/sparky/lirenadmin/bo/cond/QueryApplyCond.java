package com.sparky.lirenadmin.bo.cond;

import java.util.Date;
import java.util.List;

/**
 * @ClassName QueryApplyCond
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/12
 * @Version v0.0.1
 */
public class QueryApplyCond {

    private Long empNo;
    private Long auditEmpNo;
    private Long ccEmpNo;
    private String applyType;
    private List<String> statuses;
    private Date begin;
    private Date end;

    private Integer start;
    private Integer length;

    public Long getCcEmpNo() {
        return ccEmpNo;
    }

    public void setCcEmpNo(Long ccEmpNo) {
        this.ccEmpNo = ccEmpNo;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Long getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Long empNo) {
        this.empNo = empNo;
    }

    public List<String> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<String> statuses) {
        this.statuses = statuses;
    }

    public Long getAuditEmpNo() {
        return auditEmpNo;
    }

    public void setAuditEmpNo(Long auditEmpNo) {
        this.auditEmpNo = auditEmpNo;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
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
}
