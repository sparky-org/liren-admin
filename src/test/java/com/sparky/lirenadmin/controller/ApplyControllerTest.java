package com.sparky.lirenadmin.controller;


import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.internal.org.apache.commons.lang3.time.DateUtils;
import com.sparky.lirenadmin.bo.BeautyShopBO;
import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.constant.ApplyStatusEnum;
import com.sparky.lirenadmin.constant.ApplyTypeEnum;
import com.sparky.lirenadmin.controller.request.NewNormalApplyDTO;
import com.sparky.lirenadmin.controller.request.NewVacationApplyDTO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.CCTomeVO;
import com.sparky.lirenadmin.controller.response.ListApplyVO;
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
import java.util.Date;
import java.util.List;

@SpringBootTest
@MapperScan("com.sparky.lirenadmin.mapper")
@RunWith(SpringRunner.class)
@Transactional
@Rollback(true)
public class ApplyControllerTest {

    @Autowired
    private MyApplyController myApplyController;
    @Autowired
    private ShopEmployeeBO shopEmployeeBO;
    @Autowired
    private BeautyShopBO beautyShopBO;

    @Before
    public void before(){
        System.out.println("before====");
    }

    @Test
    public void testApplyFlow() throws ParseException {
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

        NewNormalApplyDTO newNormalApplyDTO = initNewNormalApplyDTO(employee,admin);
        newNormalApplyDTO.setCcEmpList("" + cc.getId());
        myApplyController.newNormalApply(newNormalApplyDTO);
        newNormalApplyDTO.setCcEmpList("" + cc.getId());
        myApplyController.newNormalApply(newNormalApplyDTO);
        NewVacationApplyDTO newVacationApplyDTO = initNewVacationApplyDTO(employee,admin);
        myApplyController.newVacationApply(newVacationApplyDTO);

        //查询我的申请
        PagingResponseWrapper<List<ListApplyVO>> result = myApplyController.listApply(employee.getId(), ApplyTypeEnum.VACATION.getCode(),
                ApplyStatusEnum.NEW.getCode(), DateUtils.parseDate("2019-11-01", "yyyy-MM-dd"),
                DateUtils.parseDate("2019-11-30", "yyyy-MM-dd"),
                1, 10);
        Assert.isTrue(result.getTotal() == 1, "新建申请失败");
        //查询抄送我的
        //撤回
        Long applyNo = ((List<ListApplyVO>)result.getResult()).get(0).getApplyNo();
        BaseResponseWrapper result2 = myApplyController.revertApply(applyNo, employee.getId());
        Assert.isTrue(result2.isSuccess(), "撤回失败");
        //查询待我审批
        PagingResponseWrapper<List<ListApplyVO>> result3 = myApplyController.myApprovalPending(admin.getId(), ApplyTypeEnum.NORMAL.getCode(),
                ApplyStatusEnum.NEW.getCode(), DateUtils.parseDate("2019-11-01", "yyyy-MM-dd"),
                DateUtils.parseDate("2019-11-30", "yyyy-MM-dd"),
                1, 10);
        Assert.isTrue(result3.isSuccess(), "查询待我审批异常。");
        Assert.isTrue(result3.getTotal() >= 2, "查询待我审批异常, 少于2个。");
        List<ListApplyVO> pending = (List<ListApplyVO>)result3.getResult();
        //审批通过
        BaseResponseWrapper r1 = myApplyController.approve(pending.get(0).getApplyNo(),true, admin.getId());
        Assert.isTrue(r1.isSuccess(), "审批失败");
        //拒绝
        BaseResponseWrapper r2 = myApplyController.approve(pending.get(1).getApplyNo(),false, admin.getId());
        Assert.isTrue(r2.isSuccess(), "审批失败");


        result = myApplyController.listApply(employee.getId(),null,
                null, null,null,
                1, 10);
        System.out.println(JSONObject.toJSONString(result));

        PagingResponseWrapper<List<CCTomeVO>> ccResult = myApplyController.ccToMe(cc.getId(),1,10);
        Assert.isTrue(ccResult.isSuccess(), "query cc to me failed");
        Assert.isTrue(((List<CCTomeVO>)ccResult.getResult()).size() == 1, "query cc to me failed");
    }

    private NewVacationApplyDTO initNewVacationApplyDTO(ShopEmployee employee, ShopEmployee admin) {
        NewVacationApplyDTO dto = new NewVacationApplyDTO();
        dto.setApplyEmpNo(employee.getId());
        dto.setAttachmentPicList("");
        dto.setAuditEmpNo(admin.getId());
        dto.setBegin("2000-11-01");
        dto.setEnd("2000-11-01");
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
