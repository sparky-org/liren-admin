package com.sparky.lirenadmin.controller;


import com.alibaba.fastjson.JSONObject;
import com.sparky.lirenadmin.bo.BeautyShopBO;
import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.controller.request.ModifyTaskDTO;
import com.sparky.lirenadmin.controller.request.PublishTaskDTO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.MyTaskVO;
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

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootTest
@MapperScan("com.sparky.lirenadmin.mapper")
@RunWith(SpringRunner.class)
@Transactional
@Rollback(false)
public class TaskControllerTest {

    @Autowired
    private TaskManageController taskManageController;
    @Autowired
    private ShopEmployeeBO shopEmployeeBO;
    @Autowired
    private BeautyShopBO beautyShopBO;

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
        PublishTaskDTO dto = initPublishTaskDTO(admin);
        BaseResponseWrapper result = taskManageController.publishTask(dto);
        Assert.isTrue(result.isSuccess());

        //2. 查询任务
        PagingResponseWrapper query = taskManageController.queryTask("2019-12-02","2029-12-04", admin.getJobNo(), employee.getId(), 1,1);
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

    private PublishTaskDTO initPublishTaskDTO(ShopEmployee admin) {
        PublishTaskDTO taskDTO = new PublishTaskDTO();
        taskDTO.setTaskTitle("测试任务1");
        taskDTO.setTaskDesc("测试任务内容1");
        taskDTO.setEmpNo(admin.getId());
        taskDTO.setPointConfigNo(0l);
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
