package com.sparky.lirenadmin.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName ListMyShopVO
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/25
 * @Version v0.0.1
 */
@ApiModel("查询我的店铺列表")
public class ListMyShopVO {

    @ApiModelProperty("店铺编号")
    private Long shopNo;
    @ApiModelProperty("店铺名称")
    private String shopName;

    public Long getShopNo() {
        return shopNo;
    }

    public void setShopNo(Long shopNo) {
        this.shopNo = shopNo;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
