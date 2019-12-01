package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.AttendanceConfigBO;
import com.sparky.lirenadmin.bo.ShopConfigBO;
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
    private ShopConfigBO shopConfigBO;
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
    public void createConfig(AttendanceConfig buildAttendanceConfig) {
        buildAttendanceConfig.setIsValid(true);
        buildAttendanceConfig.setGmtCreate(new Date());
        buildAttendanceConfig.setGmtModify(new Date());
        attendanceConfigMapper.insertSelective(buildAttendanceConfig);
    }
}
