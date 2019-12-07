package com.sparky.lirenadmin.controller;

import com.sparky.lirenadmin.bo.BeautyShopBO;
import com.sparky.lirenadmin.bo.EmployeeBO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.ListMyShopVO;
import com.sparky.lirenadmin.controller.response.LoginVO;
import com.sparky.lirenadmin.entity.BeautyShop;
import com.sparky.lirenadmin.entity.ShopEmployee;
import com.sparky.lirenadmin.utils.Md5Utils;
import com.sparky.lirenadmin.utils.TokenManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "登陆模块")
@Controller
@RequestMapping("/")
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private EmployeeBO employeeBO;
    @Autowired
    private BeautyShopBO beautyShopBO;

    @ApiOperation("login")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper<LoginVO> login(@RequestParam("phone") @ApiParam String phone,
                                              @RequestParam("shopNo") @ApiParam Long shopNo,
                                             @RequestParam("password") @ApiParam String password){
        try {
            ShopEmployee employee = employeeBO.getEmployeeByPrimaryPhone(phone, shopNo);
            if (null == employee){
                throw new RuntimeException("用户不存在");
            }
            String md5Pwd = Md5Utils.md5(password);
            if (!employee.getPassword().equals(md5Pwd)){
                throw new RuntimeException("密码错误");
            }
            String token = TokenManager.sign(employee, 1000 * 60 * 60 * 12);
            LoginVO loginVO = new LoginVO();
            loginVO.setToken(token);
            loginVO.setShopEmployee(employee);

            BeautyShop shop = beautyShopBO.getShop(employee.getShopNo());
            loginVO.setShopName(shop.getName());
            loginVO.setShopType(shop.getShopType());
            return BaseResponseWrapper.success(loginVO);
        } catch (Exception e) {
            logger.error("登陆未知异常", e);
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    //查询该手机号等级的店铺
    @ApiOperation("listMyShop")
    @RequestMapping(value = "/listMyShop", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper<List<ListMyShopVO>> listMyShop(String phone){
        try {
            List<ShopEmployee> employeeList = employeeBO.getEmployeeByPhone(phone);
            if (CollectionUtils.isEmpty(employeeList)){
                return BaseResponseWrapper.success(new ArrayList<>());
            }
            List<Long> shopNoList = employeeList.stream().map(ShopEmployee::getShopNo).collect(Collectors.toList());
            List<BeautyShop> shops = beautyShopBO.getShopByIdList(shopNoList);
            if (CollectionUtils.isEmpty(shops)){
                return BaseResponseWrapper.success(new ArrayList<>());
            }
            List<ListMyShopVO> vos = shops.stream().map(e -> {
                ListMyShopVO vo = new ListMyShopVO();
                vo.setShopNo(e.getId());
                vo.setShopName(e.getName());
                return vo;
            }).collect(Collectors.toList());
            return BaseResponseWrapper.success(vos);
        } catch (Exception e) {
            logger.error("获取我的店铺异常", e);
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }
}
