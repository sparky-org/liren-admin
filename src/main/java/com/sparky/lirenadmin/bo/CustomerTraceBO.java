package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.entity.CustomerTrace;
import com.sparky.lirenadmin.entity.po.CustomerActiveStatistics;

import java.util.Date;
import java.util.List;

/**
 * 客户行为跟踪，只跟踪做项目/业绩产生数据
 * @Description
 * @Author yuankai
 * @Date 2019-11-09
 * @Version 0.0.1
 */
public interface CustomerTraceBO {

    void createCustomerTrace(CustomerTrace trace);

    int countTrace(Long customerId);

    List<CustomerTrace> pagingQueryTrace(Long customerId, Integer start, Integer length);

    CustomerTrace buildCustomerTrace(Long customerNo, Date time, String activeTpye, Long relatedNo, Long shopNo, Long creator);

    List<CustomerActiveStatistics> activeCustomerStatistics(Long empNo);
}
