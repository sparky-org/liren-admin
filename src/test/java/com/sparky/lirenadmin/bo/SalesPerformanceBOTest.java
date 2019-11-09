package com.sparky.lirenadmin.bo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sparky.lirenadmin.bo.cond.QueryTaskCond;
import com.sparky.lirenadmin.entity.*;
import com.sparky.lirenadmin.entity.po.MyTaskPO;
import com.sparky.lirenadmin.utils.PagingUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@MapperScan("com.sparky.lirenadmin.mapper")
@RunWith(SpringRunner.class)
@Transactional
@Rollback(true)
public class SalesPerformanceBOTest {


    @Autowired
    private SalesPerformanceBO salesPerformanceBO;
    @Autowired
    private ShopEmployeeBO shopEmployeeBO;
    @Autowired
    private BeautyShopBO beautyShopBO;
    @Autowired
    private TaskRecordBO taskRecordBO;
    @Autowired
    private ApplyBO applyBO;
    @Autowired
    private PointBO pointBO;

    @Test
    public void testCreateSalePerformance(){
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
        SalesPerformance performance = initPerformance(employee);
        salesPerformanceBO.createSalePerformance(performance);
//        QueryTaskCond cond = initQueryCond(employee);
//        int total = taskBO.countTask(cond);
//        int start = PagingUtils.getStartIndex(total, 1,1);
//        cond.setStart(start);
//        cond.setLength(1);
//        List<MyTaskPO> taskPOS = taskBO.queryTask(cond);
//        TaskRecord record = initTaskRecord(taskPOS.iterator().next());
//        taskRecordBO.createTaskRecord(record);
        List<Apply> approvalPending = applyBO.queryApprovalPendingTasks(admin.getId());
        for (Apply apply : approvalPending){
            applyBO.approve(apply);
        }
        //结果：申请单已审批通过，任务已经奖励,打印以上多有对象
        System.out.println(JSONArray.toJSONString(approvalPending));
//        record = taskRecordBO.getTaskRecord(record.getId());
//        System.out.println(JSONObject.toJSONString(record));
        Point point = pointBO.findEmployeePoint(employee.getId());
        System.out.println(JSONObject.toJSONString(point));
    }

    private SalesPerformance initPerformance(ShopEmployee employee) {
        SalesPerformance performance = new SalesPerformance();
        performance.setAmount(new BigDecimal(1000));
        performance.setCompleteTime(new Date());
        performance.setCreator(employee.getId());
        performance.setCustomerPhone("13011111110");
        performance.setEmpNo(employee.getId());
        performance.setRewardPoint(120);
        performance.setShopNo(employee.getShopNo());
        return performance;
    }


    private TaskRecord initTaskRecord(MyTaskPO next) {
        TaskRecord record = new TaskRecord();
        record.setTaskNo(next.getId());
        record.setCompleteCount(1);
        record.setCompleteTime(new Date());
        record.setEmpNo(next.getEmpNo());
        record.setRewardPoint(10);
        record.setShopNo(next.getShopNo());
        return record;
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

    private QueryTaskCond initQueryCond(ShopEmployee employee) {
        QueryTaskCond cond = new QueryTaskCond();
        cond.setEmpNo(employee.getId());
        return cond;
    }

    private List<TaskDtl> initTaskDtls() {
        List<TaskDtl> dtls = new ArrayList<>();
        TaskDtl dtl = new TaskDtl();
        dtl.setCreator(1L);
        dtl.setEmpNo(1L);
        dtl.setShopNo(1L);
        return dtls;
    }

    private Task initTask() {
        Task task = new Task();
        task.setTitle("测试任务");
        task.setContent("测试任务");
        task.setCreator(1L);
        task.setJoinLimit(0);
        task.setPointNo(1l);
        task.setScope("ALL");
        task.setShopNo(1l);
        return task;
    }
}
