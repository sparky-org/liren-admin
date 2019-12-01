package com.sparky.lirenadmin.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

@ApiModel
public class MyAttendanceInfo {
    @ApiModelProperty("迟到次数")
    private Integer late;
    @ApiModelProperty("早退次数")
    private Integer leaveEarly;
    @ApiModelProperty("旷工次数")
    private Integer absenteeism;
    @ApiModelProperty("上班时间")
    private String startWorkTime;
    @ApiModelProperty("下班时间")
    private String endWorkTime;
    @ApiModelProperty("考勤异常日期")
    private List<String> exceptDateList;

    @ApiModelProperty("经度")
    private BigDecimal longitude;
    @ApiModelProperty("维度")
    private BigDecimal latitude;
    @ApiModelProperty("打卡半径")
    private Integer radiu;

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public Integer getRadiu() {
        return radiu;
    }

    public void setRadiu(Integer radiu) {
        this.radiu = radiu;
    }

    public Integer getLate() {
        return late;
    }

    public void setLate(Integer late) {
        this.late = late;
    }

    public Integer getLeaveEarly() {
        return leaveEarly;
    }

    public void setLeaveEarly(Integer leaveEarly) {
        this.leaveEarly = leaveEarly;
    }

    public Integer getAbsenteeism() {
        return absenteeism;
    }

    public void setAbsenteeism(Integer absenteeism) {
        this.absenteeism = absenteeism;
    }

    public String getStartWorkTime() {
        return startWorkTime;
    }

    public void setStartWorkTime(String startWorkTime) {
        this.startWorkTime = startWorkTime;
    }

    public String getEndWorkTime() {
        return endWorkTime;
    }

    public void setEndWorkTime(String endWorkTime) {
        this.endWorkTime = endWorkTime;
    }

    public List<String> getExceptDateList() {
        return exceptDateList;
    }

    public void setExceptDateList(List<String> exceptDateList) {
        this.exceptDateList = exceptDateList;
    }
}
