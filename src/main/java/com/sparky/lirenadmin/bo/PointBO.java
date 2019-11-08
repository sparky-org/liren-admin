package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.bo.cond.IncreasePointDO;

import java.util.function.Function;

public interface PointBO {
    void increasePoint(IncreasePointDO increasePointDO, Function<Long, Boolean> doRewardTask);
}
