package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.RewardRecordBO;
import com.sparky.lirenadmin.entity.RewardRecord;
import com.sparky.lirenadmin.entity.po.PointRankPO;
import com.sparky.lirenadmin.mapper.ext.RewardRecordMapperExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RewardRecordBOImpl implements RewardRecordBO {

    @Autowired
    private RewardRecordMapperExt rewardRecordMapper;

    @Override
    public void createReward(RewardRecord rewardRecord) {
        rewardRecord.setIsValid(true);
        rewardRecord.setGmtCreate(new Date());
        rewardRecord.setGmtModify(new Date());
        rewardRecordMapper.insertSelective(rewardRecord);
    }

    @Override
    public List<PointRankPO> findPointRank(Long empNo, Date date) {
        return rewardRecordMapper.findPointRank(empNo, date);
    }
}
