package com.sparky.lirenadmin.controller;

import com.alibaba.fastjson.JSONObject;
import com.sparky.lirenadmin.bo.EmployeeBO;
import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.bo.ShopJobBO;
import com.sparky.lirenadmin.controller.request.CreateOrModifyShopEmployeeDTO;
import com.sparky.lirenadmin.controller.request.CreateShopEmployeeDTO;
import com.sparky.lirenadmin.controller.request.CreateShopJobDTO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.QueryEmpGroupJob;
import com.sparky.lirenadmin.entity.ShopEmployee;
import com.sparky.lirenadmin.entity.ShopJob;
import com.sparky.lirenadmin.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sparky.lirenadmin.utils.Md5Utils.md5;

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
    @RequestMapping(value = "/getEmployeeInfo", method = RequestMethod.POST)
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
    @RequestMapping(value = "/modifyEmployee",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper modifyEmployee(@RequestBody CreateOrModifyShopEmployeeDTO dto){
        try {
            if (dto.getId() == null){
                return BaseResponseWrapper.fail(null, "待修改用户编号为空");
            }
            ShopEmployee employee = convertFrom(dto);
            shopEmployeeBO.modify(employee);
            return BaseResponseWrapper.success(null);
        } catch (Exception e) {
            logger.error("修改个人资料异常。", e);
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    @ApiOperation("修改密码")
    @RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
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
            if (!newPassword.equals(confirmNewPassword)){
                return BaseResponseWrapper.fail(null, "新密码不一致");
            }
            String password = employee.getPassword();
            if (!password.equals(md5(oldPassword))){
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
    @RequestMapping(value = "/queryEmpOfShop", method = RequestMethod.POST)
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
    @RequestMapping(value = "/createShopJob", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper createShopJob(@RequestBody CreateShopJobDTO dto){
        try {
            shopJobBO.createShopJob(buildShopJob(dto));
            return BaseResponseWrapper.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    @ApiOperation("查询岗位")
    @RequestMapping(value = "/getShopJob", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper<List<ShopJob>> getShopJob(Long shopNo){
        try {
            List<ShopJob> jobs = shopJobBO.getShopJobByShop(shopNo);
            return BaseResponseWrapper.success(jobs);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    @ApiOperation("删除岗位")
    @RequestMapping(value = "/deleteJob", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper deleteJob(@RequestParam @ApiParam Long shopNo,
                                         @RequestParam @ApiParam Long jobNo,
                                         @RequestParam @ApiParam Long empNo){
        try {
            ShopJob job = shopJobBO.getShopJob(jobNo);
            if (job == null){
                return BaseResponseWrapper.success(null);
            }
            if (shopNo.longValue() != job.getShopNo()){
                throw new RuntimeException("店铺无此职业");
            }
            ShopEmployee employee = shopEmployeeBO.getEmployee(empNo);
            if (employee == null){
                throw new RuntimeException("无此管理者");
            }
            if (employee.getShopNo().longValue() != shopNo){
                throw new RuntimeException("无此管理者");
            }
            shopJobBO.deleteJob(jobNo);
            return BaseResponseWrapper.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }


    @ApiOperation("创建员工")
    @RequestMapping(value = "/createEmployee", method = RequestMethod.POST)
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
    @RequestMapping(value = "/deleteEmployee", method = RequestMethod.POST)
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
    @RequestMapping(value = "/queryEmpGroupByJob",method = RequestMethod.POST)
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
                if(job != null && job.getIsValid()){
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

    private ShopEmployee convertFrom(CreateOrModifyShopEmployeeDTO dto) {
        ShopEmployee employee = JSONObject.parseObject(JSONObject.toJSONString(dto), ShopEmployee.class);
        if (dto.getBirthday() != null) {
            employee.setBirthday(DateUtils.getDateTime(dto.getBirthday()));
        }
        return employee;
    }

    private ShopEmployee buildShopEmployee(CreateShopEmployeeDTO dto) {
        ShopEmployee employee = new ShopEmployee();
        employee.setPassword(md5("123456"));
        employee.setBirthday(DateUtils.getDate(dto.getBirthday()));
        employee.setIsAdmin(dto.getAdmin());
        employee.setShopNo(dto.getShopNo());
        employee.setPhone(dto.getPhone());
        employee.setName(dto.getName());
        employee.setManagerNo(dto.getManagerNo());
        employee.setJobNo(dto.getJobNo());
        employee.setAge(dto.getAge());
        employee.setCreator(dto.getCreator());
        employee.setSex(dto.getSex());
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
}
