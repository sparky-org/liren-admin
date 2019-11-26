package com.sparky.lirenadmin.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class CreateDailyRecordDTO {

    @ApiModelProperty("今日总结")
    private String todayConlude;
    @ApiModelProperty("明日计划")
    private String tomorrowPlan;
    @ApiModelProperty("汇报人")
    private Long auditor;

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
