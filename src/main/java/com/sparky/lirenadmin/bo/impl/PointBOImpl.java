package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.PointBO;
import com.sparky.lirenadmin.bo.RewardRecordBO;
import com.sparky.lirenadmin.bo.cond.IncreasePointDO;
import com.sparky.lirenadmin.entity.Point;
import com.sparky.lirenadmin.entity.PointExample;
import com.sparky.lirenadmin.entity.RewardRecord;
import com.sparky.lirenadmin.mapper.PointMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Service
public class PointBOImpl implements PointBO {

    @Autowired
    private RewardRecordBO rewardRecordBO;

    @Autowired
    private PointMapper pointMapper;

    @Override
    public void increasePoint(IncreasePointDO increasePointDO, Function<Long, Boolean> doRewardTask) {
        Point point = buildPoint(increasePointDO);
        Point existPoint = getPointByEmp(increasePointDO.getEmpNo());
        if (existPoint != null){
            point.setId(existPoint.getId());
            point.setPoint(point.getPoint() + existPoint.getPoint());
            doModify(point);
        }else{
            doCreatePoint(point);
        }
        RewardRecord rewardRecord = buildRewardRecord(increasePointDO);
        rewardRecordBO.createReward(rewardRecord);
        doRewardTask.apply(increasePointDO.getOriginNo());
    }

    @Override
    public Point findEmployeePoint(Long id) {
        return getPointByEmp(id);
    }

    @Override
    public IncreasePointDO buildIncreasePointDO(String origin, Long originId, Long empNo, Long creator, Integer point, Long shopNo) {
        IncreasePointDO pointDO = new IncreasePointDO();
        pointDO.setEmpNo(empNo);
        pointDO.setOperator(creator);
        pointDO.setOrigin(origin);
        pointDO.setOriginNo(originId);
        pointDO.setPoint(point);
        pointDO.setShopNo(shopNo);
        return pointDO;
    }

    @Override
    public Point getPoint(Long pointNo) {
        return pointMapper.selectByPrimaryKey(pointNo);
    }

    private RewardRecord buildRewardRecord(IncreasePointDO increasePointDO) {
        RewardRecord reward = new RewardRecord();
        reward.setCreator(increasePointDO.getOperator());
        reward.setEmpNo(increasePointDO.getEmpNo());
        reward.setOrigin(increasePointDO.getOrigin());
        reward.setOriginNo(increasePointDO.getOriginNo());
        reward.setPoint(increasePointDO.getPoint());
        reward.setRewardTime(new Date());
        reward.setShopNo(increasePointDO.getShopNo());
        return reward;
    }

    private void doCreatePoint(Point point) {
        point.setIsValid(true);
        point.setGmtCreate(new Date());
        point.setGmtModify(new Date());
        pointMapper.insertSelective(point);
    }

    private Point buildPoint(IncreasePointDO increasePointDO) {
        Point point = new Point();
        point.setCreator(increasePointDO.getOperator());
        point.setEmpNo(increasePointDO.getEmpNo());
        point.setPoint(increasePointDO.getPoint());
        point.setShopNo(increasePointDO.getShopNo());
        return point;
    }

    private void doModify(Point point) {
        if (point.getId() == null){
            throw new RuntimeException("更新失败，原纪录不存在");
        }
        point.setGmtModify(new Date());
        pointMapper.updateByPrimaryKeySelective(point);
    }

    private Point getPointByEmp(Long empNo) {
        PointExample example = new PointExample();
        example.createCriteria().andEmpNoEqualTo(empNo).andIsValidEqualTo(true);
        List<Point> pointList = pointMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(pointList)){
            return null;
        }
        if (pointList.size() > 1){
            throw new RuntimeException(String.format("数据异常，员工[%s]存在多笔积分记录，请联系管理员。", empNo));
        }
        return pointList.iterator().next();
    }
}
