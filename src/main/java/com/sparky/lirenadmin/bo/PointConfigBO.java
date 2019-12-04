package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.entity.PointConfig;

import java.util.List;

public interface PointConfigBO {
    void createPointConfig(PointConfig buildPoint);

    void modifyPointConfig(PointConfig config);

    List<PointConfig> getPointConfig(Long shopNo);

    PointConfig getPointConfigByPrimaryKey(Long pointNo);

    /**
     * 获取积分配置
     * @param shopNo
     * @param pointConfigType
     * @return
     */
    PointConfig getPointConfig(Long shopNo, String pointConfigType);

    /**
     * 获取指定配置的积分
     * @param shopNo
     * @param pointConfigType
     * @return
     */
    Integer getPointReward(Long shopNo, String pointConfigType);
}
