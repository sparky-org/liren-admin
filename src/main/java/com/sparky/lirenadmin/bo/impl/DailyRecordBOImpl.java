package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.DailyRecordBO;
import com.sparky.lirenadmin.bo.PointBO;
import com.sparky.lirenadmin.bo.PointConfigBO;
import com.sparky.lirenadmin.bo.cond.IncreasePointDO;
import com.sparky.lirenadmin.constant.PointTypeEnum;
import com.sparky.lirenadmin.constant.RewardTypeEnum;
import com.sparky.lirenadmin.entity.DailyRecord;
import com.sparky.lirenadmin.entity.DailyRecordExample;
import com.sparky.lirenadmin.mapper.ext.DailyRecordMapperExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DailyRecordBOImpl implements DailyRecordBO {

    @Autowired
    private PointBO pointBO;
    @Autowired
    private PointConfigBO pointConfigBO;

    @Autowired
    private DailyRecordMapperExt dailyRecordMapper;

    @Override
    public void createDailyRecord(DailyRecord dailyRecord) {
        doCreateDailyRecord(dailyRecord);
        reward(dailyRecord);
    }

    @Override
    public List<DailyRecord> queryDailyRecord(Long reporter, Long auditor) {
        DailyRecordExample example = new DailyRecordExample();
        DailyRecordExample.Criteria criteria = example.createCriteria();
        criteria.andIsValidEqualTo(true);

        return null;
    }

    @Override
    public Integer countDailyRecord(Long reporter, Long auditor) {
        return dailyRecordMapper.countDailyRecord(reporter, auditor);
    }

    @Override
    public List<DailyRecord> queryDailyRecord(Long empNo, Long auditor, Integer startIndex, Integer pageSize) {
        return dailyRecordMapper.queryDailyRecord(empNo, auditor, startIndex, pageSize);
    }

    private IncreasePointDO buildIncreasePointDO(DailyRecord record) {
        //积分值,若无配置设置为0
        Integer point = getRewardPoint(record.getShopNo());
        return pointBO.buildIncreasePointDO(RewardTypeEnum.DAILY_RECORD.getCode(), record.getId(),
                record.getCreator(), record.getCreator(), point, record.getShopNo());

    }

    private Integer getRewardPoint(Long shopNo){
        return pointConfigBO.getPointReward(shopNo, PointTypeEnum.DAILY_RECORD.getCode());
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
