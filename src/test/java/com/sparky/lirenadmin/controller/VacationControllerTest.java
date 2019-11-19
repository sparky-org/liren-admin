package com.sparky.lirenadmin.controller;

import com.sparky.lirenadmin.bo.BeautyShopBO;
import com.sparky.lirenadmin.bo.CustomerBO;
import com.sparky.lirenadmin.bo.ServiceItemBO;
import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.controller.request.ApplySalesPerfDTO;
import com.sparky.lirenadmin.controller.request.ApplyServiceRecordDTO;
import com.sparky.lirenadmin.controller.request.NewNormalApplyDTO;
import com.sparky.lirenadmin.controller.request.NewVacationApplyDTO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.entity.BeautyShop;
import com.sparky.lirenadmin.entity.CustomerInfo;
import com.sparky.lirenadmin.entity.ServiceItem;
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

    private ServiceItem initServiceItem(ShopEmployee admin) {
        ServiceItem serviceItem = new ServiceItem();
        serviceItem.setRewardPoint(200);
        serviceItem.setShopNo(admin.getShopNo());
        serviceItem.setCreator(admin.getId());
        serviceItem.setItemName("肾保健");
        serviceItem.setItemDesc("项目描述，暂时没有");
        serviceItem.setDuration(120);
        return serviceItem;
    }

    private ApplyServiceRecordDTO initApplyServiceRecordDTO(ShopEmployee employee, ShopEmployee admin, ShopEmployee cc, CustomerInfo info, ServiceItem serviceItem) {
        ApplyServiceRecordDTO dto = new ApplyServiceRecordDTO();
        dto.setAuditor(admin.getId());
        dto.setCcEmpList(cc.getId().toString());
        dto.setCompleteTime(new Date());
        dto.setContent("测试业绩内容弄个");
        dto.setTitle("测试业绩申请标题");
        dto.setCustomerNo(info.getId());
        dto.setEmpNo(employee.getId());
        dto.setPoint(30);
        dto.setServiceItemNo(serviceItem.getId());
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

    private ApplySalesPerfDTO initApplySalesPerfDTO(ShopEmployee employee, ShopEmployee admin, ShopEmployee cc, CustomerInfo info) {
        ApplySalesPerfDTO dto = new ApplySalesPerfDTO();
        dto.setAuditor(admin.getId());
        dto.setCcEmpList(cc.getId().toString());
        dto.setCompleteTime(new Date());
        dto.setContent("测试业绩内容弄个");
        dto.setTitle("测试业绩申请标题");
        dto.setCustomerNo(info.getId());
        dto.setEmpNo(employee.getId());
        dto.setPoint(30);
        dto.setTargetAmount(new BigDecimal(1000));
        return dto;
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
        admin.setPassword("1111");
        admin.setName("张三");
        admin.setManagerNo(0l);
        admin.setPhone("13011111111");
        return admin;
    }
}
