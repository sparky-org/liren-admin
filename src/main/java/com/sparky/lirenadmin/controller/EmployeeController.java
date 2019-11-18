package com.sparky.lirenadmin.controller;

import com.alibaba.fastjson.JSONObject;
import com.sparky.lirenadmin.bo.EmployeeBO;
import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.bo.ShopJobBO;
import com.sparky.lirenadmin.controller.request.CreateShopEmployeeDTO;
import com.sparky.lirenadmin.controller.request.CreateShopJobDTO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.QueryEmpGroupJob;
import com.sparky.lirenadmin.entity.ShopEmployee;
import com.sparky.lirenadmin.entity.ShopJob;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName UserCenter
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/11
 * @Version v0.0.1
 */
@Api(tags = "个人中心")
@Controller
@RequestMapping("/employee")
public class EmployeeController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private ShopEmployeeBO shopEmployeeBO;
    @Autowired
    private EmployeeBO employeeBO;
    @Autowired
    private ShopJobBO shopJobBO;

    @ApiOperation("个人资料")
    @RequestMapping("/getEmployeeInfo")
    @ResponseBody
    public BaseResponseWrapper<ShopEmployee> getEmployeeInfo(@RequestParam @ApiParam Long empNo){
        try {
            ShopEmployee employee = shopEmployeeBO.getEmployee(empNo);
            return BaseResponseWrapper.success(employee);
        } catch (Exception e) {
            logger.error("查询个人资料异常。", e);
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    @ApiOperation("修改资料")
    @RequestMapping("/modifyEmployee")
    @ResponseBody
    public BaseResponseWrapper modifyEmployee(@RequestParam @ApiParam ShopEmployee shopEmployee){
        try {
            if (shopEmployee.getId() == null){
                return BaseResponseWrapper.fail(null, "待修改用户编号为空");
            }
            shopEmployeeBO.modify(shopEmployee);
            return BaseResponseWrapper.success(null);
        } catch (Exception e) {
            logger.error("修改个人资料异常。", e);
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    @ApiOperation("修改密码")
    @RequestMapping("/modifyPassword")
    @ResponseBody
    public BaseResponseWrapper modifyPassword(@RequestParam @ApiParam Long empNo,
                                              @RequestParam @ApiParam String newPassword,
                                              @RequestParam @ApiParam String confirmNewPassword,
                                              @RequestParam @ApiParam String oldPassword){
        try {
            ShopEmployee employee = shopEmployeeBO.getEmployee(empNo);
            if (employee == null){
                return BaseResponseWrapper.fail(null, "待修改用户不存在");
            }
            if (newPassword.equals(confirmNewPassword)){
                return BaseResponseWrapper.fail(null, "新密码不一致");
            }
            String password = employee.getPassword();
            if (password.equals(md5(oldPassword))){
                return BaseResponseWrapper.fail(null, "旧密码错误");
            }
            employee.setPassword(md5(newPassword));
            shopEmployeeBO.modify(employee);
            return BaseResponseWrapper.success(null);
        } catch (Exception e) {
            logger.error("修改个人资料异常。", e);
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    /**
     * 查询本店所有员工
     * @param shopNo
     * @return
     */
    @ApiOperation("查询本店所有员工")
    @RequestMapping("/queryEmpOfShop")
    @ResponseBody
    public BaseResponseWrapper<List<ShopEmployee>> queryEmpOfShop(@RequestParam @ApiParam Long shopNo){
        try {
            List<ShopEmployee> employees = employeeBO.getEmployeeByShopNo(shopNo);
            return BaseResponseWrapper.success(employees);
        } catch (Exception e) {
            logger.error("查询本店所有员工异常！",e);
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    @ApiOperation("创建岗位")
    @RequestMapping("/createShopJob")
    @ResponseBody
    public BaseResponseWrapper createShopJob(CreateShopJobDTO dto){
        try {
            shopJobBO.createShopJob(buildShopJob(dto));
            return BaseResponseWrapper.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    @ApiOperation("查询岗位")
    @RequestMapping("/getShopJob")
    @ResponseBody
    public BaseResponseWrapper getShopJob(Long shopNo){
        try {
            List<ShopJob> jobs = shopJobBO.getShopJobByShop(shopNo);
            return BaseResponseWrapper.success(jobs);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }


    @ApiOperation("创建员工")
    @RequestMapping("/createEmployee")
    @ResponseBody
    public BaseResponseWrapper createEmployee(@RequestBody CreateShopEmployeeDTO dto){
        try {
            shopEmployeeBO.createEmployee(buildShopEmployee(dto));
            return BaseResponseWrapper.success(null);
        } catch (Exception e) {
            logger.error("修改个人资料异常。", e);
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    @ApiOperation("删除员工")
    @RequestMapping("/deleteEmployee")
    @ResponseBody
    public BaseResponseWrapper deleteEmployee(@RequestParam @ApiParam Long empNo,
                                              @RequestParam @ApiParam Long operator) {
        try {
            ShopEmployee admin = shopEmployeeBO.getEmployee(operator);
            if (admin == null || !admin.getIsAdmin()){
                throw new RuntimeException("只有管理员才可以删除员工");
            }
            if (empNo.longValue() == operator){
                throw new RuntimeException("管理员不能删除自己");
            }
            ShopEmployee employee = new ShopEmployee();
            employee.setId(empNo);
            employee.setIsValid(false);
            shopEmployeeBO.modify(employee);
            return BaseResponseWrapper.success(null);
        } catch (Exception e) {
            logger.error("删除员工异常。", e);
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    /**
     * 按岗位分组查询本店所有员工
     * @param shopNo
     * @return
     */
    @ApiOperation("按岗位分组查询本店所有员工")
    @RequestMapping("/queryEmpGroupByJob")
    @ResponseBody
    public BaseResponseWrapper<List<QueryEmpGroupJob>> queryEmpGroupByJob(@RequestParam @ApiParam Long shopNo){
        try {
            List<ShopEmployee> employees = employeeBO.getEmployeeByShopNo(shopNo);
            if (CollectionUtils.isEmpty(employees)){
                return BaseResponseWrapper.success(new ArrayList<>());
            }
            Map<Long, List<ShopEmployee>> empMap= employees.stream().collect(Collectors.groupingBy(ShopEmployee::getJobNo));
            List<QueryEmpGroupJob> list = new ArrayList<>();
            for (Map.Entry<Long, List<ShopEmployee>> entry : empMap.entrySet()){
                ShopJob job = shopJobBO.getShopJob(entry.getKey());
                if(job != null){
                    QueryEmpGroupJob groupJob = new QueryEmpGroupJob();
                    groupJob.setJobNo(job.getId());
                    groupJob.setJobName(job.getName());
                    List<QueryEmpGroupJob.EmpInfo> empInfos = entry.getValue().stream().map(e -> {
                        QueryEmpGroupJob.EmpInfo empInfo = new QueryEmpGroupJob.EmpInfo();
                        empInfo.setEmpNo(e.getId());
                        empInfo.setEmpName(e.getName());
                        empInfo.setPhone(e.getPhone());
                        return empInfo;
                    }).collect(Collectors.toList());
                    groupJob.setEmpInfos(empInfos);
                    list.add(groupJob);
                }
            }
            return BaseResponseWrapper.success(list);
        } catch (Exception e) {
            logger.error("按岗位分组查询本店所有员工异常！",e);
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    private ShopEmployee buildShopEmployee(CreateShopEmployeeDTO dto) {
        ShopEmployee employee = JSONObject.parseObject(JSONObject.toJSONString(dto), ShopEmployee.class);
        return employee;
    }

    private ShopJob buildShopJob(CreateShopJobDTO dto) {
        ShopJob job = new ShopJob();
        job.setName(dto.getJobName());
        job.setCreator(dto.getEmpNo());
        ShopEmployee employee = shopEmployeeBO.getEmployee(dto.getEmpNo());
        if (employee == null){
            throw new RuntimeException("创建人员不存在");
        }
        if (!employee.getIsAdmin()){
            throw new RuntimeException("只有管理员才可以创建岗位");
        }
        job.setShopNo(employee.getShopNo());
        return job;
    }

    private String md5(String password){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(password.getBytes());
            String md5Pwd = new BigInteger(messageDigest.digest()).toString(16);
            if (password.equals(md5Pwd)){
                throw new RuntimeException("新旧密码不一致");
            }
            return md5Pwd;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("修改密码失败");
        }
    }
}
