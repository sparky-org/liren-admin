package com.sparky.lirenadmin.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @ClassName CreateAppointmentDTO
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/14
 * @Version v0.0.1
 */
@ApiModel("预约")
public class CreateAppointmentDTO {
    @ApiModelProperty("顾客手机号")
    private String customerPhone;
    @ApiModelProperty("服务项目编号")
    private Long serviceItemNo;
    @ApiModelProperty("预约到店日期")
    private Date appointDate;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("美容师编号")
    private Long empNo;
    @ApiModelProperty("预约人编号")
    private Long operator;

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public Long getServiceItemNo() {
        return serviceItemNo;
    }

    public void setServiceItemNo(Long serviceItemNo) {
        this.serviceItemNo = serviceItemNo;
    }

    public Date getAppointDate() {
        return appointDate;
    }

    public void setAppointDate(Date appointDate) {
        this.appointDate = appointDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Long empNo) {
        this.empNo = empNo;
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }
}
