package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.entity.ShopEmployee;

public interface ShopEmployeeBO {
    ShopEmployee getEmployee(Long empNo);

    ShopEmployee getShopAdmin(Long empNo);
}