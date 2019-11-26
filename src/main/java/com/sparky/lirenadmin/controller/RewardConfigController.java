package com.sparky.lirenadmin.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sparky.lirenadmin.bo.ShopConfigBO;
import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.constant.ShopConfigTypeEnum;
import com.sparky.lirenadmin.controller.request.PublishPosterDTO;
import com.sparky.lirenadmin.controller.request.PublishRewardConfigDTO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.ViewPosterVO;
import com.sparky.lirenadmin.controller.response.ViewPublishRewardConfigVO;
import com.sparky.lirenadmin.entity.ShopConfig;
import com.sparky.lirenadmin.entity.ShopEmployee;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 *  自动奖励配置，目前支持写日志奖励和打卡奖励
 *
 * @ClassName RewardConfigController
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/26
 * @Version v0.0.1
 */

@Api(tags = "自动奖励配置")
@Controller
@RequestMapping("/config")
public class RewardConfigController {

    @Autowired
    private ShopConfigBO shopConfigBO;
    @Autowired
    private ShopEmployeeBO shopEmployeeBO;

    @ApiOperation("提交自动奖励配置")
    @RequestMapping(value = "/publishRewardConfig",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper publishRewardConfig(@RequestBody PublishRewardConfigDTO dto){
        try {
            shopConfigBO.createModifyConfig(buildConfig(dto));
            return BaseResponseWrapper.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    @ApiOperation("查看轮播图")
    @RequestMapping(value = "/viewRewardConfig",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper<ViewPublishRewardConfigVO> viewRewardConfig(@RequestParam @ApiParam Long shopNo,
                                                                           @RequestParam @ApiParam Long empNo){
        try {
            ShopConfig config = shopConfigBO.getShopConfig(shopNo, ShopConfigTypeEnum.REWARD_CONFIG.getCode());
            return BaseResponseWrapper.success(convertToDto(config, empNo));
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    private ViewPublishRewardConfigVO convertToDto(ShopConfig config, Long empNo) {
        ViewPublishRewardConfigVO dto = new ViewPublishRewardConfigVO();
        ShopEmployee employee = shopEmployeeBO.getEmployee(empNo);
        dto.setCanEdit(false);
        if (employee != null){
            dto.setCanEdit(employee.getIsAdmin());
        }
        dto.setRewardConfigNo(config.getId());
        dto.setContent(JSONArray.parseArray(config.getContent(), ViewPublishRewardConfigVO.RewardConfig.class));
        return dto;
    }

    private ShopConfig buildConfig(PublishRewardConfigDTO dto){
        ShopConfig config = new ShopConfig();
        config.setConfigName(ShopConfigTypeEnum.REWARD_CONFIG.getDesc());
        config.setConfigType(ShopConfigTypeEnum.REWARD_CONFIG.getCode());
        if(dto.getContent() == null){
            throw new RuntimeException("积分奖励配置为空");
        }
        config.setContent(JSONArray.toJSONString(dto.getContent()));
        config.setCreator(dto.getEmpNo());
        config.setId(dto.getRewardConfigNo());
        ShopEmployee employee = shopEmployeeBO.getEmployee(dto.getEmpNo());
        if (employee == null || !employee.getIsAdmin()){
            throw new RuntimeException(String.format("员工[%d]非管理员不能创建积分配置", dto.getEmpNo()));
        }
        config.setShopNo(employee.getShopNo());
        return config;
    }
}
