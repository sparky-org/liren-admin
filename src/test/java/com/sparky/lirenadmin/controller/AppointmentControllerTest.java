package com.sparky.lirenadmin.controller;


import com.alibaba.fastjson.JSONObject;
import com.sparky.lirenadmin.bo.*;
import com.sparky.lirenadmin.controller.request.CreateAppointmentDTO;
import com.sparky.lirenadmin.controller.response.AppointmentInfo;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.PagingResponseWrapper;
import com.sparky.lirenadmin.entity.BeautyShop;
import com.sparky.lirenadmin.entity.CustomerInfo;
import com.sparky.lirenadmin.entity.ServiceItem;
import com.sparky.lirenadmin.entity.ShopEmployee;
import com.sparky.lirenadmin.entity.po.PointRankPO;
import org.apache.http.client.utils.DateUtils;
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
public class AppointmentControllerTest {

    @Autowired
    private MyAppointmentController myAppointmentController;
    @Autowired
    private RewardRecordBO rewardRecordBO;
    @Autowired
    private ShopEmployeeBO shopEmployeeBO;
    @Autowired
    private BeautyShopBO beautyShopBO;
    @Autowired
    private CustomerBO customerBO;
    @Autowired
    private ServiceItemBO serviceItemBO;

    @Before
    public void before(){
        System.out.println("before====");
    }

    @Test
    public void testCreateAppointment() throws ParseException {
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

        CustomerInfo customerInfo = initCustomer(employee);
        customerBO.createCustomer(customerInfo);

        ServiceItem serviceItem = initServiceItem(admin);
        serviceItemBO.createUpdateServiceItem(serviceItem);

        CreateAppointmentDTO dto = initCreateAppointmentDTO(employee,customerInfo, serviceItem);
        BaseResponseWrapper result = myAppointmentController.createAppointment(dto);
        Assert.isTrue(result.isSuccess(), "创建失败");


        //普通员工查询
        BaseResponseWrapper<List<Date>> results1 = myAppointmentController.queryAppointmentDays(employee.getId(), DateUtils.formatDate(new Date(), "yyyy-MM"));
        Assert.isTrue(results1.isSuccess(), "查询失败");

        PagingResponseWrapper<List<AppointmentInfo>> results2 = myAppointmentController.queryAppointment(employee.getId(), DateUtils.formatDate(new Date(), "yyyy-MM-dd"), 1,10);
        Assert.isTrue(results2.isSuccess(), "查询失败");
        List<PointRankPO> result3 = rewardRecordBO.findPointRank(employee.getId(), new Date());
        System.out.println(JSONObject.toJSONString(result3));
//
    }

    private CustomerInfo initCustomer(ShopEmployee employee) {
        CustomerInfo info = new CustomerInfo();
        info.setName("王灵儿");
        info.setSex("FEMALE");
        info.setPhone("13611111110");
        info.setShopNo(employee.getShopNo());
        info.setCreator(employee.getId());
        return info;
    }

    private CreateAppointmentDTO initCreateAppointmentDTO(ShopEmployee employee, CustomerInfo customer, ServiceItem serviceItem) {
        CreateAppointmentDTO dto = new CreateAppointmentDTO();
        dto.setAppointDate("2000-11-01");
        dto.setCustomerPhone(customer.getPhone());
        dto.setEmpNo(employee.getId());
        dto.setServiceItemNo(serviceItem.getId());
        dto.setRemark("VIP客户，做好服务");
        dto.setOperator(employee.getId());
        return dto;
    }

    private ServiceItem initServiceItem(ShopEmployee admin){
        ServiceItem item = new ServiceItem();
        item.setShopNo(admin.getShopNo());
        item.setCreator(admin.getId());
        item.setDuration(60);
        item.setItemName("脸部护理");
        item.setItemDesc("护理手法要求：护理时间要求：");
        item.setRewardPoint(30);
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
