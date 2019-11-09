package com.sparky.lirenadmin.bo;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sparky.lirenadmin.entity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@SpringBootTest
@MapperScan("com.sparky.lirenadmin.mapper")
@RunWith(SpringRunner.class)
@Transactional
@Rollback(false)
public class AppointmentBOTest {


    @Autowired
    private AppointmentBO appointmentBO;
    @Autowired
    private ShopEmployeeBO shopEmployeeBO;
    @Autowired
    private BeautyShopBO beautyShopBO;
    @Autowired
    private TaskRecordBO taskRecordBO;
    @Autowired
    private ApplyBO applyBO;
    @Autowired
    private PointBO pointBO;

    @Test
    public void testCreateAppointment(){
        //1. 初始化管理员
//        BeautyShop shop = initShop();
//        beautyShopBO.createShop(shop);
//        ShopEmployee admin = initEmployee();
//        admin.setShopNo(shop.getId());
//        shopEmployeeBO.createEmployee(admin);
//        //初始化一名普通员工
//        ShopEmployee employee = initEmployee();
//        employee.setName("美容师");
//        employee.setPhone("13000000001");
//        employee.setShopNo(shop.getId());
//        employee.setManagerNo(admin.getId());
//        employee.setIsAdmin(false);
//        shopEmployeeBO.createEmployee(employee);
        //****
        ShopEmployee employee = shopEmployeeBO.getEmployee(6l);
        ShopEmployee admin = shopEmployeeBO.getEmployee(5l);
        //****
        Appointment performance = initAppointment(employee);
        appointmentBO.createAppointment(performance);
        List<Apply> approvalPending = applyBO.queryApprovalPendingTasks(admin.getId());
        for (Apply apply : approvalPending){
            applyBO.approve(apply);
        }
        //结果：申请单已审批通过，任务已经奖励,打印以上多有对象
        System.out.println(JSONArray.toJSONString(approvalPending));
        Point point = pointBO.findEmployeePoint(employee.getId());
        System.out.println(JSONObject.toJSONString(point));
    }

    private Appointment initAppointment(ShopEmployee employee) {
        Appointment performance = new Appointment();
        performance.setAppointEmpNo(employee.getId());
        performance.setCustomerPhone("13011111110");
        performance.setServiceNo(0l);
        performance.setAppointTime(new Date());
        performance.setRewardPoint(120);
        performance.setServiceEmpNo(employee.getId());
        performance.setCreator(employee.getId());
        performance.setShopNo(employee.getShopNo());
        return performance;
    }

    private BeautyShop initShop() {
        BeautyShop shop = new BeautyShop();
        shop.setName("美容院X");
        shop.setJoinDate(new Date());
        return shop;
    }

    private ShopEmployee initEmployee() {
        ShopEmployee admin = new ShopEmployee();
        admin.setIsAdmin(true);
        admin.setJobNo(1l);
        admin.setShopNo(1l);
        admin.setPassword("1111");
        admin.setName("张三");
        admin.setManagerNo(1l);
        admin.setPhone("13011111111");
        return admin;
    }
}
