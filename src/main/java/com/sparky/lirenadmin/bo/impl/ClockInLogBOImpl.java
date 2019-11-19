package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.*;
import com.sparky.lirenadmin.constant.ApplyStatusEnum;
import com.sparky.lirenadmin.constant.ApplyTypeEnum;
import com.sparky.lirenadmin.constant.AttendanceStatus;
import com.sparky.lirenadmin.entity.*;
import com.sparky.lirenadmin.mapper.ClockInLogMapper;
import com.sparky.lirenadmin.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class ClockInLogBOImpl implements ClockInLogBO {

    @Autowired
    private ClockInLogMapper clockInLogMapper;
    @Autowired
    private ApplyBO applyBO;
    @Autowired
    private ShopEmployeeBO shopEmployeeBO;
    @Autowired
    private AttendanceRecordBO attendanceRecordBO;
    @Autowired
    private AttendanceConfigBO attendanceConfigBO;

    @Override
    public void createClockInfoLog(ClockInLog log) {
        doCreate(log);
        if (log.getIsOutside()){
            ShopEmployee employee = shopEmployeeBO.getEmployee(log.getEmpNo());
            String content = String.format("%s申请外勤打卡，打卡地点(%s,%s). 请经纬度信息复制到腾讯地图拾取器查看。", employee.getName(), log.getLongitude().toString(), log.getLatitude().toString());
            Apply apply = applyBO.buildApply(ApplyTypeEnum.OUTSIDE_SIGN.getCode(), log.getId(), content, log.getEmpNo(), log.getCreator(), log.getShopNo());
            applyBO.createApply(apply, null);
        }
    }

    @Override
    public List<ClockInLog> getAllClockInfoLogs(Long shopNo, Date date) {
        ClockInLogExample example = new ClockInLogExample();
        Date begin  = DateUtils.getDayBegining(date);
        Date end = DateUtils.getDayEnding(date);
        example.createCriteria().andIsValidEqualTo(true).andShopNoEqualTo(shopNo).andClockInDateBetween(begin, end);
        return clockInLogMapper.selectByExample(example);
    }

    @Override
    public void afterApproved(Long originNo) {
        Apply apply = applyBO.queryApplyByOrigin(ApplyTypeEnum.OUTSIDE_SIGN.getCode(), originNo);
        if (!ApplyStatusEnum.PASSED.getCode().equals(apply.getAuditStatus())){
            return;
        }
        ClockInLog log = clockInLogMapper.selectByPrimaryKey(originNo);
        ShopEmployee employee = shopEmployeeBO.getEmployee(log.getEmpNo());
        List<ClockInLog> clockInLogs = queryClockInLog(log.getShopNo(), log.getEmpNo(), log.getClockInDate());
        AttendanceConfig config = attendanceConfigBO.getConfig(log.getShopNo());
        executeAttendance(employee, clockInLogs, config, log.getClockInDate());
    }

    @Override
    public void executeAttendance(ShopEmployee emp, List<ClockInLog> att, AttendanceConfig config, Date date) {
        ClockInLog start = null;
        ClockInLog end = null;
        Date first = null;
        Date last = null;
        if (!CollectionUtils.isEmpty(att)){
            for (ClockInLog log : att){
                if (first.after(log.getClockInTime())){
                    //查找上班时间
                    first = log.getClockInTime();
                    start = log;
                }
                if (last.before(log.getClockInTime())){
                    //查找上班时间
                    last = log.getClockInTime();
                    end = log;
                }
            }
            if (first.compareTo(last) == 0){
                //前后相等，把第二个时间设置为空
                last = null;
                end = null;
            }
        }
        if (start == null || end == null){
            //旷工
            attendanceRecordBO.createAttendanceRecord(buildRecord(first, last, AttendanceStatus.ABSENTEEISM.getCode(), emp, date));
        }else{
            if (first.after(config.getStartWork())){
                attendanceRecordBO.createAttendanceRecord(buildRecord(first, last, AttendanceStatus.BE_LATE.getCode(), emp, date));
            }else if(last.before(config.getEndWork())){
                attendanceRecordBO.createAttendanceRecord(buildRecord(first, last, AttendanceStatus.LEAVE_EARLY.getCode(), emp, date));
            }
        }
    }

    @Override
    public List<ClockInLog> getMyLog(Long empNo, Date today) {
        ClockInLogExample example = new ClockInLogExample();
        Date begin  = DateUtils.getDayBegining(today);
        Date end = DateUtils.getDayEnding(today);
        example.createCriteria().andIsValidEqualTo(true).andEmpNoEqualTo(empNo).andClockInDateBetween(begin, end);
        return clockInLogMapper.selectByExample(example);
    }


    private AttendanceRecord buildRecord(Date first, Date last, String status, ShopEmployee emp, Date date) {
        AttendanceRecord record = new AttendanceRecord();
        record.setAttendanceDate(date);
        record.setStartWork(first);
        record.setEndWork(last);
        record.setEmpNo(emp.getId());
        record.setCreator(0l);
        record.setShopNo(emp.getShopNo());
        record.setStatus(status);
        return record;
    }

    private void doCreate(ClockInLog log){
        log.setGmtCreate(new Date());
        log.setGmtModify(new Date());
        log.setIsValid(true);
        clockInLogMapper.insertSelective(log);
    }

    private List<ClockInLog> queryClockInLog(Long shopNo, Long empNo, Date clockInDate) {
        ClockInLogExample example = new ClockInLogExample();
        Date begin = DateUtils.getDayBegining(clockInDate);
        Date end = DateUtils.getDayEnding(clockInDate);
        example.createCriteria().andIsValidEqualTo(true).andShopNoEqualTo(shopNo)
                .andEmpNoEqualTo(empNo).andClockInDateBetween(begin, end);
        return clockInLogMapper.selectByExample(example);
    }
}
