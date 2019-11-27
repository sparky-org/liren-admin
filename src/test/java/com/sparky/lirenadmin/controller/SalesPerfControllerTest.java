package com.sparky.lirenadmin.controller;


import com.alibaba.fastjson.JSONObject;
import com.sparky.lirenadmin.bo.BeautyShopBO;
import com.sparky.lirenadmin.bo.CustomerBO;
import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.constant.ApplyStatusEnum;
import com.sparky.lirenadmin.constant.ApplyTypeEnum;
import com.sparky.lirenadmin.controller.request.ApplySalesPerfDTO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.ListApplyVO;
import com.sparky.lirenadmin.controller.response.PagingResponseWrapper;
import com.sparky.lirenadmin.entity.BeautyShop;
import com.sparky.lirenadmin.entity.CustomerInfo;
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

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@SpringBootTest
@MapperScan("com.sparky.lirenadmin.mapper")
@RunWith(SpringRunner.class)
@Transactional
@Rollback(true)
public class SalesPerfControllerTest {

    @Autowired
    private SalesPerformanceController salesPerformanceController;
    @Autowired
    private MyApplyController myApplyController;
    @Autowired
    private CustomerBO customerBO;

    @Autowired
    private ShopEmployeeBO shopEmployeeBO;
    @Autowired
    private BeautyShopBO beautyShopBO;

    @Before
    public void before(){
        System.out.println("before====");
    }

    //case 1.美容师提交业绩，美容师撤回申请
    //case 2.美容师提交业绩，老板审批通过
    //case 3.美容师提交业绩，老板审批拒绝
    @Test
    public void testCase1() throws ParseException {
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

        //创建业绩申请
        ApplySalesPerfDTO applySalesPerfDTO = initApplySalesPerfDTO(employee,admin,cc, customerInfo);
        BaseResponseWrapper wrapper = salesPerformanceController.applySalesPerf(applySalesPerfDTO);
        Assert.isTrue(wrapper.isSuccess());

        //申请单
        PagingResponseWrapper<List<ListApplyVO>> wrapper1 = myApplyController.listApply(employee.getId(), ApplyTypeEnum.SAL_PERF.getCode(), ApplyStatusEnum.NEW.getCode(), null, null, 1, 10);
        Assert.isTrue(wrapper1.isSuccess());
        //撤销申请单
        Long salesPerfNo = ((List<ListApplyVO>)wrapper1.getResult()).get(0).getSalesPerformance().getSalesPerfNo();
        BaseResponseWrapper wrapper2 = salesPerformanceController.revertSalesPerf(salesPerfNo);
        Assert.isTrue(wrapper2.isSuccess());
        //查看业绩申请详情
        BaseResponseWrapper wrapper3 = salesPerformanceController.getSalesPerfApply(salesPerfNo);
        Assert.isTrue(wrapper3.isSuccess());
        System.out.println(JSONObject.toJSONString(wrapper3.getResult()));
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
        dto.setCompleteTime("2000-11-01");
        dto.setContent("测试业绩内容弄个");
        dto.setTitle("测试业绩申请标题");
        dto.setCustomerNo(info.getId());
        dto.setEmpNo(employee.getId());
        dto.setPoint(30);
        dto.setTargetAmount(new BigDecimal(1000));
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
