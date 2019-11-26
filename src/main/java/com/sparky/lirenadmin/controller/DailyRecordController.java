package com.sparky.lirenadmin.controller;

import com.sparky.lirenadmin.bo.ShopConfigBO;
import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.constant.ShopConfigTypeEnum;
import com.sparky.lirenadmin.controller.request.CreateDailyRecordDTO;
import com.sparky.lirenadmin.controller.request.PublishCompanySystemDTO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.ViewCompanySystemVO;
import com.sparky.lirenadmin.entity.ShopConfig;
import com.sparky.lirenadmin.entity.ShopEmployee;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 *  员工日志
 *
 * @ClassName NoticeController
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/26
 * @Version v0.0.1
 */

@Api(tags = "员工日志接口")
@Controller
@RequestMapping("/daily/record")
public class DailyRecordController {

    @Autowired
    private ShopConfigBO shopConfigBO;
    @Autowired
    private ShopEmployeeBO shopEmployeeBO;

    @ApiOperation("添加日志")
    @RequestMapping(value = "/createRecord",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper createRecord(@RequestBody CreateDailyRecordDTO dto){
        try {
            shopConfigBO.createModifyConfig(buildConfig(dto));
            return BaseResponseWrapper.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    @ApiOperation("查看公司制度")
    @RequestMapping(value = "/viewSystem",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper<ViewCompanySystemVO> viewNotice(@RequestParam @ApiParam Long shopNo,
                                                               @RequestParam @ApiParam Long empNo){
        try {
            ShopConfig config = shopConfigBO.getShopConfig(shopNo, ShopConfigTypeEnum.SYSTEM.getCode());
            return BaseResponseWrapper.success(convertToDto(config, empNo));
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    private ViewCompanySystemVO convertToDto(ShopConfig config,Long empNo) {
        ViewCompanySystemVO dto = new ViewCompanySystemVO();
        ShopEmployee employee = shopEmployeeBO.getEmployee(empNo);
        dto.setCanEdit(false);
        if (employee != null){
            dto.setCanEdit(employee.getIsAdmin());
        }
        dto.setCompanySystemNo(config.getId());
        dto.setContent(config.getContent());
        return dto;
    }

    private ShopConfig buildConfig(PublishCompanySystemDTO dto){
        ShopConfig config = new ShopConfig();
        config.setConfigName(ShopConfigTypeEnum.SYSTEM.getDesc());
        config.setConfigType(ShopConfigTypeEnum.SYSTEM.getCode());
        config.setContent(dto.getContent());
        config.setCreator(dto.getEmpNo());
        config.setId(dto.getNoticeNo());
        ShopEmployee employee = shopEmployeeBO.getEmployee(dto.getEmpNo());
        if (employee == null || !employee.getIsAdmin()){
            throw new RuntimeException(String.format("员工[%d]非管理员不能发布公司制度", dto.getEmpNo()));
        }
        config.setShopNo(employee.getShopNo());
        return config;
    }
}
