package com.sparky.lirenadmin.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName QueryAwardDeductionVO
 * @Description TODO
 * @Author 86186
 * @Date 2019/12/2
 * @Version v0.0.1
 */
@ApiModel
public class QueryAwardDeductionVO {
    @ApiModelProperty("员工姓名")
    private String empName;
    @ApiModelProperty("是否奖励，控制括号内容颜色")
    private Boolean isReward;
    @ApiModelProperty("奖罚文案，括号内的内容")
    private String rewardDesc;
    @ApiModelProperty("日期")
    private String date;
    @ApiModelProperty("内容")
    private String content;

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Boolean getReward() {
        return isReward;
    }

    public void setReward(Boolean reward) {
        isReward = reward;
    }

    public String getRewardDesc() {
        return rewardDesc;
    }

    public void setRewardDesc(String rewardDesc) {
        this.rewardDesc = rewardDesc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
