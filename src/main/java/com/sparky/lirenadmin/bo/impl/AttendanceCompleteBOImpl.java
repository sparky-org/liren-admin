package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.AttendanceCompleteBO;
import com.sparky.lirenadmin.bo.PointBO;
import com.sparky.lirenadmin.bo.cond.IncreasePointDO;
import com.sparky.lirenadmin.constant.RewardTypeEnum;
import com.sparky.lirenadmin.entity.AttendanceComplete;
import com.sparky.lirenadmin.mapper.AttendanceCompleteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AttendanceCompleteBOImpl implements AttendanceCompleteBO {

    @Autowired
    private AttendanceCompleteMapper attendanceMapper;
    @Autowired
    private PointBO pointBO;

    @Override
    public void createAttendanceComplete(AttendanceComplete attendance) {
        doCreateAttendance(attendance);
        reward(attendance);
    }

    private IncreasePointDO buildIncreasePointDO(AttendanceComplete attendanceComplete) {
        return pointBO.buildIncreasePointDO(RewardTypeEnum.ATTENDANCE.getCode(), attendanceComplete.getId(),
                attendanceComplete.getEmpNo(), attendanceComplete.getCreator(), attendanceComplete.getRewardPoint(), attendanceComplete.getShopNo());

    }

    private Boolean doReward(Long salesId) {
        AttendanceComplete attendanceComplete = new AttendanceComplete();
        attendanceComplete.setId(salesId);
        attendanceComplete.setIsRewarded(true);
        attendanceComplete.setRewardTime(new Date());
        attendanceComplete.setGmtModify(new Date());
        attendanceMapper.updateByPrimaryKeySelective(attendanceComplete);
        return true;
    }

    private void reward(AttendanceComplete attendance) {
        pointBO.increasePoint(buildIncreasePointDO(attendance), this::doReward);
    }

    private void doCreateAttendance(AttendanceComplete attendance) {
        attendance.setIsValid(true);
        attendance.setGmtModify(new Date());
        attendance.setIsRewarded(false);
        attendance.setGmtCreate(new Date());
        attendanceMapper.insertSelective(attendance);
    }
    //考勤正常直接奖励，不再走审批流
//    private Apply buildApply(AttendanceComplete attendance) {
//        return applyBO.buildApply("ATTENDANCE", attendance.getId(),
//                "考勤正常申请奖励", attendance.getEmpNo(), attendance.getCreator(),
//                attendance.getShopNo());
//    }
//
}
