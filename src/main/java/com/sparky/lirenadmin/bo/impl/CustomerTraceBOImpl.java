package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.CustomerTraceBO;
import com.sparky.lirenadmin.entity.CustomerTrace;
import com.sparky.lirenadmin.mapper.CustomerTraceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CustomerTraceBOImpl implements CustomerTraceBO {

    @Autowired
    private CustomerTraceMapper customerTraceMapper;

    @Override
    public void createCustomerTrace(CustomerTrace trace) {
        trace.setIsValid(true);
        trace.setGmtCreate(new Date());
        trace.setGmtModify(new Date());
        customerTraceMapper.insertSelective(trace);
    }

    @Override
    public int countTrace(Long customerId) {
        return 0;
    }

    @Override
    public List<CustomerTrace> pagingQueryTrace(Long customerId, Integer start, Integer length) {
        return null;
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
}
