package com.sparky.lirenadmin.controller;


import com.alibaba.fastjson.JSONObject;
import com.sparky.lirenadmin.bo.BeautyShopBO;
import com.sparky.lirenadmin.constant.PointTypeEnum;
import com.sparky.lirenadmin.controller.request.CreatePointDTO;
import com.sparky.lirenadmin.controller.request.CreateShopEmployeeDTO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.LoginVO;
import com.sparky.lirenadmin.entity.BeautyShop;
import com.sparky.lirenadmin.entity.PointConfig;
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

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@SpringBootTest
@MapperScan("com.sparky.lirenadmin.mapper")
@RunWith(SpringRunner.class)
@Transactional
@Rollback(true)
public class PointConfigControllerTest {

    @Autowired
    private PointController pointController;
    @Autowired
    private BeautyShopBO beautyShopBO;
    @Autowired
    private EmployeeController employeeController;
    @Autowired
    private LoginController loginController;

    @Test
    public void testPointConfig() throws ParseException {
        //初始化店铺
        BeautyShop shop = initShop();
        beautyShopBO.createShop(shop);
        //初始化老板
        CreateShopEmployeeDTO boss = initBoss(shop);
        BaseResponseWrapper result = employeeController.createEmployee(boss);
        Assert.isTrue(result.isSuccess(), "createEmployee异常");
        //模拟老板登录
        BaseResponseWrapper<LoginVO> loginWrapper = loginController.login("13000001111","123456");
        Assert.isTrue(loginWrapper.isSuccess(), "Boss 登录异常");
        LoginVO loginVO = loginWrapper.getResult();

        //创建积分
        CreatePointDTO dto = initCreatePointDTO(loginVO.getShopEmployee());
        BaseResponseWrapper wrapper = pointController.createOrModifyPointConfig(dto);
        Assert.isTrue(wrapper.isSuccess());

        BaseResponseWrapper<List<PointConfig>> pointConfigs = pointController.getPointConfig(boss.getShopNo());
        Assert.isTrue(wrapper.isSuccess());
        System.out.println(JSONObject.toJSONString(pointConfigs.getResult()));
    }

    private CreatePointDTO initCreatePointDTO(ShopEmployee boss) {
        CreatePointDTO dto = new CreatePointDTO();
        dto.setContent("ddddd");
        dto.setEmpNo(boss.getId());
        dto.setPointType(PointTypeEnum.ATTENDANCE.getCode());
        dto.setValue(102);
        return dto;
    }

    private CreateShopEmployeeDTO initBoss(BeautyShop shop) {
        CreateShopEmployeeDTO dto = new CreateShopEmployeeDTO();
        dto.setAdmin(true);
        dto.setAge(33);
        dto.setBirthday(new Date());
        dto.setCreator(0l);
        dto.setJobNo(0l);
        dto.setManagerNo(0l);
        dto.setName("老板娘");
        dto.setPhone("13000001111");
        dto.setAdmin(true);
        dto.setShopNo(shop.getId());
        return dto;
    }

    private BeautyShop initShop() {
        BeautyShop shop = new BeautyShop();
        shop.setName("美容院X");
        shop.setJoinDate(new Date());
        return shop;
    }
}
