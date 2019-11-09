package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.entity.RewardRecord;
import com.sparky.lirenadmin.entity.po.PointRankPO;

import java.util.Date;
import java.util.List;

public interface RewardRecordBO {
    void createReward(RewardRecord rewardRecord);

    List<PointRankPO> findPointRank(Long empNo, Date date);
}
