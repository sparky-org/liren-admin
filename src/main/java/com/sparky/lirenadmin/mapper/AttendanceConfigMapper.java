package com.sparky.lirenadmin.mapper;

import com.sparky.lirenadmin.entity.AttendanceConfig;
import com.sparky.lirenadmin.entity.AttendanceConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceConfigMapper {
    long countByExample(AttendanceConfigExample example);

    int deleteByExample(AttendanceConfigExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AttendanceConfig record);

    int insertSelective(AttendanceConfig record);

    List<AttendanceConfig> selectByExample(AttendanceConfigExample example);

    AttendanceConfig selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AttendanceConfig record, @Param("example") AttendanceConfigExample example);

    int updateByExample(@Param("record") AttendanceConfig record, @Param("example") AttendanceConfigExample example);

    int updateByPrimaryKeySelective(AttendanceConfig record);

    int updateByPrimaryKey(AttendanceConfig record);
}