package com.sparky.lirenadmin.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName CustomerDetailVO
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/11
 * @Version v0.0.1
 */
@ApiModel
public class SimpleCustomerVO {

    @ApiModelProperty("顾客编号")
    private Long customerNo;
    @ApiModelProperty("顾客姓名")
    private String name;
    @ApiModelProperty("手机号/130 **** 1111")
    private String phone;
    @ApiModelProperty("新增日期/yyyy-mm-dd")
    private String addDate;

    public Long getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(Long customerNo) {
        this.customerNo = customerNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }
}
