package com.sparky.lirenadmin.controller;

import com.alibaba.fastjson.JSONObject;
import com.sparky.lirenadmin.bo.BeautyShopBO;
import com.sparky.lirenadmin.bo.CustomerBO;
import com.sparky.lirenadmin.bo.ServiceItemBO;
import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.constant.AutoRewardConfigEnum;
import com.sparky.lirenadmin.controller.request.PublishNoticeDTO;
import com.sparky.lirenadmin.controller.request.PublishRewardConfigDTO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.ViewPublishNoticeVO;
import com.sparky.lirenadmin.controller.response.ViewPublishRewardConfigVO;
import com.sparky.lirenadmin.entity.BeautyShop;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@MapperScan("com.sparky.lirenadmin.mapper")
@RunWith(SpringRunner.class)
@Transactional
@Rollback(true)
public class ShopConfigSubControllerTest {


    @Autowired
    private NoticeController noticeController;
    @Autowired
    private RewardConfigController rewardConfigController;
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

        //创建公告，修改公告，查看公告
        BaseResponseWrapper wrapper = noticeController.publishNotice(initPublishNotice(employee));
        Assert.isTrue(!wrapper.isSuccess());
        wrapper = noticeController.publishNotice(initPublishNotice(admin));
        Assert.isTrue(wrapper.isSuccess());

        wrapper = noticeController.viewNotice(shop.getId(), employee.getId());
        Assert.isTrue(wrapper.isSuccess());
        Assert.isTrue(!((ViewPublishNoticeVO)wrapper.getResult()).getCanEdit());

        wrapper = noticeController.viewNotice(shop.getId(), admin.getId());
        Assert.isTrue(wrapper.isSuccess());
        Assert.isTrue(((ViewPublishNoticeVO)wrapper.getResult()).getCanEdit());
    }

    @Test
    public void testCase2() {
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

        //创建公告，修改公告，查看公告
        BaseResponseWrapper wrapper = rewardConfigController.publishRewardConfig(initRewardConfig(employee));
        Assert.isTrue(!wrapper.isSuccess());
        wrapper = rewardConfigController.publishRewardConfig(initRewardConfig(admin));
        Assert.isTrue(wrapper.isSuccess());

        wrapper = rewardConfigController.viewRewardConfig(shop.getId(), employee.getId());
        Assert.isTrue(wrapper.isSuccess());
        Assert.isTrue(!((ViewPublishRewardConfigVO)wrapper.getResult()).getCanEdit());

        wrapper = rewardConfigController.viewRewardConfig(shop.getId(), admin.getId());
        Assert.isTrue(wrapper.isSuccess());
        Assert.isTrue(((ViewPublishRewardConfigVO)wrapper.getResult()).getCanEdit());
        System.out.println(JSONObject.toJSONString(wrapper.getResult()));
    }

    private PublishRewardConfigDTO initRewardConfig(ShopEmployee employee) {
        PublishRewardConfigDTO dto = new PublishRewardConfigDTO();
        dto.setEmpNo(employee.getId());
        List<PublishRewardConfigDTO.RewardConfig> configs = new ArrayList<>();
        PublishRewardConfigDTO.RewardConfig config = new PublishRewardConfigDTO.RewardConfig();
        config.setType(AutoRewardConfigEnum.ATTENDANCE.getCode());
        config.setRewardPoint(10);
        configs.add(config);
        PublishRewardConfigDTO.RewardConfig config2 = new PublishRewardConfigDTO.RewardConfig();
        config2.setType(AutoRewardConfigEnum.DAILY_RECORD.getCode());
        config2.setRewardPoint(20);
        configs.add(config2);
        dto.setContent(configs);
        return dto;
    }

    private PublishNoticeDTO initPublishNotice(ShopEmployee employee) {
        PublishNoticeDTO dto = new PublishNoticeDTO();
        dto.setEmpNo(employee.getId());
        dto.setContent("公告测试，公告测试，公告测试，公告测试，公告测试，公告测试，公告测试，公告测试");
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
