package com.sparky.lirenadmin.mapper;

import com.sparky.lirenadmin.entity.RewardPunishment;
import com.sparky.lirenadmin.entity.RewardPunishmentExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RewardPunishmentMapper {
    long countByExample(RewardPunishmentExample example);

    int deleteByExample(RewardPunishmentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RewardPunishment record);

    int insertSelective(RewardPunishment record);

    List<RewardPunishment> selectByExample(RewardPunishmentExample example);

    RewardPunishment selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RewardPunishment record, @Param("example") RewardPunishmentExample example);

    int updateByExample(@Param("record") RewardPunishment record, @Param("example") RewardPunishmentExample example);

    int updateByPrimaryKeySelective(RewardPunishment record);

    int updateByPrimaryKey(RewardPunishment record);
}