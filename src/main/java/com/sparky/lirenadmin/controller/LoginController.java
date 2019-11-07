package com.sparky.lirenadmin.controller;

import com.sparky.lirenadmin.annotations.NeedLogin;
import com.sparky.lirenadmin.utils.TokenManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(value = "登陆模块")
@Controller
@RequestMapping("/")
public class LoginController {

    @ApiOperation("login")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestParam @ApiParam String phone,
                        @RequestParam @ApiParam String password){
        return TokenManager.sign(phone, 1000 * 60 * 60 * 12);
    }

    @NeedLogin
    @ApiOperation("validate")
    @RequestMapping(value = "/validate",method = RequestMethod.POST)
    @ResponseBody
    public String testLogin(){
        return "success";
    }
}
