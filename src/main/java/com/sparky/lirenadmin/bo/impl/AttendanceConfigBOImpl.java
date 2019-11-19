package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.AttendanceConfigBO;
import com.sparky.lirenadmin.entity.AttendanceConfig;
import com.sparky.lirenadmin.entity.AttendanceConfigExample;
import com.sparky.lirenadmin.mapper.AttendanceConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
}
