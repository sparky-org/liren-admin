package com.sparky.lirenadmin.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @ClassName AddCustomerDTO
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/11
 * @Version v0.0.1
 */
@ApiModel
public class ModifyCustomerDTO extends AddCustomerDTO {
    @ApiModelProperty("顾客编号(id)")
    private Long customerNo;

    public Long getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(Long customerNo) {
        this.customerNo = customerNo;
    }
}
