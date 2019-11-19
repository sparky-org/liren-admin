package com.sparky.lirenadmin.controller;

import com.sparky.lirenadmin.bo.*;
import com.sparky.lirenadmin.constant.ApplyTypeEnum;
import com.sparky.lirenadmin.controller.request.ApplyServiceRecordDTO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.ServiceRecordDetailVO;
import com.sparky.lirenadmin.entity.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "项目申报接口")
@Controller
@RequestMapping("/service")
public class ServiceItemRecordController {
    private Logger logger = LoggerFactory.getLogger(ServiceItemRecordController.class);

    @Autowired
    private ServiceItemRecordBO serviceItemRecordBO;
    @Autowired
    private ApplyBO applyBO;
    @Autowired
    private ShopEmployeeBO shopEmployeeBO;
    @Autowired
    private CustomerBO customerBO;
    @Autowired
    private ApplyDtlBO applyDtlBO;

    @ApiOperation("项目申报")
    @RequestMapping(value = "/applyItem", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper applyItem(@RequestBody ApplyServiceRecordDTO applyServiceRecordDTO){
        try {
            List<ApplyDtl> dtls = null;
            if (applyServiceRecordDTO.getCcEmpList() != null && !applyServiceRecordDTO.getCcEmpList().isEmpty()){
                dtls = Arrays.stream(applyServiceRecordDTO.getCcEmpList().split(",")).map(s -> {
                    ApplyDtl dtl = new ApplyDtl();
                    dtl.setCcNo(Long.parseLong(s));
                    return dtl;
                }).collect(Collectors.toList());
            }

            serviceItemRecordBO.createServiceRecord(buildServiceRecord(applyServiceRecordDTO), dtls);
            return BaseResponseWrapper.success(null);
        } catch (Exception e) {
            logger.error("项目申报异常", e);
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    @ApiOperation("获取项目申报详情")
    @RequestMapping(value = "/getItemApply", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper<ServiceRecordDetailVO> getSalesPerfApply(@RequestParam @ApiParam Long serviceRecordNo){
        ServiceItemRecord record = serviceItemRecordBO.getServiceItemRecord(serviceRecordNo);
        if (record == null){
            return BaseResponseWrapper.success(new ServiceRecordDetailVO());
        }
        Apply apply = applyBO.queryApplyByOrigin(ApplyTypeEnum.SERVICE_ITEM.getCode(), serviceRecordNo);

        ServiceRecordDetailVO vo = buildServiceRecordDetailVO(apply, record);
        return BaseResponseWrapper.success(vo);
    }

    @ApiOperation("撤销项目申请")
    @RequestMapping(value = "/revertServiceRecord", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper revertServiceRecord(@RequestParam @ApiParam Long serviceRecordNo){
        try {
            ServiceItemRecord sales = serviceItemRecordBO.getServiceItemRecord(serviceRecordNo);
            if(sales == null){
                throw new RuntimeException("撤销失败，无此项目申报记录");
            }
            Apply apply = applyBO.queryApplyByOrigin(ApplyTypeEnum.SERVICE_ITEM.getCode(), serviceRecordNo);
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

    private ServiceRecordDetailVO buildServiceRecordDetailVO(Apply apply, ServiceItemRecord record) {
        ServiceRecordDetailVO applyVO = new ServiceRecordDetailVO();
        applyVO.setServiceRecordNo(record.getId());
        applyVO.setApplyCreateTime(apply.getGmtCreate());
        applyVO.setApplyPoint(record.getRewardPoint());
        ShopEmployee employee = shopEmployeeBO.getEmployee(apply.getAuditEmpNo());
        List<ApplyDtl> applyDtls = applyDtlBO.listApplyDtl(apply.getId());
        if (employee != null){
            applyVO.setAuditor(employee.getName());
        }
        if (null != applyDtls){
            List<Long> ccNos = applyDtls.stream().map(ApplyDtl::getCcNo).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(ccNos)){
                List<ShopEmployee> employees = shopEmployeeBO.listEmploy(ccNos);
                List<String> ccNames = employees.stream().map(ShopEmployee::getName).collect(Collectors.toList());
                applyVO.setCcEmpNames(ccNames);
            }
        }
        CustomerInfo customerInfo = customerBO.getCustomerByPhone(record.getCustomerPhone());
        applyVO.setContent(record.getContent());
        applyVO.setTitle(record.getTitle());
        applyVO.setCustomerName(customerInfo.getName());
        applyVO.setCompleteTime(DateUtils.formatDate(record.getCompleteTime(), "yyyy-MM-dd HH:mm:ss"));
        applyVO.setStatus(apply.getAuditStatus());
        applyVO.setServiceItemName(record.getTitle());
        return applyVO;
    }

    private ServiceItemRecord buildServiceRecord(ApplyServiceRecordDTO dto) {
        ServiceItemRecord record = new ServiceItemRecord();
        ShopEmployee employee = shopEmployeeBO.getEmployee(dto.getEmpNo());
        if (employee == null){
            throw new RuntimeException("员工不存在");
        }
        record.setRewardPoint(dto.getPoint());
        record.setShopNo(employee.getShopNo());
        CustomerInfo customerInfo = customerBO.getCustomer(dto.getCustomerNo());
        record.setEmpNo(dto.getEmpNo());
        if (customerInfo == null || customerInfo.getShopNo() != employee.getShopNo().longValue()){
            throw new RuntimeException("该美容院无此顾客");
        }
        record.setCreator(dto.getEmpNo());
        record.setCustomerPhone(customerInfo.getPhone());
        record.setServiceNo(dto.getServiceItemNo());
        record.setTitle(dto.getTitle());
        record.setCompleteTime(dto.getCompleteTime());
        record.setContent(dto.getContent());
        return record;
    }

}
