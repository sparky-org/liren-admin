package com.sparky.lirenadmin.controller;


import com.alibaba.fastjson.JSONObject;
import com.sparky.lirenadmin.bo.BeautyShopBO;
import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.controller.request.NewNormalApplyDTO;
import com.sparky.lirenadmin.controller.request.SubmitAwardDeductionDTO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.PagingResponseWrapper;
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
import java.util.Arrays;
import java.util.Date;

@SpringBootTest
@MapperScan("com.sparky.lirenadmin.mapper")
@RunWith(SpringRunner.class)
@Transactional
@Rollback(true)
public class RewardPunishmentControllerTest {

    @Autowired
    private RewardPunishmentController rewardPunishmentController;
    @Autowired
    private ShopEmployeeBO shopEmployeeBO;
    @Autowired
    private BeautyShopBO beautyShopBO;

    @Before
    public void before(){
        System.out.println("before====");
    }

    @Test
    public void testRewardPunishment(){
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

        SubmitAwardDeductionDTO dto = initSubmitAwardDeductionDTO(employee, admin);
        BaseResponseWrapper result = rewardPunishmentController.submitAwardDeduction(dto);
        Assert.isTrue(result.isSuccess());

        PagingResponseWrapper responseWrapper = rewardPunishmentController.queryAwardDeduction(employee.getId(), null, 1, 10);
        Assert.isTrue(responseWrapper.isSuccess());
        System.out.println(JSONObject.toJSONString(responseWrapper));
    }

    private SubmitAwardDeductionDTO initSubmitAwardDeductionDTO(ShopEmployee employee, ShopEmployee admin) {
        SubmitAwardDeductionDTO dto = new SubmitAwardDeductionDTO();
        dto.setContent("打扫卫生后未清理垃圾桶");
        dto.setEmpNoList(Arrays.asList(employee.getId()));
        dto.setPicList(Arrays.asList("/lr-resource/xxx"));
        dto.setPoint(-10);
        dto.setSubmitter(admin.getId());
        dto.setTitle("测试测试测试");
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
