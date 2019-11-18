package com.sparky.lirenadmin.controller;

import com.sparky.lirenadmin.bo.ApplyBO;
import com.sparky.lirenadmin.bo.SalesPerformanceBO;
import com.sparky.lirenadmin.bo.ServiceItemRecordBO;
import com.sparky.lirenadmin.controller.request.ApplySalesPerfDTO;
import com.sparky.lirenadmin.controller.request.ApplyServiceRecordDTO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.SalesPerfApplyVO;
import com.sparky.lirenadmin.entity.Apply;
import com.sparky.lirenadmin.entity.SalesPerformance;
import com.sparky.lirenadmin.entity.ServiceItem;
import com.sparky.lirenadmin.entity.ServiceItemRecord;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Api(tags = "项目申报接口")
@Controller
@RequestMapping("/service")
public class ServiceItemRecordController {
    private Logger logger = LoggerFactory.getLogger(ServiceItemRecordController.class);

    @Autowired
    private ServiceItemRecordBO serviceItemRecordBO;
    @Autowired
    private ApplyBO applyBO;

    @ApiOperation("项目申报")
    @RequestMapping(value = "/applyItem", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper applyItem(@RequestBody ApplyServiceRecordDTO applyServiceRecordDTO){
        try {
            serviceItemRecordBO.createServiceRecord(buildServiceRecord(applyServiceRecordDTO));
            return BaseResponseWrapper.success(null);
        } catch (Exception e) {
            logger.error("登陆未知异常", e);
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    @ApiOperation("获取项目申报详情")
    @RequestMapping(value = "/getItemApply", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper<SalesPerfApplyVO> getSalesPerfApply(@RequestParam @ApiParam Long applyNo){
        Apply apply = applyBO.getApply(applyNo);
        if (apply == null){
            return BaseResponseWrapper.success(new SalesPerfApplyVO());
        }
        ServiceItemRecord salesPerformance = serviceItemRecordBO.getServiceItemRecord(apply.getOriginNo());
        if (salesPerformance == null){
            throw new RuntimeException("未查询到关联业绩数据");
        }
//        SalesPerfApplyVO applyVO = buildAppVO(apply, salesPerformance);
        return BaseResponseWrapper.success(null);
    }

    private ServiceItemRecord buildServiceRecord(ApplyServiceRecordDTO applyServiceRecordDTO) {
        return null;
    }

}
