package com.sparky.lirenadmin.controller;

import com.alibaba.fastjson.JSONArray;
import com.sparky.lirenadmin.bo.CustomerBO;
import com.sparky.lirenadmin.bo.CustomerTraceBO;
import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.bo.cond.QueryCustomerCond;
import com.sparky.lirenadmin.controller.request.AddCustomerDTO;
import com.sparky.lirenadmin.controller.request.ModifyCustomerDTO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.CustomerDetailVO;
import com.sparky.lirenadmin.controller.response.PagingResponseWrapper;
import com.sparky.lirenadmin.controller.response.SimpleCustomerVO;
import com.sparky.lirenadmin.entity.CustomerInfo;
import com.sparky.lirenadmin.entity.CustomerTrace;
import com.sparky.lirenadmin.entity.ShopEmployee;
import com.sparky.lirenadmin.entity.po.CustomerActiveStatistics;
import com.sparky.lirenadmin.entity.po.CustomerGrowthStatisticsPO;
import com.sparky.lirenadmin.utils.DateUtils;
import com.sparky.lirenadmin.utils.PagingUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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
            addCustomerValid(addCustomerDTO);
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
            addCustomerValid(modifyCustomerDTO);
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
            CustomerDetailVO detailVO = new CustomerDetailVO();
            fillBaseInfo(detailVO, customerInfo);
            Integer total = customerTraceBO.countTrace(customerNo);
            if (total < 1){
                detailVO.setTraceInfoList(new ArrayList<>());
            }else {
                Integer start1 = PagingUtils.getStartIndex(total, start, pageSize);
                List<CustomerTrace> traceList = customerTraceBO.pagingQueryTrace(customerNo, start1, pageSize);
                if (!CollectionUtils.isEmpty(traceList)) {
                    List<CustomerDetailVO.TraceInfo> traceInfos = traceList.stream().map(t -> {
                        CustomerDetailVO.TraceInfo info = new CustomerDetailVO.TraceInfo();
                        info.setDate(DateUtils.formatDate(t.getDate(), "yyyy-MM-dd HH:mm:ss"));
                        info.setContent(t.getActiveType());
                        return info;
                    }).collect(Collectors.toList());
                    detailVO.setTraceInfoList(traceInfos);
                }
            }
            detailVO.setTotal(total);
            return BaseResponseWrapper.success(detailVO);
        } catch (Exception e) {
            logger.error("获取客户详情异常。", e.getMessage());
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    @ApiOperation("客户统计")
    @RequestMapping(value = "/getGrowthStatistics",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper<CustomerGrowthStatisticsPO> getGrowthStatistics(@RequestParam @ApiParam Long empNo){
        try {
            CustomerGrowthStatisticsPO po = customerBO.getGrowthStatistics(empNo);
            return BaseResponseWrapper.success(po);
        } catch (Exception e) {
            logger.error("统计客户信息异常。", e);
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    @ApiOperation("活跃客户曲线")
    @RequestMapping(value = "/activeCustomerStatistics",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper<List<CustomerActiveStatistics>> activeCustomerStatistics(@RequestParam @ApiParam Long empNo){
        try {
            List<CustomerActiveStatistics> po = customerTraceBO.activeCustomerStatistics(empNo);
            System.out.println(JSONArray.toJSONString(po));
            List<String> monthList = new ArrayList<>();
            Date today = new Date();
            monthList.add(DateUtils.formatDate(today, "yyyy-MM"));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(today);
            for(int i=0; i<11; i++){
                calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
                Date day = calendar.getTime();
                monthList.add(DateUtils.formatDate(day, "yyyy-MM"));
            }
            Map<String, Integer> activeMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(po)){
                activeMap = po.stream().collect(Collectors.toMap(e -> e.getYearMonth(), e-> e.getCount()));
            }
            List<CustomerActiveStatistics> statistics = new ArrayList<>();
            for (String m : monthList){
                CustomerActiveStatistics s = new CustomerActiveStatistics();
                    s.setYearMonth(m);
                if (activeMap.get(m) != null){
                    s.setCount(activeMap.get(m));
                }else{
                    s.setCount(0);
                }
                statistics.add(s);
            }
            return BaseResponseWrapper.success(statistics);
        } catch (Exception e) {
            logger.error("统计活跃客户异常。", e);
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    @ApiOperation("我的客户")
    @RequestMapping(value = "/myCustomers",method = RequestMethod.POST)
    @ResponseBody
    public PagingResponseWrapper<List<SimpleCustomerVO>> myCustomers(@RequestParam @ApiParam Long empNo,
                                                             @RequestParam @ApiParam Integer currentPage,
                                                             @RequestParam @ApiParam Integer pageSize){
        try {
            ShopEmployee employee = shopEmployeeBO.getEmployee(empNo);
            if(null == employee){
                throw new RuntimeException("员工不存在");
            }
            QueryCustomerCond cond = new QueryCustomerCond(employee.getShopNo(), empNo, employee.getIsAdmin(), null, null);
            Integer total = customerBO.countCustomerByCond(cond);
            if (total < 1){
                return PagingResponseWrapper.success(new ArrayList<>(), 0);
            }
            Integer start = PagingUtils.getStartIndex(total, currentPage, pageSize);
            cond.setStart(start);
            cond.setPageSize(pageSize);
            List<CustomerInfo> customerInfos = customerBO.pagingQueryCustomerByCond(cond);
            List<SimpleCustomerVO> vos = customerInfos.stream().map(this::convertToSimpleCustomerVO).collect(Collectors.toList());
            return PagingResponseWrapper.success(vos,total);
        } catch (Exception e) {
            logger.error("统计活跃客户异常。", e);
            return PagingResponseWrapper.fail1(null, e.getMessage());
        }
    }

    @ApiOperation("分页查询本店顾客")
    @RequestMapping(value = "/pagingQueryCustomer",method = RequestMethod.POST)
    @ResponseBody
    public PagingResponseWrapper<List<CustomerInfo>> pagingQueryCustomer(@RequestParam @ApiParam Long shopNo,
                                                                         @RequestParam(required = false) @ApiParam String phoneLike,
                                                                         @RequestParam(required = false) @ApiParam String nameLike,
                                                                         @RequestParam @ApiParam Integer currentPage,
                                                                         @RequestParam @ApiParam Integer pageSize){
        try {
            QueryCustomerCond cond = new QueryCustomerCond(shopNo, null, null, phoneLike, nameLike);
            Integer total = customerBO.countCustomerByCond(cond);
            if (total < 1){
                return PagingResponseWrapper.success(new ArrayList<>(), 0);
            }
            Integer start = PagingUtils.getStartIndex(total, currentPage, pageSize);
            cond.setStart(start);
            cond.setPageSize(pageSize);
            List<CustomerInfo> infos = customerBO.pagingQueryCustomerByCond(cond);
            return PagingResponseWrapper.success(infos, total);
        } catch (Exception e) {
            logger.error("分页查询本店顾客异常。", e);
            return PagingResponseWrapper.fail1(null, e.getMessage());
        }
    }

    private void addCustomerValid(AddCustomerDTO addCustomerDTO) {
        Assert.notNull(addCustomerDTO.getPhone(), "手机号必填");
        Assert.isTrue(addCustomerDTO.getPhone().matches("0?(13|14|15|17|18|19)[0-9]{9}"), "手机号不合法");
    }

    private SimpleCustomerVO convertToSimpleCustomerVO(CustomerInfo customerInfo) {
        SimpleCustomerVO vo = new SimpleCustomerVO();
        vo.setCustomerNo(customerInfo.getId());
        vo.setName(customerInfo.getName());
        vo.setPhone(customerInfo.getPhone());
        vo.setAddDate(DateUtils.formatDate(customerInfo.getGmtCreate(), "yyyy-MM-dd"));
        return vo;
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
        String remark = customerInfo.getRemark();
        if (remark != null){
            info.setRemark(remark.replaceAll("\n", "<br>"));
        }
        String yearPlan = customerInfo.getYearPlan();
        if (yearPlan != null){
            info.setYearPlan(yearPlan.replaceAll("\n", "<br>"));
        }
        ShopEmployee employee = shopEmployeeBO.getEmployee(customerInfo.getCreator());
        if(employee != null){
            info.setRelatedEmp(employee.getName());
        }
        detailVO.setBaseInfo(info);
    }


    private CustomerInfo buildCustomer(AddCustomerDTO addCustomerDTO, Long shopNo) {
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setBirthday(com.sparky.lirenadmin.utils.DateUtils.getDate(addCustomerDTO.getBirthDay()));
        customerInfo.setCreator(addCustomerDTO.getEmpNo());
        customerInfo.setFavor(addCustomerDTO.getFavor());
        customerInfo.setName(addCustomerDTO.getCustomerName());
        customerInfo.setPhone(addCustomerDTO.getPhone());
        customerInfo.setRemark(addCustomerDTO.getRemark());
        customerInfo.setSex(addCustomerDTO.getSex());
        customerInfo.setYearPlan(addCustomerDTO.getYearPlan());
        customerInfo.setShopNo(shopNo);
        return customerInfo;
    }

}
