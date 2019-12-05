package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.entity.RewardRecord;
import com.sparky.lirenadmin.entity.po.PointRankPO;
import com.sparky.lirenadmin.entity.po.PointTablePO;

import java.util.Date;
import java.util.List;

public interface RewardRecordBO {
    void createReward(RewardRecord rewardRecord);

    List<PointRankPO> findPointRank(Long shopNo, Long empNo, Date date);

    int countRewardRecord(String shopNo);

    List<RewardRecord> pagingQueryRewardRecord(String shopNo, int start, Integer pageSize);

    Integer countPointTable(Long shopNo, String interval, String beginDate, String endDate);

    List<PointTablePO> getPointTable(Long shopNo, String interval, String beginDate, String endDate, Integer start, Integer pageSize);

    Integer countPointDetail(Long empNo, Long shopNo, String interval, String beginDate, String endDate);

    List<RewardRecord> queryPointDetail(Long empNo, Long shopNo, String interval, String beginDate, String endDate, Integer start, Integer pageSize);
}
