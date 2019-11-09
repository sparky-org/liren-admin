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
@RunWith(SpringRunner.class)
@MapperScan("com.sparky.lirenadmin.mapper")
@Transactional
@Rollback(true)
public class VacationBOTest {
    @Autowired
    private VacationApplyBO vacationApplyBO;
    @Autowired
    private ShopEmployeeBO shopEmployeeBO;
    @Autowired
    private BeautyShopBO beautyShopBO;
    @Autowired
    private ApplyBO applyBO;
    @Autowired
    private PointBO pointBO;

    @Test
    public void testTaskReward(){
        //1. 初始化管理员
        BeautyShop shop = initShop();
        beautyShopBO.createShop(shop);
        ShopEmployee admin = initEmployee();
        admin.setShopNo(shop.getId());
        shopEmployeeBO.createEmployee(admin);
        //初始化一名普通员工
        ShopEmployee employee = initEmployee();
        employee.setName("美容师");
        employee.setPhone("13000000001");
        employee.setShopNo(shop.getId());
        employee.setManagerNo(admin.getId());
        employee.setIsAdmin(false);
        shopEmployeeBO.createEmployee(employee);
        VacationApply record = initVacationApply(employee);
        vacationApplyBO.createVacationApply(record);
        List<Apply> approvalPending = applyBO.queryApprovalPendingTasks(admin.getId());
        for (Apply apply : approvalPending){
            applyBO.approve(apply);
        }
        //结果：申请单已审批通过，任务已经奖励,打印以上多有对象
        System.out.println(JSONArray.toJSONString(approvalPending));
        record = vacationApplyBO.getVacation(record.getId());
        System.out.println(JSONObject.toJSONString(record));
        Point point = pointBO.findEmployeePoint(employee.getId());
        System.out.println(JSONObject.toJSONString(point));
    }

    private VacationApply initVacationApply(ShopEmployee emp) {
        VacationApply apply = new VacationApply();
        apply.setApplyEmpNo(emp.getId());
        apply.setAuditEmpNo(emp.getManagerNo());
        apply.setBeginDate(new Date());
        apply.setEndDate(new Date());
        apply.setReason("没有理由的请假");
        apply.setPicList("['picUrl']");
        apply.setShopNo(emp.getShopNo());
        return apply;
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
