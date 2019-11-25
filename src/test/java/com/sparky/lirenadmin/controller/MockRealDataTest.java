package com.sparky.lirenadmin.controller;

import com.alibaba.fastjson.JSONObject;
import com.sparky.lirenadmin.bo.BeautyShopBO;
import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.bo.ShopJobBO;
import com.sparky.lirenadmin.constant.PointTypeEnum;
import com.sparky.lirenadmin.entity.*;
import com.sparky.lirenadmin.utils.Md5Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName MockRealDataTest
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/20
 * @Version v0.0.1
 */
@SpringBootTest
@MapperScan("com.sparky.lirenadmin.mapper")
@RunWith(SpringRunner.class)
@Transactional
@Rollback(false)
public class MockRealDataTest {

    @Autowired
    private MyApplyController myApplyController;
    @Autowired
    private ShopEmployeeBO shopEmployeeBO;
    @Autowired
    private BeautyShopBO beautyShopBO;
    @Autowired
    private ShopJobBO shopJobBO;

    private Integer seq = 1;

    private Integer getNext(){
        return seq ++;
    }

    @Test
    public void test(){
        //1. 创建店铺，指定不超过10个
        String[] shopNames = {"克丽缇女子美容会所","俪人美妆","女人花美容会所","都市丽人美容会所","聚人美妆化妆品公司"};
        List<BeautyShop> shops = buildShop(shopNames);
        if (shops == null){
            System.out.println("测试数据已经就绪.");
            return;
        }
        for(BeautyShop shop : shops) {
            //2. 创建一个超级管理员
            ShopEmployee admin = createAdmin(shop);

            //3. 每个角色创建至少创建一个员工
            List<ShopEmployee> master = createShopManager(admin);
            List<ShopEmployee> salesConsultant = createConsultant(master.get(0));
            String[] names = {"谭新梅","许竹君","徐莉"};
            List<ShopEmployee> employees1 = createEmployee(salesConsultant.get(0),salesConsultant.get(0), names);
            String[] names1 = {"张兵","王帅","孙佳"};
            List<ShopEmployee> employees2 = createEmployee(salesConsultant.get(1),salesConsultant.get(1), names1);

            //4. 创建积分，每个积分类型一个积分实例
            List<PointConfig> configs = createPointConfigs(admin);

            //5. 创建三个任务
            List<Task> tasks = createTaskConfigs(admin);

            //6. 创建海报

            //6. 创建公告

            //6. 创建公司制度
        }
    }

    private List<Task> createTaskConfigs(ShopEmployee admin) {
        return null;
    }

    private List<PointConfig> createPointConfigs(ShopEmployee admin) {
        List<PointConfig> pointConfigs = new ArrayList<>();
        PointConfig point = new PointConfig();
        point.setShopNo(admin.getShopNo());
        point.setCreator(admin.getId());
        point.setPointType(PointTypeEnum.ACTION.getCode());
        point.setPointName("行为积分");
        point.setPoint(10);
        point.setPointDesc("满足以下条件者可以获取行为积分。<br /> 1. 工作按时完成。<br /> 2. 完成多少个业绩");
        pointConfigs.add(point);
        PointConfig config2 = JSONObject.parseObject(JSONObject.toJSONString(point), PointConfig.class);
        config2.setPointType(PointTypeEnum.CHARACTER.getCode());
        config2.setPointName("品德积分");
        pointConfigs.add(config2);

        PointConfig config3 = JSONObject.parseObject(JSONObject.toJSONString(point), PointConfig.class);
        config3.setPointType(PointTypeEnum.CHARACTER.getCode());
        config3.setPointName("品德积分");
        pointConfigs.add(config3);

        return pointConfigs;
    }

    private List<ShopEmployee> createConsultant(ShopEmployee admin) {
        List<ShopEmployee> employees = new ArrayList<>();
        String[] names = {"方竹兵","曹丹","胡燕群","谭新梅","许竹君","","","",""};
        ShopJob guwen = initJob("顾问", admin);
        shopJobBO.createShopJob(guwen);
        ShopEmployee guwenEmp = buildEmp(admin, guwen);
        guwenEmp.setName(names[0]);
        shopEmployeeBO.createEmployee(guwenEmp);
        employees.add(guwenEmp);

        ShopEmployee guwenEmp1 = buildEmp(admin, guwen);
        guwenEmp1.setName(names[1]);
        shopEmployeeBO.createEmployee(guwenEmp1);
        employees.add(guwenEmp1);
        return employees;
    }

    private List<ShopEmployee> createShopManager(ShopEmployee admin) {
        List<ShopEmployee> employees = new ArrayList<>();
        String[] names = {"方竹兵","曹丹","胡燕群","谭新梅","许竹君","","","",""};

        ShopJob dianzhang = initJob("店长", admin);
        shopJobBO.createShopJob(dianzhang);
        ShopEmployee dianzhangEmp = buildEmp(admin, dianzhang);
        dianzhangEmp.setName(names[0]);
        shopEmployeeBO.createEmployee(dianzhangEmp);
        employees.add(dianzhangEmp);
        return employees;
    }

    private List<ShopEmployee> createEmployee(ShopEmployee admin, ShopEmployee salesConsultant, String[] names) {
        List<ShopEmployee> employees = new ArrayList<>();
        ShopJob meirongshi = initJob("美容师", admin);
        shopJobBO.createShopJob(meirongshi);
        ShopEmployee meirongshi1 = buildEmp(salesConsultant, meirongshi);
        meirongshi1.setName(names[0]);
        shopEmployeeBO.createEmployee(meirongshi1);
        employees.add(meirongshi1);

        ShopEmployee meirongshi2 = buildEmp(salesConsultant, meirongshi);
        meirongshi2.setName(names[1]);
        shopEmployeeBO.createEmployee(meirongshi2);
        employees.add(meirongshi2);

        ShopEmployee meirongshi3 = buildEmp(salesConsultant, meirongshi);
        meirongshi3.setName(names[2]);
        shopEmployeeBO.createEmployee(meirongshi3);
        employees.add(meirongshi3);
        return employees;
    }

    private ShopEmployee buildEmp(ShopEmployee admin, ShopJob job) {
        ShopEmployee emp = new ShopEmployee();
        emp.setIsAdmin(false);
        emp.setJobNo(job.getId());
        emp.setShopNo(admin.getShopNo());
        emp.setPassword(Md5Utils.md5("123456"));
        emp.setName("张三");
        emp.setManagerNo(0l);
        emp.setPhone("130111111" + (getNext() > 9 ? getNext().toString() : "0"+getNext().toString()));
        return emp;
    }

    private List<ShopJob> createJob(ShopEmployee admin) {
        List<ShopJob> jobs = new ArrayList<>();
        ShopJob managerJob = initJob("店长", admin);
        ShopJob guwenJob = initJob("顾问", admin);
        ShopJob empJob = initJob("员工", admin);
        jobs.add(managerJob);
        jobs.add(guwenJob);
        jobs.add(empJob);
        return jobs;
    }

    private ShopJob initJob(String level, ShopEmployee admin) {
        ShopJob dto = new ShopJob();
        dto.setCreator(admin.getId());
        dto.setName(level);
        dto.setShopNo(admin.getShopNo());
        return dto;
    }

    private ShopEmployee createAdmin(BeautyShop shop) {
        ShopEmployee admin = new ShopEmployee();
        admin.setIsAdmin(true);
        admin.setJobNo(0l);
        admin.setShopNo(shop.getId());
        admin.setPassword(Md5Utils.md5("123456"));
        admin.setName("张三");
        admin.setManagerNo(0l);
        admin.setPhone("130111111" + (getNext() > 9 ? getNext().toString() : "0"+getNext().toString()));
        return admin;
    }

    private List<BeautyShop> buildShop(String[] names) {
        //先查询，如果已经存在则不流程创建
        List<BeautyShop> shopExist = beautyShopBO.getAllShop();
        if (shopExist != null && shopExist.size() > 0){
            List<String> shopNameList = shopExist.stream().map(BeautyShop::getName).collect(Collectors.toList());
            if (shopNameList.contains(names[0])){
                return null;
            }
        }
        List<BeautyShop> shops = new ArrayList<>();
        for (String name : names){
            BeautyShop shop = initShop(name);
            beautyShopBO.createShop(shop);
            shops.add(shop);
        }
        return shops;
    }

    private BeautyShop initShop(String name) {
        BeautyShop shop = new BeautyShop();
        shop.setName(name);
        shop.setJoinDate(new Date());
        return shop;
    }

}
