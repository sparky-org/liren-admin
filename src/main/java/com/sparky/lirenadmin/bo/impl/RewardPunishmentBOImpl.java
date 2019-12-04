package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.PointBO;
import com.sparky.lirenadmin.bo.RewardPunishmentBO;
import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.bo.cond.IncreasePointDO;
import com.sparky.lirenadmin.constant.RewardTypeEnum;
import com.sparky.lirenadmin.entity.RewardPunishment;
import com.sparky.lirenadmin.entity.ShopEmployee;
import com.sparky.lirenadmin.mapper.ext.RewardPunishmentMapperExt;
import io.swagger.annotations.AuthorizationScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RewardPunishmentBOImpl implements RewardPunishmentBO {

    @Autowired
    private RewardPunishmentMapperExt rewardPunishmentMapper;
    @Autowired
    private PointBO pointBO;
    @Autowired
    private ShopEmployeeBO shopEmployeeBO;

    @Override
    public void createRewardPunishment(RewardPunishment rp) {
        doCreateRewardPunishment(rp);
        reward(rp);
    }

    @Override
    public int countRewardPunishment(Long empNo, Long contentLike) {
        ShopEmployee employee =  getEmployee(empNo);
        Long shopNo = employee.getShopNo();
        Boolean isAdmin = employee.getIsAdmin();
        return rewardPunishmentMapper.countRewardPunishment(empNo, shopNo, isAdmin, contentLike);
    }

    @Override
    public List<RewardPunishment> queryRewardPunishment(Long empNo, Long contentLike, int start, Integer pageSize) {
        ShopEmployee employee = getEmployee(empNo);
        Long shopNo = employee.getShopNo();
        Boolean isAdmin = employee.getIsAdmin();
        return rewardPunishmentMapper.queryRewardPunishment(empNo,shopNo, isAdmin, contentLike, start, pageSize);
    }

    private ShopEmployee getEmployee(Long empNo){
        ShopEmployee employee = shopEmployeeBO.getEmployee(empNo);
        if(null == employee){
            throw new RuntimeException("查询人不存在");
        }
        return employee;
    }

    private IncreasePointDO buildIncreasePointDO(RewardPunishment record) {
        return pointBO.buildIncreasePointDO(RewardTypeEnum.REWARD_PUNISH.getCode(), record.getId(),
                record.getEmpNo(), record.getCreator(), record.getPoint(), record.getShopNo());

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
