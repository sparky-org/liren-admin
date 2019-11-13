package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.entity.SalesPerformance;
import com.sparky.lirenadmin.entity.ShopEmployee;

import java.util.Date;

public interface SalesPerformanceBO {

    void createSalePerformance(SalesPerformance salesPerformance);

    void reward(Long salesNo);

    Integer sumSalePerformanceNum(ShopEmployee employee, Date today);

    SalesPerformance getSalesPerf(Long id);
}
