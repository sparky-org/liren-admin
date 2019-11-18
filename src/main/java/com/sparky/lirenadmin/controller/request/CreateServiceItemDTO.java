package com.sparky.lirenadmin.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName CreateServiceItemDTO
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/18
 * @Version v0.0.1
 */
@ApiModel
public class CreateServiceItemDTO {

    @ApiModelProperty("操作人编号")
    private Long empNo;
    @ApiModelProperty("项目名称")
    private String itemName;
    @ApiModelProperty("护理时长")
    private Integer duration;
    @ApiModelProperty("项目描述")
    private String desc;

    @ApiModelProperty("项目完成奖励")
    private Integer point;

    public Long getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Long empNo) {
        this.empNo = empNo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
}
