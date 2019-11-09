package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.entity.SalesPerformance;

public interface SalesPerformanceBO {

    void createSalePerformance(SalesPerformance salesPerformance);

    void reward(Long salesNo);

}
