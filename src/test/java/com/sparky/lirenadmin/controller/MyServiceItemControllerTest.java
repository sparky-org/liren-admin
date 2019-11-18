package com.sparky.lirenadmin.controller;


import com.alibaba.fastjson.JSONArray;
import com.sparky.lirenadmin.bo.BeautyShopBO;
import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.controller.request.CreateServiceItemDTO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.entity.BeautyShop;
import com.sparky.lirenadmin.entity.ServiceItem;
import com.sparky.lirenadmin.entity.ShopEmployee;
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
public class MyServiceItemControllerTest {

    @Autowired
    private MyServiceItemController myServiceItemController;
    @Autowired
    private ShopEmployeeBO shopEmployeeBO;
    @Autowired
    private BeautyShopBO beautyShopBO;

    @Before
    public void before(){
        System.out.println("before====");
    }

    @Test
    public void testCreateServiceItem() {
        BeautyShop shop = initShop();
        beautyShopBO.createShop(shop);
        ShopEmployee admin = initEmployee(shop);
        admin.setShopNo(shop.getId());
        shopEmployeeBO.createEmployee(admin);
        //初始化一名普通员工
        ShopEmployee employee = initEmployee(shop);
        employee.setName("美容师");
        employee.setPhone("13000000001");
        employee.setShopNo(shop.getId());
        employee.setManagerNo(admin.getId());
        employee.setIsAdmin(false);
        shopEmployeeBO.createEmployee(employee);
        //初始化一名普通员工
        ShopEmployee cc = initEmployee(shop);
        cc.setName("美容师");
        cc.setPhone("13000000001");
        cc.setShopNo(shop.getId());
        cc.setManagerNo(admin.getId());
        cc.setIsAdmin(false);
        shopEmployeeBO.createEmployee(cc);

        CreateServiceItemDTO dto = initCreateServiceItemDTO(admin);
        BaseResponseWrapper result = myServiceItemController.createServiceItem(dto);
        Assert.isTrue(result.isSuccess(), "创建失败");

        BaseResponseWrapper<List<ServiceItem>> result2 = myServiceItemController.queryServiceItems(employee.getShopNo());
        Assert.isTrue(result2.isSuccess(), "查询失败");
        System.out.println(JSONArray.toJSONString(result2.getResult()));

        BaseResponseWrapper<List<ServiceItem>> result3 = myServiceItemController.pagingQueryServiceItems(employee.getShopNo(), 1, 10);
        Assert.isTrue(result3.isSuccess(), "查询失败");
        System.out.println(JSONArray.toJSONString(result3.getResult()));
    }

    private CreateServiceItemDTO initCreateServiceItemDTO(ShopEmployee admin){
        CreateServiceItemDTO item = new CreateServiceItemDTO();
        item.setItemName("脸部护理");
        item.setDesc("护理手法要求：护理时间要求：");
        item.setEmpNo(admin.getId());
        item.setDuration(120);
        item.setPoint(10);
        return item;
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
        admin.setPassword("1111");
        admin.setName("张三");
        admin.setManagerNo(0l);
        admin.setPhone("13011111111");
        return admin;
    }
}
