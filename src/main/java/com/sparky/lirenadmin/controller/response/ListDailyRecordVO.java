package com.sparky.lirenadmin.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel
public class ListDailyRecordVO {

    @ApiModelProperty("写日志人编号")
    private Long empNo;
    @ApiModelProperty("写日志员工姓名")
    private String empName;
    @ApiModelProperty("写日志人岗位")
    private String jobName;
    @ApiModelProperty("写日志人头像")
    private String icon;
    @ApiModelProperty("奖励积分")
    private Integer rewardPoint;
    @ApiModelProperty("汇报人姓名")
    private String auditorName;
    @ApiModelProperty("写日志日期")
    private Date date;
    @ApiModelProperty("今日总结")
    private String todayConlude;
    @ApiModelProperty("明日计划")
    private String tomorrowPlan;

    public Long getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Long empNo) {
        this.empNo = empNo;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Integer getRewardPoint() {
        return rewardPoint;
    }

    public void setRewardPoint(Integer rewardPoint) {
        this.rewardPoint = rewardPoint;
    }

    public String getAuditorName() {
        return auditorName;
    }

    public void setAuditorName(String auditorName) {
        this.auditorName = auditorName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
}
