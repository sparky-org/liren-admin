package com.sparky.lirenadmin.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @ClassName PublishRewardConfigDTO
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/26
 * @Version v0.0.1
 */
@ApiModel
public class PublishRewardConfigDTO {

    @ApiModelProperty("自动奖励配置编号")
    private Long rewardConfigNo;
    @ApiModelProperty("操作人")
    private Long empNo;
    @ApiModelProperty("奖励类型(DAILY_RECORD_REWARD/CLOCK_IN_REWARD)")
    private List<RewardConfig> content;

    public Long getRewardConfigNo() {
        return rewardConfigNo;
    }

    public void setRewardConfigNo(Long rewardConfigNo) {
        this.rewardConfigNo = rewardConfigNo;
    }

    public Long getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Long empNo) {
        this.empNo = empNo;
    }

    public List<RewardConfig> getContent() {
        return content;
    }

    public void setContent(List<RewardConfig> content) {
        this.content = content;
    }

    public static class RewardConfig{
        private String type;
        private Integer rewardPoint;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Integer getRewardPoint() {
            return rewardPoint;
        }

        public void setRewardPoint(Integer rewardPoint) {
            this.rewardPoint = rewardPoint;
        }
    }
}
