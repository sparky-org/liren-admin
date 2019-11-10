package com.sparky.lirenadmin.controller.response;

import org.dom4j.datatype.DatatypeElement;

import java.util.Date;

public class PointTraceVO {

    private String empName;
    private String title;
    private String rewardTime;
    private String empIcon;
    private Integer point;

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRewardTime() {
        return rewardTime;
    }

    public void setRewardTime(String rewardTime) {
        this.rewardTime = rewardTime;
    }

    public String getEmpIcon() {
        return empIcon;
    }

    public void setEmpIcon(String empIcon) {
        this.empIcon = empIcon;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
}
