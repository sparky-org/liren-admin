package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.entity.VacationApply;

public interface VacationApplyBO {

    void createVacationApply(VacationApply apply);

    void afterApproved(Long applyId, String result);

    VacationApply getVacation(Long id);
}
