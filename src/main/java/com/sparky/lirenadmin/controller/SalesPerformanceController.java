package com.sparky.lirenadmin.controller;

import com.sparky.lirenadmin.bo.ApplyBO;
import com.sparky.lirenadmin.bo.CustomerBO;
import com.sparky.lirenadmin.bo.SalesPerformanceBO;
import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.controller.request.ApplySalesPerfDTO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.SalesPerfApplyVO;
import com.sparky.lirenadmin.entity.Apply;
import com.sparky.lirenadmin.entity.CustomerInfo;
import com.sparky.lirenadmin.entity.SalesPerformance;
import com.sparky.lirenadmin.entity.ShopEmployee;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Api(tags = "业绩接口")
@Controller
@RequestMapping("/sales")
public class SalesPerformanceController {
    private Logger logger = LoggerFactory.getLogger(SalesPerformanceController.class);

    @Autowired
    private SalesPerformanceBO salesPerformanceBO;
    @Autowired
    private ApplyBO applyBO;
    @Autowired
    private ShopEmployeeBO shopEmployeeBO;
    @Autowired
    private CustomerBO customerBO;

    @ApiOperation("业绩申报")
    @RequestMapping(value = "/applySalesPerf", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper applySalesPerf(@RequestBody ApplySalesPerfDTO applySalesPerfDTO){
        try {
            String cc = applySalesPerfDTO.getCcEmpList();
            List<Long> ccList = new ArrayList<>();
            if (cc != null && !cc.trim().isEmpty()){
                for (String s : cc.split(",")){
                    ccList.add(Long.parseLong(s));
                }
            }
            salesPerformanceBO.createSalePerformance(buildSalesPerf(applySalesPerfDTO), ccList);
            return BaseResponseWrapper.success(null);
        } catch (Exception e) {
            logger.error("登陆未知异常", e);
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    @ApiOperation("获取业绩申报详情")
    @RequestMapping(value = "/getSalesPerfApply", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper<SalesPerfApplyVO> getSalesPerfApply(@RequestParam @ApiParam Long applyNo){
        Apply apply = applyBO.getApply(applyNo);
        if (apply == null){
            return BaseResponseWrapper.success(new SalesPerfApplyVO());
        }
        SalesPerformance salesPerformance = salesPerformanceBO.getSalesPerf(apply.getOriginNo());
        if (salesPerformance == null){
            throw new RuntimeException("未查询到关联业绩数据");
        }
        SalesPerfApplyVO applyVO = buildAppVO(apply, salesPerformance);
        return BaseResponseWrapper.success(applyVO);
    }

    private SalesPerfApplyVO buildAppVO(Apply apply, SalesPerformance sales) {
        SalesPerfApplyVO applyVO = new SalesPerfApplyVO();
        applyVO.setApplyNo(apply.getId());
        applyVO.setApplyCreateTime(apply.getAuditTime());
        applyVO.setApplyPoint(sales.getRewardPoint());
        ShopEmployee employee = shopEmployeeBO.getEmployee(apply.getAuditEmpNo());
        if (employee != null){
            applyVO.setCcEmpNames(null);
            applyVO.setContent(sales.getContent());
            CustomerInfo customerInfo = customerBO.getCustomer(apply.getId());
//            applyVO.setCustomerName();
        }
//        applyVO.setAuditor();
        return applyVO;
    }

    private SalesPerformance buildSalesPerf(ApplySalesPerfDTO dto) {
        SalesPerformance performance = new SalesPerformance();
        ShopEmployee employee = shopEmployeeBO.getEmployee(dto.getEmpNo());
        if (employee == null){
            throw new RuntimeException("员工不存在");
        }
        performance.setShopNo(employee.getShopNo());
        performance.setRewardPoint(dto.getPoint());
        performance.setEmpNo(dto.getEmpNo());
        CustomerInfo customerInfo = customerBO.getCustomer(dto.getCustomerNo());
        if (customerInfo == null || customerInfo.getShopNo() != employee.getShopNo().longValue()){
            throw new RuntimeException("该美容院无此顾客");
        }
        performance.setCustomerPhone(customerInfo.getPhone());
        performance.setCreator(dto.getEmpNo());
        performance.setAmount(dto.getTargetAmount());
        performance.setTitle(dto.getTitle());
        performance.setContent(dto.getContent());
        performance.setCompleteTime(dto.getCompleteTime());
        return performance;
    }
}
