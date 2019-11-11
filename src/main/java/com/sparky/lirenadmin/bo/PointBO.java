package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.bo.cond.IncreasePointDO;
import com.sparky.lirenadmin.entity.Point;

import java.util.function.Function;

public interface PointBO {
    void increasePoint(IncreasePointDO increasePointDO, Function<Long, Boolean> doRewardTask);

    Point findEmployeePoint(Long id);

    IncreasePointDO buildIncreasePointDO(String origin, Long originId, Long empNo, Long creator, Integer point, Long shopNo);

    Point getPoint(Long pointNo);
}
