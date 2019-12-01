package com.sparky.lirenadmin.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class GetPointRewardDetailVO {

    @ApiModelProperty("积分明细标题")
    private String title;
    @ApiModelProperty("奖励时间")
    private String time;
    @ApiModelProperty("奖励积分值，正负")
    private Integer point;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
}
