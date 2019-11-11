package com.sparky.lirenadmin.controller;

import com.sparky.lirenadmin.bo.CustomerBO;
import com.sparky.lirenadmin.bo.CustomerTraceBO;
import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.controller.request.AddCustomerDTO;
import com.sparky.lirenadmin.controller.request.ModifyCustomerDTO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.CustomerDetailVO;
import com.sparky.lirenadmin.entity.CustomerInfo;
import com.sparky.lirenadmin.entity.CustomerTrace;
import com.sparky.lirenadmin.entity.ShopEmployee;
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

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName MyCustomerController
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/11
 * @Version v0.0.1
 */
@Api(tags = "我的客户接口")
@Controller
@RequestMapping("/myCustomer")
public class MyCustomerController {

    private static final Logger logger = LoggerFactory.getLogger(MyCustomerController.class);

    @Autowired
    private CustomerBO customerBO;
    @Autowired
    private ShopEmployeeBO shopEmployeeBO;
    @Autowired
    private CustomerTraceBO customerTraceBO;

    @ApiOperation("添加客户")
    @RequestMapping(value = "/addCustomer", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper addCustomer(@RequestBody AddCustomerDTO addCustomerDTO){
        try {
            ShopEmployee employee = shopEmployeeBO.getEmployee(addCustomerDTO.getEmpNo());
            if (employee == null){
                throw new RuntimeException(String.format("员工[%d]不存在。", addCustomerDTO.getEmpNo()));
            }
            customerBO.createCustomer(buildCustomer(addCustomerDTO, employee.getShopNo()));
        } catch (Exception e) {
            logger.error("创建客户异常。", e.getMessage());
            return BaseResponseWrapper.fail("创建客户异常。", e.getMessage());
        }
        return BaseResponseWrapper.success(null);
    }

    @ApiOperation("修改客户")
    @RequestMapping(value = "/modifyCustomer", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper modifyCustomer(@RequestBody ModifyCustomerDTO modifyCustomerDTO){
        try {
            ShopEmployee employee = shopEmployeeBO.getEmployee(modifyCustomerDTO.getEmpNo());
            if (employee == null){
                throw new RuntimeException(String.format("员工[%d]不存在。", modifyCustomerDTO.getEmpNo()));
            }
            CustomerInfo customerInfo = buildCustomer(modifyCustomerDTO, employee.getShopNo());
            customerInfo.setId(modifyCustomerDTO.getCustomerNo());
            customerBO.modifyCustomer(customerInfo, !employee.getIsAdmin());
        } catch (Exception e) {
            logger.error("创建客户异常。", e.getMessage());
            return BaseResponseWrapper.fail("创建客户异常。", e.getMessage());
        }
        return BaseResponseWrapper.success(null);
    }

    @ApiOperation("客户详情")
    @RequestMapping(value = "/getCustomerDetail",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper<CustomerDetailVO> getCustomerDetail(@RequestParam @ApiParam Long customerNo,
                                                                   @RequestParam @ApiParam Integer start,
                                                                   @RequestParam @ApiParam Integer pageSize){
        try {
            CustomerInfo customerInfo = customerBO.getCustomer(customerNo);
            if (customerInfo == null){
                throw new RuntimeException(String .format("编号[%d]的顾客不存在。", customerNo));
            }
            List<CustomerTrace> traceList = customerTraceBO.pagingQueryTrace(customerNo, start, pageSize);
            CustomerDetailVO detailVO = new CustomerDetailVO();
            fillBaseInfo(detailVO, customerInfo);
            if (CollectionUtils.isEmpty(traceList)){
                List<CustomerDetailVO.TraceInfo> traceInfos = traceList.stream().map(t -> {
                    CustomerDetailVO.TraceInfo info = new CustomerDetailVO.TraceInfo();
                    info.setDate(DateUtils.formatDate(t.getDate(), "yyyy-MM-dd HH:mm:ss"));
                    info.setContent(t.getActiveType());
                    return info;
                }).collect(Collectors.toList());
                detailVO.setTraceInfoList(traceInfos);
            }
            return BaseResponseWrapper.success(detailVO);
        } catch (Exception e) {
            logger.error("创建客户异常。", e.getMessage());
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    private void fillBaseInfo(CustomerDetailVO detailVO, CustomerInfo customerInfo) {
        detailVO.setCustomerNo(customerInfo.getId());
        detailVO.setName(customerInfo.getName());
        detailVO.setSex(customerInfo.getSex());
        detailVO.setPhone(customerInfo.getPhone());
        CustomerDetailVO.BaseInfo info = new CustomerDetailVO.BaseInfo();
        info.setAddDate(DateUtils.formatDate(customerInfo.getGmtCreate(), "yyyy-MM-dd"));
        if (customerInfo.getBirthday() != null) {
            info.setBirthDay(DateUtils.formatDate(customerInfo.getBirthday(), "yyyy-MM-dd"));
        }
        info.setFavor(customerInfo.getFavor());
        info.setRemark(customerInfo.getRemark());
        info.setYearPlan(customerInfo.getYearPlan());
        ShopEmployee employee = shopEmployeeBO.getEmployee(customerInfo.getCreator());
        if(employee != null){
            info.setRelatedEmp(employee.getName());
        }
        detailVO.setBaseInfo(info);
    }


    private CustomerInfo buildCustomer(AddCustomerDTO addCustomerDTO, Long shopNo) {
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setBirthday(addCustomerDTO.getBirthDay());
        customerInfo.setCreator(addCustomerDTO.getEmpNo());
        customerInfo.setFavor(addCustomerDTO.getFavor());
        customerInfo.setName(addCustomerDTO.getCustomerName());
        customerInfo.setPhone(addCustomerDTO.getPhone());
        customerInfo.setRemark(addCustomerDTO.getRemark());
        customerInfo.setSex(addCustomerDTO.getSex());
        customerInfo.setYearPlan(customerInfo.getYearPlan());
        customerInfo.setShopNo(shopNo);
        return customerInfo;
    }

}
