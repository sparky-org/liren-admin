package com.sparky.lirenadmin.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sparky.lirenadmin.bo.BeautyShopBO;
import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.controller.request.CreateDailyRecordDTO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.entity.BeautyShop;
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

import java.text.ParseException;
import java.util.Date;

@SpringBootTest
@MapperScan("com.sparky.lirenadmin.mapper")
@RunWith(SpringRunner.class)
@Transactional
@Rollback(true)
public class DailyRecordControllerTest {

    @Autowired
    private DailyRecordController dailyRecordController;
    @Autowired
    private ShopEmployeeBO shopEmployeeBO;
    @Autowired
    private BeautyShopBO beautyShopBO;

    @Before
    public void before(){
        System.out.println("before====");
    }

    @Test
    public void testApplyFlow() throws ParseException {
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

        CreateDailyRecordDTO dto = initCreateDailyRecordDTO(employee, admin);
        BaseResponseWrapper wrapper = dailyRecordController.createRecord(dto);
        Assert.isTrue(wrapper.isSuccess());
        wrapper = dailyRecordController.listMyDailyRecord(employee.getId(), 1, 10);
        Assert.isTrue(wrapper.isSuccess());
        System.out.println(JSONArray.toJSONString(wrapper.getResult()));
        wrapper = dailyRecordController.listReportToMeRecord(admin.getId(), 1, 10);
        Assert.isTrue(wrapper.isSuccess());
        System.out.println(JSONArray.toJSONString(wrapper.getResult()));
    }

    private CreateDailyRecordDTO initCreateDailyRecordDTO(ShopEmployee employee, ShopEmployee admin) {
        CreateDailyRecordDTO dto = new CreateDailyRecordDTO();
        dto.setAuditor(admin.getId());
        dto.setDate(new Date());
        dto.setEmpNo(employee.getId());
        dto.setTodayConlude("测试今天日志测试今天日志测试今天日志测试今天日志测试今天日志测试今天日志测试今天日志");
        dto.setTomorrowPlan("测试明日计划测试明日计划测试明日计划测试明日计划测试明日计划测试明日计划测试明日计划");
        return dto;
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
