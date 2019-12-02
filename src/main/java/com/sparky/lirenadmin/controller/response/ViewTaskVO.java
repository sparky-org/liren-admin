package com.sparky.lirenadmin.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel
public class ViewTaskVO {

    @ApiModelProperty("任务编号")
    private Long taskNo;
    @ApiModelProperty("任务编号")
    private String title;
    @ApiModelProperty("积分类型")
    private String pointType;
    @ApiModelProperty("积分编号")
    private Long pointNo;
    @ApiModelProperty("是否全选")
    private Boolean selectAll;
    @ApiModelProperty("选择员工")
    private List<Long> empNoList;
    @ApiModelProperty("描述")
    private String content;
    @ApiModelProperty("奖励积分")
    private Integer rewardPoint;

    public Long getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(Long taskNo) {
        this.taskNo = taskNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPointType() {
        return pointType;
    }

    public void setPointType(String pointType) {
        this.pointType = pointType;
    }

    public Long getPointNo() {
        return pointNo;
    }

    public void setPointNo(Long pointNo) {
        this.pointNo = pointNo;
    }

    public Boolean getSelectAll() {
        return selectAll;
    }

    public void setSelectAll(Boolean selectAll) {
        this.selectAll = selectAll;
    }

    public List<Long> getEmpNoList() {
        return empNoList;
    }

    public void setEmpNoList(List<Long> empNoList) {
        this.empNoList = empNoList;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getRewardPoint() {
        return rewardPoint;
    }

    public void setRewardPoint(Integer rewardPoint) {
        this.rewardPoint = rewardPoint;
    }
}
