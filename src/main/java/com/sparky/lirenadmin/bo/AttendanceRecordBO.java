package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.entity.AttendanceRecord;
import com.sparky.lirenadmin.entity.po.AttendanceStatisticsPO;

import java.util.Date;
import java.util.List;

public interface AttendanceRecordBO {

    void createAttendanceRecord(AttendanceRecord record);

    AttendanceStatisticsPO getStatistics(Long empNo, Date month);

    List<AttendanceRecord> getExceptRecord(Long empNo, Date month);
}
