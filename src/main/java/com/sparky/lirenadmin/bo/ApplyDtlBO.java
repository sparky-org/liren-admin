package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.entity.ApplyDtl;

import java.util.List;

public interface ApplyDtlBO {
    List<ApplyDtl> listApplyDtl(Long applyNo);

    void createApplyDtl(ApplyDtl dtl);
}
