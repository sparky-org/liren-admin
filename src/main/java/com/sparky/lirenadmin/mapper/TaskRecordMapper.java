package com.sparky.lirenadmin.mapper;

import com.sparky.lirenadmin.entity.TaskRecord;
import com.sparky.lirenadmin.entity.TaskRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TaskRecordMapper {
    long countByExample(TaskRecordExample example);

    int deleteByExample(TaskRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TaskRecord record);

    int insertSelective(TaskRecord record);

    List<TaskRecord> selectByExample(TaskRecordExample example);

    TaskRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TaskRecord record, @Param("example") TaskRecordExample example);

    int updateByExample(@Param("record") TaskRecord record, @Param("example") TaskRecordExample example);

    int updateByPrimaryKeySelective(TaskRecord record);

    int updateByPrimaryKey(TaskRecord record);
}