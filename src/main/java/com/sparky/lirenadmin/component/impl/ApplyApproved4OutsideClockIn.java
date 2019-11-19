package com.sparky.lirenadmin.component.impl;

import com.sparky.lirenadmin.bo.ClockInLogBO;
import com.sparky.lirenadmin.component.ApplyApprovedHandler;
import com.sparky.lirenadmin.constant.ApplyTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplyApproved4OutsideClockIn implements ApplyApprovedHandler {

    @Autowired
    private ClockInLogBO clockInLogBO;

    @Override
    public void afterApplyApproved(String origin, Long originNo) {
        if (!ApplyTypeEnum.OUTSIDE_SIGN.getCode().equals(origin)){
            return;
        }
        clockInLogBO.afterApproved(originNo);
    }
}
