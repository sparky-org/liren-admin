package com.sparky.lirenadmin.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName AddCustomerDTO
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/11
 * @Version v0.0.1
 */
@ApiModel
public class AddCustomerDTO {
    @ApiModelProperty("员工编号(id)")
    private Long empNo;
    @ApiModelProperty("客户姓名")
    private String customerName;
    @ApiModelProperty("性别：MALE/FEMALE")
    private String sex;
    @ApiModelProperty("首选联系电话，手机号")
    private String phone;
    @ApiModelProperty("爱好，逗号隔开的字符串")
    private String favor;
    @ApiModelProperty("生日")
    private String birthDay;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("年度计划")
    private String yearPlan;

    public Long getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Long empNo) {
        this.empNo = empNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFavor() {
        return favor;
    }

    public void setFavor(String favor) {
        this.favor = favor;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getYearPlan() {
        return yearPlan;
    }

    public void setYearPlan(String yearPlan) {
        this.yearPlan = yearPlan;
    }
}
