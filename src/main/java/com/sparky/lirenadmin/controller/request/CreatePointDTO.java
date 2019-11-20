package com.sparky.lirenadmin.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class CreatePointDTO {

    @ApiModelProperty("积分配置编号，可空")
    private Long pointConfigNo;

    @ApiModelProperty("员工编号，一般是管理员编号")
    private Long empNo;
    @ApiModelProperty("积分配置类型")
    private String pointType;
    @ApiModelProperty("积分配置名称")
    private String pointName;
    @ApiModelProperty("内容")
    private String content;
    @ApiModelProperty("积分值")
    private Integer value;

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public Long getPointConfigNo() {
        return pointConfigNo;
    }

    public void setPointConfigNo(Long pointConfigNo) {
        this.pointConfigNo = pointConfigNo;
    }

    public Long getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Long empNo) {
        this.empNo = empNo;
    }

    public String getPointType() {
        return pointType;
    }

    public void setPointType(String pointType) {
        this.pointType = pointType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
