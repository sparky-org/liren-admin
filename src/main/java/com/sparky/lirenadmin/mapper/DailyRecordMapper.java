package com.sparky.lirenadmin.mapper;

import com.sparky.lirenadmin.entity.DailyRecord;
import com.sparky.lirenadmin.entity.DailyRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyRecordMapper {
    long countByExample(DailyRecordExample example);

    int deleteByExample(DailyRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DailyRecord record);

    int insertSelective(DailyRecord record);

    List<DailyRecord> selectByExample(DailyRecordExample example);

    DailyRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DailyRecord record, @Param("example") DailyRecordExample example);

    int updateByExample(@Param("record") DailyRecord record, @Param("example") DailyRecordExample example);

    int updateByPrimaryKeySelective(DailyRecord record);

    int updateByPrimaryKey(DailyRecord record);
}