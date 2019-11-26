package com.sparky.lirenadmin.bo.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sparky.lirenadmin.bo.DailyRecordBO;
import com.sparky.lirenadmin.bo.PointBO;
import com.sparky.lirenadmin.bo.ShopConfigBO;
import com.sparky.lirenadmin.bo.cond.IncreasePointDO;
import com.sparky.lirenadmin.constant.AutoRewardConfigEnum;
import com.sparky.lirenadmin.constant.RewardTypeEnum;
import com.sparky.lirenadmin.constant.ShopConfigTypeEnum;
import com.sparky.lirenadmin.entity.DailyRecord;
import com.sparky.lirenadmin.entity.ShopConfig;
import com.sparky.lirenadmin.mapper.DailyRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;

@Service
public class DailyRecordBOImpl implements DailyRecordBO {

    @Autowired
    private PointBO pointBO;
    @Autowired
    private ShopConfigBO shopConfigBO;

    @Autowired
    private DailyRecordMapper dailyRecordMapper;

    @Override
    public void createDailyRecord(DailyRecord dailyRecord) {
        doCreateDailyRecord(dailyRecord);
        reward(dailyRecord);
    }

    private IncreasePointDO buildIncreasePointDO(DailyRecord record) {
        //积分值,若无配置设置为0
        ShopConfig config = shopConfigBO.getShopConfig(record.getShopNo(), ShopConfigTypeEnum.REWARD_CONFIG.getCode());
        Integer point = 0;
        if (config != null){
            JSONArray array = JSONArray.parseArray(config.getContent());
            Iterator it = array.iterator();
            while (it.hasNext()){
                JSONObject o = (JSONObject) it.next();
                Integer p = o.getInteger(AutoRewardConfigEnum.DAILY_RECORD.getCode());
                point = p;
            }
        }
        return pointBO.buildIncreasePointDO(RewardTypeEnum.DAILY_RECORD.getCode(), record.getId(),
                record.getCreator(), record.getCreator(), point, record.getShopNo());

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
