package com.sparky.lirenadmin.controller;

import com.sparky.lirenadmin.bo.ServiceItemBO;
import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.controller.request.CreateServiceItemDTO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.PagingResponseWrapper;
import com.sparky.lirenadmin.entity.ServiceItem;
import com.sparky.lirenadmin.entity.ShopEmployee;
import com.sparky.lirenadmin.utils.PagingUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MyServiceItemController
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/18
 * @Version v0.0.1
 */
@Api(tags = "服务项目接口")
@Controller
@RequestMapping("/serviceItem")
public class MyServiceItemController {

    @Autowired
    private ServiceItemBO serviceItemBO;
    @Autowired
    private ShopEmployeeBO shopEmployeeBO;

    @ApiOperation("查询本店服务项目")
    @RequestMapping(value = "/queryServiceItems", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper<List<ServiceItem>> queryServiceItems(@RequestParam @ApiParam Long shopNo){
        try {
            List<ServiceItem> items = serviceItemBO.getServiceItemByShopNo(shopNo);
            return BaseResponseWrapper.success(items);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    @ApiOperation("分页查询本店服务项目")
    @RequestMapping(value = "/pagingQueryServiceItems", method = RequestMethod.POST)
    @ResponseBody
    public PagingResponseWrapper<List<ServiceItem>> pagingQueryServiceItems(@RequestParam @ApiParam Long shopNo,
                                                                            @RequestParam @ApiParam Integer currentPage,
                                                                            @RequestParam @ApiParam Integer pageSize){
        try {
            Integer total = serviceItemBO.countServiceItem(shopNo);
            if(total < 1){
                return PagingResponseWrapper.success(new ArrayList<>(),0);
            }
            Integer start = PagingUtils.getStartIndex(total, currentPage, pageSize);
            List<ServiceItem> items = serviceItemBO.pagingQueryServiceItem(shopNo, start, pageSize);
            return PagingResponseWrapper.success(items,total);
        } catch (Exception e) {
            e.printStackTrace();
            return PagingResponseWrapper.fail1(null, e.getMessage());
        }
    }

    @ApiOperation("创建服务项目")
    @RequestMapping(value = "/createServiceItem", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper createServiceItem(@RequestBody CreateServiceItemDTO createServiceItemDTO){
        try {
            serviceItemBO.createUpdateServiceItem(buildServiceItem(createServiceItemDTO));
            return BaseResponseWrapper.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    private ServiceItem buildServiceItem(CreateServiceItemDTO dto) {
        ServiceItem item = new ServiceItem();
        item.setItemName(dto.getItemName());
        item.setDuration(dto.getDuration());
        item.setItemDesc(dto.getDesc());
        item.setRewardPoint(dto.getPoint());
        item.setCreator(dto.getEmpNo());
        ShopEmployee employee = shopEmployeeBO.getEmployee(dto.getEmpNo());
        if(employee == null){
            throw new RuntimeException("创建人不存在");
        }
        item.setShopNo(employee.getShopNo());
        return item;
    }
}
