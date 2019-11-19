package com.sparky.lirenadmin.mapper;

import com.sparky.lirenadmin.entity.AttendanceRecord;
import com.sparky.lirenadmin.entity.AttendanceRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

public interface AttendanceRecordMapper {
    long countByExample(AttendanceRecordExample example);

    int deleteByExample(AttendanceRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AttendanceRecord record);

    int insertSelective(AttendanceRecord record);

    List<AttendanceRecord> selectByExample(AttendanceRecordExample example);

    AttendanceRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AttendanceRecord record, @Param("example") AttendanceRecordExample example);

    int updateByExample(@Param("record") AttendanceRecord record, @Param("example") AttendanceRecordExample example);

    int updateByPrimaryKeySelective(AttendanceRecord record);

    int updateByPrimaryKey(AttendanceRecord record);
}