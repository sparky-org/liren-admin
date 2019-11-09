package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.ApplyBO;
import com.sparky.lirenadmin.bo.ServiceItemRecordBO;
import com.sparky.lirenadmin.entity.*;
import com.sparky.lirenadmin.mapper.ServiceItemRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceItemRecordBOImpl implements ServiceItemRecordBO {

    @Autowired
    private ApplyBO applyBO;

    @Autowired
    private ServiceItemRecordMapper serviceItemRecordMapper;

    @Override
    public void createServiceRecord(ServiceItemRecord record) {
        doCreateServiceItem(record);
        applyBO.createApply(buildApply(record));
    }

    private void doCreateServiceItem(ServiceItemRecord record) {

    }

    private Apply buildApply(ServiceItemRecord record) {
        Apply apply = new Apply();
//        apply.setApplyContent(buildApplyContent(record));
//        apply.setApplyEmpNo(record.getEmpNo());
//        ShopEmployee employee = shopEmployeeBO.getShopAdmin(record.getEmpNo());
//        apply.setAuditEmpNo(employee.getManagerNo());
//        apply.setCreator(record.getEmpNo());
//        apply.setShopNo(record.getShopNo());
//        apply.setOrigin("SERVICE_RECORD");
//        apply.setOriginNo(record.getId());
        return apply;
    }

    private String buildApplyContent(ServiceItemRecord record) {
//        Task task = taskBO.getTask(record.getTaskNo());
        StringBuilder builder = new StringBuilder();
//        builder.append(String.format("任务名称：%s \n任务内容：%s \n申请奖励：%d 积分", task.getTitle(), task.getContent(), record.getRewardPoint()));
        return builder.toString();
    }
}
