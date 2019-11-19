package com.sparky.lirenadmin.controller;


import com.alibaba.fastjson.JSONObject;
import com.sparky.lirenadmin.bo.BeautyShopBO;
import com.sparky.lirenadmin.bo.CustomerBO;
import com.sparky.lirenadmin.bo.ServiceItemBO;
import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.constant.ApplyStatusEnum;
import com.sparky.lirenadmin.constant.ApplyTypeEnum;
import com.sparky.lirenadmin.controller.request.ApplySalesPerfDTO;
import com.sparky.lirenadmin.controller.request.ApplyServiceRecordDTO;
import com.sparky.lirenadmin.controller.request.NewNormalApplyDTO;
import com.sparky.lirenadmin.controller.request.NewVacationApplyDTO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.ListApplyVO;
import com.sparky.lirenadmin.controller.response.PagingResponseWrapper;
import com.sparky.lirenadmin.entity.BeautyShop;
import com.sparky.lirenadmin.entity.CustomerInfo;
import com.sparky.lirenadmin.entity.ServiceItem;
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
import java.util.Date;
import java.util.List;

@SpringBootTest
@MapperScan("com.sparky.lirenadmin.mapper")
@RunWith(SpringRunner.class)
@Transactional
@Rollback(true)
public class ServiceRecordControllerTest {

    @Autowired
    private ServiceItemRecordController serviceItemRecordController;
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

    @Before
    public void before(){
        System.out.println("before====");
    }

    //case 1.美容师提交项目，美容师撤回申请
    //case 2.美容师提交项目，老板审批通过
    //case 3.美容师提交项目，老板审批拒绝
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

        //初始化一个项目
        ServiceItem serviceItem = initServiceItem(admin);
        serviceItemBO.createUpdateServiceItem(serviceItem);

        //创建业绩申请
        ApplyServiceRecordDTO applySalesPerfDTO = initApplyServiceRecordDTO(employee,admin,cc, customerInfo, serviceItem);
        BaseResponseWrapper wrapper = serviceItemRecordController.applyItem(applySalesPerfDTO);
        Assert.isTrue(wrapper.isSuccess());

        //申请单
        PagingResponseWrapper<List<ListApplyVO>> wrapper1 = myApplyController.listApply(employee.getId(), ApplyTypeEnum.SERVICE_ITEM.getCode(), ApplyStatusEnum.NEW.getCode(), null, null, 1, 10);
        Assert.isTrue(wrapper1.isSuccess());
        //撤销申请单
        Long salesPerfNo = ((List<ListApplyVO>)wrapper1.getResult()).get(0).getServiceItem().getServiceRecordNo();
        BaseResponseWrapper wrapper2 = serviceItemRecordController.revertServiceRecord(salesPerfNo);
        Assert.isTrue(wrapper2.isSuccess());
        //查看业绩申请详情
        BaseResponseWrapper wrapper3 = serviceItemRecordController.getSalesPerfApply(salesPerfNo);
        Assert.isTrue(wrapper3.isSuccess());
        System.out.println(JSONObject.toJSONString(wrapper3.getResult()));
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
