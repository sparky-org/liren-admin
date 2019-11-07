package com.sparky.lirenadmin.mapper;

import com.sparky.lirenadmin.entity.AttendanceComplete;
import com.sparky.lirenadmin.entity.AttendanceCompleteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AttendanceCompleteMapper {
    long countByExample(AttendanceCompleteExample example);

    int deleteByExample(AttendanceCompleteExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AttendanceComplete record);

    int insertSelective(AttendanceComplete record);

    List<AttendanceComplete> selectByExample(AttendanceCompleteExample example);

    AttendanceComplete selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AttendanceComplete record, @Param("example") AttendanceCompleteExample example);

    int updateByExample(@Param("record") AttendanceComplete record, @Param("example") AttendanceCompleteExample example);

    int updateByPrimaryKeySelective(AttendanceComplete record);

    int updateByPrimaryKey(AttendanceComplete record);
}