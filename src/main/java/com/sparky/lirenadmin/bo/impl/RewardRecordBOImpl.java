package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.RewardRecordBO;
import com.sparky.lirenadmin.entity.RewardRecord;
import com.sparky.lirenadmin.mapper.RewardRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RewardRecordBOImpl implements RewardRecordBO {

    @Autowired
    private RewardRecordMapper rewardRecordMapper;

    @Override
    public void createReward(RewardRecord rewardRecord) {
        rewardRecord.setIsValid(true);
        rewardRecord.setGmtCreate(new Date());
        rewardRecord.setGmtModify(new Date());
        rewardRecordMapper.insertSelective(rewardRecord);
    }
}
