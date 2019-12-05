package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.entity.ApplyDtl;
import com.sparky.lirenadmin.entity.ServiceItemRecord;
import com.sparky.lirenadmin.entity.ShopEmployee;

import java.util.Date;
import java.util.List;

public interface ServiceItemRecordBO {

    void createServiceRecord(ServiceItemRecord record, List<ApplyDtl> applyDtlList, Long auditor);

    void reward(Long salesNo);

    Integer sumServiceItemRecordNum(ShopEmployee employee, Date today);

    ServiceItemRecord getServiceItemRecord(Long id);
}
