package com.sparky.lirenadmin.controller;


import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.internal.org.apache.commons.lang3.time.DateUtils;
import com.sparky.lirenadmin.bo.*;
import com.sparky.lirenadmin.bo.cond.IncreasePointDO;
import com.sparky.lirenadmin.constant.ApplyStatusEnum;
import com.sparky.lirenadmin.constant.ApplyTypeEnum;
import com.sparky.lirenadmin.controller.request.ApplySalesPerfDTO;
import com.sparky.lirenadmin.controller.request.GetPointTableDTO;
import com.sparky.lirenadmin.controller.request.NewNormalApplyDTO;
import com.sparky.lirenadmin.controller.request.NewVacationApplyDTO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.CCTomeVO;
import com.sparky.lirenadmin.controller.response.ListApplyVO;
import com.sparky.lirenadmin.controller.response.PagingResponseWrapper;
import com.sparky.lirenadmin.entity.Apply;
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
public class PointControllerTest {

    @Autowired
    private MyApplyController myApplyController;
    @Autowired
    private ApplyBO applyBO;
    @Autowired
    private ShopEmployeeBO shopEmployeeBO;
    @Autowired
    private BeautyShopBO beautyShopBO;
    @Autowired
    private PointController pointController;
    @Autowired
    private PointBO pointBO;

    @Autowired
    private SalesPerformanceController salesPerformanceController;
    @Autowired
    private CustomerBO customerBO;

    @Before
    public void before(){
        System.out.println("before====");
    }

    @Test
    public void testGetPointTable() throws ParseException {
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

        //初始化一个顾客
        CustomerInfo customerInfo = initCustomer(employee);
        customerBO.createCustomer(customerInfo);

        //创建业绩申请
        ApplySalesPerfDTO applySalesPerfDTO = initApplySalesPerfDTO(employee,admin,cc, customerInfo);
        BaseResponseWrapper wrapper = salesPerformanceController.applySalesPerf(applySalesPerfDTO);
        Assert.isTrue(wrapper.isSuccess());

        //审批，奖励积分
        PagingResponseWrapper<List<ListApplyVO>> result3 = myApplyController.myApprovalPending(admin.getId(), ApplyTypeEnum.SAL_PERF.getCode(),
                ApplyStatusEnum.NEW.getCode(), DateUtils.parseDate("2019-11-01", "yyyy-MM-dd"),
                DateUtils.parseDate("2019-12-30", "yyyy-MM-dd"),
                1, 10);
        Assert.isTrue(result3.isSuccess(), "查询待我审批异常。");
        List<ListApplyVO> pending = (List<ListApplyVO>)result3.getResult();
        //审批通过
        BaseResponseWrapper r1 = myApplyController.approve(pending.get(0).getApplyNo(),true, admin.getId());
        Assert.isTrue(r1.isSuccess(), "审批失败");

        GetPointTableDTO dto = new GetPointTableDTO();
        dto.setCurrentPage(1);
        dto.setPageSize(10);
        dto.setShopNo(shop.getId());
        dto.setInterval("YEAR");
        PagingResponseWrapper result = pointController.getPointTable(dto);
        System.out.println(JSONObject.toJSONString(result));
    }

    @Test
    public void testGetPointDetail() throws ParseException {
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

        //初始化一个顾客
        CustomerInfo customerInfo = initCustomer(employee);
        customerBO.createCustomer(customerInfo);

        //创建业绩申请
        ApplySalesPerfDTO applySalesPerfDTO = initApplySalesPerfDTO(employee,admin,cc, customerInfo);
        BaseResponseWrapper wrapper = salesPerformanceController.applySalesPerf(applySalesPerfDTO);
        Assert.isTrue(wrapper.isSuccess());

        //审批，奖励积分
        PagingResponseWrapper<List<ListApplyVO>> result3 = myApplyController.myApprovalPending(admin.getId(), ApplyTypeEnum.SAL_PERF.getCode(),
                ApplyStatusEnum.NEW.getCode(), DateUtils.parseDate("2019-11-01", "yyyy-MM-dd"),
                DateUtils.parseDate("2019-12-30", "yyyy-MM-dd"),
                1, 10);
        Assert.isTrue(result3.isSuccess(), "查询待我审批异常。");
        List<ListApplyVO> pending = (List<ListApplyVO>)result3.getResult();
        //审批通过
        BaseResponseWrapper r1 = myApplyController.approve(pending.get(0).getApplyNo(),true, admin.getId());
        Assert.isTrue(r1.isSuccess(), "审批失败");

        GetPointTableDTO dto = new GetPointTableDTO();
        dto.setEmpNo(employee.getId());
        dto.setCurrentPage(1);
        dto.setPageSize(10);
        dto.setShopNo(shop.getId());
        dto.setInterval("YEAR");
        PagingResponseWrapper result = pointController.getPointRewardDetail(dto);
        System.out.println(JSONObject.toJSONString(result));
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

    private CustomerInfo initCustomer(ShopEmployee employee) {
        CustomerInfo info = new CustomerInfo();
        info.setName("张莹莹");
        info.setSex("FEMALE");
        info.setPhone("13611111110");
        info.setShopNo(employee.getShopNo());
        info.setCreator(employee.getId());
        return info;
    }
    private Apply initApply(ShopEmployee employee, ShopEmployee admin) {
        Apply apply = applyBO.buildApply(ApplyTypeEnum.NORMAL.getCode(), 1L, "测试图片测试图片", employee.getId(), employee.getId(), employee.getShopNo());

        apply.setPicList("ccccc,ccsdfqe,cfere");
        return apply;
    }

    private NewVacationApplyDTO initNewVacationApplyDTO(ShopEmployee employee, ShopEmployee admin) {
        NewVacationApplyDTO dto = new NewVacationApplyDTO();
        dto.setApplyEmpNo(employee.getId());
        dto.setAttachmentPicList("");
        dto.setAuditEmpNo(admin.getId());
        dto.setBegin("2019-11-01");
        dto.setEnd("2019-11-01");
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
