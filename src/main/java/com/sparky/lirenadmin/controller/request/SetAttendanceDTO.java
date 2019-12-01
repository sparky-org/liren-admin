package com.sparky.lirenadmin.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

@ApiModel
public class SetAttendanceDTO {
    @ApiModelProperty("店铺编号")
    private Long shopNo;
    @ApiModelProperty("操作员")
    private Long operator;
    @ApiModelProperty("上班时间")
    private String startWorkTime;

    @ApiModelProperty("下班时间")
    private String endWorkTime;

    @ApiModelProperty("经度")
    private BigDecimal longitude;
    @ApiModelProperty("维度")
    private BigDecimal latitude;
    @ApiModelProperty("打卡地址")
    private String address;

    @ApiModelProperty("打卡范围")
    private Integer scopeRadio;

    @ApiModelProperty("按时打卡奖励积分")
    private Integer rewardPoint;

    @ApiModelProperty("格式如下：星期一，星期二，星期三，星期四，星期五，星期六，星期日")
    private String workDay;

    public Long getShopNo() {
        return shopNo;
    }

    public void setShopNo(Long shopNo) {
        this.shopNo = shopNo;
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getScopeRadio() {
        return scopeRadio;
    }

    public void setScopeRadio(Integer scopeRadio) {
        this.scopeRadio = scopeRadio;
    }

    public Integer getRewardPoint() {
        return rewardPoint;
    }

    public void setRewardPoint(Integer rewardPoint) {
        this.rewardPoint = rewardPoint;
    }

    public String getWorkDay() {
        return workDay;
    }

    public void setWorkDay(String workDay) {
        this.workDay = workDay;
    }
}
