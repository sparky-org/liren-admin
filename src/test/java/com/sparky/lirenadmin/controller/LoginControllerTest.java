package com.sparky.lirenadmin.controller;


import com.alibaba.fastjson.JSONObject;
import com.sparky.lirenadmin.bo.BeautyShopBO;
import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.ListMyShopVO;
import com.sparky.lirenadmin.controller.response.LoginVO;
import com.sparky.lirenadmin.entity.BeautyShop;
import com.sparky.lirenadmin.entity.ShopEmployee;
import com.sparky.lirenadmin.utils.Md5Utils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@SpringBootTest
@MapperScan("com.sparky.lirenadmin.mapper")
@RunWith(SpringRunner.class)
@Transactional
@Rollback(true)
public class LoginControllerTest {

    @Autowired
    private LoginController loginController;
    @Autowired
    private ShopEmployeeBO shopEmployeeBO;
    @Autowired
    private BeautyShopBO beautyShopBO;

    @Before
    public void before(){
        System.out.println("before====");
    }

    @Test
    public void testLogin() {
        BeautyShop shop = initShop();
        beautyShopBO.createShop(shop);
        ShopEmployee admin = initEmployee(shop);
        admin.setShopNo(shop.getId());
        shopEmployeeBO.createEmployee(admin);
        //初始化一名普通员工
        String phone = "13000000001";
        ShopEmployee employee = initEmployee(shop);
        employee.setName("美容师");
        employee.setPhone(phone);
        employee.setShopNo(shop.getId());
        employee.setManagerNo(admin.getId());
        employee.setIsAdmin(false);
        shopEmployeeBO.createEmployee(employee);

        //查询该手机账号有几个店铺
        BaseResponseWrapper<List<ListMyShopVO>> wrapper = loginController.listMyShop(phone);
        Assert.isTrue(wrapper.isSuccess());

        List<ListMyShopVO> vos = wrapper.getResult();

        BaseResponseWrapper<LoginVO> loginVOWrapper = loginController.login(phone, vos.get(0).getShopNo(), "123456");
        Assert.isTrue(loginVOWrapper.isSuccess());
        System.out.println(JSONObject.toJSONString(loginVOWrapper.getResult()));
    }

    private BeautyShop initShop() {
        BeautyShop shop = new BeautyShop();
        shop.setName("美容院X");
        shop.setJoinDate(new Date());
        return shop;
    }

    private ShopEmployee initEmployee(BeautyShop shop) {
        ShopEmployee admin = new ShopEmployee();
        admin.setIsAdmin(true);
        admin.setJobNo(1l);
        admin.setShopNo(shop.getId());
        admin.setPassword(Md5Utils.md5("123456"));
        admin.setName("张三");
        admin.setManagerNo(0l);
        admin.setPhone("13011111111");
        return admin;
    }
}
