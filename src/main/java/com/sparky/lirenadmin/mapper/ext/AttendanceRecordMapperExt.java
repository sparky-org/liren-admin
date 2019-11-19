package com.sparky.lirenadmin.mapper.ext;

import com.sparky.lirenadmin.entity.po.AttendanceStatisticsPO;
import com.sparky.lirenadmin.mapper.AttendanceRecordMapper;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface AttendanceRecordMapperExt extends AttendanceRecordMapper {

    AttendanceStatisticsPO getStatistics(Long empNo, Date begin, Date end);
}