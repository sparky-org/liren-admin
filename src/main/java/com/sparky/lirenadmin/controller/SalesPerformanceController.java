package com.sparky.lirenadmin.controller;

import com.sparky.lirenadmin.bo.*;
import com.sparky.lirenadmin.constant.ApplyTypeEnum;
import com.sparky.lirenadmin.controller.request.ApplySalesPerfDTO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.SalesPerfApplyVO;
import com.sparky.lirenadmin.entity.*;
import com.sparky.lirenadmin.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private ApplyDtlBO applyDtlBO;
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
    public BaseResponseWrapper<SalesPerfApplyVO> getSalesPerfApply(@RequestParam @ApiParam Long salesPerfNo){
        SalesPerformance salesPerformance = salesPerformanceBO.getSalesPerf(salesPerfNo);
        if (salesPerformance == null){
            throw new RuntimeException("未查询到关联业绩数据");
        }
        Apply apply = applyBO.queryApplyByOrigin(ApplyTypeEnum.SAL_PERF.getCode(), salesPerfNo);
        if (apply == null){
            return BaseResponseWrapper.success(new SalesPerfApplyVO());
        }
        SalesPerfApplyVO applyVO = buildAppVO(apply, salesPerformance);
        return BaseResponseWrapper.success(applyVO);
    }

    @ApiOperation("撤销业绩申请")
    @RequestMapping(value = "/revertSalesPerf", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper revertSalesPerf(@RequestParam @ApiParam Long salesPerfNo){
        try {
            SalesPerformance sales = salesPerformanceBO.getSalesPerf(salesPerfNo);
            if(sales == null){
                throw new RuntimeException("撤销失败，无此业绩");
            }
            Apply apply = applyBO.queryApplyByOrigin(ApplyTypeEnum.SAL_PERF.getCode(), salesPerfNo);
            if (apply == null){
                return BaseResponseWrapper.success("未查到业绩申请，无须撤销");
            }
            applyBO.revert(apply);
            return BaseResponseWrapper.success(null);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    private SalesPerfApplyVO buildAppVO(Apply apply, SalesPerformance sales) {
        SalesPerfApplyVO applyVO = new SalesPerfApplyVO();
        applyVO.setSalesPerfNo(sales.getId());
        applyVO.setApplyCreateTime(apply.getAuditTime());
        applyVO.setApplyPoint(sales.getRewardPoint());
        ShopEmployee employee = shopEmployeeBO.getEmployee(apply.getAuditEmpNo());
        if (employee != null){
            applyVO.setAuditor(employee.getName());
        }
        List<ApplyDtl> applyDtls = applyDtlBO.listApplyDtl(apply.getId());
        if (null != applyDtls){
            List<Long> ccNos = applyDtls.stream().map(ApplyDtl::getCcNo).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(ccNos)){
                List<ShopEmployee> employees = shopEmployeeBO.listEmploy(ccNos);
                List<String> ccNames = employees.stream().map(ShopEmployee::getName).collect(Collectors.toList());
                applyVO.setCcEmpNames(ccNames);
            }
        }
        applyVO.setContent(sales.getContent());
        CustomerInfo customerInfo = customerBO.getCustomerByPhone(sales.getCustomerPhone());
        applyVO.setCustomerName(customerInfo.getName());

        applyVO.setCompleteTime(DateUtils.formatDate(sales.getCompleteTime(), "yyyy-MM-dd HH:mm:ss"));
        applyVO.setStatus(apply.getAuditStatus());
        applyVO.setTitle(sales.getTitle());
        applyVO.setTargetAmount(sales.getAmount());
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
        performance.setCompleteTime(com.sparky.lirenadmin.utils.DateUtils.getDateTime(dto.getCompleteTime()));
        return performance;
    }
}
