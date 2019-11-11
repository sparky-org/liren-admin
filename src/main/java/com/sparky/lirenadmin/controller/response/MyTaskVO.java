package com.sparky.lirenadmin.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName MyTaskVO
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/11
 * @Version v0.0.1
 */
@ApiModel
public class MyTaskVO {
    @ApiModelProperty("任务编号")
    private Long taskNo;
    @ApiModelProperty("任务标题")
    private String taskName;
    @ApiModelProperty("任务内容")
    private String content;
    @ApiModelProperty("奖励积分")
    private Integer rewardPoint;

    public Long getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(Long taskNo) {
        this.taskNo = taskNo;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
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
