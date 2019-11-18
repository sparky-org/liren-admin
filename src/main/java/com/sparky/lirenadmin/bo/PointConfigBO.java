package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.entity.PointConfig;

import java.util.List;

public interface PointConfigBO {
    void createPointConfig(PointConfig buildPoint);

    void modifyPointConfig(PointConfig config);

    List<PointConfig> getPointConfig(Long shopNo);
}
