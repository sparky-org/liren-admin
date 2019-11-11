package com.sparky.lirenadmin.component.impl;

import com.sparky.lirenadmin.bo.ServiceItemRecordBO;
import com.sparky.lirenadmin.component.ApplyApprovedHandler;
import com.sparky.lirenadmin.constant.ApplyTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplyApproved4ServiceItemComponent implements ApplyApprovedHandler {

    @Autowired
    private ServiceItemRecordBO serviceItemRecordBO;

    @Override
    public void afterApplyApproved(String origin, Long originNo) {
        if (!ApplyTypeEnum.SERVICE_ITEM.getCode().equals(origin)){
            return;
        }
        serviceItemRecordBO.reward(originNo);
    }
}
