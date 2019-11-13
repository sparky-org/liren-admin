package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.entity.ShopEmployee;

import java.util.List;

public interface ShopEmployeeBO {
    ShopEmployee getEmployee(Long empNo);

    ShopEmployee getShopAdmin(Long empNo);

    void createEmployee(ShopEmployee admin);

    void modify(ShopEmployee shopEmployee);

    List<ShopEmployee> listEmploy(List<Long> idList);
}
