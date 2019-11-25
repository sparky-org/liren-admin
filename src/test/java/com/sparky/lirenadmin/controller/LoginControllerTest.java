package com.sparky.lirenadmin.controller;


import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.internal.org.apache.commons.lang3.time.DateUtils;
import com.sparky.lirenadmin.bo.BeautyShopBO;
import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.constant.ApplyStatusEnum;
import com.sparky.lirenadmin.constant.ApplyTypeEnum;
import com.sparky.lirenadmin.controller.request.NewNormalApplyDTO;
import com.sparky.lirenadmin.controller.request.NewVacationApplyDTO;
import com.sparky.lirenadmin.controller.response.*;
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

import java.text.ParseException;
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

    private NewVacationApplyDTO initNewVacationApplyDTO(ShopEmployee employee, ShopEmployee admin) {
        NewVacationApplyDTO dto = new NewVacationApplyDTO();
        dto.setApplyEmpNo(employee.getId());
        dto.setAttachemntPicList("");
        dto.setAuditEmpNo(admin.getId());
        dto.setBegin(new Date());
        dto.setEnd(new Date());
        dto.setCcList("");
        dto.setReason("请假请假请假");
        return dto;
    }

    private NewNormalApplyDTO initNewNormalApplyDTO(ShopEmployee employee, ShopEmployee admin) {
        NewNormalApplyDTO dto = new NewNormalApplyDTO();
        dto.setEmpNo(employee.getId());
        dto.setAuditEmpNo(admin.getId());
        dto.setAttachmentPicList("");
        dto.setCcEmpList("");
        dto.setContent("测试测试测试");
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
        admin.setPassword(Md5Utils.md5("123456"));
        admin.setName("张三");
        admin.setManagerNo(0l);
        admin.setPhone("13011111111");
        return admin;
    }
}
