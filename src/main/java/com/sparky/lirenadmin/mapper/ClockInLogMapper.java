package com.sparky.lirenadmin.mapper;

import com.sparky.lirenadmin.entity.ClockInLog;
import com.sparky.lirenadmin.entity.ClockInLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClockInLogMapper {
    long countByExample(ClockInLogExample example);

    int deleteByExample(ClockInLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ClockInLog record);

    int insertSelective(ClockInLog record);

    List<ClockInLog> selectByExample(ClockInLogExample example);

    ClockInLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ClockInLog record, @Param("example") ClockInLogExample example);

    int updateByExample(@Param("record") ClockInLog record, @Param("example") ClockInLogExample example);

    int updateByPrimaryKeySelective(ClockInLog record);

    int updateByPrimaryKey(ClockInLog record);
}