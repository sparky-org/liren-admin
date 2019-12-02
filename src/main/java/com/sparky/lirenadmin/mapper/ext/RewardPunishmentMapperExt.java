package com.sparky.lirenadmin.mapper.ext;

import com.sparky.lirenadmin.entity.RewardPunishment;
import com.sparky.lirenadmin.mapper.RewardPunishmentMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardPunishmentMapperExt extends RewardPunishmentMapper {

    int countRewardPunishment(Long empNo, Long shopNo, Boolean isAdmin, Long contentLike);

    List<RewardPunishment> queryRewardPunishment(Long empNo, Long shopNo, Boolean isAdmin, Long contentLike, int start, Integer pageSize);
}