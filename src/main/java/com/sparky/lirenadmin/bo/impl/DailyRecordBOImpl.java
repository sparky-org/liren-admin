package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.DailyRecordBO;
import com.sparky.lirenadmin.bo.PointBO;
import com.sparky.lirenadmin.bo.cond.IncreasePointDO;
import com.sparky.lirenadmin.entity.DailyRecord;
import com.sparky.lirenadmin.mapper.DailyRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DailyRecordBOImpl implements DailyRecordBO {

    @Autowired
    private PointBO pointBO;

    @Autowired
    private DailyRecordMapper dailyRecordMapper;

    @Override
    public void createDailyRecord(DailyRecord dailyRecord) {
        doCreateDailyRecord(dailyRecord);
        reward(dailyRecord);
    }

    private IncreasePointDO buildIncreasePointDO(DailyRecord record) {
        //TODO 缺少积分值
        return pointBO.buildIncreasePointDO("DAILY_RECORD", record.getId(),
                record.getCreator(), record.getCreator(), 10, record.getShopNo());

    }

    private Boolean doReward(Long salesId) {
        DailyRecord dailyRecord = new DailyRecord();
        dailyRecord.setRewardTime(new Date());
        dailyRecord.setId(salesId);
        dailyRecord.setGmtModify(new Date());
        dailyRecord.setIsRewarded(true);
        dailyRecordMapper.updateByPrimaryKeySelective(dailyRecord);
        return true;
    }

    private void reward(DailyRecord record) {
        pointBO.increasePoint(buildIncreasePointDO(record), this::doReward);
    }

    private void doCreateDailyRecord(DailyRecord dailyRecord) {
        dailyRecord.setIsValid(true);
        dailyRecord.setGmtCreate(new Date());
        dailyRecord.setIsRewarded(false);
        dailyRecord.setGmtModify(new Date());
        dailyRecordMapper.insertSelective(dailyRecord);
    }
}
