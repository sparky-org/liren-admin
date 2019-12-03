package com.sparky.lirenadmin.controller;


import com.alibaba.fastjson.JSONObject;
import com.sparky.lirenadmin.bo.BeautyShopBO;
import com.sparky.lirenadmin.bo.PointConfigBO;
import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.constant.PointTypeEnum;
import com.sparky.lirenadmin.controller.request.ModifyTaskDTO;
import com.sparky.lirenadmin.controller.request.PublishTaskDTO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.MyTaskVO;
import com.sparky.lirenadmin.controller.response.PagingResponseWrapper;
import com.sparky.lirenadmin.entity.BeautyShop;
import com.sparky.lirenadmin.entity.PointConfig;
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

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootTest
@MapperScan("com.sparky.lirenadmin.mapper")
@RunWith(SpringRunner.class)
@Transactional
@Rollback(true)
public class TaskControllerTest {

    @Autowired
    private TaskManageController taskManageController;
    @Autowired
    private MyTaskController myTaskController;
    @Autowired
    private ShopEmployeeBO shopEmployeeBO;
    @Autowired
    private BeautyShopBO beautyShopBO;
    @Autowired
    private PointConfigBO pointConfigBO;

    @Before
    public void before(){
        System.out.println("before====");
    }

    @Test
    public void testPublishTask() {
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

        //1. 发布任务
        PointConfig pointConfig = initPointConfig(admin);
        pointConfigBO.createPointConfig(pointConfig);
        PublishTaskDTO dto = initPublishTaskDTO(admin, pointConfig);
        BaseResponseWrapper result = taskManageController.publishTask(dto);
        Assert.isTrue(result.isSuccess());

        //2. 查询任务
        PagingResponseWrapper query = taskManageController.queryTask("2019-12-02","2029-12-04", admin.getJobNo(), admin.getId(), 1,1);
        Assert.isTrue(query.isSuccess());
        List<MyTaskVO> res = (List<MyTaskVO>) query.getResult();
        Assert.isTrue(res.size() > 0);

        Long taskNo = res.get(0).getTaskNo();
        //查看任务
        result = taskManageController.viewTask(taskNo, employee.getId());
        Assert.isTrue(result.isSuccess());
        System.out.println(JSONObject.toJSONString(result));

        ModifyTaskDTO modifyTaskDTO = initModifyTaskDTO(admin, taskNo);
        result = taskManageController.modifyTask(modifyTaskDTO);
        Assert.isTrue(result.isSuccess());

        //3. 删除任务
        result = taskManageController.deleteTask(taskNo, admin.getId());
        Assert.isTrue(result.isSuccess());
    }

    @Test
    public void testMyTask(){
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

        //1. 发布任务面向全体的
        PointConfig pointConfig = initPointConfig(admin);
        pointConfigBO.createPointConfig(pointConfig);
        PublishTaskDTO dto = initPublishTaskDTO(admin, pointConfig);
        dto.setSelectAll(true);
        BaseResponseWrapper result = taskManageController.publishTask(dto);
        Assert.isTrue(result.isSuccess());

        //查询带我完成的任务
        PagingResponseWrapper<List<MyTaskVO>> rs = myTaskController.queryMyTask(PointTypeEnum.ACTION.getCode(), null, employee.getId(), 1,1);
        Assert.isTrue(rs.isSuccess());
        System.out.println(JSONObject.toJSONString(rs));
        List<MyTaskVO> myTaskVOS = (List<MyTaskVO>)rs.getResult();
        result = myTaskController.completeTask(myTaskVOS.get(0).getTaskNo(), employee.getId());
        Assert.isTrue(result.isSuccess());
        System.out.printf(JSONObject.toJSONString(result));

    }

    private PointConfig initPointConfig(ShopEmployee admin) {
        PointConfig config = new PointConfig();
        config.setPointDesc(PointTypeEnum.CHARACTER.getDesc());
        config.setPoint(102);
        config.setPointName("品的积分");
        config.setPointType(PointTypeEnum.CHARACTER.getCode());
        config.setCreator(admin.getId());
        config.setShopNo(admin.getShopNo());
        return config;
    }

    private PublishTaskDTO initPublishTaskDTO(ShopEmployee admin, PointConfig config) {
        PublishTaskDTO taskDTO = new PublishTaskDTO();
        taskDTO.setTaskTitle("测试任务1");
        taskDTO.setTaskDesc("测试任务内容1");
        taskDTO.setEmpNo(admin.getId());
        taskDTO.setPointConfigNo(config.getId());
        taskDTO.setRewardPoint(10);
        taskDTO.setSelectAll(false);
        return taskDTO;
    }

    private ModifyTaskDTO initModifyTaskDTO(ShopEmployee admin, Long taskNo) {
        ModifyTaskDTO taskDTO = new ModifyTaskDTO();
        taskDTO.setTaskNo(taskNo);
        taskDTO.setTaskTitle("测试任务（修改后）");
        taskDTO.setTaskDesc("测试任务内容（修改后）");
        taskDTO.setEmpNo(admin.getId());
        taskDTO.setPointConfigNo(0l);
        taskDTO.setEmpList(Arrays.asList(admin.getId()));
        taskDTO.setSelectAll(false);
        return taskDTO;
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
