package com.sparky.lirenadmin.component.impl;

import com.sparky.lirenadmin.bo.SalesPerformanceBO;
import com.sparky.lirenadmin.component.ApplyApprovedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplyApproved4SalesComponent implements ApplyApprovedHandler {

    @Autowired
    private SalesPerformanceBO salesPerformanceBO;

    @Override
    public void afterApplyApproved(String origin, Long originNo) {
        if (!"SALES_PERF".equals(origin)){
            return;
        }
        salesPerformanceBO.reward(originNo);
    }
}
