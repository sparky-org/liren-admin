package com.sparky.lirenadmin.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class GetPointTableVO {
    @ApiModelProperty("排名")
    private Integer rank;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("员工编号")
    private Long empNo;
    @ApiModelProperty("加分")
    private Integer increase;
    @ApiModelProperty("扣分")
    private Integer decrease;
    @ApiModelProperty("总分")
    private Integer total;

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Long empNo) {
        this.empNo = empNo;
    }

    public Integer getIncrease() {
        return increase;
    }

    public void setIncrease(Integer increase) {
        this.increase = increase;
    }

    public Integer getDecrease() {
        return decrease;
    }

    public void setDecrease(Integer decrease) {
        this.decrease = decrease;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
