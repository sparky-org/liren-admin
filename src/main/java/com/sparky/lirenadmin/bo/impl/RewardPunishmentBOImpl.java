package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.PointBO;
import com.sparky.lirenadmin.bo.RewardPunishmentBO;
import com.sparky.lirenadmin.bo.cond.IncreasePointDO;
import com.sparky.lirenadmin.entity.RewardPunishment;
import com.sparky.lirenadmin.mapper.RewardPunishmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RewardPunishmentBOImpl implements RewardPunishmentBO {

    @Autowired
    private RewardPunishmentMapper rewardPunishmentMapper;
    @Autowired
    private PointBO pointBO;

    @Override
    public void createRewardPunishment(RewardPunishment rp) {
        doCreateRewardPunishment(rp);
        reward(rp);
    }

    private IncreasePointDO buildIncreasePointDO(RewardPunishment record) {
        //TODO 缺少积分值
        return pointBO.buildIncreasePointDO("REWARD_PUNISH", record.getId(),
                record.getCreator(), record.getCreator(), 10, record.getShopNo());

    }

    private Boolean doReward(Long salesId) {
        RewardPunishment dailyRecord = new RewardPunishment();
        dailyRecord.setId(salesId);
        dailyRecord.setGmtModify(new Date());
        //TODO 增加是否已发放积分，发放时间两个字段
        rewardPunishmentMapper.updateByPrimaryKeySelective(dailyRecord);
        return true;
    }

    private void reward(RewardPunishment record) {
        pointBO.increasePoint(buildIncreasePointDO(record), this::doReward);
    }

    private void doCreateRewardPunishment(RewardPunishment rp) {
        rp.setIsValid(true);
        rp.setGmtCreate(new Date());
        rp.setGmtModify(new Date());
        rewardPunishmentMapper.insertSelective(rp);
    }
}
