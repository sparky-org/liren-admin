package com.sparky.lirenadmin.controller;

import com.sparky.lirenadmin.bo.EmployeeBO;
import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.entity.ShopEmployee;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

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
