package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.entity.CustomerInfo;
import com.sparky.lirenadmin.entity.po.CustomerGrowthStatisticsPO;

import java.util.List;

public interface CustomerBO {

    void createCustomer(CustomerInfo customerInfo);

    void modifyCustomer(CustomerInfo customerInfo, Boolean managerApproved);

    void deleteCustomer(Long customerNo);

    CustomerInfo getCustomer(Long customerNo);

    int countCustomer(Long shopNo, Long empNo);

    List<CustomerInfo> queryCustomer(Long shopNo, Long empNo);

    CustomerInfo getCustomerByPhone(String customerPhone);

    CustomerGrowthStatisticsPO getGrowthStatistics(Long empNo);
}
