package com.sparky.lirenadmin.controller;

import com.alibaba.fastjson.JSONObject;
import com.sparky.lirenadmin.bo.BeautyShopBO;
import com.sparky.lirenadmin.bo.CustomerBO;
import com.sparky.lirenadmin.bo.ServiceItemBO;
import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.controller.request.SetAttendanceDTO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.entity.AttendanceConfig;
import com.sparky.lirenadmin.entity.BeautyShop;
import com.sparky.lirenadmin.entity.CustomerInfo;
import com.sparky.lirenadmin.entity.ShopEmployee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootTest
@MapperScan("com.sparky.lirenadmin.mapper")
@RunWith(SpringRunner.class)
@Transactional
@Rollback(true)
public class VacationControllerTest {


    @Autowired
    private VacationController vacationController;
    @Autowired
    private PointController pointController;
    @Autowired
    private MyApplyController myApplyController;
    @Autowired
    private ServiceItemBO serviceItemBO;
    @Autowired
    private CustomerBO customerBO;

    @Autowired
    private ShopEmployeeBO shopEmployeeBO;
    @Autowired
    private BeautyShopBO beautyShopBO;


    @Test
    public void testCase1() {
        BeautyShop shop = initShop();
        beautyShopBO.createShop(shop);
        ShopEmployee admin = initEmployee(shop);
        admin.setShopNo(shop.getId());
        shopEmployeeBO.createEmployee(admin);
        //初始化一名普通员工
        ShopEmployee employee = initEmployee(shop);
        employee.setName("美容师1");
        employee.setPhone("13000000001");
        employee.setShopNo(shop.getId());
        employee.setManagerNo(admin.getId());
        employee.setIsAdmin(false);
        shopEmployeeBO.createEmployee(employee);
        //初始化一名普通员工
        ShopEmployee cc = initEmployee(shop);
        cc.setName("美容师2");
        cc.setPhone("13000000002");
        cc.setShopNo(shop.getId());
        cc.setManagerNo(admin.getId());
        cc.setIsAdmin(false);
        shopEmployeeBO.createEmployee(cc);

        //初始化一个顾客
        CustomerInfo customerInfo = initCustomer(employee);
        customerBO.createCustomer(customerInfo);

        //打卡
        BaseResponseWrapper wrapper = vacationController.clockIn(employee.getId(), 0.1,0.1, false);
        Assert.isTrue(wrapper.isSuccess());
        wrapper = vacationController.clockIn(employee.getId(), 0.1,0.1, true);
        Assert.isTrue(wrapper.isSuccess());

    }

    @Test
    public void testAddConfig(){
        BeautyShop shop = initShop();
        beautyShopBO.createShop(shop);
        ShopEmployee admin = initEmployee(shop);
        admin.setShopNo(shop.getId());
        shopEmployeeBO.createEmployee(admin);
        SetAttendanceDTO dto = initSetAttendanceDTO(shop, admin);
        BaseResponseWrapper result = vacationController.setAttendance(dto);
        System.out.println(JSONObject.toJSONString(result));
    }

    private SetAttendanceDTO initSetAttendanceDTO(BeautyShop shop, ShopEmployee admin) {
        SetAttendanceDTO dto = new SetAttendanceDTO();
        dto.setAddress("浙江省余杭区梦想小镇");
        dto.setEndWorkTime("09:00");
        dto.setLatitude(new BigDecimal(20.000012));
        dto.setLongitude(new BigDecimal(1231.000923));
        dto.setOperator(admin.getId());
        dto.setRewardPoint(100);
        dto.setScopeRadio(100);
        dto.setShopNo(shop.getId());
        dto.setStartWorkTime("09:00");
        dto.setWorkDay("星期一，星期二，星期三，星期四，星期五");
        return dto;
    }

    private CustomerInfo initCustomer(ShopEmployee employee) {
        CustomerInfo info = new CustomerInfo();
        info.setName("张莹莹");
        info.setSex("FEMALE");
        info.setPhone("13611111110");
        info.setShopNo(employee.getShopNo());
        info.setCreator(employee.getId());
        return info;
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
