package com.sparky.lirenadmin.controller.response;

import com.sparky.lirenadmin.entity.ShopEmployee;
import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName LoginVO
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/8
 * @Version v0.0.1
 */
public class LoginVO {
    private String token;
    private String shopName;
    @ApiModelProperty("商家类型SHOP/AGENCY")
    private String shopType;
    private ShopEmployee shopEmployee;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ShopEmployee getShopEmployee() {
        return shopEmployee;
    }

    public void setShopEmployee(ShopEmployee shopEmployee) {
        this.shopEmployee = shopEmployee;
    }
}
