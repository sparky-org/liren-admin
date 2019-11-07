package com.sparky.lirenadmin.mapper;

import com.sparky.lirenadmin.entity.TaskDtl;
import com.sparky.lirenadmin.entity.TaskDtlExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TaskDtlMapper {
    long countByExample(TaskDtlExample example);

    int deleteByExample(TaskDtlExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TaskDtl record);

    int insertSelective(TaskDtl record);

    List<TaskDtl> selectByExample(TaskDtlExample example);

    TaskDtl selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TaskDtl record, @Param("example") TaskDtlExample example);

    int updateByExample(@Param("record") TaskDtl record, @Param("example") TaskDtlExample example);

    int updateByPrimaryKeySelective(TaskDtl record);

    int updateByPrimaryKey(TaskDtl record);
}