package com.sparky.lirenadmin.controller;

import com.sparky.lirenadmin.bo.EmployeeBO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.LoginVO;
import com.sparky.lirenadmin.entity.ShopEmployee;
import com.sparky.lirenadmin.utils.TokenManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(tags = "登陆模块")
@Controller
@RequestMapping("/")
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private EmployeeBO employeeBO;

    @ApiOperation("login")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper<LoginVO> login(@RequestParam @ApiParam String phone,
                                             @RequestParam @ApiParam String password){
        try {
            ShopEmployee employee = employeeBO.getEmployeeByPrimaryPhone(phone);
            if (null == employee){
                throw new RuntimeException("用户不存在");
            }
            String md5Pwd = DigestUtils.md5DigestAsHex(password.getBytes());
            if (!employee.getPassword().equals(md5Pwd)){
                throw new RuntimeException("密码错误");
            }
            String token = TokenManager.sign(employee, 1000 * 60 * 60 * 12);
            LoginVO loginVO = new LoginVO();
            loginVO.setToken(token);
            loginVO.setShopEmployee(employee);
            return BaseResponseWrapper.success(loginVO);
        } catch (Exception e) {
            logger.error("登陆未知异常", e);
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }
}
