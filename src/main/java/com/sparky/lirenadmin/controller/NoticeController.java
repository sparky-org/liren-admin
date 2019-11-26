package com.sparky.lirenadmin.controller;

import com.sparky.lirenadmin.bo.ShopConfigBO;
import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.constant.ShopConfigTypeEnum;
import com.sparky.lirenadmin.controller.request.PublishNoticeDTO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.ViewPublishNoticeVO;
import com.sparky.lirenadmin.entity.ShopConfig;
import com.sparky.lirenadmin.entity.ShopEmployee;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 *  管理员可以发布公告，修改公告
 *  普通员工只可以查看公告
 *
 * @ClassName NoticeController
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/26
 * @Version v0.0.1
 */

@Api(tags = "公告接口")
@Controller
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private ShopConfigBO shopConfigBO;
    @Autowired
    private ShopEmployeeBO shopEmployeeBO;

    @ApiOperation("发布公告/修改公告")
    @RequestMapping(value = "/publishNotice",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper publishNotice(@RequestBody PublishNoticeDTO dto){
        try {
            shopConfigBO.createModifyConfig(buildConfig(dto));
            return BaseResponseWrapper.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    @ApiOperation("查看公告")
    @RequestMapping(value = "/viewNotice",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper<ViewPublishNoticeVO> viewNotice(@RequestParam @ApiParam Long shopNo,
                                                               @RequestParam @ApiParam Long empNo){
        try {
            ShopConfig config = shopConfigBO.getShopConfig(shopNo, ShopConfigTypeEnum.NOTICE.getCode());
            return BaseResponseWrapper.success(convertToDto(config, empNo));
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    private ViewPublishNoticeVO convertToDto(ShopConfig config, Long empNo) {
        ViewPublishNoticeVO dto = new ViewPublishNoticeVO();
        ShopEmployee employee = shopEmployeeBO.getEmployee(empNo);
        dto.setCanEdit(false);
        if (employee != null){
            dto.setCanEdit(employee.getIsAdmin());
        }
        dto.setNoticeNo(config.getId());
        dto.setContent(config.getContent());
        return dto;
    }

    private ShopConfig buildConfig(PublishNoticeDTO dto){
        ShopConfig config = new ShopConfig();
        config.setConfigName(ShopConfigTypeEnum.NOTICE.getDesc());
        config.setConfigType(ShopConfigTypeEnum.NOTICE.getCode());
        config.setContent(dto.getContent());
        config.setCreator(dto.getEmpNo());
        config.setId(dto.getNoticeNo());
        ShopEmployee employee = shopEmployeeBO.getEmployee(dto.getEmpNo());
        if (employee == null || !employee.getIsAdmin()){
            throw new RuntimeException(String.format("员工[%d]非管理员不能发布公告", dto.getEmpNo()));
        }
        config.setShopNo(employee.getShopNo());
        return config;
    }
}
