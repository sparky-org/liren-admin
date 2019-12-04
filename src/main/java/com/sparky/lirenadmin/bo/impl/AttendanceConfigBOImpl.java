package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.AttendanceConfigBO;
import com.sparky.lirenadmin.entity.AttendanceConfig;
import com.sparky.lirenadmin.entity.AttendanceConfigExample;
import com.sparky.lirenadmin.mapper.AttendanceConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class AttendanceConfigBOImpl implements AttendanceConfigBO {

    @Autowired
    private AttendanceConfigMapper attendanceConfigMapper;

    @Override
    public AttendanceConfig getConfig(Long shopNo) {
        AttendanceConfigExample example = new AttendanceConfigExample();
        example.createCriteria().andIsValidEqualTo(true).andShopNoEqualTo(shopNo);
        List<AttendanceConfig> list = attendanceConfigMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)){
            return null;
        }
        return list.iterator().next();
    }

    @Override
    public void createOrModifyConfig(AttendanceConfig config) {
        AttendanceConfig exist = getConfig(config.getShopNo());
        if (exist == null){
            doCreate(config);
        }else {
            config.setId(exist.getId());
            doModifyConfig(config);
        }
    }

    private void doModifyConfig(AttendanceConfig config) {
        config.setGmtModify(new Date());
        attendanceConfigMapper.insertSelective(config);
    }

    private void doCreate(AttendanceConfig config) {
        config.setIsValid(true);
        config.setGmtCreate(new Date());
        config.setGmtModify(new Date());
        attendanceConfigMapper.insertSelective(config);
    }
}
