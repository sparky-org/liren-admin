//package com.sparky.lirenadmin.controller;
//
//import com.alibaba.fastjson.JSONArray;
//import com.sparky.lirenadmin.bo.BeautyShopBO;
//import com.sparky.lirenadmin.controller.request.CreateShopEmployeeDTO;
//import com.sparky.lirenadmin.controller.request.CreateShopJobDTO;
//import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
//import com.sparky.lirenadmin.controller.response.QueryEmpGroupJob;
//import com.sparky.lirenadmin.entity.BeautyShop;
//import com.sparky.lirenadmin.entity.ShopEmployee;
//import com.sparky.lirenadmin.entity.ShopJob;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.Assert;
//
//import java.util.Date;
//import java.util.List;
//
///**
// * @ClassName EmployeeControllerTest
// * @Description TODO
// * @Author 86186
// * @Date 2019/11/18
// * @Version v0.0.1
// */
//@SpringBootTest
//@MapperScan("com.sparky.lirenadmin.mapper")
//@RunWith(SpringRunner.class)
//@Transactional
//@Rollback(true)
//public class EmployeeControllerTest {
//
//    @Autowired
//    private EmployeeController employeeController;
//    @Autowired
//    private BeautyShopBO beautyShopBO;
//
//    public void testCreate(){
//        //初始化店铺
//        BeautyShop shop = initShop();
//        beautyShopBO.createShop(shop);
//        //初始化老板
//        CreateShopEmployeeDTO boss = initBoss(shop);
//        BaseResponseWrapper result = employeeController.createEmployee(boss);
//        Assert.isTrue(result.isSuccess(), "createEmployee异常");
//
//
//        CreateShopJobDTO manager = initManager(shop, boss);
//        result = employeeController.createShopJob(manager);
//        Assert.isTrue(result.isSuccess(), "createShopJob异常");
//
//        CreateShopJobDTO normalJob = initNormalEmp(shop, boss);
//        result = employeeController.createShopJob(normalJob);
//        Assert.isTrue(result.isSuccess(), "createShopJob异常");
//
//        CreateShopEmployeeDTO managerEmp = initManagerEmp(shop, boss, manager);
//        result = employeeController.createEmployee(managerEmp);
//        Assert.isTrue(result.isSuccess(), "createEmployee异常");
//
//        CreateShopEmployeeDTO normalEmp = initNormalEmployee(shop, boss, manager);
//        result = employeeController.createEmployee(normalEmp);
//        Assert.isTrue(result.isSuccess(), "createEmployee异常");
//
//        result = employeeController.queryEmpGroupByJob(shop.getId());
//        Assert.isTrue(result.isSuccess(), "分组查询异常");
//        System.out.println(JSONArray.toJSONString(result.getResult()));
//    }
//
//    private CreateShopEmployeeDTO initNormalEmployee(BeautyShop shop, ShopEmployee boss, ShopJob job) {
//        CreateShopEmployeeDTO dto = new CreateShopEmployeeDTO();
//        dto.setAdmin(false);
//        dto.setAge(33);
//        dto.setBirthday(new Date());
//        dto.setCreator(boss.getId());
//        dto.setJobNo(job.getId());
//        dto.setManagerNo(boss.getId());
//        dto.setName("张翠三");
//        dto.setPhone("13000001112");
//        return dto;
//    }
//
//    private CreateShopEmployeeDTO initManagerEmp(BeautyShop shop, ShopEmployee boss, ShopJob job) {
//        CreateShopEmployeeDTO dto = new CreateShopEmployeeDTO();
//        dto.setAdmin(false);
//        dto.setAge(33);
//        dto.setBirthday(new Date());
//        dto.setCreator(boss.getId());
//        dto.setJobNo(job.getId());
//        dto.setManagerNo(boss.getId());
//        dto.setName("张翠三");
//        dto.setPhone("13000001112");
//        return dto;
//    }
//
//    private CreateShopJobDTO initNormalEmp(BeautyShop shop, CreateShopEmployeeDTO boss) {
//        return null;
//    }
//
//    private CreateShopJobDTO initManager(BeautyShop shop, CreateShopEmployeeDTO boss) {
//        return null;
//    }
//
//    private CreateShopEmployeeDTO initBoss(BeautyShop shop) {
//        CreateShopEmployeeDTO dto = new CreateShopEmployeeDTO();
//        dto.setAdmin(true);
//        dto.setAge(33);
//        dto.setBirthday(new Date());
//        dto.setCreator(0l);
//        dto.setJobNo(0l);
//        dto.setManagerNo(0l);
//        dto.setName("老板娘");
//        dto.setPhone("13000001111");
//        return dto;
//    }
//
//    private BeautyShop initShop() {
//        BeautyShop shop = new BeautyShop();
//        shop.setName("美容院X");
//        shop.setJoinDate(new Date());
//        return shop;
//    }
//
//}
