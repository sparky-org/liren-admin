package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.PointConfigBO;
import com.sparky.lirenadmin.entity.PointConfig;
import com.sparky.lirenadmin.entity.PointConfigExample;
import com.sparky.lirenadmin.mapper.PointConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PointConfigBOImpl implements PointConfigBO {

    @Autowired
    private PointConfigMapper pointConfigMapper;

    @Override
    public void createPointConfig(PointConfig buildPoint) {
        buildPoint.setIsValid(true);
        buildPoint.setGmtCreate(new Date());
        buildPoint.setGmtModify(new Date());
        pointConfigMapper.insertSelective(buildPoint);
    }

    @Override
    public void modifyPointConfig(PointConfig config) {
        config.setGmtModify(new Date());
        pointConfigMapper.updateByPrimaryKeySelective(config);
    }

    @Override
    public List<PointConfig> getPointConfig(Long shopNo) {
        PointConfigExample example = new PointConfigExample();
        example.createCriteria().andIsValidEqualTo(true).andShopNoEqualTo(shopNo);
        return pointConfigMapper.selectByExample(example);
    }

    @Override
    public PointConfig getPointConfigByPrimaryKey(Long pointNo) {
        return pointConfigMapper.selectByPrimaryKey(pointNo);
    }
}
