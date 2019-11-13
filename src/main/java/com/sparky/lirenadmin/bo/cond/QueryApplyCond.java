package com.sparky.lirenadmin.bo.cond;

import java.util.Date;

/**
 * @ClassName QueryApplyCond
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/12
 * @Version v0.0.1
 */
public class QueryApplyCond {

    private Long empNo;
    private String applyType;
    private String status;
    private Date begin;
    private Date end;

    private Integer start;
    private Integer length;

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

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
