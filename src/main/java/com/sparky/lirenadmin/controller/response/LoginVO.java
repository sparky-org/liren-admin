package com.sparky.lirenadmin.controller.response;

import com.sparky.lirenadmin.entity.ShopEmployee;

/**
 * @ClassName LoginVO
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/8
 * @Version v0.0.1
 */
public class LoginVO {
    private String token;
    private ShopEmployee shopEmployee;

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
