package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.PointBO;
import com.sparky.lirenadmin.bo.RewardPunishmentBO;
import com.sparky.lirenadmin.bo.cond.IncreasePointDO;
import com.sparky.lirenadmin.constant.RewardTypeEnum;
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
        return pointBO.buildIncreasePointDO(RewardTypeEnum.REWARD_PUNISH.getCode(), record.getId(),
                record.getCreator(), record.getCreator(), record.getPoint(), record.getShopNo());

    }

    private Boolean doReward(Long salesId) {
        RewardPunishment dailyRecord = new RewardPunishment();
        dailyRecord.setId(salesId);
        dailyRecord.setRewardTime(new Date());
        dailyRecord.setGmtModify(new Date());
        dailyRecord.setIsRewarded(true);
        rewardPunishmentMapper.updateByPrimaryKeySelective(dailyRecord);
        return true;
    }

    private void reward(RewardPunishment record) {
        pointBO.increasePoint(buildIncreasePointDO(record), this::doReward);
    }

    private void doCreateRewardPunishment(RewardPunishment rp) {
        rp.setIsValid(true);
        rp.setIsRewarded(false);
        rp.setGmtCreate(new Date());
        rp.setGmtModify(new Date());
        rewardPunishmentMapper.insertSelective(rp);
    }
}
