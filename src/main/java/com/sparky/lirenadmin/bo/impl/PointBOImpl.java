package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.PointBO;
import com.sparky.lirenadmin.bo.cond.IncreasePointDO;
import com.sparky.lirenadmin.mapper.PointMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PointBOImpl implements PointBO {

    @Autowired
    private PointMapper pointMapper;

    @Override
    public void increasePoint(IncreasePointDO increasePointDO, Function<Long, Boolean> doRewardTask) {
        
    }
}
