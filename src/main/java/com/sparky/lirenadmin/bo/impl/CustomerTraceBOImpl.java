package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.CustomerTraceBO;
import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.entity.CustomerTrace;
import com.sparky.lirenadmin.entity.CustomerTraceExample;
import com.sparky.lirenadmin.entity.ShopEmployee;
import com.sparky.lirenadmin.entity.po.CustomerActiveStatistics;
import com.sparky.lirenadmin.mapper.ext.CustomerTraceMapperExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CustomerTraceBOImpl implements CustomerTraceBO {

    @Autowired
    private CustomerTraceMapperExt customerTraceMapper;
    @Autowired
    private ShopEmployeeBO shopEmployeeBO;

    @Override
    public void createCustomerTrace(CustomerTrace trace) {
        trace.setIsValid(true);
        trace.setGmtCreate(new Date());
        trace.setGmtModify(new Date());
        customerTraceMapper.insertSelective(trace);
    }

    @Override
    public int countTrace(Long customerId) {
        CustomerTraceExample example = new CustomerTraceExample();
        example.createCriteria().andCustomerNoEqualTo(customerId);
        return new Long(customerTraceMapper.countByExample(example)).intValue();
    }

    @Override
    public List<CustomerTrace> pagingQueryTrace(Long customerId, Integer start, Integer length) {
        return customerTraceMapper.pagingQueryTrace(customerId, start, length);
    }

    @Override
    public CustomerTrace buildCustomerTrace(Long customerNo, Date time, String activeTpye, Long relatedNo, Long shopNo, Long creator){
        CustomerTrace trace = new CustomerTrace();
        trace.setCustomerNo(customerNo);
        trace.setDate(time);
        trace.setActiveType(activeTpye);
        trace.setRelatedNo(relatedNo);
        trace.setShopNo(shopNo);
        trace.setCreator(creator);
        return trace;
    }

    @Override
    public List<CustomerActiveStatistics> activeCustomerStatistics(Long empNo) {
        ShopEmployee employee = shopEmployeeBO.getEmployee(empNo);
        if(employee == null){
            return new ArrayList<>();
        }
        return customerTraceMapper.activeCustomerStatistics(employee.getShopNo(), empNo, employee.getIsAdmin());
    }
}
