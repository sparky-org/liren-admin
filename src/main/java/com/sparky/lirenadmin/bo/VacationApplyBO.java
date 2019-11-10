package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.entity.ShopEmployee;
import com.sparky.lirenadmin.entity.VacationApply;

import java.util.Date;

public interface VacationApplyBO {

    void createVacationApply(VacationApply apply);

    void afterApproved(Long applyId, String result);

    VacationApply getVacation(Long id);

    Integer sumRestEmployeeNum(ShopEmployee employee, Date today);
}
