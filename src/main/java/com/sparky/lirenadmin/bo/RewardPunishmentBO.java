package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.entity.RewardPunishment;

import java.util.List;

public interface RewardPunishmentBO {

    void createRewardPunishment(RewardPunishment rp);

    int countRewardPunishment(Long empNo, Long contentLike);

    List<RewardPunishment> queryRewardPunishment(Long empNo, Long contentLike, int start, Integer pageSize);
}
