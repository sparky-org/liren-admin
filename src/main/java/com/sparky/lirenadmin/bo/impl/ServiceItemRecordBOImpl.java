package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.*;
import com.sparky.lirenadmin.bo.cond.IncreasePointDO;
import com.sparky.lirenadmin.constant.ApplyTypeEnum;
import com.sparky.lirenadmin.constant.RewardTypeEnum;
import com.sparky.lirenadmin.entity.*;
import com.sparky.lirenadmin.mapper.ext.ServiceItemRecordMapperExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
    private ServiceItemRecordMapperExt serviceItemRecordMapper;

    @Override
    public void createServiceRecord(ServiceItemRecord record, List<ApplyDtl> applyDtlList, Long auditor) {
        doCreateServiceItem(record);
        applyBO.createApply(buildApply(record, auditor), applyDtlList);
        customerTraceBO.createCustomerTrace(buildTrace(record));
    }

    @Override
    public void reward(Long serviceItemRecordId) {
        ServiceItemRecord itemRecord = getServiceItem(serviceItemRecordId);
        if (null == itemRecord){
            throw new RuntimeException("增加项目完成积分失败，项目完成记录不存在");
        }
        pointBO.increasePoint(buildIncreasePointDO(itemRecord), this::doReward);
    }

    @Override
    public Integer sumServiceItemRecordNum(ShopEmployee employee, Date today) {
        int total;
        if (employee.getIsAdmin()){
            total = serviceItemRecordMapper.sumServiceItemRecordNumByShop(employee.getShopNo(), today);
        }else{
            total = serviceItemRecordMapper.sumServiceItemRecordNumByEmp(employee.getId(), today);
        }
        return total;
    }

    @Override
    public ServiceItemRecord getServiceItemRecord(Long id) {
        return getServiceItem(id);
    }

    private CustomerTrace buildTrace(ServiceItemRecord record) {
        CustomerInfo customer = customerBO.getCustomerByPhone(record.getCustomerPhone());
        return customerTraceBO.buildCustomerTrace(customer.getId(), record.getCompleteTime(),
                RewardTypeEnum.REWARD_PUNISH.getCode(), record.getId(), record.getShopNo(), record.getEmpNo());
    }

    private ServiceItemRecord getServiceItem(Long serviceItemRecordId) {
        return serviceItemRecordMapper.selectByPrimaryKey(serviceItemRecordId);
    }

    private IncreasePointDO buildIncreasePointDO(ServiceItemRecord itemRecord) {
        return pointBO.buildIncreasePointDO(RewardTypeEnum.REWARD_PUNISH.getCode(), itemRecord.getId(),
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

    private Apply buildApply(ServiceItemRecord record, Long auditor) {
        return applyBO.buildApply(ApplyTypeEnum.SERVICE_ITEM.getCode(), record.getId(), buildApplyContent(record),
                record.getEmpNo(), auditor, record.getCreator(), record.getShopNo());
    }

    private String buildApplyContent(ServiceItemRecord record) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("任务名称：%s <br>任务内容：%s <br>申请奖励：%d 积分", record.getTitle(), record.getContent(), record.getRewardPoint()));
        return builder.toString();
    }
}
