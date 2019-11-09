package com.sparky.lirenadmin.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel
public class TodayBusinessVO {

    @ApiModelProperty("今日预约客户")
    private int appointmentCustomerNum;

    @ApiModelProperty("今日业绩")
    private int salesPerformanceNum;

    @ApiModelProperty("今日项目数量")
    private int serviceItemRecordNum;

    @ApiModelProperty("今日休息员工")
    private int restEmployeeNum;

    @ApiModelProperty("今日积分排名")
    private int yourRank;

    @ApiModelProperty("今日获得积分")
    private int obtainPoint;

    @ApiModelProperty("昨日冠军姓名")
    private String championName;

    @ApiModelProperty("昨日日期")
    private Date championDate;

    public int getAppointmentCustomerNum() {
        return appointmentCustomerNum;
    }

    public void setAppointmentCustomerNum(int appointmentCustomerNum) {
        this.appointmentCustomerNum = appointmentCustomerNum;
    }

    public int getSalesPerformanceNum() {
        return salesPerformanceNum;
    }

    public void setSalesPerformanceNum(int salesPerformanceNum) {
        this.salesPerformanceNum = salesPerformanceNum;
    }

    public int getServiceItemRecordNum() {
        return serviceItemRecordNum;
    }

    public void setServiceItemRecordNum(int serviceItemRecordNum) {
        this.serviceItemRecordNum = serviceItemRecordNum;
    }

    public int getRestEmployeeNum() {
        return restEmployeeNum;
    }

    public void setRestEmployeeNum(int restEmployeeNum) {
        this.restEmployeeNum = restEmployeeNum;
    }

    public int getYourRank() {
        return yourRank;
    }

    public void setYourRank(int yourRank) {
        this.yourRank = yourRank;
    }

    public int getObtainPoint() {
        return obtainPoint;
    }

    public void setObtainPoint(int obtainPoint) {
        this.obtainPoint = obtainPoint;
    }

    public String getChampionName() {
        return championName;
    }

    public void setChampionName(String championName) {
        this.championName = championName;
    }

    public Date getChampionDate() {
        return championDate;
    }

    public void setChampionDate(Date championDate) {
        this.championDate = championDate;
    }
}
