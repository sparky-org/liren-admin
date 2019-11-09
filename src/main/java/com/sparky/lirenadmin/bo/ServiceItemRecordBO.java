package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.entity.ServiceItemRecord;
import com.sparky.lirenadmin.entity.ShopEmployee;

import java.util.Date;

public interface ServiceItemRecordBO {

    void createServiceRecord(ServiceItemRecord record);

    void reward(Long salesNo);

    Integer countAppointCustomer(ShopEmployee employee, Date today);
}
