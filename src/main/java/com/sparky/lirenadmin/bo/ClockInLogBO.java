package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.entity.AttendanceConfig;
import com.sparky.lirenadmin.entity.ClockInLog;
import com.sparky.lirenadmin.entity.ShopEmployee;

import java.util.Date;
import java.util.List;

public interface ClockInLogBO {
    void createClockInfoLog(ClockInLog clockInLog);

    List<ClockInLog> getAllClockInfoLogs(Long shopNo, Date date);

    void afterApproved(Long originNo);

    void executeAttendance(ShopEmployee emp, List<ClockInLog> att, AttendanceConfig config, Date date);

    List<ClockInLog> getMyLog(Long empNo, Date today);
}
