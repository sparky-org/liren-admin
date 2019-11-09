package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.*;
import com.sparky.lirenadmin.bo.cond.IncreasePointDO;
import com.sparky.lirenadmin.entity.Apply;
import com.sparky.lirenadmin.entity.CustomerInfo;
import com.sparky.lirenadmin.entity.CustomerTrace;
import com.sparky.lirenadmin.entity.ServiceItemRecord;
import com.sparky.lirenadmin.mapper.ServiceItemRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ServiceItemRecordBOImpl implements ServiceItemRecordBO {

    @Autowired
    private ApplyBO applyBO;
    @Autowired
    private PointBO pointBO;
    @Autowired
    private CustomerBO customerBO;
    @Autowired
    private CustomerTraceBO customerTraceBO;

    @Autowired
    private ServiceItemRecordMapper serviceItemRecordMapper;

    @Override
    public void createServiceRecord(ServiceItemRecord record) {
        doCreateServiceItem(record);
        applyBO.createApply(buildApply(record));
        customerTraceBO.createCustomerTrace(buildTrace(record));
    }

    @Override
    public void reward(Long serviceItemRecordId) {
        ServiceItemRecord sales = getServiceItem(serviceItemRecordId);
        if (null == sales){
            throw new RuntimeException("增加业绩完成积分失败，业绩不存在");
        }
        pointBO.increasePoint(buildIncreasePointDO(sales), this::doReward);
    }

    private CustomerTrace buildTrace(ServiceItemRecord record) {
        CustomerInfo customer = customerBO.getCustomerByPhone(record.getCustomerPhone());
        return customerTraceBO.buildCustomerTrace(customer.getId(), record.getCompleteTime(),
                "SERVICE_ITEM", record.getId(), record.getShopNo(), record.getEmpNo());
    }

    private ServiceItemRecord getServiceItem(Long serviceItemRecordId) {
        return serviceItemRecordMapper.selectByPrimaryKey(serviceItemRecordId);
    }

    private IncreasePointDO buildIncreasePointDO(ServiceItemRecord itemRecord) {
        return pointBO.buildIncreasePointDO("SERVICE_ITEM", itemRecord.getId(),
                itemRecord.getEmpNo(), itemRecord.getCreator(), itemRecord.getRewardPoint(), itemRecord.getShopNo());

    }

    private Boolean doReward(Long salesId) {
        ServiceItemRecord itemRecord = new ServiceItemRecord();
        itemRecord.setId(salesId);
        itemRecord.setGmtModify(new Date());
        itemRecord.setRewardTime(new Date());
        itemRecord.setIsRewarded(true);
        serviceItemRecordMapper.updateByPrimaryKeySelective(itemRecord);
        return true;
    }

    private void doCreateServiceItem(ServiceItemRecord record) {
        record.setIsValid(true);
        record.setIsRewarded(false);
        record.setGmtModify(new Date());
        record.setGmtCreate(new Date());
        serviceItemRecordMapper.insertSelective(record);
    }

    private Apply buildApply(ServiceItemRecord record) {
        return applyBO.buildApply("SERVICE_ITEM", record.getId(), "项目内容申请",
                record.getEmpNo(), record.getCreator(), record.getShopNo());
    }

    private String buildApplyContent(ServiceItemRecord record) {
//        Task task = taskBO.getTask(record.getTaskNo());
        StringBuilder builder = new StringBuilder();
//        builder.append(String.format("任务名称：%s \n任务内容：%s \n申请奖励：%d 积分", task.getTitle(), task.getContent(), record.getRewardPoint()));
        return builder.toString();
    }
}
