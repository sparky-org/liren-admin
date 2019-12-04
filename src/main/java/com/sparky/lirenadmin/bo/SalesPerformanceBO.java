package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.entity.SalesPerformance;
import com.sparky.lirenadmin.entity.ShopEmployee;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface SalesPerformanceBO {

    void createSalePerformance(SalesPerformance salesPerformance, List<Long> ccList);

    void reward(Long salesNo);

    BigDecimal sumSalePerformanceNum(ShopEmployee employee, Date today);

    SalesPerformance getSalesPerf(Long id);
}
