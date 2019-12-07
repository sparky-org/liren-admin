package com.sparky.lirenadmin.controller;

import com.sparky.lirenadmin.bo.ShopConfigBO;
import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.constant.PicUrl;
import com.sparky.lirenadmin.constant.ShopConfigTypeEnum;
import com.sparky.lirenadmin.controller.request.PublishPosterDTO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.ViewPosterVO;
import com.sparky.lirenadmin.entity.ShopConfig;
import com.sparky.lirenadmin.entity.ShopEmployee;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 *  管理员可以发布/修改轮播图
 *  所有人可以查看轮播图
 *
 * @ClassName NoticeController
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/26
 * @Version v0.0.1
 */

@Api(tags = "轮播图管理")
@Controller
@RequestMapping("/poster")
public class PosterController {

    @Autowired
    private ShopConfigBO shopConfigBO;
    @Autowired
    private ShopEmployeeBO shopEmployeeBO;

    @ApiOperation("提交轮播图地址，结合upload接口使用")
    @RequestMapping(value = "/publishPoster",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper publishPoster(@RequestBody PublishPosterDTO dto){
        try {
            shopConfigBO.createModifyConfig(buildConfig(dto));
            return BaseResponseWrapper.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    @ApiOperation("查看轮播图")
    @RequestMapping(value = "/viewPosters",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper<ViewPosterVO> viewPosters(@RequestParam @ApiParam Long shopNo,
                                                         @RequestParam @ApiParam Long empNo){
        try {
            ShopConfig config = shopConfigBO.getShopConfig(shopNo, ShopConfigTypeEnum.POSTER.getCode());
            if(null == config){
                return BaseResponseWrapper.success(null);
            }
            return BaseResponseWrapper.success(convertToDto(config, empNo));
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    private ViewPosterVO convertToDto(ShopConfig config, Long empNo) {
        ViewPosterVO dto = new ViewPosterVO();
        ShopEmployee employee = shopEmployeeBO.getEmployee(empNo);
        dto.setCanEdit(false);
        if (employee != null){
            dto.setCanEdit(employee.getIsAdmin());
        }
        dto.setPosterNo(config.getId());
        List<ViewPosterVO.Content> contents = new ArrayList<>();
        if (config.getContent() != null && !config.getContent().trim().isEmpty()){
            String[] images = config.getContent().split(",");
            for (String img : images){
                ViewPosterVO.Content content = new ViewPosterVO.Content();
                content.setRelativePath(img);
                content.setAbsolutePath(PicUrl.url + img);
                contents.add(content);
            }
        }
        dto.setContent(contents);
        return dto;
    }

    private ShopConfig buildConfig(PublishPosterDTO dto){
        ShopConfig config = new ShopConfig();
        config.setConfigName(ShopConfigTypeEnum.POSTER.getDesc());
        config.setConfigType(ShopConfigTypeEnum.POSTER.getCode());
        String content = removeUrlPrefix(dto.getContent());
        config.setContent(content);
        config.setCreator(dto.getEmpNo());
        config.setId(dto.getPosterNo());
        ShopEmployee employee = shopEmployeeBO.getEmployee(dto.getEmpNo());
        if (employee == null || !employee.getIsAdmin()){
            throw new RuntimeException(String.format("员工[%d]非管理员不能发布公告", dto.getEmpNo()));
        }
        config.setShopNo(employee.getShopNo());
        return config;
    }

    private String removeUrlPrefix(String content) {
        if (content == null){
            return null;
        }
        return content.replaceAll(PicUrl.url, "");
    }
}
