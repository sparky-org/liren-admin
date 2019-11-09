package com.sparky.lirenadmin.component.impl;

import com.sparky.lirenadmin.bo.ServiceItemRecordBO;
import com.sparky.lirenadmin.component.ApplyApprovedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplyApproved4ServiceItemComponent implements ApplyApprovedHandler {

    @Autowired
    private ServiceItemRecordBO serviceItemRecordBO;

    @Override
    public void afterApplyApproved(String origin, Long originNo) {
        if (!"SERVICE_ITEM".equals(origin)){
            return;
        }
        serviceItemRecordBO.reward(originNo);
    }
}
