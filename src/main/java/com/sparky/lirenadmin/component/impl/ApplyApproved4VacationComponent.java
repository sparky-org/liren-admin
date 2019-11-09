package com.sparky.lirenadmin.component.impl;

import com.sparky.lirenadmin.bo.ApplyBO;
import com.sparky.lirenadmin.bo.VacationApplyBO;
import com.sparky.lirenadmin.component.ApplyApprovedHandler;
import com.sparky.lirenadmin.entity.Apply;
import com.sparky.lirenadmin.entity.VacationApply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplyApproved4VacationComponent implements ApplyApprovedHandler {

    @Autowired
    private VacationApplyBO vacationApplyBO;
    @Autowired
    private ApplyBO applyBO;

    @Override
    public void afterApplyApproved(String origin, Long originNo) {
        if (!"VACATION".equals(origin)){
            return;
        }
        Apply apply = applyBO.queryApplyByOrigin(origin, originNo);
        if (null == apply){
            return;
        }
        vacationApplyBO.afterApproved(originNo, apply.getAuditStatus());
    }
}
