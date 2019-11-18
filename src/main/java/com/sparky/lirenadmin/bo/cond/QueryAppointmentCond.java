package com.sparky.lirenadmin.bo.cond;

import java.util.Date;

/**
 * @ClassName QueryAppointmentCond
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/18
 * @Version v0.0.1
 */
public class QueryAppointmentCond {

    private Long shopNo;
    private Long empNo;
    private Date begin;
    private Date end;

    public QueryAppointmentCond(Long shopNo, Long empNo, Date begin, Date end) {
        this.shopNo = shopNo;
        this.empNo = empNo;
        this.begin = begin;
        this.end = end;
    }

    public Long getShopNo() {
        return shopNo;
    }

    public void setShopNo(Long shopNo) {
        this.shopNo = shopNo;
    }

    public Long getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Long empNo) {
        this.empNo = empNo;
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
