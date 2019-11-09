package com.sparky.lirenadmin.mapper;

import com.sparky.lirenadmin.entity.RewardRecord;
import com.sparky.lirenadmin.entity.RewardRecordExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RewardRecordMapper {
    long countByExample(RewardRecordExample example);

    int deleteByExample(RewardRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RewardRecord record);

    int insertSelective(RewardRecord record);

    List<RewardRecord> selectByExample(RewardRecordExample example);

    RewardRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RewardRecord record, @Param("example") RewardRecordExample example);

    int updateByExample(@Param("record") RewardRecord record, @Param("example") RewardRecordExample example);

    int updateByPrimaryKeySelective(RewardRecord record);

    int updateByPrimaryKey(RewardRecord record);
}