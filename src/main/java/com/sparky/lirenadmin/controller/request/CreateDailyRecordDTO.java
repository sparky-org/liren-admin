package com.sparky.lirenadmin.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel
public class CreateDailyRecordDTO {

    @ApiModelProperty("写日志人")
    private Long empNo;
    @ApiModelProperty("写日志日期")
    private Date date;
    @ApiModelProperty("今日总结")
    private String todayConlude;
    @ApiModelProperty("明日计划")
    private String tomorrowPlan;
    @ApiModelProperty("汇报人")
    private Long auditor;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Long empNo) {
        this.empNo = empNo;
    }

    public String getTodayConlude() {
        return todayConlude;
    }

    public void setTodayConlude(String todayConlude) {
        this.todayConlude = todayConlude;
    }

    public String getTomorrowPlan() {
        return tomorrowPlan;
    }

    public void setTomorrowPlan(String tomorrowPlan) {
        this.tomorrowPlan = tomorrowPlan;
    }

    public Long getAuditor() {
        return auditor;
    }

    public void setAuditor(Long auditor) {
        this.auditor = auditor;
    }
}
