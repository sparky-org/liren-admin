package com.sparky.lirenadmin.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel
public class PublishTaskDTO {
    @ApiModelProperty("操作员")
    private Long empNo;
    @ApiModelProperty("任务标题")
    private String taskTitle;
    @ApiModelProperty("任务描述")
    private String taskDesc;
    @ApiModelProperty("积分配置编号")
    private Long pointConfigNo;
    @ApiModelProperty("奖励积分")
    private Integer rewardPoint;
    @ApiModelProperty("是否全选")
    private Boolean selectAll;
    @ApiModelProperty("可见员工岗位列表")
    private List<Long> jobList;
    @ApiModelProperty("可见员工列表")
    private List<Long> empList;

    public Integer getRewardPoint() {
        return rewardPoint;
    }

    public void setRewardPoint(Integer rewardPoint) {
        this.rewardPoint = rewardPoint;
    }

    public Boolean getSelectAll() {
        return selectAll;
    }

    public void setSelectAll(Boolean selectAll) {
        this.selectAll = selectAll;
    }

    public Long getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Long empNo) {
        this.empNo = empNo;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public Long getPointConfigNo() {
        return pointConfigNo;
    }

    public void setPointConfigNo(Long pointConfigNo) {
        this.pointConfigNo = pointConfigNo;
    }

    public List<Long> getJobList() {
        return jobList;
    }

    public void setJobList(List<Long> jobList) {
        this.jobList = jobList;
    }

    public List<Long> getEmpList() {
        return empList;
    }

    public void setEmpList(List<Long> empList) {
        this.empList = empList;
    }
}
