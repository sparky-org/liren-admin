package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.RewardRecordBO;
import com.sparky.lirenadmin.entity.RewardRecord;
import com.sparky.lirenadmin.entity.po.PointRankPO;
import com.sparky.lirenadmin.entity.po.PointTablePO;
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
    public List<PointRankPO> findPointRank(Long shopNo, Long empNo, Date date) {
        return rewardRecordMapper.findPointRank(shopNo, empNo, date);
    }

    @Override
    public int countRewardRecord(String shopNo) {
        return rewardRecordMapper.countRewardRecord(shopNo);
    }

    @Override
    public List<RewardRecord> pagingQueryRewardRecord(String shopNo, int start, Integer pageSize) {
        return rewardRecordMapper.pagingQueryRewardRecord(shopNo, start, pageSize);
    }

    @Override
    public Integer countPointTable(Long shopNo, String interval, String beginDate, String endDate) {
        return rewardRecordMapper.countPointTable(null, shopNo, interval, beginDate, endDate);
    }

    @Override
    public List<PointTablePO> getPointTable(Long shopNo, String interval, String beginDate, String endDate, Integer start, Integer pageSize) {
        return rewardRecordMapper.getPointTable(null, shopNo, interval, beginDate, endDate, start, pageSize);
    }

    @Override
    public Integer countPointDetail(Long empNo, Long shopNo, String interval, String beginDate, String endDate) {
        return rewardRecordMapper.countPointDetail(empNo, shopNo, interval, beginDate, endDate);
    }

    @Override
    public List<RewardRecord> queryPointDetail(Long empNo, Long shopNo, String interval, String beginDate, String endDate, Integer start, Integer pageSize) {
        return rewardRecordMapper.queryPointDetail(empNo, shopNo, interval, beginDate, endDate, start, pageSize);
    }
}
